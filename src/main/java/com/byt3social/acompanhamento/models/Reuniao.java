package com.byt3social.acompanhamento.models;

import com.byt3social.acompanhamento.dto.OnlineMeetingDTO;
import com.byt3social.acompanhamento.enums.StatusReuniao;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "reunioes")
@Entity(name = "Reuniao")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
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
    private Acompanhamento acompanhamento;
    @OneToMany(mappedBy = "reuniao", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    @OrderBy(value = "escolhido DESC")
    private List<Horario> horarios = new ArrayList<>();

    public Reuniao(Acompanhamento acompanhamento, OnlineMeetingDTO onlineMeetingDTO) {
        this.organizacaoId = acompanhamento.getOrganizacaoId();
        this.acompanhamento = acompanhamento;
        this.link = onlineMeetingDTO.joinWebUrl();
        this.status = StatusReuniao.SOLICITADA;
    }

    public void agendarHorario() {
        this.status = StatusReuniao.AGENDADA;
    }
}
