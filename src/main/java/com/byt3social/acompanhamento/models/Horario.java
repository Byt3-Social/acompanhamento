package com.byt3social.acompanhamento.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Table(name = "horarios")
@Entity(name = "Horario")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "data_horario")
    private Date dataHorario;
    private Boolean escolhido;
    @ManyToOne
    @JoinColumn(name = "reuniao_id")
    @JsonBackReference
    private Reuniao reuniao;

    public Horario(Date horario, Reuniao reuniao) {
        this.dataHorario = horario;
        this.reuniao = reuniao;
        this.escolhido = false;

    }

    public void agendar(Reuniao reuniao) {
        this.escolhido = true;
        this.reuniao = reuniao;
    }

    public void desmarcar() {
        this.escolhido = false;
    }
}
