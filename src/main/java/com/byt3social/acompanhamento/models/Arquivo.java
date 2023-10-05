package com.byt3social.acompanhamento.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Table(name = "arquivos")
@Entity(name = "Arquivo")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
@Setter
public class Arquivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonProperty("caminho_s3")
    @Column(name = "caminho_s3")
    private String caminhoS3;
    @JsonProperty("nome_arquivo_original")
    @Column(name = "nome_arquivo_original")
    private String nomeArquivoOriginal;
    private Long tamanho;
    @ManyToOne
    @JoinColumn(name = "acompanhamento_id")
    @JsonBackReference
    private Acompanhamento acompanhamento;
    @CreationTimestamp
    @JsonProperty("created_at")
    @Column(name = "created_at")
    private Date createdAt;
    @UpdateTimestamp
    @JsonProperty("updated_at")
    @Column(name = "updated_at")
    private Date updatedAt;

    public Arquivo(String caminhoArquivo, String nomeArquivo, Long tamanhoArquivo, Acompanhamento acompanhamento) {
        this.caminhoS3 = caminhoArquivo;
        this.nomeArquivoOriginal = nomeArquivo;
        this.tamanho = tamanhoArquivo;
        this.acompanhamento = acompanhamento;
    }
}
