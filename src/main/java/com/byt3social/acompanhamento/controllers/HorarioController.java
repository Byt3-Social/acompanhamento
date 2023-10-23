package com.byt3social.acompanhamento.controllers;

import com.byt3social.acompanhamento.models.Horario;
import com.byt3social.acompanhamento.repositories.HorarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HorarioController {
    @Autowired
    private HorarioRepository horarioRepository;

    @GetMapping("/horarios")
    public ResponseEntity consultarHorarios() {
        List<Horario> horarios = horarioRepository.findAll();

        return new ResponseEntity<>(horarios, HttpStatus.OK);
    }

    @GetMapping("/horarios/{id}")
    public ResponseEntity consultarHorarios(@PathVariable("id") Integer horarioID) {
        Horario horario = horarioRepository.findById(horarioID).get();

        return new ResponseEntity<>(horario, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/horarios/{id}")
    public ResponseEntity excluirHorario(@PathVariable("id") Integer horarioID) {
        horarioRepository.deleteById(horarioID);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
