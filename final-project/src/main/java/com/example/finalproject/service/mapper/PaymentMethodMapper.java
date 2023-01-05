package com.example.finalproject.service.mapper;

import com.example.finalproject.persistence.model.PaymentMethod;
import com.example.finalproject.web.DTO.CreatePaymentMethodDTO;
import com.example.finalproject.web.DTO.PaymentMethodDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface PaymentMethodMapper {

    PaymentMethodMapper INSTANCE = Mappers.getMapper(PaymentMethodMapper.class);

    List<PaymentMethodDTO> paymentMethodToPaymentMethodDTO(List<PaymentMethod> paymentMethod);

    PaymentMethod createPaymentMethodDTOToPaymentMethod(CreatePaymentMethodDTO createPaymentMethodDTO);

}
