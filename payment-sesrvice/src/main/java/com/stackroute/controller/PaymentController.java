package com.stackroute.controller;

import com.stackroute.domain.ChargeRequest;
import com.stackroute.domain.PaymentDetails;
import com.stackroute.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1")
@CrossOrigin
public class PaymentController {

    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService){
        this.paymentService=paymentService;
    }

    @PostMapping("/charge")
    public Charge charge(@RequestBody ChargeRequest chargeRequest) throws StripeException{

        chargeRequest.setDescription("For Movie Ticket Booking");
        chargeRequest.setCurrency(ChargeRequest.Currency.USD);
        System.out.println(chargeRequest.getStripeToken());
        System.out.println(chargeRequest.getAmount());
        Charge charge=paymentService.charge(chargeRequest);
        System.out.println(charge);

        return charge;
    }

    @PostMapping("/paymentDetails")
    public ResponseEntity<?> save(@RequestBody PaymentDetails paymentDetails){
        PaymentDetails paymentDetails1=paymentService.savePaymentDetails(paymentDetails);
        ResponseEntity responseEntity=new ResponseEntity<PaymentDetails>(paymentDetails1, HttpStatus.CREATED);
        return responseEntity;
    }
}
