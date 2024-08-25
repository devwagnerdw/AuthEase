package com.client.ws.ws.Service.impl;


import com.client.ws.ws.Service.PaymentInfoService;
import com.client.ws.ws.dto.PaymentProcessDto;

import com.client.ws.ws.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {


    PaymentInfoServiceImpl(){

    }

    @Override
    public Boolean process(PaymentProcessDto dto) {
        //verifica usuario por id
        //salvar informacoes de pagamento
        //cria ou atualiza usuario raspay
        //cria o pedido de pagamento
        //processa o pagamento
        //enviar email de criacao de conta
        //retorna o sucesso ou nao do pagamento

        return null;
    }
}
