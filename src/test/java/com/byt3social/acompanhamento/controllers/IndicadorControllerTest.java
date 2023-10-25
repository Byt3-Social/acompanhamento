package com.byt3social.acompanhamento.controllers;

import com.byt3social.acompanhamento.dto.IndicadorDTO;
import com.byt3social.acompanhamento.enums.TipoIndicador;
import com.byt3social.acompanhamento.models.Indicador;
import com.byt3social.acompanhamento.repositories.IndicadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IndicadorControllerTest {
    @InjectMocks
    private IndicadorController indicadorController;

    @Mock
    private IndicadorRepository indicadorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConsultarIndicadores() {
        List<Indicador> indicadores = new ArrayList<>();
        Mockito.when(indicadorRepository.findAll()).thenReturn(indicadores);

        ResponseEntity response = indicadorController.consultarIndicadores();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(indicadores, response.getBody());
    }

    @Test
    void testConsultarIndicador() {
        int indicadorId = 1;
        Indicador indicador = createIndicador();
        Mockito.when(indicadorRepository.findById(indicadorId)).thenReturn(Optional.of(indicador));

        ResponseEntity response = indicadorController.consultarIndicador(indicadorId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(indicador, response.getBody());
    }

    @Test
    void testCadastrarIndicador() {
        IndicadorDTO indicadorDTO = createIndicadorDTO();
        Mockito.when(indicadorRepository.save(Mockito.any(Indicador.class))).thenAnswer(invocation -> {
            Indicador indicador = invocation.getArgument(0);
            indicador.setId(1);
            return indicador;
        });

        ResponseEntity response = indicadorController.cadastrarIndicador(indicadorDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testAtualizarIndicador() {
        int indicadorId = 1;
        IndicadorDTO indicadorDTO = createIndicadorDTO();
        Indicador indicador = createIndicador();
        Mockito.when(indicadorRepository.findById(indicadorId)).thenReturn(Optional.of(indicador));

        ResponseEntity response = indicadorController.atualizarIndicador(indicadorId, indicadorDTO);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(indicadorDTO.nome(), indicador.getNome());
        assertEquals(indicadorDTO.descricao(), indicador.getDescricao());
        assertEquals(indicadorDTO.tipo(), indicador.getTipo());
    }

    @Test
    void testExcluirIndicador() {
        int indicadorId = 1;
        Mockito.doNothing().when(indicadorRepository).deleteById(indicadorId);

        ResponseEntity response = indicadorController.excluirIndicador(indicadorId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private Indicador createIndicador() {
        Indicador indicador = new Indicador();
        indicador.setId(1);
        indicador.setNome("Indicador Teste");
        indicador.setDescricao("Descrição do Indicador de Teste");
        indicador.setTipo(TipoIndicador.NUMBER);
        return indicador;
    }

    private IndicadorDTO createIndicadorDTO() {
        return new IndicadorDTO("Indicador Teste", "Descrição do Indicador de Teste", TipoIndicador.NUMBER);
    }
}
