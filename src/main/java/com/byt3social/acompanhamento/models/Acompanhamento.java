package com.byt3social.acompanhamento.models;

import com.byt3social.acompanhamento.dto.AcompanhamentoDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "acompanhamentos")
@Entity(name = "Acompanhamento")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
@Setter
public class Acompanhamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "nome", column = @Column(name = "nome_representante")),
            @AttributeOverride(name = "email", column = @Column(name = "email_representante")),
            @AttributeOverride(name = "telefone", column = @Column(name = "telefone_representante")),
            @AttributeOverride(name = "cargo", column = @Column(name = "cargo_representante"))
    })
    private Representante representante;
    @JsonProperty("informacoes_adicionais")
    @Column(name = "informacoes_adicionais")
    private String informacoesAdicionais;
    @JsonProperty("acao_id")
    @Column(name = "acao_id")
    private Integer acaoId;
    @JsonProperty("organizacao_id")
    @Column(name = "organizacao_id")
    private Integer organizacaoId;
    @JsonProperty("usuario_id")
    @Column(name = "usuario_id")
    private Integer usuarioId;
    @CreationTimestamp
    @JsonProperty("created_at")
    @Column(name = "created_at")
    private Date createdAt;
    @UpdateTimestamp
    @JsonProperty("updated_at")
    @Column(name = "updated_at")
    private Date updatedAt;
    @OneToMany(mappedBy = "acompanhamento")
    @JsonManagedReference
    private List<Arquivo> arquivos;
    @OneToMany(mappedBy = "acompanhamento")
    private List<Reuniao> reunioes;
    @OneToMany(mappedBy = "acompanhamento", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JsonManagedReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<IndicadorSolicitado> indicadoresSolicitados = new ArrayList<>();

    public Acompanhamento(AcompanhamentoDTO acompanhamentoDTO) {
        this.acaoId = acompanhamentoDTO.acaoId();
        this.organizacaoId = acompanhamentoDTO.organizacaoId();
        this.usuarioId = acompanhamentoDTO.usuarioId();
    }

    public void vincularIndicadoresSolicitados(List<IndicadorSolicitado> indicadoresSolicitados) {
        this.indicadoresSolicitados = indicadoresSolicitados;
    }
}
