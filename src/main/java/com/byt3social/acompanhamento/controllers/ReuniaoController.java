package com.byt3social.acompanhamento.controllers;

import com.byt3social.acompanhamento.dto.HorarioDTO;
import com.byt3social.acompanhamento.dto.ReuniaoDTO;
import com.byt3social.acompanhamento.models.Reuniao;
import com.byt3social.acompanhamento.services.ReuniaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReuniaoController {
    @Autowired
    private ReuniaoService reuniaoService;

    @GetMapping("/reunioes")
    public ResponseEntity consultarReunioes() {
        List<Reuniao> reuniaos = reuniaoService.consultarReunioes();

        return new ResponseEntity<>(reuniaos, HttpStatus.OK);
    }

    @GetMapping("/reunioes/{id}")
    public ResponseEntity consultarReuniao(@PathVariable("id") Integer reuniaoID) {
        Reuniao reuniao = reuniaoService.consultarReuniao(reuniaoID);

        return new ResponseEntity<>(reuniao, HttpStatus.OK);
    }

    @PostMapping("/reunioes")
    public ResponseEntity solicitarReuniao(@RequestBody ReuniaoDTO reuniaoDTO) {
        reuniaoService.solicitarReuniao(reuniaoDTO);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/reunioes/{id}")
    public ResponseEntity agendarReuniao(@PathVariable("id") Integer reuniaoID, @RequestBody HorarioDTO horarioDTO) {
        reuniaoService.agendarHorario(reuniaoID, horarioDTO.horarioId());

        return new ResponseEntity(HttpStatus.OK);
    }
}
