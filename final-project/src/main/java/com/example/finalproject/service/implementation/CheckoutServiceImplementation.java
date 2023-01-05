package com.example.finalproject.service.implementation;

import com.example.finalproject.exception.*;
import com.example.finalproject.persistence.model.*;
import com.example.finalproject.persistence.repository.*;
import com.example.finalproject.service.CheckoutService;
import com.example.finalproject.service.mapper.CheckoutMapper;
import com.example.finalproject.service.mapper.CheckoutOrderMapper;
import com.example.finalproject.web.DTO.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CheckoutServiceImplementation implements CheckoutService {

    private final CheckoutRepository checkoutRepository;
    private final CheckoutProductRepository checkoutProductRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final TransactionRepository transactionRepository;


    /**
     * This method will return a CheckoutDTO with the information of the checkout related to the current user
     * @param email The email of the current authenticated user, used to get the user information
     * @return a CheckoutDTO with all the information of the checkout related to the user
     * @throws ResourceNotFoundException when there is no checkout related to the user, or
     * when there is no user found with the email
     */
    @Override
    public CheckoutDTO getCheckout(String email) {
        //Get the specific user, its checkout and the product that is needed
        User user = getUser(email);
        //Validates if the checkout exist before obtaining it
        Checkout checkout = getCheckout(user);

        //Mapping the checkout to its dto
        CheckoutDTO checkoutDTO = CheckoutMapper.INSTANCE.checkoutToCheckoutDTO(checkout);

        //Mapping each checkout product to its dto
        List<CheckoutProductDTO> checkoutProductDTO = new ArrayList<>();
        for (CheckoutProduct checkoutProduct: checkout.getCheckoutProducts())
        {
            CheckoutProductDTO dto = CheckoutMapper.INSTANCE.checkoutProductAndProductToProductCheckoutDTO
                    (checkoutProduct,checkoutProduct.getProduct());
            checkoutProductDTO.add(dto);
        }
        //Setting the list of product dtos to the checkout dto
        checkoutDTO.setCheckoutProducts(checkoutProductDTO);
        //Calculates a subtotal and set it to the checkout dto
        checkoutDTO.setSubTotal(calculateCheckoutSubtotal(checkout));
        return checkoutDTO;
    }

    /**
     * This method will create a checkout with a product or a list of products
     * @param email The email of the current authenticated user, used to get the user information
     * @param checkoutDTO The DTO with the information necessary to create the checkout
     * @throws ResourceNotFoundException when there is no user found with the email
     * @throws ResourceAlreadyExistException when there is already a checkout in this user
     */
    @Override
    public void createCheckOut(String email,CreateCheckoutDTO checkoutDTO)
    {
        //Find and validates the user
        User user = getUser(email);

        //Find the checkout of the user
        Checkout getCheckout = checkoutRepository.findByUser(user);
        //The user can have only one checkout, if there is one throw exception
        if (getCheckout != null)
        {
            throw new ResourceAlreadyExistException("This user already has a checkout");
        }

        //If there is no checkout we create a new one and save it
        Checkout checkout = createCheckoutBasedOnUser(user);
        Checkout savedCheckout = checkoutRepository.save(checkout);

        //Creates the specific checkout products with dto list information
        for (int i= 0; i < checkoutDTO.getProducts().size();i++)
        {
            //Obtains the specific product and validates its existence
            Product getProduct = getProduct(checkoutDTO.getProducts().get(i).getId());
            //Validates if there is enough stock and creates the checkout product
            createCheckoutProduct(getProduct,checkoutDTO.getProducts().get(i).getQuantity(),savedCheckout);
        }

    }

    /**
     * This method will add a product to a checkout, if checkout doesnt exist then will create a checkout with one
     * product, if the product is already on the checkout will add quantity to the product in the checkout
     * @param email The email of the current authenticated user, used to get the user information
     * @param checkoutProductDTO The DTO with the information of the product and the quantity
     * @throws ResourceNotFoundException when there is no checkout, or when there is no user found with the email
     * @throws NotEnoughStockException when there is no enough stock in the product
     */
    @Override
    public void addProductToCheckout(String email, CreateCheckoutProductDTO checkoutProductDTO)
    {
        //Get the specific user, its checkout and the product that is needed
        User user = getUser(email);

        //If checkout doesn't exists it creates one
        Checkout checkout = checkoutRepository.findByUser(user);
        if (checkout == null)
        {
            checkout = createCheckoutBasedOnUser(user);
            checkout = checkoutRepository.save(checkout);
        }

        //Obtaining the specific product
        Product getProduct = getProduct(checkoutProductDTO.getId());

        //Trying to find a checkout product with this product
        CheckoutProduct checkoutProduct = checkoutProductRepository.findByCheckoutAndProduct(checkout,getProduct);
        //If it doesn't exist we create a new one
        if (checkoutProduct == null)
        {
            //Validates if there is enough stock and creates the checkout product
            createCheckoutProduct(getProduct,checkoutProductDTO.getQuantity(),checkout);
            return;
        }
        //if exist we validate that there is enough stock and set the quantity
        setCheckoutProductQuantity(checkoutProduct,checkoutProduct.getQuantity() +
                checkoutProductDTO.getQuantity(),getProduct.getStock());
    }


    /**
     * This method add or remove quantity from a product in the checkout
     * @param email The email of the current authenticated user, used to get the user information
     * @param productID The id of the product that we want to modify its quantity
     * @param updateCheckoutProductDTO The DTO with the information necesary to modify the quantity of the product
     * @throws ResourceNotFoundException when there is no checkout, or when there is no product in this checkout with
     * the id, or when there is no user found with the email
     * @throws NotEnoughStockException when there is no enough stock in the product
     */
    @Override
    public void modifyCheckoutProductQuantity(String email,long productID, UpdateCheckoutProductDTO updateCheckoutProductDTO)
    {
        //Get the specific user, its checkout and the product that is needed
        User user = getUser(email);
        //Validates the checkout before obtaining it by user
        Checkout checkout = getCheckout(user);
        //Validates that the product exist before obtaining it
        Product getProduct = getProduct(productID);

        //Validates that the checkout product exist before obtaining it
        CheckoutProduct checkoutProduct = getCheckoutProduct(checkout,getProduct);

        //Method modifies the quanitty of the product
        setCheckoutProductQuantity(checkoutProduct, checkoutProduct.getQuantity() +
                updateCheckoutProductDTO.getQuantity(), getProduct.getStock());

        //Delete the product from checkout if quantity is zero
        deleteCheckoutProductWhenQuantityZero(checkoutProduct);
        //Delete Checkout when there are no products
        deleteCheckoutNoProducts(checkout);
    }


    /**
     * This method delete a product in the checkout, when the checkout is deleted when has no products
     * @param email The email of the current authenticated user, used to get the user information
     * @param productId The id of the product that we want to delete
     * @throws ResourceNotFoundException when there is no checkout, or when there is no product in this checkout with
     * the id, or when there is no user found with the email
     */
    @Override
    public void deleteCheckoutProduct(String email,long productId)
    {
        //Get the specific user, its checkout and the product that is needed
        User user = getUser(email);
        //Validates the checkout before obtaining it by user
        Checkout checkout = getCheckout(user);
        //Validates that the product exist before obtaining it
        Product getProduct = getProduct(productId);

        //Trying to find a checkout product
        CheckoutProduct checkoutProduct = checkoutProductRepository.findByCheckoutAndProduct(checkout,getProduct);
        //If it doesnt find it then will throw an exception
        if (checkoutProduct ==  null)
        {
            throw new ResourceNotFoundException("We could not find this product in the checkout with the given information");
        }

        // Erase the product
        checkoutProductDeletion(checkoutProduct);

        //If there is no more products on this checkout then delete it
        deleteCheckoutNoProducts(checkout);
    }


    /**
     * This method delete a the checkout related to the current authenticated user
     * @param email The email of the current authenticated user, used to get the user information
     * @throws ResourceNotFoundException when there is no checkout, or when there is no user found with the email
     */
    @Override
    public void deleteCheckout(String email)
    {
        //Get the specific user, its checkout and the product that is needed
        User user = getUser(email);
        //Validates the checkout before obtaining it by user
        Checkout checkout = getCheckout(user);

        checkoutProductRepository.deleteAllByCheckout(checkout);
        checkoutRepository.delete(checkout);
    }

    /**
     * This method changes the delivery address of the checkout with another related to the user
     * @param email The email of the current authenticated user, used to get the user information
     * @param id The id of the address related to the user that we want to set
     * @throws ResourceNotFoundException when there is no checkout, or when there is no address related to the user
     * with the id, or when there is no user found with the email
     */
    @Override
    public void changeCheckoutAddress (String email,long id)
    {
        //Get the specific user, its checkout and the product that is needed
        User user = getUser(email);
        //Validates the checkout before obtaining it by user
        Checkout checkout = getCheckout(user);

        //Find an address based on the user and the id of the address
        Address address = addressRepository.findByUserAndId(user,id);

        //If it doesn't find it throw an exception
        if (address == null)
        {
            throw new ResourceNotFoundException("We could not find an address with this id in this user");
        }

        //set the address of the checkout
        checkout.setAddress(address);
        //saves the modification to the database
        checkoutRepository.save(checkout);
    }


    /**
     * This method changes the payment method of the checkout with another related to the user
     * @param email The email of the current authenticated user, used to get the user information
     * @param id The id of the payment method related to the user that we want to set
     * @throws ResourceNotFoundException when there is no checkout, or when there is no payment method related to
     * the user with the id, or when there is no user found with the email
     */
    @Override
    public void changeCheckoutPaymentMethod (String email,long id)
    {
        //Get the specific user, its checkout and the product that is needed
        User user = getUser(email);
        //Validates the checkout before obtaining it by user
        Checkout checkout = getCheckout(user);

        //Find a payment method based on the user and the id
        PaymentMethod paymentMethod = paymentMethodRepository.findByUserAndId(user,id);

        //If it doesn't find one throw an exception
        if (paymentMethod == null)
        {
            throw new ResourceNotFoundException("We could not find a payment method with this id in this user");
        }

        //Sets the payment method of the checkout
        checkout.setPaymentMethod(paymentMethod);
        //saves the modification to the database
        checkoutRepository.save(checkout);
    }


    /**
     * This method generates an order based on the information of the checkout of the current user
     * @param email The email of the current authenticated user, used to get the user information
     * @throws ResourceNotFoundException when there is no checkout, or when there is no user found with the email
     * @throws NotEnoughStockException when there are not enough stock on a product to generate the order
     * @throws NotEnoughFoundsException when there are not enough founds on the payment method to generate the order
     * @throws RequiredInformationNullException when the address or payment method of the checkout are not specified
     */
    @Override
    public void generateOrder(String email)
    {
        //Get the specific user, its checkout and the product that is needed
        User user = getUser(email);
        //Validates the checkout before obtaining it by user
        Checkout checkout = getCheckout(user);
        //Verify if the address and payment of the checkout are not null
        mandatoryCheckoutElementsValidation(checkout);
        //Generate basic order
        Orders order = createOrderBasedOnCheckout(checkout);

        //Create all de order products and getting the total price
        double total = createOrderProductsCalculateTotal(checkout,order);
        order.setTotal(total);

        //Generate transaction based on the order created
        Transaction transaction = generateTransaction(order);
        order.setTransaction(transaction);

        //Remove founds from payment method
        setPaymentMethodFounds(transaction);

        //Saving the order with all the elements and deleting the checkout
        orderRepository.save(order);
        // deletes the checkout after the order is completed
        deleteCheckout(email);
    }



    //Secondary Methods

    /**
     * This method sets the quantity of the checkout product and validates that there is enough stock
     * @param checkoutProduct The specific product that is going to change its quantity
     * @param quantity The quantity to be set on the chekout product
     * @param stock The quantity of stock that is currently on the product
     * @throws NotEnoughFoundsException when there is not enough stock of the product
     */
    //Sets the products quantity on the checkout product
    private void setCheckoutProductQuantity(CheckoutProduct checkoutProduct, int quantity, int stock)
    {
        if (quantity > stock)
        {
            throw new NotEnoughStockException("Not enough "+ checkoutProduct.getProduct().getName() +
                    " on stock - current stock: " + checkoutProduct.getProduct().getStock());
        }
        checkoutProduct.setQuantity(quantity);
        checkoutProductRepository.save(checkoutProduct);
    }

    /**
     * This method deletes the checkout product when its quantity is less or equals to zero
     * @param checkoutProduct The specific product that is going to be deleted
     */
    //Deletes the product of the checkout when the quantity reaches zero
    private void deleteCheckoutProductWhenQuantityZero(CheckoutProduct checkoutProduct)
    {
        if (checkoutProduct.getQuantity() <= 0)
        {
            checkoutProductDeletion(checkoutProduct);
        }
    }

    /**
     * This method deletes the checkout product from the database and is removed from the list of its checkout
     * @param checkoutProduct The specific product that is going to be deleted
     */
    //Method used to delete a checkout product
    private void checkoutProductDeletion(CheckoutProduct checkoutProduct)
    {
        checkoutProduct.getCheckout().getCheckoutProducts().remove(checkoutProduct);
        checkoutProductRepository.delete(checkoutProduct);
    }

    /**
     * This method deletes the checkout when it has no more products on it
     * @param checkout The checkout that is going to be deleted
     */
    //Deletes the checkout if it doesn't have any more products
    private void deleteCheckoutNoProducts(Checkout checkout)
    {
        if (checkout.getCheckoutProducts().isEmpty())
        {
            checkoutRepository.delete(checkout);
        }
    }

    /**
     * This method wraps the creation of the checkout product and validates that there is enough stock
     * @param product The specific product that is going to be set on this checkout product
     * @param quantity The quantity of the product that is going to be added to the checkout
     * @param checkout The checkout that is going to be related to this product
     * @throws NotEnoughFoundsException when there is not enough stock of the product
     */
    //Method used to create a checkout product and validates that the quantity is less than stock of product
    private void createCheckoutProduct(Product product,int quantity, Checkout checkout)
    {
        CheckoutProduct checkoutProduct = CheckoutProduct.builder()
                .product(product)
                .quantity(quantity)
                .checkout(checkout)
                .build();
        if (checkoutProduct.getQuantity() > product.getStock())
        {
            throw new NotEnoughStockException("Not enough "+ product.getName() + " on stock - current stock: " + product.getStock());
        }

        checkoutProductRepository.save(checkoutProduct);
    }

    /**
     * This method calculates the subtotal of the current checkout related to the user
     * @param checkout The specific checkout that is going to be used to calculate its subtotal
     */
    //Calculates the subtotal of the current state of the checkout
    private double calculateCheckoutSubtotal(Checkout checkout)
    {
        double subTotal = 0;
        for (int i = 0; i < checkout.getCheckoutProducts().size(); i++)
        {
            CheckoutProduct product = checkout.getCheckoutProducts().get(i);
            subTotal += product.getProduct().getPrice() * product.getQuantity();
        }

        return subTotal;
    }

    /**
     * This method creates the order products related to an order, mapping the checkout products to a new order product,
     * removes the quantity of stock from each product,
     * calculates the total from the products and validates that there is enough stock
     * @param checkout The checkout that provides the checkout products to be mapped
     * @param order The generated order where the mapped products are going to be insert in
     * @throws NotEnoughStockException when there is not enough stock of the product
     */
    //Create the order products based on the checkout products, removes stock from products and returns total
    private double createOrderProductsCalculateTotal(Checkout checkout, Orders order)
    {
        double total = 0;
        // Generate Order Products
        for (int i= 0; i < checkout.getCheckoutProducts().size();i++)
        {
            OrderProduct orderProduct = CheckoutOrderMapper.INSTANCE.
                    checkoutProductToOrderProduct(checkout.getCheckoutProducts().get(i));
            orderProduct.setOrder(order);

            discountStockFromProducts(orderProduct);
            //Getting total
            total += orderProduct.getProduct().getPrice() * orderProduct.getQuantity();

            orderProductRepository.save(orderProduct);
        }

        return total;
    }

    /**
     * This method will discount a specific quantity to a product
     * @param orderProduct The specific order product that provides the product and the quantity to discount
     * @throws NotEnoughStockException when there is not enough stock of the product
     */
    //Removes stock from products after the order is generated
    private void discountStockFromProducts(OrderProduct orderProduct)
    {
        //Eliminating stocks from product
        if(orderProduct.getQuantity() > orderProduct.getProduct().getStock())
        {
            throw new NotEnoughStockException("Not enough "+ orderProduct.getProduct().getName() +
                    " on stock - current stock: " + orderProduct.getProduct().getStock());
        }
        orderProduct.getProduct().setStock(orderProduct.getProduct().getStock() - orderProduct.getQuantity());
        productRepository.save(orderProduct.getProduct());
    }

    /**
     * This method wraps the creation of the transaction based on the information of the order
     * @param order The specific order that is going to provide the payment method and total of the purchase
     */
    //Creates the transaction based on the order
    private Transaction generateTransaction (Orders order)
    {
        //Generate Transaction
        Transaction transaction = Transaction.builder()
                .paymentMethod(order.getPaymentMethod())
                .quantity(order.getTotal())
                .build();
        return transactionRepository.save(transaction);
    }

    /**
     * This method create an order based on the checkout information
     * @param checkout The specific checkout that provides the information to be mapped to create the order
     * @throws NotEnoughFoundsException when there is not enough stock of the product
     */
    //Creates an order based on the user checkout
    private Orders createOrderBasedOnCheckout (Checkout checkout)
    {
        Orders createOrder = CheckoutOrderMapper.INSTANCE.checkoutToOrder(checkout);

        if (calculateCheckoutSubtotal(checkout) > createOrder.getPaymentMethod().getFounds())
        {
            throw new NotEnoughFoundsException("Not enough founds on your payment method");
        }

        //Generate basic order
        return orderRepository.save(createOrder);
    }


    /**
     * This method validates that the payment method and address from a checkout are specified
     * @param checkout The specific checkout to be checked
     * @throws RequiredInformationNullException when the address or payment method of the checkout are empty
     */
    //Validates that the address and the payment are not null when generating the order
    private void mandatoryCheckoutElementsValidation(Checkout checkout)
    {
        if (checkout.getAddress() == null)
        {
            throw new RequiredInformationNullException("Address is mandatory, please set one to continue");
        }
        if (checkout.getPaymentMethod() == null)
        {
            throw new RequiredInformationNullException("PaymentMethod is mandatory, please set one to continue");
        }
    }

    /**
     * This method wraps the creation of the checkout based on an user
     * @param user The specific user to be used to create a checkout with its information
     */
    //Creates the checkout based on the user
    private Checkout createCheckoutBasedOnUser (User user)
    {
        //Creates the Checkout with user information
        return Checkout.builder()
                .user(user)
                .address(user.getAddress().isEmpty() ? null : user.getAddress().get(0))
                .paymentMethod(user.getPaymentMethods().isEmpty() ? null : user.getPaymentMethods().get(0))
                .build();
    }

    /**
     * This method removes the founds from a payment method when a transaction is generated
     * @param transaction The transaction that will remove founds from the payment method
     * @throws NotEnoughFoundsException when there are not enough founds on the payment method
     */
    //Remove founds when generating the order, if there are not enough founds then throw exception
    private void setPaymentMethodFounds(Transaction transaction)
    {
        if (transaction.getQuantity() > transaction.getPaymentMethod().getFounds())
        {
            throw new NotEnoughFoundsException("Not enough founds on your payment method");
        }
        transaction.getPaymentMethod().setFounds(transaction.getPaymentMethod().getFounds() - transaction.getQuantity());
        paymentMethodRepository.save(transaction.getPaymentMethod());
    }



    /**
     * This method will return a User found on the database with the email specified
     * @param email The email of the current authenticated user, used to get the user information
     * @return an User found in the database
     * @throws ResourceNotFoundException when there is no user found with the email
     */
    ////Validates the Objects we recieve
    private User getUser (String email)
    {
        User user = userRepository.findByEmail(email);
        if (user == null)
        {
            throw new ResourceNotFoundException("We could not find a user with the given id");
        }
        return user;
    }

    /**
     * This method will return a Product found on the database with the id specified
     * @param id The id of the product
     * @return a Product found in the database
     * @throws ResourceNotFoundException when there is no product with that id
     */
    private Product getProduct (long id)
    {
        Optional<Product> getProduct = productRepository.findById(id);
        if (!getProduct.isPresent())
        {
            throw new ResourceNotFoundException("We could not find a product with the given id");
        }
        return getProduct.get();
    }

    /**
     * This method will return a checkout found with an user
     * @param user The user that is related to the checkout to find
     * @return a Checkout found in the database
     * @throws ResourceNotFoundException when there is no checkout with that user
     */
    private Checkout getCheckout (User user)
    {
        Checkout checkout = checkoutRepository.findByUser(user);
        if (checkout == null)
        {
            throw new ResourceNotFoundException("We could not find a checkout with the given user, please create one");
        }
        return checkout;
    }

    /**
     * This method will return a Checkout Product found on the database with the checkout and the product
     * @param checkout The checkout that containts the product
     * @param product The specified on the checkout
     * @return a CheckoutProduct found in the database
     * @throws ResourceNotFoundException when there is no CheckoutProduct with that information
     */
    private CheckoutProduct getCheckoutProduct(Checkout checkout, Product product)
    {
        CheckoutProduct checkoutProduct = checkoutProductRepository.findByCheckoutAndProduct(checkout,product);
        if (checkoutProduct ==  null)
        {
            throw new ResourceNotFoundException("We could not find a product checkout with the given information");
        }

        return checkoutProduct;
    }


}
