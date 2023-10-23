package com.byt3social.acompanhamento.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Table(name = "arquivos")
@Entity(name = "Arquivo")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Arquivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "caminho_s3")
    private String caminhoS3;
    @Column(name = "nome_arquivo_original")
    private String nomeArquivoOriginal;
    private Long tamanho;
    @ManyToOne
    @JoinColumn(name = "acompanhamento_id")
    @JsonBackReference
    private Acompanhamento acompanhamento;
    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    public Arquivo(String caminhoArquivo, String nomeArquivo, Long tamanhoArquivo, Acompanhamento acompanhamento) {
        this.caminhoS3 = caminhoArquivo;
        this.nomeArquivoOriginal = nomeArquivo;
        this.tamanho = tamanhoArquivo;
        this.acompanhamento = acompanhamento;
    }
}
