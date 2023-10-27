package com.byt3social.acompanhamento.controllers;

import com.byt3social.acompanhamento.models.Horario;
import com.byt3social.acompanhamento.repositories.HorarioRepository;
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

public class HorarioControllerTest {
    @InjectMocks
    private HorarioController horarioController;

    @Mock
    private HorarioRepository horarioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConsultarHorarios() {
        List<Horario> horarios = new ArrayList<>();
        Mockito.when(horarioRepository.findAll()).thenReturn(horarios);

        ResponseEntity response = horarioController.consultarHorarios();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(horarios, response.getBody());
    }

    @Test
    void testConsultarHorario() {
        int horarioId = 1;
        Horario horario = createHorario();
        Mockito.when(horarioRepository.findById(horarioId)).thenReturn(Optional.of(horario));

        ResponseEntity response = horarioController.consultarHorarios(horarioId); 

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(horario, response.getBody());
    }

    @Test
    void testExcluirHorario() {
        int horarioId = 1;
        Mockito.doNothing().when(horarioRepository).deleteById(horarioId);

        ResponseEntity response = horarioController.excluirHorario(horarioId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private Horario createHorario() {
        Horario horario = new Horario();
        horario.setId(1);
        return horario;
    }
}
