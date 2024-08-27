package com.client.ws.ws.dto.wsraspay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {

    private String id;

    private String cpf;

    private String email;

    private String firstName;

    private String lastName;
}