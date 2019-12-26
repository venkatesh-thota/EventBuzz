package com.stackroute.service;

import com.stackroute.domain.ChargeRequest;
import com.stackroute.domain.PaymentDetails;
import com.stripe.exception.*;
import com.stripe.model.Charge;

public interface PaymentService {

    public Charge charge(ChargeRequest chargeRequest) throws AuthenticationException, InvalidRequestException,
            APIConnectionException, CardException, APIException;
    public PaymentDetails savePaymentDetails(PaymentDetails paymentDetails);

}
