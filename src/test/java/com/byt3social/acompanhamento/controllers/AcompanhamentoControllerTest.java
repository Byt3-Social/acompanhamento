package com.byt3social.acompanhamento.controllers;

import com.byt3social.acompanhamento.dto.AcompanhamentoDTO;
import com.byt3social.acompanhamento.dto.RepresentanteDTO;
import com.byt3social.acompanhamento.models.Acompanhamento;
import com.byt3social.acompanhamento.models.Arquivo;
import com.byt3social.acompanhamento.models.IndicadorSolicitado;
import com.byt3social.acompanhamento.services.AcompanhamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.MockitoAnnotations;

public class AcompanhamentoControllerTest {
    @InjectMocks
    private AcompanhamentoController acompanhamentoController;

    @Mock
    private AcompanhamentoService acompanhamentoService;

    @BeforeEach
    void setUp() {  
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConsultarAcompanhamentos() {

        List<Acompanhamento> acompanhamentos = new ArrayList<>();
        Mockito.when(acompanhamentoService.consultarAcompanhamentos()).thenReturn(acompanhamentos);

        ResponseEntity response = acompanhamentoController.consultarAcompanhamentos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(acompanhamentos, response.getBody());
    }

    @Test
    void testConsultarAcompanhamento() {

        int acompanhamentoId = 1;
        Acompanhamento acompanhamento = createAcompanhamento(1);
        Mockito.when(acompanhamentoService.consultarAcompanhamento(acompanhamentoId)).thenReturn(acompanhamento);

        ResponseEntity response = acompanhamentoController.consultarAcompanhamento(acompanhamentoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(acompanhamento, response.getBody());
    }

    @Test
    void testCadastrarAcompanhamento() {

        AcompanhamentoDTO acompanhamentoDTO = new AcompanhamentoDTO(
            1, 1, 1,
            new RepresentanteDTO("nome_representante", "emailrepresentante@gmail.com", "11111111111", "cargo_representante"),
            null, null, "info"
        );
        Mockito.doNothing().when(acompanhamentoService).cadastrarAcompanhamento(acompanhamentoDTO);

        ResponseEntity response = acompanhamentoController.cadastrarAcompanhamento(acompanhamentoDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testAtualizarAcompanhamento() {
        int acompanhamentoId = 1;
        AcompanhamentoDTO acompanhamentoDTO = createAcompanhamentoDTO();
        Mockito.doNothing().when(acompanhamentoService).atualizarAcompanhamento(acompanhamentoId, acompanhamentoDTO);

        ResponseEntity response = acompanhamentoController.atualizarAcompanhamento(acompanhamentoId, acompanhamentoDTO);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testExcluirAcompanhamento() {
        int acompanhamentoId = 1;
        Mockito.doNothing().when(acompanhamentoService).excluirAcompanhamento(acompanhamentoId);

        ResponseEntity response = acompanhamentoController.excluirAcompanhamento(acompanhamentoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testSalvarArquivoAcompanhamento() {
        int acompanhamentoId = 1;
        MultipartFile arquivo = createMultipartFile();
        Arquivo arquivoSubmetido = createArquivo();
        Mockito.when(acompanhamentoService.salvarArquivoAcompanhamento(acompanhamentoId, arquivo)).thenReturn(arquivoSubmetido);

        ResponseEntity response = acompanhamentoController.salvarArquivoAcompanhamento(acompanhamentoId, arquivo);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(arquivoSubmetido, response.getBody());
    }

    @Test
    void testRecuperarArquivoAcompanhamento() {
        int arquivoId = 1;
        String urlArquivo = "example-url";
        Mockito.when(acompanhamentoService.recuperarArquivoAcompanhamento(arquivoId)).thenReturn(urlArquivo);

        ResponseEntity response = acompanhamentoController.recuperarArquivoAcompanhamento(arquivoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(urlArquivo, response.getBody());
    }

    @Test
    void testExcluirArquivoAcompanhamento() {
        int arquivoId = 1;
        Mockito.doNothing().when(acompanhamentoService).excluirArquivoAcompanhamento(arquivoId);

        ResponseEntity response = acompanhamentoController.excluirArquivoAcompanhamento(arquivoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testBuscarAcompanhamentosOrganizacao() {
        String organizacaoId = "1";
        List<Acompanhamento> acompanhamentos = createAcompanhamentos();
        Mockito.when(acompanhamentoService.buscarAcompanhamentos(Integer.valueOf(organizacaoId))).thenReturn(acompanhamentos);

        ResponseEntity response = acompanhamentoController.buscarAcompanhamentosOrganizacao(organizacaoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(acompanhamentos, response.getBody());
    }

    private Acompanhamento createAcompanhamento(Integer organizacaoId) {

        AcompanhamentoDTO acompanhamentoDTO = new AcompanhamentoDTO(
            1, organizacaoId, 1,
            new RepresentanteDTO("nome_representante", "emailrepresentante@gmail.com", "11111111111", "cargo_representante"),
            null, null, "info"
        );
        Acompanhamento acompanhamento = new Acompanhamento(acompanhamentoDTO);
        acompanhamento.setCreatedAt(new Date());
        acompanhamento.setUpdatedAt(new Date());
    
        List<IndicadorSolicitado> indicadoresSolicitados = createIndicadoresSolicitados();
        acompanhamento.vincularIndicadoresSolicitados(indicadoresSolicitados);
    
        return acompanhamento;
    }
    

    private List<IndicadorSolicitado> createIndicadoresSolicitados() {

        List<IndicadorSolicitado> indicadoresSolicitados = new ArrayList<>();
        indicadoresSolicitados.add(new IndicadorSolicitado());
        indicadoresSolicitados.add(new IndicadorSolicitado());

        return indicadoresSolicitados;
    }

    private AcompanhamentoDTO createAcompanhamentoDTO() {
    return new AcompanhamentoDTO(
        1, 1, 1,
        new RepresentanteDTO("nome_representante", "emailrepresentante@gmail.com", "11111111111", "cargo_representante"),
        null, null, "info"
    );
    }

    private MultipartFile createMultipartFile() {
        return new MockMultipartFile("file", "test.txt", "text/plain", "Hello, World!".getBytes());
    }

    private Arquivo createArquivo() {
        Arquivo arquivo = new Arquivo();
        arquivo.setId(1);
        arquivo.setNomeArquivoOriginal("test.txt");
        return arquivo;
    }

    private List<Acompanhamento> createAcompanhamentos() {

        List<Acompanhamento> acompanhamentos = new ArrayList<>();
        acompanhamentos.add(createAcompanhamento(1));
        acompanhamentos.add(createAcompanhamento(2));
        return acompanhamentos;
    }
}
