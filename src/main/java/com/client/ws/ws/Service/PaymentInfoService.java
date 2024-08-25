package com.client.ws.ws.Service;

import com.client.ws.ws.dto.PaymentProcessDto;

public interface PaymentInfoService {

    Boolean process(PaymentProcessDto dto);
}