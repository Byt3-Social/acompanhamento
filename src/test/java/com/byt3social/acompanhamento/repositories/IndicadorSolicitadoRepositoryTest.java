package com.byt3social.acompanhamento.repositories;

import com.byt3social.acompanhamento.dto.AcompanhamentoDTO;
import com.byt3social.acompanhamento.dto.IndicadorDTO;
import com.byt3social.acompanhamento.dto.IndicadorSolicitadoDTO;
import com.byt3social.acompanhamento.dto.RepresentanteDTO;
import com.byt3social.acompanhamento.enums.TipoIndicador;
import com.byt3social.acompanhamento.models.Acompanhamento;
import com.byt3social.acompanhamento.models.Indicador;
import com.byt3social.acompanhamento.models.IndicadorSolicitado;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class IndicadorSolicitadoRepositoryTest {
    @Autowired
    private IndicadorSolicitadoRepository indicadorSolicitadoRepository;

    @Autowired
    private AcompanhamentoRepository acompanhamentoRepository;

    @Autowired
    private IndicadorRepository indicadorRepository;

    @Test
    void testSalvarIndicadorSolicitado() {
        Acompanhamento acompanhamento = createAcompanhamento(1);
        Indicador indicador = createIndicador("teste", "teste", TipoIndicador.NUMBER);

        Acompanhamento savedAcompanhamento = acompanhamentoRepository.save(acompanhamento);
        Indicador savedIndicador = indicadorRepository.save(indicador);

        IndicadorSolicitado indicadorSolicitado = new IndicadorSolicitado(savedIndicador, savedAcompanhamento);
        IndicadorSolicitado savedIndicadorSolicitado = indicadorSolicitadoRepository.save(indicadorSolicitado);

        assertEquals(indicadorSolicitado, savedIndicadorSolicitado);
    }

    @Test
    void testBuscarIndicadorSolicitadoPorId() {
        Acompanhamento acompanhamento = createAcompanhamento(1);
        Indicador indicador = createIndicador("teste", "teste", TipoIndicador.NUMBER);

        Acompanhamento savedAcompanhamento = acompanhamentoRepository.save(acompanhamento);
        Indicador savedIndicador = indicadorRepository.save(indicador);

        IndicadorSolicitado indicadorSolicitado = new IndicadorSolicitado(savedIndicador, savedAcompanhamento);
        IndicadorSolicitado savedIndicadorSolicitado = indicadorSolicitadoRepository.save(indicadorSolicitado);
        Integer indicadorSolicitadoId = savedIndicadorSolicitado.getId();

        IndicadorSolicitado retrievedIndicadorSolicitado = indicadorSolicitadoRepository.findById(indicadorSolicitadoId).orElse(null);

        assertNotNull(retrievedIndicadorSolicitado);
        assertEquals(indicadorSolicitadoId, retrievedIndicadorSolicitado.getId());
    }

    @Test
    void testAtualizarIndicadorSolicitado() {
        Acompanhamento acompanhamento = createAcompanhamento(1);
        Indicador indicador = createIndicador("teste", "teste", TipoIndicador.NUMBER);

        Acompanhamento savedAcompanhamento = acompanhamentoRepository.save(acompanhamento);
        Indicador savedIndicador = indicadorRepository.save(indicador);

        IndicadorSolicitado indicadorSolicitado = new IndicadorSolicitado(savedIndicador, savedAcompanhamento);
        IndicadorSolicitado savedIndicadorSolicitado = indicadorSolicitadoRepository.save(indicadorSolicitado);

        IndicadorSolicitadoDTO updatedDTO = new IndicadorSolicitadoDTO(savedIndicadorSolicitado.getId(), "Novo Valor");
        savedIndicadorSolicitado.atualizar(updatedDTO);

        IndicadorSolicitado updatedIndicadorSolicitado = indicadorSolicitadoRepository.findById(savedIndicadorSolicitado.getId()).orElse(null);
        assertEquals("Novo Valor", updatedIndicadorSolicitado.getValor());
    }

    private Acompanhamento createAcompanhamento(Integer organizacaoId) {

        AcompanhamentoDTO acompanhamentoDTO = new AcompanhamentoDTO(
            1, organizacaoId, 1,
            new RepresentanteDTO("nome_representante", "emailrepresentante@gmail.com", "11111111111", "cargo_representante"),
            null, null, "info"
        );
        Acompanhamento acompanhamento = new Acompanhamento(acompanhamentoDTO);
        acompanhamento.setCreatedAt(new Date());
        acompanhamento.setUpdatedAt(new Date());
    
        List<IndicadorSolicitado> indicadoresSolicitados = createIndicadoresSolicitados();
        acompanhamento.vincularIndicadoresSolicitados(indicadoresSolicitados);
    
        return acompanhamento;
    }
    

    private List<IndicadorSolicitado> createIndicadoresSolicitados() {

        List<IndicadorSolicitado> indicadoresSolicitados = new ArrayList<>();
        indicadoresSolicitados.add(new IndicadorSolicitado());
        indicadoresSolicitados.add(new IndicadorSolicitado());

        return indicadoresSolicitados;
    }
    
    private Indicador createIndicador(String nome, String descricao, TipoIndicador tipo) {
        IndicadorDTO indicadorDTO = new IndicadorDTO(nome, descricao, tipo);
        return new Indicador(indicadorDTO);
    }
}
