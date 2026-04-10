package com.app.ecommerce.controller;

import com.app.ecommerce.dto.Purchase;
import com.app.ecommerce.dto.PurchaseResponse;
import com.app.ecommerce.service.CheckoutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @CrossOrigin("http://localhost:4200")
    @PostMapping(path = "/purchase")
    public ResponseEntity<PurchaseResponse> placeOrder(@RequestBody Purchase purchase) {
        PurchaseResponse purchaseResponse = checkoutService.placeOrder(purchase);
        log.info("Tracking number: {}", purchaseResponse.orderTrackingNumber());
        return ResponseEntity.ok(purchaseResponse);
    }
}
