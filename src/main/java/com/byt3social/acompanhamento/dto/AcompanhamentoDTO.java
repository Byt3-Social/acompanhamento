package com.byt3social.acompanhamento.dto;

import java.util.List;

public record AcompanhamentoDTO(
        Integer acaoId,
        Integer organizacaoId,
        Integer usuarioId,
        RepresentanteDTO representante,
        List<Integer> novosIndicadoresSolicitados,
        List<IndicadorSolicitadoDTO> indicadoresSolicitados,
        String informacoesAdicionais
) {
}
