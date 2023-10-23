package com.byt3social.acompanhamento.models;

import com.byt3social.acompanhamento.dto.RepresentanteDTO;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Representante {
    private String nome;
    private String email;
    private String telefone;
    private String cargo;

    public void atualizar(RepresentanteDTO representante) {
        this.nome = representante.nome();
        this.email = representante.email();
        this.telefone = representante.telefone();
        this.cargo = representante.cargo();
    }
}
