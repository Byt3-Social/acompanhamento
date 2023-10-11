package com.byt3social.acompanhamento.models;

import com.byt3social.acompanhamento.dto.IndicadorDTO;
import com.byt3social.acompanhamento.enums.TipoIndicador;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table(name = "indicadores")
@Entity(name = "Indicador")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
@Setter
public class Indicador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String descricao;
    @Enumerated(value = EnumType.STRING)
    private TipoIndicador tipo;
    @OneToMany(mappedBy = "indicador")
    @JsonBackReference
    private List<IndicadorSolicitado> indicadoresSolicitados;

    public Indicador(IndicadorDTO indicadorDTO) {
        this.nome = indicadorDTO.nome();
        this.descricao = indicadorDTO.descricao();
        this.tipo = indicadorDTO.tipo();
    }

    public void atualizar(IndicadorDTO indicadorDTO) {
        if(indicadorDTO.tipo() != null) {
            this.tipo = indicadorDTO.tipo();
        }

        if(indicadorDTO.descricao() != null) {
            this.descricao = indicadorDTO.descricao();
        }

        if(indicadorDTO.nome() != null) {
            this.nome = indicadorDTO.nome();
        }
    }
}
