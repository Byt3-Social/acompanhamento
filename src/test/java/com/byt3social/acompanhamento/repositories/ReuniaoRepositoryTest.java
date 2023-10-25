package com.byt3social.acompanhamento.repositories;

import com.byt3social.acompanhamento.models.Acompanhamento;
import com.byt3social.acompanhamento.models.IndicadorSolicitado;
import com.byt3social.acompanhamento.models.Reuniao;
import com.byt3social.acompanhamento.dto.AcompanhamentoDTO;
import com.byt3social.acompanhamento.dto.OnlineMeetingDTO;
import com.byt3social.acompanhamento.dto.RepresentanteDTO;
import com.byt3social.acompanhamento.enums.StatusReuniao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class ReuniaoRepositoryTest {

    @Autowired
    private ReuniaoRepository reuniaoRepository;

    @Test
    void testSalvarReuniao() {
        Reuniao reuniao = new Reuniao();
        reuniao.setLink("https://example.com/meeting");
        reuniao.setStatus(StatusReuniao.SOLICITADA);

        Reuniao savedReuniao = reuniaoRepository.save(reuniao);
        assertNotNull(savedReuniao.getId());
    }

    @Test
    void testBuscarReuniaoPorOrganizacaoId() {
        Integer organizacaoId = 1;
        Reuniao reuniao = new Reuniao();
        reuniao.setLink("https://example.com/meeting");
        reuniao.setStatus(StatusReuniao.SOLICITADA);
        reuniao.setOrganizacaoId(organizacaoId);

        Reuniao savedReuniao = reuniaoRepository.save(reuniao);

        List<Reuniao> reunioesEncontradas = reuniaoRepository.findByOrganizacaoId(organizacaoId, Sort.by("id"));

        assertNotNull(reunioesEncontradas);
        assertEquals(1, reunioesEncontradas.size());
        assertEquals(savedReuniao.getId(), reunioesEncontradas.get(0).getId());
    }

    @Test
    void testAgendarHorario() {
        OnlineMeetingDTO onlineMeetingDTO = new OnlineMeetingDTO("https://example.com/meeting");
        Acompanhamento acompanhamento = createAcompanhamento(1);
        Reuniao reuniao = new Reuniao(acompanhamento,onlineMeetingDTO);
        reuniao.setLink("https://example.com/meeting");
        reuniao.setStatus(StatusReuniao.SOLICITADA);
        assertEquals(StatusReuniao.SOLICITADA, reuniao.getStatus());

        reuniao.agendarHorario();

        assertEquals(StatusReuniao.AGENDADA, reuniao.getStatus());
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
