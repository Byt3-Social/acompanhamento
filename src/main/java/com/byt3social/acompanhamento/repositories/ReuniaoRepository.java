package com.byt3social.acompanhamento.repositories;

import com.byt3social.acompanhamento.models.Reuniao;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReuniaoRepository extends JpaRepository<Reuniao, Integer> {
    List<Reuniao> findByOrganizacaoId(Integer organizacaoId, Sort sort);
}
