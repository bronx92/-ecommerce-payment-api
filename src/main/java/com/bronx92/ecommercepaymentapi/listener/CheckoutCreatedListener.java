package com.bronx92.ecommercepaymentapi.listener;

import com.bronx92.ecommerce.checkoutg.event.CheckoutCreatedEvent;
import com.bronx92.ecommerce.payment.event.PaymentCreatedEvent;
import com.bronx92.ecommercepaymentapi.streaming.CheckoutProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CheckoutCreatedListener {

    private final CheckoutProcessor checkoutProcessor;

    @StreamListener (CheckoutProcessor.INPUT)
    public void handler(CheckoutCreatedEvent event) {
        final PaymentCreatedEvent paymentCreatedEvent = PaymentCreatedEvent.newBuilder()
                .setCheckoutCode(event.getCheckoutCode())
                .setCheckoutStatus(event.getStatus())
                .setPaymentCode(UUID.randomUUID().toString())
                .build();
        checkoutProcessor.output().send(MessageBuilder.withPayload(paymentCreatedEvent).build());
    }
}
