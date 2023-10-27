package com.byt3social.acompanhamento.controllers;

import com.byt3social.acompanhamento.models.Horario;
import com.byt3social.acompanhamento.repositories.HorarioRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/horarios")
public class HorarioController {
    @Autowired
    private HorarioRepository horarioRepository;

    @Operation(summary = "Consultar todos os horários")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso!", content = @Content(schema = @Schema(implementation = Horario.class)))
    @ApiResponse(responseCode = "401", description = "Consulta mal-sucedida")
    @GetMapping
    public ResponseEntity consultarHorarios() {
        List<Horario> horarios = horarioRepository.findAll();

        return new ResponseEntity<>(horarios, HttpStatus.OK);
    }

    @Operation(summary = "Consultar um horário específico")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso!", content = @Content(schema = @Schema(implementation = Horario.class)))
    @ApiResponse(responseCode = "404", description = "Horário não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity consultarHorarios(@PathVariable("id") Integer horarioID) {
        Horario horario = horarioRepository.findById(horarioID).get();

        return new ResponseEntity<>(horario, HttpStatus.OK);
    }

    @Operation(summary = "Excluir um horário")
    @ApiResponse(responseCode = "200", description = "Horário excluído com sucesso!")
    @ApiResponse(responseCode = "404", description = "Horário não encontrado")    
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity excluirHorario(@PathVariable("id") Integer horarioID) {
        horarioRepository.deleteById(horarioID);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
