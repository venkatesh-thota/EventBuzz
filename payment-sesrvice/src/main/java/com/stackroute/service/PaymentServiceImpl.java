package com.stackroute.service;

import com.stackroute.domain.ChargeRequest;
import com.stackroute.domain.PaymentDetails;
import com.stackroute.repository.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    private String secretKey="sk_test_8w3sNV1BNWYwYjwW89mCqRiV";

    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository){
        this.paymentRepository=paymentRepository;
    }

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    @Override
    public Charge charge(ChargeRequest chargeRequest) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {

        Map<String, Object> chargeParams = new HashMap<>();

        chargeParams.put("amount",chargeRequest.getAmount());
        chargeParams.put("currency",chargeRequest.getCurrency());
        chargeParams.put("description",chargeRequest.getDescription());
        chargeParams.put("source",chargeRequest.getStripeToken());
        System.out.println(chargeParams);
        Charge charge=Charge.create(chargeParams);

        return charge;
    }

    @Override
    public PaymentDetails savePaymentDetails(PaymentDetails paymentDetails){
        return paymentRepository.save(paymentDetails);
    }

}
