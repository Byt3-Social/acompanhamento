package com.byt3social.acompanhamento.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OrganizacaoDTO(
        @JsonProperty("nome")
        String nomeEmpresarial,
        String email
) {
}
