package com.byt3social.acompanhamento.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public record HorarioDTO(
        @JsonProperty("data_horario")
        Date dataHorario,
        @JsonProperty("horario_id")
        Integer horarioId
) {
}
