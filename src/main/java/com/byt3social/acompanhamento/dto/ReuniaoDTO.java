package com.byt3social.acompanhamento.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ReuniaoDTO(
        @JsonProperty("organizacao_id")
        Integer organizacaoId,
        @JsonProperty("acompanhamento_id")
        Integer acompanhamento_id
) {
}
