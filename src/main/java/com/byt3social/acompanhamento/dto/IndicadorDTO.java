package com.byt3social.acompanhamento.dto;

import com.byt3social.acompanhamento.enums.TipoIndicador;

public record IndicadorDTO(
        String nome,
        String descricao,
        TipoIndicador tipo
) {
}
