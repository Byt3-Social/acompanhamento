package com.byt3social.acompanhamento.repositories;

import com.byt3social.acompanhamento.dto.AcompanhamentoDTO;
import com.byt3social.acompanhamento.dto.RepresentanteDTO;
import com.byt3social.acompanhamento.models.Acompanhamento;
import com.byt3social.acompanhamento.models.IndicadorSolicitado;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class AcompanhamentoRepositoryTest {
    @Autowired
    private AcompanhamentoRepository acompanhamentoRepository;

    @Test
    void testFindByOrganizacaoId() {

        Acompanhamento acompanhamento1 = createAcompanhamento(123);
        Acompanhamento acompanhamento2 = createAcompanhamento(123);

        acompanhamentoRepository.save(acompanhamento1);
        acompanhamentoRepository.save(acompanhamento2);
        List<Acompanhamento> acompanhamentos = acompanhamentoRepository.findByOrganizacaoId(123);

        assertEquals(2, acompanhamentos.size());
    }

    @Test
    void testCadastrarAcompanhamento() {

        Acompanhamento acompanhamento = createAcompanhamento(456);

        Acompanhamento savedAcompanhamento = acompanhamentoRepository.save(acompanhamento);

        assertEquals(acompanhamento, savedAcompanhamento);
    }

    @Test
    void testVincularIndicadoresSolicitados() {

        Acompanhamento acompanhamento = createAcompanhamento(789);
        List<IndicadorSolicitado> indicadoresSolicitados = createIndicadoresSolicitados();

        acompanhamento.vincularIndicadoresSolicitados(indicadoresSolicitados);

        assertEquals(indicadoresSolicitados, acompanhamento.getIndicadoresSolicitados());
    }

    @Test
    void testAtualizarAcompanhamento() {

        Acompanhamento acompanhamento = createAcompanhamento(123);
        AcompanhamentoDTO updatedDTO = new AcompanhamentoDTO(
            1, 123, 2,
            new RepresentanteDTO("Updated Name", "Updated Email", "Updated Phone", "Updated Cargo"),
            null, null, "Updated Info"
        );

        acompanhamento.atualizar(updatedDTO);

        assertEquals("Updated Name", acompanhamento.getRepresentante().getNome());
        assertEquals("Updated Email", acompanhamento.getRepresentante().getEmail());
        assertEquals("Updated Phone", acompanhamento.getRepresentante().getTelefone());
        assertEquals("Updated Cargo", acompanhamento.getRepresentante().getCargo());
    }

    @Test
    void testExcluirAcompanhamento() {

        Acompanhamento acompanhamento = createAcompanhamento(789);
        
        Acompanhamento savedAcompanhamento = acompanhamentoRepository.save(acompanhamento);
        acompanhamentoRepository.delete(savedAcompanhamento);

        boolean exists = acompanhamentoRepository.existsById(savedAcompanhamento.getId());
        assertFalse(exists, "Acompanhamento should be deleted");
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
 