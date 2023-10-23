package com.byt3social.acompanhamento.controllers;

import com.byt3social.acompanhamento.dto.AcompanhamentoDTO;
import com.byt3social.acompanhamento.models.Acompanhamento;
import com.byt3social.acompanhamento.models.Arquivo;
import com.byt3social.acompanhamento.services.AcompanhamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class AcompanhamentoController {
    @Autowired
    private AcompanhamentoService acompanhamentoService;

    @GetMapping("/acompanhamentos")
    public ResponseEntity consultarAcompanhamentos() {
        List<Acompanhamento> acompanhamentos = acompanhamentoService.consultarAcompanhamentos();

        return new ResponseEntity<>(acompanhamentos, HttpStatus.OK);
    }

    @GetMapping("/acompanhamentos/{id}")
    public ResponseEntity consultarAcompanhamento(@PathVariable("id") Integer acompanhamentoID) {
       Acompanhamento acompanhamento = acompanhamentoService.consultarAcompanhamento(acompanhamentoID);

        return new ResponseEntity<>(acompanhamento, HttpStatus.OK);
    }

    @PostMapping("/acompanhamentos")
    public ResponseEntity cadastrarAcompanhamento(@RequestBody AcompanhamentoDTO acompanhamentoDTO) {
        acompanhamentoService.cadastrarAcompanhamento(acompanhamentoDTO);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/acompanhamentos/{id}")
    public ResponseEntity atualizarAcompanhamento(@PathVariable("id") Integer acompanhamentoID, @RequestBody AcompanhamentoDTO acompanhamentoDTO) {
        acompanhamentoService.atualizarAcompanhamento(acompanhamentoID, acompanhamentoDTO);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/acompanhamentos/{id}")
    public ResponseEntity excluirAcompanhamento(@PathVariable("id") Integer acompanhamentoID) {
        acompanhamentoService.excluirAcompanhamento(acompanhamentoID);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/acompanhamentos/{id}/arquivos")
    public ResponseEntity salvarArquivoAcompanhamento(@PathVariable("id") Integer acompanhamentoID, @RequestBody MultipartFile arquivo) {
        Arquivo arquivoSubmetido = acompanhamentoService.salvarArquivoAcompanhamento(acompanhamentoID, arquivo);

        return new ResponseEntity(arquivoSubmetido, HttpStatus.OK);
    }

    @GetMapping("/acompanhamentos/arquivos/{id}")
    public ResponseEntity recuperarArquivoAcompanhamento(@PathVariable("id") Integer arquivoID) {
        String urlArquivo = acompanhamentoService.recuperarArquivoAcompanhamento(arquivoID);

        return new ResponseEntity(urlArquivo, HttpStatus.OK);
    }

    @DeleteMapping("/acompanhamentos/arquivos/{id}")
    public ResponseEntity excluirArquivoAcompanhamento(@PathVariable("id") Integer arquivoID) {
        acompanhamentoService.excluirArquivoAcompanhamento(arquivoID);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/acompanhamentos/organizacoes")
    public ResponseEntity buscarAcompanhamentosOrganizacao(@RequestHeader("B3Social-Organizacao") String organizacaoId) {
        List<Acompanhamento> acompanhamentos = acompanhamentoService.buscarAcompanhamentos(Integer.valueOf(organizacaoId));

        return new ResponseEntity(acompanhamentos, HttpStatus.OK);
    }
}
