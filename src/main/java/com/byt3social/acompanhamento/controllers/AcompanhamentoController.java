package com.byt3social.acompanhamento.controllers;

import com.byt3social.acompanhamento.dto.AcompanhamentoDTO;
import com.byt3social.acompanhamento.models.Acompanhamento;
import com.byt3social.acompanhamento.models.Arquivo;
import com.byt3social.acompanhamento.services.AcompanhamentoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/acompanhamentos")
public class AcompanhamentoController {
    @Autowired
    private AcompanhamentoService acompanhamentoService;

    @Operation(summary = "Consultar todos os acompanhamentos")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso!", content = @Content(schema = @Schema(implementation = Acompanhamento.class)))
    @ApiResponse(responseCode = "401", description = "Consulta mal-sucedida")
    @GetMapping
    public ResponseEntity<List<Acompanhamento>> consultarAcompanhamentos() {
        List<Acompanhamento> acompanhamentos = acompanhamentoService.consultarAcompanhamentos();
        return new ResponseEntity<>(acompanhamentos, HttpStatus.OK);
    }

    @Operation(summary = "Consultar um acompanhamento específico cadastrado")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso!", content = @Content(schema = @Schema(implementation = Acompanhamento.class)))
    @ApiResponse(responseCode = "404", description = "Acompanhamento não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<Acompanhamento> consultarAcompanhamento(@PathVariable("id") Integer acompanhamentoID) {
        Acompanhamento acompanhamento = acompanhamentoService.consultarAcompanhamento(acompanhamentoID);
        return new ResponseEntity<>(acompanhamento, HttpStatus.OK);
    }

    @Operation(summary = "Cadastrar uma acompanhamento")
    @ApiResponse(responseCode = "201", description = "Acompanhamento cadasatrado com sucesso!")
    @ApiResponse(responseCode = "400", description = "Cadastro mal-sucedido, verifique os dados")
    @PostMapping
    public ResponseEntity<Void> cadastrarAcompanhamento(@RequestBody AcompanhamentoDTO acompanhamentoDTO) {
        acompanhamentoService.cadastrarAcompanhamento(acompanhamentoDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar um acompanhamento cadastrado")
    @ApiResponse(responseCode = "204", description = "Acompanhamento atualizado com sucesso!")
    @ApiResponse(responseCode = "404", description = "Acompanhamento não encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarAcompanhamento(@PathVariable("id") Integer acompanhamentoID, @RequestBody AcompanhamentoDTO acompanhamentoDTO) {
        acompanhamentoService.atualizarAcompanhamento(acompanhamentoID, acompanhamentoDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Deletar um acompanhamento cadastrado")
    @ApiResponse(responseCode = "200", description = "Acompanhamento deletado com sucesso!")
    @ApiResponse(responseCode = "404", description = "Acompanhamento não encontrado")    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAcompanhamento(@PathVariable("id") Integer acompanhamentoID) {
        acompanhamentoService.excluirAcompanhamento(acompanhamentoID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Salvar um arquivo em um acompanhamento")
    @ApiResponse(responseCode = "200", description = "Arquivo salvo com sucesso!", content = @Content(schema = @Schema(implementation = Arquivo.class)))
    @ApiResponse(responseCode = "400", description = "Cadastro mal-sucedido, verifique os dados")
    @PostMapping("/{id}/arquivos")
    public ResponseEntity<Arquivo> salvarArquivoAcompanhamento(@PathVariable("id") Integer acompanhamentoID, @RequestParam("arquivo") MultipartFile arquivo) {
        Arquivo arquivoSubmetido = acompanhamentoService.salvarArquivoAcompanhamento(acompanhamentoID, arquivo);
        return new ResponseEntity<>(arquivoSubmetido, HttpStatus.OK);
    }


    @Operation(summary = "Recuperar um arquivo de um acompanhamento")
    @ApiResponse(responseCode = "200", description = "Arquivo recuperado com sucesso!", content = @Content(schema = @Schema(implementation = String.class)))
    @ApiResponse(responseCode = "404", description = "Arquivo não encontrado")
    @GetMapping("/arquivos/{id}")
    public ResponseEntity<String> recuperarArquivoAcompanhamento(@PathVariable("id") Integer arquivoID) {
        String urlArquivo = acompanhamentoService.recuperarArquivoAcompanhamento(arquivoID);
        return new ResponseEntity<>(urlArquivo, HttpStatus.OK);
    }


    @Operation(summary = "Deletar um arquivo de um acompanhamento")
    @ApiResponse(responseCode = "200", description = "Arquivo deletado com sucesso!")
    @ApiResponse(responseCode = "404", description = "Arquivo não encontrado")   
    @DeleteMapping("/arquivos/{id}")
    public ResponseEntity<Void> excluirArquivoAcompanhamento(@PathVariable("id") Integer arquivoID) {
        acompanhamentoService.excluirArquivoAcompanhamento(arquivoID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Recuperar acompanhamentos de uma organização")
    @ApiResponse(responseCode = "200", description = "Acompanhamentos recuperados com sucesso!", content = @Content(schema = @Schema(implementation = Acompanhamento.class)))
    @ApiResponse(responseCode = "404", description = "Organização não encontrada") 
    @GetMapping("/organizacoes")
    public ResponseEntity<List<Acompanhamento>> buscarAcompanhamentosOrganizacao(@RequestHeader("B3Social-Organizacao") String organizacaoId) {
        List<Acompanhamento> acompanhamentos = acompanhamentoService.buscarAcompanhamentos(Integer.valueOf(organizacaoId));
        return new ResponseEntity<>(acompanhamentos, HttpStatus.OK);
    }
}
