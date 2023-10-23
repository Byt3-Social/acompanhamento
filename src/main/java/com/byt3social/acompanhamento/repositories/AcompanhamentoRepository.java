package com.byt3social.acompanhamento.repositories;

import com.byt3social.acompanhamento.models.Acompanhamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcompanhamentoRepository extends JpaRepository<Acompanhamento, Integer> {
    List<Acompanhamento> findByOrganizacaoId(Integer organizacaoId);
}
