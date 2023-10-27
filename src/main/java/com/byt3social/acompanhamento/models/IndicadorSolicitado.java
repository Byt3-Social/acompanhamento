package com.byt3social.acompanhamento.models;

import com.byt3social.acompanhamento.dto.IndicadorSolicitadoDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "indicadores_solicitados")
@Entity(name = "IndicadorSolicitado")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IndicadorSolicitado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String valor;
    @ManyToOne
    @JoinColumn(name = "indicador_id")
    @JsonManagedReference
    private Indicador indicador;
    @ManyToOne
    @JoinColumn(name = "acompanhamento_id")
    @JsonBackReference
    private Acompanhamento acompanhamento;

    public IndicadorSolicitado(Indicador indicador, Acompanhamento acompanhamento) {
        this.indicador = indicador;
        this.acompanhamento = acompanhamento;
    }

    public void atualizar(IndicadorSolicitadoDTO indicadorSolicitadoDTO) {
        this.valor = indicadorSolicitadoDTO.valor();
    }
}
