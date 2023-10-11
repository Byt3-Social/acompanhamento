package com.byt3social.acompanhamento.models;

import com.byt3social.acompanhamento.dto.OnlineMeetingDTO;
import com.byt3social.acompanhamento.dto.ReuniaoDTO;
import com.byt3social.acompanhamento.enums.StatusReuniao;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "reunioes")
@Entity(name = "Reuniao")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
@Setter
public class Reuniao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String link;
    @Enumerated(value = EnumType.STRING)
    private StatusReuniao status;
    @JsonProperty("organizacao_id")
    @Column(name = "organizacao_id")
    private Integer organizacaoId;
    @ManyToOne
    @JoinColumn(name = "acompanhamento_id")
    @JsonBackReference
    private Acompanhamento acompanhamento;
    @OneToOne
    @JoinColumn(name = "horario_id")
    @JsonManagedReference
    private Horario horario;

    public Reuniao(ReuniaoDTO reuniaoDTO, Acompanhamento acompanhamento, OnlineMeetingDTO onlineMeetingDTO) {
        this.organizacaoId = reuniaoDTO.organizacaoId();
        this.acompanhamento = acompanhamento;
        this.link = onlineMeetingDTO.joinWebUrl();
        this.status = StatusReuniao.SOLICITADA;
    }

    public void agendarHorario(Horario horario) {
        this.horario = horario;
        this.status = StatusReuniao.AGENDADA;
    }
}
