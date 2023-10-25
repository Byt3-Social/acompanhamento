package com.byt3social.acompanhamento.controllers;

import com.byt3social.acompanhamento.dto.HorarioDTO;
import com.byt3social.acompanhamento.dto.ReuniaoDTO;
import com.byt3social.acompanhamento.enums.StatusReuniao;
import com.byt3social.acompanhamento.models.Reuniao;
import com.byt3social.acompanhamento.services.ReuniaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReuniaoControllerTest {
    @InjectMocks
    private ReuniaoController reuniaoController;

    @Mock
    private ReuniaoService reuniaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConsultarReunioes() {
        List<Reuniao> reunioes = new ArrayList<>();
        Mockito.when(reuniaoService.consultarReunioes()).thenReturn(reunioes);

        ResponseEntity response = reuniaoController.consultarReunioes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reunioes, response.getBody());
    }

    @Test
    void testConsultarReuniao() {
        int reuniaoId = 1;
        Reuniao reuniao = createReuniao();
        Mockito.when(reuniaoService.consultarReuniao(reuniaoId)).thenReturn(reuniao);

        ResponseEntity response = reuniaoController.consultarReuniao(reuniaoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reuniao, response.getBody());
    }

    @Test
    void testSolicitarReuniao() {
        ReuniaoDTO reuniaoDTO = createReuniaoDTO();
        Mockito.doNothing().when(reuniaoService).solicitarReuniao(reuniaoDTO, "test-token");

        ResponseEntity response = reuniaoController.solicitarReuniao("test-token", reuniaoDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testAgendarReuniao() {
        int reuniaoId = 1;
        HorarioDTO horarioDTO = new HorarioDTO(1);
        Mockito.doNothing().when(reuniaoService).agendarHorario(reuniaoId, horarioDTO.horarioId(), "test-token");

        ResponseEntity response = reuniaoController.agendarReuniao("test-token", reuniaoId, horarioDTO);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    private Reuniao createReuniao() {
    Reuniao reuniao = new Reuniao();
    reuniao.setId(1);
    reuniao.setLink("https://example.com/meeting");
    reuniao.setStatus(StatusReuniao.SOLICITADA);
    reuniao.setOrganizacaoId(1); 
    return reuniao;
}

private ReuniaoDTO createReuniaoDTO() {
    List<Date> disponibilidades = new ArrayList<>();
    disponibilidades.add(new Date());
    disponibilidades.add(new Date());
    return new ReuniaoDTO(1, disponibilidades);
}
}
