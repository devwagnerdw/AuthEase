package com.client.ws.ws.Service.impl;


import com.client.ws.ws.Service.PaymentInfoService;
import com.client.ws.ws.dto.PaymentProcessDto;

import com.client.ws.ws.dto.wsraspay.CustomerDto;
import com.client.ws.ws.dto.wsraspay.OrderDto;
import com.client.ws.ws.dto.wsraspay.PaymentDto;
import com.client.ws.ws.exception.BusinessException;
import com.client.ws.ws.exception.NotFoudException;
import com.client.ws.ws.integration.WsRaspayIntegration;
import com.client.ws.ws.mapper.UserPaymentInfoMapper;
import com.client.ws.ws.mapper.wsraspey.CreditCardMapper;
import com.client.ws.ws.mapper.wsraspey.CustomerMapper;
import com.client.ws.ws.mapper.wsraspey.OrderMapper;
import com.client.ws.ws.mapper.wsraspey.PaymentMapper;
import com.client.ws.ws.model.User;
import com.client.ws.ws.model.UserPaymentInfo;
import com.client.ws.ws.repository.UserPaymentInfoRepository;
import com.client.ws.ws.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {

    private final UserRepository userRepository;
    private final UserPaymentInfoRepository userPaymentInfoRepository;
    private final WsRaspayIntegration wsRaspayIntegration;

    PaymentInfoServiceImpl(UserRepository userRepository, UserPaymentInfoRepository userPaymentInfoRepository,
                           WsRaspayIntegration wsRaspayIntegration){
        this.userRepository = userRepository;
        this.userPaymentInfoRepository = userPaymentInfoRepository;
        this.wsRaspayIntegration = wsRaspayIntegration;
    }

    @Override
    public Boolean process(PaymentProcessDto dto) {
        //verifica usuario por id e verifica se já existe assinatura
        var userOpt = userRepository.findById(dto.getUserPaymentInfoDto().getUserId());
        if(userOpt.isEmpty()) {
            throw new NotFoudException("Usuário não encontrado");
        }
        User user = userOpt.get();
        if(Objects.nonNull(user.getSubscriptionType())) {
            throw new BusinessException("Pagamento não pode ser processado pois usuário já possui assinatura");
        }
        //cria ou atualiza usuario raspay
        CustomerDto customerDto = wsRaspayIntegration.createCustomer(CustomerMapper.build(user));

        //cria o pedido de pagamento
        OrderDto orderDto = wsRaspayIntegration.createOrder(OrderMapper.build(customerDto.getId(),dto));

        //processa o pagamento
        PaymentDto paymentDto =  PaymentMapper.build(customerDto.getId(), orderDto.getId(), CreditCardMapper.build(dto.getUserPaymentInfoDto(), user.getCpf()));
        Boolean successPayment = wsRaspayIntegration.processPayment(paymentDto);

        if (successPayment) {
            //salvar informacoes de pagamento
            UserPaymentInfo userPaymentInfo = UserPaymentInfoMapper.fromDtoToEntity(dto.getUserPaymentInfoDto(), user);
            userPaymentInfoRepository.save(userPaymentInfo);
        }
        //enviar email de criacao de conta
        //retorna o sucesso ou nao do pagamento

        return null;
    }
}
