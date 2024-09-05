package com.client.ws.ws.Service.impl;


import com.client.ws.ws.Service.PaymentInfoService;
import com.client.ws.ws.dto.PaymentProcessDto;

import com.client.ws.ws.dto.wsraspay.CustomerDto;
import com.client.ws.ws.dto.wsraspay.OrderDto;
import com.client.ws.ws.dto.wsraspay.PaymentDto;
import com.client.ws.ws.enums.UserTypeEnum;
import com.client.ws.ws.exception.BusinessException;
import com.client.ws.ws.exception.NotFoudException;
import com.client.ws.ws.integration.MailIntegration;
import com.client.ws.ws.integration.WsRaspayIntegration;
import com.client.ws.ws.mapper.UserPaymentInfoMapper;
import com.client.ws.ws.mapper.wsraspey.CreditCardMapper;
import com.client.ws.ws.mapper.wsraspey.CustomerMapper;
import com.client.ws.ws.mapper.wsraspey.OrderMapper;
import com.client.ws.ws.mapper.wsraspey.PaymentMapper;
import com.client.ws.ws.model.User;
import com.client.ws.ws.model.UserCredentials;
import com.client.ws.ws.model.UserPaymentInfo;
import com.client.ws.ws.repository.UserDetailsRepository;
import com.client.ws.ws.repository.UserPaymentInfoRepository;
import com.client.ws.ws.repository.UserRepository;
import com.client.ws.ws.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {

    @Value("${webservices.rasplus.default.password}")
    private String defaultPass;

    private final UserRepository userRepository;
    private final UserPaymentInfoRepository userPaymentInfoRepository;
    private final WsRaspayIntegration wsRaspayIntegration;
    private final MailIntegration mailIntegration;
    private final UserDetailsRepository userDetailsRepository;
    private final UserTypeRepository userTypeRepository;

    PaymentInfoServiceImpl(UserRepository userRepository, UserPaymentInfoRepository userPaymentInfoRepository,
                           WsRaspayIntegration wsRaspayIntegration, MailIntegration mailIntegration,
                           UserDetailsRepository userDetailsRepository, UserTypeRepository userTypeRepository) {
        this.userRepository = userRepository;
        this.userPaymentInfoRepository = userPaymentInfoRepository;
        this.wsRaspayIntegration = wsRaspayIntegration;
        this.mailIntegration = mailIntegration;
        this.userDetailsRepository = userDetailsRepository;
        this.userTypeRepository = userTypeRepository;
    }
    @Override
    public Boolean process(PaymentProcessDto dto) {
        //verifica usuario por id e verifica se já existe assinatura
        var userOpt = userRepository.findById(dto.getUserPaymentInfoDto().getUserId());
        if (userOpt.isEmpty()) {
            throw new NotFoudException("Usuário não encontrado");
        }
        User user = userOpt.get();
        if (Objects.nonNull(user.getSubscriptionType())) {
            throw new BusinessException("Pagamento não pode ser processado pois usuário já possui assinatura");
        }

        CustomerDto customerDto = wsRaspayIntegration.createCustomer(CustomerMapper.build(user));
        OrderDto orderDto = wsRaspayIntegration.createOrder(OrderMapper.build(customerDto.getId(), dto));
        PaymentDto paymentDto = PaymentMapper.build(customerDto.getId(), orderDto.getId(), CreditCardMapper.build(dto.getUserPaymentInfoDto(), user.getCpf()));
        Boolean successPayment = wsRaspayIntegration.processPayment(paymentDto);

        if (Boolean.TRUE.equals(successPayment)) {
            UserPaymentInfo userPaymentInfo = UserPaymentInfoMapper.fromDtoToEntity(dto.getUserPaymentInfoDto(), user);
            userPaymentInfoRepository.save(userPaymentInfo);

            var userTypeOpt = userTypeRepository.findById(UserTypeEnum.ALUNO.getId());

            if (userTypeOpt.isEmpty()) {
                throw new NotFoudException("UserType não encontrado");
            }
            UserCredentials userCredentials = new UserCredentials(null, user.getEmail(),new BCryptPasswordEncoder().encode(defaultPass), userTypeOpt.get());
            userDetailsRepository.save(userCredentials);

            mailIntegration.send(user.getEmail(), "Usuario: " + user.getEmail() + " - Senha: " + defaultPass, "Acesso liberado");
            return true;
        }

        return false;
    }
}