package com.example.finalproject.service;

import com.example.finalproject.web.DTO.OrderDTO;

import java.util.List;

public interface OrderService {

    List<OrderDTO> getAllOrders(String email);

    OrderDTO getOrder(String email,long id);
}
