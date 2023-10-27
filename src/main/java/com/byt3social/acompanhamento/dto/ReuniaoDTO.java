package com.byt3social.acompanhamento.dto;

import java.util.Date;
import java.util.List;

public record ReuniaoDTO(
        Integer acompanhamentoId,
        List<Date> disponibilidades
) {
}
