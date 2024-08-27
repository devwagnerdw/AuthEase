package com.client.ws.ws.Service.impl;


import com.client.ws.ws.Service.PaymentInfoService;
import com.client.ws.ws.dto.PaymentProcessDto;

import com.client.ws.ws.exception.BusinessException;
import com.client.ws.ws.exception.NotFoudException;
import com.client.ws.ws.mapper.UserPaymentInfoMapper;
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

    PaymentInfoServiceImpl(UserRepository userRepository, UserPaymentInfoRepository userPaymentInfoRepository){
        this.userRepository = userRepository;
        this.userPaymentInfoRepository = userPaymentInfoRepository;
    }

    @Override
    public Boolean process(PaymentProcessDto dto) {
        //verifica usuario por id
        var userOpt = userRepository.findById(dto.getUserPaymentInfoDto().getUserId());
        if(userOpt.isEmpty()) {
            throw new NotFoudException("Usuário não encontrado");
        }
        User user = userOpt.get();
        if(Objects.nonNull(user.getSubscriptionType())) {
            throw new BusinessException("Pagamento não pode ser processado pois usuário já possui assinatura");
        }
        //salvar informacoes de pagamento
        UserPaymentInfo userPaymentInfo = UserPaymentInfoMapper.fromDtoToEntity(dto.getUserPaymentInfoDto(),user);
        userPaymentInfoRepository.save(userPaymentInfo);

        //cria ou atualiza usuario raspay
        //cria o pedido de pagamento
        //processa o pagamento
        //enviar email de criacao de conta
        //retorna o sucesso ou nao do pagamento

        return null;
    }
}
