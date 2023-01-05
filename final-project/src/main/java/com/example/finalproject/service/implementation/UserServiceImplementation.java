package com.example.finalproject.service.implementation;

import com.example.finalproject.exception.ResourceNotFoundException;
import com.example.finalproject.persistence.model.Address;
import com.example.finalproject.persistence.model.PaymentMethod;
import com.example.finalproject.persistence.model.User;
import com.example.finalproject.persistence.repository.AddressRepository;
import com.example.finalproject.persistence.repository.PaymentMethodRepository;
import com.example.finalproject.persistence.repository.UserRepository;
import com.example.finalproject.service.UserService;
import com.example.finalproject.service.mapper.AddressMapper;
import com.example.finalproject.service.mapper.PaymentMethodMapper;
import com.example.finalproject.service.mapper.UserMapper;
import com.example.finalproject.web.DTO.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    /**
     * This method will return an existing product of the store based on its id
     * @param userEmail The email of the current authenticated user, used to get the user information
     * @return a UserDTO with the current user information on the database
     * @throws ResourceNotFoundException when there is no user found with the email
     */
    @Override
    public UserDTO getUser(String userEmail) {
        User foundUser = foundUser(userEmail);
        return UserMapper.INSTANCE.userToUserDTO(foundUser);
    }

    /**
     * This method will return a List of PaymentMethodDTO with the information of all the payment methods of the user
     * @param email The email of the current authenticated user, used to get the user information
     * @return a List of PaymentMethodDTO with the information of each payment method
     * @throws ResourceNotFoundException when there is no payment method found related to the user, or no user found with
     * the email
     */
    @Override
    public List<PaymentMethodDTO> getAllPaymentMethods(String email)
    {
        //Get the specific user, its checkout and the product that is needed
        User user = foundUser(email);
        //Find all the payment method related to the user
        List<PaymentMethod> getPaymentMethods = paymentMethodRepository.findAllByUser(user);
        if (getPaymentMethods.isEmpty())
        {
            throw new ResourceNotFoundException("There are no payment methods in this user, try to create one");
        }

        return PaymentMethodMapper.INSTANCE.paymentMethodToPaymentMethodDTO(getPaymentMethods);
    }

    /**
     * This method will create a payment method related to the current authenticated user
     * @param email The email of the current authenticated user, used to get the user information
     * @param createPaymentMethodDTO the DTO with the information to create the payment method
     * @throws ResourceNotFoundException when there is no user found with the email
     */
    @Override
    public void createPaymentMethod(String email, CreatePaymentMethodDTO createPaymentMethodDTO)
    {
        //Get the specific user, its checkout and the product that is needed
        User user = foundUser(email);
        //Mapping the dto to a payment method
        PaymentMethod createPaymentMethod = PaymentMethodMapper.INSTANCE.createPaymentMethodDTOToPaymentMethod(createPaymentMethodDTO);
        createPaymentMethod.setUser(user);
        //saving the payment method
        paymentMethodRepository.save(createPaymentMethod);
    }

    /**
     * This method will return a List of UserAddressDTO with the information of all the addresses of the user
     * @param email The email of the current authenticated user, used to get the user information
     * @return a List of UserAddressDTO with the information of each address
     * @throws ResourceNotFoundException when there are no addresses related to this user, or
     * when there is no user found with the email
     */
    @Override
    public List<UserAddressDTO> getAllAddresses(String email)
    {
        //Get the specific user, its checkout and the product that is needed
        User user = foundUser(email);
        //Find all the addresses related to the user
        List<Address> getAddresses = addressRepository.findAllByUser(user);
        if (getAddresses.isEmpty())
        {
            throw new ResourceNotFoundException("There are no addresses in this user, try to create one");
        }

        return AddressMapper.INSTANCE.addressToCheckoutUserAddressDTO(getAddresses);
    }

    /**
     * This method will create an address related to the current authenticated user
     * @param email The email of the current authenticated user, used to get the user information
     * @param createAddressDTO The DTO with the information to create an address related to the user
     * @throws ResourceNotFoundException when there is no user found with the email
     */
    @Override
    public void createAddress(String email, CreateAddressDTO createAddressDTO)
    {
        //Get the specific user, its checkout and the product that is needed
        User user = foundUser(email);
        //Mapping the dto to an address
        Address createAddress = AddressMapper.INSTANCE.createAddressDTOToAddress(createAddressDTO);
        createAddress.setUser(user);
        //saving the address
        addressRepository.save(createAddress);
    }


    /**
     * This method will return a User found on the database with the email specified
     * @param email The email of the current authenticated user, used to get the user information
     * @return an User found in the database
     * @throws ResourceNotFoundException when there is no user found with the email
     */
    private User foundUser (String email)
    {
        User user = userRepository.findByEmail(email);
        if (user == null)
        {
            throw new ResourceNotFoundException("We could not find a user with the given email");
        }
        return user;
    }


}
