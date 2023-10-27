package com.byt3social.acompanhamento.controllers;

import com.byt3social.acompanhamento.dto.IndicadorDTO;
import com.byt3social.acompanhamento.models.Indicador;
import com.byt3social.acompanhamento.repositories.IndicadorRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/indicadores")
public class IndicadorController {
    @Autowired
    private IndicadorRepository indicadorRepository;

    @Operation(summary = "Consultar todos os indicadores")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso!", 
        content = @Content(schema = @Schema(implementation = Indicador.class)))
    @GetMapping
    public ResponseEntity consultarIndicadores() {
        List<Indicador> indicadores = indicadorRepository.findAll();

        return new ResponseEntity<>(indicadores, HttpStatus.OK);
    }

    @Operation(summary = "Consultar um indicador específico")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso!", 
        content = @Content(schema = @Schema(implementation = Indicador.class)))
    @ApiResponse(responseCode = "404", description = "Indicador não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity consultarIndicador(@PathVariable("id") Integer indicadorID) {
        Indicador indicador = indicadorRepository.findById(indicadorID).get();

        return new ResponseEntity<>(indicador, HttpStatus.OK);
    }

    @Operation(summary = "Cadastrar um indicador")
    @ApiResponse(responseCode = "201", description = "Indicador cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Cadastro mal-sucedido, verifique os dados")
    @Transactional
    @PostMapping
    public ResponseEntity cadastrarIndicador(@RequestBody IndicadorDTO indicadorDTO) {
        Indicador indicador = new Indicador(indicadorDTO);
        indicadorRepository.save(indicador);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar um indicador")
    @ApiResponse(responseCode = "204", description = "Indicador atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Indicador não encontrado")
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity atualizarIndicador(@PathVariable("id") Integer indicadorID, @RequestBody IndicadorDTO indicadorDTO) {
        Indicador indicador = indicadorRepository.findById(indicadorID).get();
        indicador.atualizar(indicadorDTO);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Excluir um indicador")
    @ApiResponse(responseCode = "200", description = "Indicador excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Indicador não encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity excluirIndicador(@PathVariable("id") Integer indicadorID) {
        indicadorRepository.deleteById(indicadorID);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
