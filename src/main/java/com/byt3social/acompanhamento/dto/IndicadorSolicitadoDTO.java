package com.byt3social.acompanhamento.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record IndicadorSolicitadoDTO(
        Integer id,
        String valor,
        @JsonProperty("indicador_id")
        Integer indicadorId
) {
}
