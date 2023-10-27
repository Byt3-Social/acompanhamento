package com.byt3social.acompanhamento.repositories;

import com.byt3social.acompanhamento.dto.AcompanhamentoDTO;
import com.byt3social.acompanhamento.dto.OnlineMeetingDTO;
import com.byt3social.acompanhamento.dto.RepresentanteDTO;
import com.byt3social.acompanhamento.models.Acompanhamento;
import com.byt3social.acompanhamento.models.Horario;
import com.byt3social.acompanhamento.models.IndicadorSolicitado;
import com.byt3social.acompanhamento.models.Reuniao;
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
public class HorarioRepositoryTest {
    @Autowired
    private HorarioRepository horarioRepository;

    @Test
    void testSalvarHorario() {
        Reuniao reuniao = criarReuniao();
        Horario horario = new Horario(new Date(), reuniao);

        Horario savedHorario = horarioRepository.save(horario);

        assertEquals(horario, savedHorario);
    }

    @Test
    void testBuscarHorarioPorId() {
        Reuniao reuniao = criarReuniao();
        Horario horario = new Horario(new Date(), reuniao);
        Horario savedHorario = horarioRepository.save(horario);
        Integer horarioId = savedHorario.getId();

        Optional<Horario> retrievedHorario = horarioRepository.findById(horarioId);

        assertTrue(retrievedHorario.isPresent());
        assertEquals(horarioId, retrievedHorario.get().getId());
    }

    @Test
    void testAgendarHorario() {
        Reuniao reuniao = criarReuniao();
        Horario horario = new Horario(new Date(), reuniao);
    
        assertFalse(horario.getEscolhido());
        horario.agendar(reuniao);
    
        assertTrue(horario.getEscolhido()); 
        assertEquals(reuniao, horario.getReuniao());
    }
    
    @Test
    void testDesmarcarHorario() {
        Reuniao reuniao = criarReuniao();
        Horario horario = new Horario(new Date(), reuniao);

        horario.agendar(reuniao);

        assertTrue(horario.getEscolhido()); 
        horario.desmarcar();

        assertFalse(horario.getEscolhido()); 
    }

    private Reuniao criarReuniao() {
    Acompanhamento acompanhamento = createAcompanhamento(1);
    OnlineMeetingDTO onlineMeetingDTO = new OnlineMeetingDTO("URL_DA_REUNIAO");
    return new Reuniao(acompanhamento, onlineMeetingDTO);
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
