package com.byt3social.acompanhamento.repositories;

import com.byt3social.acompanhamento.models.Arquivo;
import com.byt3social.acompanhamento.models.IndicadorSolicitado;
import com.byt3social.acompanhamento.dto.AcompanhamentoDTO;
import com.byt3social.acompanhamento.dto.RepresentanteDTO;
import com.byt3social.acompanhamento.models.Acompanhamento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class ArquivoRepositoryTest {
    @Autowired
    private ArquivoRepository arquivoRepository;

    @Test
    void testSalvarArquivo() {
        Acompanhamento acompanhamento = createAcompanhamento(1);
        Arquivo arquivo = new Arquivo("caminho/arquivo", "arquivo.txt", 1024L, acompanhamento);

        Arquivo savedArquivo = arquivoRepository.save(arquivo);

        assertEquals(arquivo, savedArquivo);
    }

    @Test
    void testBuscarArquivoPorId() {
        Acompanhamento acompanhamento = createAcompanhamento(1);
        Arquivo arquivo = new Arquivo("caminho/arquivo", "arquivo.txt", 1024L, acompanhamento);
        Arquivo savedArquivo = arquivoRepository.save(arquivo);
        Integer arquivoId = savedArquivo.getId();

        Optional<Arquivo> retrievedArquivo = arquivoRepository.findById(arquivoId);

        assertTrue(retrievedArquivo.isPresent());
        assertEquals(arquivoId, retrievedArquivo.get().getId());
    }

    @Test
    void testAtualizarArquivo() {
        Acompanhamento acompanhamento = createAcompanhamento(1);
        Arquivo arquivo = new Arquivo("caminho/arquivo", "arquivo.txt", 1024L, acompanhamento);
        Arquivo savedArquivo = arquivoRepository.save(arquivo);
        Integer arquivoId = savedArquivo.getId();

        Optional<Arquivo> retrievedArquivo = arquivoRepository.findById(arquivoId);
        assertTrue(retrievedArquivo.isPresent());

        Arquivo updatedArquivo = retrievedArquivo.get();
        updatedArquivo.setNomeArquivoOriginal("novo_nome.txt");
        arquivoRepository.save(updatedArquivo);

        retrievedArquivo = arquivoRepository.findById(arquivoId);
        assertTrue(retrievedArquivo.isPresent());
        assertEquals("novo_nome.txt", retrievedArquivo.get().getNomeArquivoOriginal());
    }

    @Test
    void testExcluirArquivo() {
        Acompanhamento acompanhamento = createAcompanhamento(1);
        Arquivo arquivo = new Arquivo("caminho/arquivo", "arquivo.txt", 1024L, acompanhamento);
        Arquivo savedArquivo = arquivoRepository.save(arquivo);
        Integer arquivoId = savedArquivo.getId();

        arquivoRepository.deleteById(arquivoId);

        Optional<Arquivo> deletedArquivo = arquivoRepository.findById(arquivoId);
        assertFalse(deletedArquivo.isPresent());
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
}
