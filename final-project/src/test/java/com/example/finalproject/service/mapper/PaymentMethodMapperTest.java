package com.example.finalproject.service.mapper;

import com.example.finalproject.persistence.model.PaymentMethod;
import com.example.finalproject.web.DTO.PaymentMethodDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


class PaymentMethodMapperTest {

    private final PaymentMethodMapper mapper = Mappers.getMapper(PaymentMethodMapper.class);

    @Test
    @DisplayName("Mapping payment method to Payment method dto list")
    void paymentMethodToPaymentMethodDTO_EmptyList_NullList()
    {
        List<PaymentMethodDTO> paymentMethodDTOList = mapper.paymentMethodToPaymentMethodDTO(null);
        assertThat(paymentMethodDTOList,is(equalTo(null)));
    }

    @Test
    @DisplayName("Mapping payment method dto to Payment method ")
    void createPaymentMethodDTOToPaymentMethod_Empty_Null()
    {
        PaymentMethod paymentMethod = mapper.createPaymentMethodDTOToPaymentMethod(null);
        assertThat(paymentMethod,is(equalTo(null)));
    }
}