package com.byt3social.acompanhamento.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "indicadores_solicitados")
@Entity(name = "IndicadorSolicitado")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
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
//
//    public void atualizar(Indicador indicador) {
//        this.indicador = indicador;
//    }
}
