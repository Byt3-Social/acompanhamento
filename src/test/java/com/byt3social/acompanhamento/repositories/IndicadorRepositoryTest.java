package com.byt3social.acompanhamento.repositories;

import com.byt3social.acompanhamento.models.Indicador;
import com.byt3social.acompanhamento.enums.TipoIndicador;
import com.byt3social.acompanhamento.dto.IndicadorDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class IndicadorRepositoryTest {
    @Autowired
    private IndicadorRepository indicadorRepository;

    @Test
    void testSalvarIndicador() {
        IndicadorDTO indicadorDTO = new IndicadorDTO("Nome do Indicador", "Descrição do Indicador", TipoIndicador.NUMBER);
        Indicador indicador = new Indicador(indicadorDTO);

        Indicador savedIndicador = indicadorRepository.save(indicador);

        assertEquals(indicador, savedIndicador);
    }

    @Test
    void testBuscarIndicadorPorId() {
        IndicadorDTO indicadorDTO = new IndicadorDTO("Nome do Indicador", "Descrição do Indicador", TipoIndicador.NUMBER);
        Indicador indicador = new Indicador(indicadorDTO);
        Indicador savedIndicador = indicadorRepository.save(indicador);
        Integer indicadorId = savedIndicador.getId();

        assertTrue(indicadorRepository.existsById(indicadorId));

        Indicador retrievedIndicador = indicadorRepository.findById(indicadorId).orElse(null);
        assertEquals(savedIndicador, retrievedIndicador);
    }

    @Test
    void testAtualizarIndicador() {
        IndicadorDTO indicadorDTO = new IndicadorDTO("Nome do Indicador", "Descrição do Indicador", TipoIndicador.NUMBER);
        Indicador indicador = new Indicador(indicadorDTO);
        Indicador savedIndicador = indicadorRepository.save(indicador);

        IndicadorDTO updatedDTO = new IndicadorDTO("Novo Nome", "Nova Descrição", TipoIndicador.NUMBER); 
        savedIndicador.atualizar(updatedDTO);

        Indicador updatedIndicador = indicadorRepository.findById(savedIndicador.getId()).orElse(null);
        assertEquals("Novo Nome", updatedIndicador.getNome());
        assertEquals("Nova Descrição", updatedIndicador.getDescricao());
        assertEquals(TipoIndicador.NUMBER, updatedIndicador.getTipo());
    }

    @Test
    void testExcluirIndicador() {
        IndicadorDTO indicadorDTO = new IndicadorDTO("Nome do Indicador", "Descrição do Indicador", TipoIndicador.NUMBER);
        Indicador indicador = new Indicador(indicadorDTO);
        Indicador savedIndicador = indicadorRepository.save(indicador);

        indicadorRepository.delete(savedIndicador);

        assertFalse(indicadorRepository.existsById(savedIndicador.getId()));
    }
}
