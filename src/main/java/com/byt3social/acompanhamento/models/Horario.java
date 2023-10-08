package com.byt3social.acompanhamento.models;

import com.byt3social.acompanhamento.dto.HorarioDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Table(name = "horarios")
@Entity(name = "Horario")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
@Setter
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonProperty("data_horario")
    @Column(name = "data_horario")
    private Date dataHorario;
    private Boolean escolhido;
    @OneToOne(mappedBy = "horario")
    @JsonBackReference
    private Reuniao reuniao;

    public Horario(HorarioDTO horarioDTO) {
        this.dataHorario = horarioDTO.dataHorario();
    }

    public void agendar(Reuniao reuniao) {
        this.escolhido = true;
        this.reuniao = reuniao;
    }
}
