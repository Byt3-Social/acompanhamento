package com.byt3social.acompanhamento.controllers;

import com.byt3social.acompanhamento.dto.HorarioDTO;
import com.byt3social.acompanhamento.dto.ReuniaoDTO;
import com.byt3social.acompanhamento.models.Reuniao;
import com.byt3social.acompanhamento.services.ReuniaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/reunioes")
@RestController
public class ReuniaoController {
    @Autowired
    private ReuniaoService reuniaoService;

    @Operation(summary = "Consultar todas as reuniões")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso!", 
        content = @Content(schema = @Schema(implementation = Reuniao.class)))
    @GetMapping
    public ResponseEntity consultarReunioes() {
        List<Reuniao> reuniaos = reuniaoService.consultarReunioes();

        return new ResponseEntity<>(reuniaos, HttpStatus.OK);
    }

    @Operation(summary = "Consultar reuniões de uma organização")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso!", 
        content = @Content(schema = @Schema(implementation = Reuniao.class)))
    @GetMapping("/organizacoes")
    public ResponseEntity consultarReunioesOrganizacao(@RequestHeader("B3Social-Organizacao") String organizacaoId) {
        List<Reuniao> reuniaos = reuniaoService.consultarReunioes(Integer.valueOf(organizacaoId));

        return new ResponseEntity<>(reuniaos, HttpStatus.OK);
    }

    @Operation(summary = "Consultar uma reunião específica")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso!", 
        content = @Content(schema = @Schema(implementation = Reuniao.class)))
    @ApiResponse(responseCode = "404", description = "Reunião não encontrada")
    @GetMapping("/{id}")
    public ResponseEntity consultarReuniao(@PathVariable("id") Integer reuniaoID) {
        Reuniao reuniao = reuniaoService.consultarReuniao(reuniaoID);

        return new ResponseEntity<>(reuniao, HttpStatus.OK);
    }

    @Operation(summary = "Solicitar uma reunião")
    @ApiResponse(responseCode = "201", description = "Reunião solicitada com sucesso")
    @ApiResponse(responseCode = "400", description = "Solicitação de reunião mal-sucedida, verifique os dados")
    @PostMapping
    public ResponseEntity solicitarReuniao(@RequestHeader("Authorization") String token, @RequestBody ReuniaoDTO reuniaoDTO) {
        reuniaoService.solicitarReuniao(reuniaoDTO, token);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Operation(summary = "Agendar uma reunião")
    @ApiResponse(responseCode = "204", description = "Reunião agendada com sucesso")
    @ApiResponse(responseCode = "404", description = "Reunião não encontrada")
    @PutMapping("/{id}")
    public ResponseEntity agendarReuniao(@RequestHeader("Authorization") String token, @PathVariable("id") Integer reuniaoID, @RequestBody HorarioDTO horarioDTO) {
        reuniaoService.agendarHorario(reuniaoID, horarioDTO.horarioId(), token);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
