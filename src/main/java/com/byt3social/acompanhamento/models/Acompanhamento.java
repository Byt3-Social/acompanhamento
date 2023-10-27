package com.byt3social.acompanhamento.models;

import com.byt3social.acompanhamento.dto.AcompanhamentoDTO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "acompanhamentos")
@Entity(name = "Acompanhamento")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
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
    @Column(name = "informacoes_adicionais")
    private String informacoesAdicionais;
    @Column(name = "acao_id")
    private Integer acaoId;
    @Column(name = "organizacao_id")
    private Integer organizacaoId;
    @Column(name = "usuario_id")
    private Integer usuarioId;
    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
    @OneToMany(mappedBy = "acompanhamento")
    @JsonManagedReference
    private List<Arquivo> arquivos;
    @OneToMany(mappedBy = "acompanhamento")
    private List<Reuniao> reunioes;
    @OneToMany(mappedBy = "acompanhamento", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<IndicadorSolicitado> indicadoresSolicitados = new ArrayList<>();

    public Acompanhamento(AcompanhamentoDTO acompanhamentoDTO) {
        this.acaoId = acompanhamentoDTO.acaoId();
        this.organizacaoId = acompanhamentoDTO.organizacaoId();
        this.usuarioId = acompanhamentoDTO.usuarioId();
        this.informacoesAdicionais = acompanhamentoDTO.informacoesAdicionais();
        this.representante = new Representante("", "", "", "");
    }

    public void vincularIndicadoresSolicitados(List<IndicadorSolicitado> indicadoresSolicitados) {
        this.indicadoresSolicitados = indicadoresSolicitados;
    }

    public void atualizar(AcompanhamentoDTO acompanhamentoDTO) {
        this.representante.atualizar(acompanhamentoDTO.representante());
    }
}
