package com.app.ecommerce.service;

import com.app.ecommerce.dto.Purchase;
import com.app.ecommerce.dto.PurchaseResponse;
import com.app.ecommerce.entity.Customer;
import com.app.ecommerce.entity.Order;
import com.app.ecommerce.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService{

    private final CustomerRepository customerRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    @Override
    public PurchaseResponse placeOrder(Purchase purchase) {
        Customer customer = purchase.customer();
        // Retrieve the order info from dto
        Order order = purchase.order();
        // Generate tracking number
        order.setOrderTrackingNumber(generateUUIDForOrder());
        // Populate order with orderItems
        purchase.items().forEach(order::addItem);
        purchase.items().forEach(orderItem -> orderItem.setOrder(order));
        // Populate order with billingAddress and shippingAddress
        order.setShippingAddres(purchase.shippingAddress());
        order.setBillingAddress(purchase.billingAddress());
        // Populate customer with order;
        customer.addOrder(order);
        // Save to the database
        customerRepository.save(customer);

        // return a response

        return new PurchaseResponse(order.getOrderTrackingNumber());
    }

    private String generateUUIDForOrder() {
        return UUID.randomUUID().toString();
    }
}
