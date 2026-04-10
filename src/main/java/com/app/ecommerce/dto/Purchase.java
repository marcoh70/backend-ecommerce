package com.app.ecommerce.dto;

import com.app.ecommerce.entity.Address;
import com.app.ecommerce.entity.Customer;
import com.app.ecommerce.entity.Order;
import com.app.ecommerce.entity.OrderItem;

import java.util.Set;

public record Purchase(
        Customer customer,
        Address shippingAddress,
        Address billingAddress,
        Order order,
        Set<OrderItem> items) {
}
