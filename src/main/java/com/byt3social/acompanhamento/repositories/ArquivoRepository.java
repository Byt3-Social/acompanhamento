package com.byt3social.acompanhamento.repositories;

import com.byt3social.acompanhamento.models.Arquivo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArquivoRepository extends JpaRepository<Arquivo, Integer> {
}
