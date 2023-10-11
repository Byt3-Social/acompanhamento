package com.byt3social.acompanhamento.controllers;

import com.byt3social.acompanhamento.dto.IndicadorDTO;
import com.byt3social.acompanhamento.models.Indicador;
import com.byt3social.acompanhamento.repositories.IndicadorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IndicadorController {
    @Autowired
    private IndicadorRepository indicadorRepository;

    @GetMapping("/indicadores")
    public ResponseEntity consultarIndicadores() {
        List<Indicador> indicadores = indicadorRepository.findAll();

        return new ResponseEntity<>(indicadores, HttpStatus.OK);
    }

    @GetMapping("/indicadores/{id}")
    public ResponseEntity consultarIndicador(@PathVariable("id") Integer indicadorID) {
        Indicador indicador = indicadorRepository.findById(indicadorID).get();

        return new ResponseEntity<>(indicador, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/indicadores")
    public ResponseEntity cadastrarIndicador(@RequestBody IndicadorDTO indicadorDTO) {
        Indicador indicador = new Indicador(indicadorDTO);
        indicadorRepository.save(indicador);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Transactional
    @PutMapping("/indicadores/{id}")
    public ResponseEntity atualizarIndicador(@PathVariable("id") Integer indicadorID, @RequestBody IndicadorDTO indicadorDTO) {
        Indicador indicador = indicadorRepository.findById(indicadorID).get();
        indicador.atualizar(indicadorDTO);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/indicadores/{id}")
    public ResponseEntity excluirIndicador(@PathVariable("id") Integer indicadorID) {
        indicadorRepository.deleteById(indicadorID);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
