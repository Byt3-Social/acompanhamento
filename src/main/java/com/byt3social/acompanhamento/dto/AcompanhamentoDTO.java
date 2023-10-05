package com.byt3social.acompanhamento.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AcompanhamentoDTO(
        @JsonProperty("acao_id")
        Integer acaoId,
        @JsonProperty("organizacao_id")
        Integer organizacaoId,
        @JsonProperty("usuario_id")
        Integer usuarioId,
        @JsonProperty("indicadores_solicitados")
        List<Integer> indicadoresSolicitados
) {
}
