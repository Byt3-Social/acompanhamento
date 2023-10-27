package com.byt3social.acompanhamento.services;

import com.byt3social.acompanhamento.dto.AcompanhamentoDTO;
import com.byt3social.acompanhamento.dto.IndicadorSolicitadoDTO;
import com.byt3social.acompanhamento.models.Acompanhamento;
import com.byt3social.acompanhamento.models.Arquivo;
import com.byt3social.acompanhamento.models.Indicador;
import com.byt3social.acompanhamento.models.IndicadorSolicitado;
import com.byt3social.acompanhamento.repositories.AcompanhamentoRepository;
import com.byt3social.acompanhamento.repositories.ArquivoRepository;
import com.byt3social.acompanhamento.repositories.IndicadorRepository;
import com.byt3social.acompanhamento.repositories.IndicadorSolicitadoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class AcompanhamentoService {
    @Autowired
    private AcompanhamentoRepository acompanhamentoRepository;
    @Autowired
    private IndicadorRepository indicadorRepository;
    @Autowired
    private IndicadorSolicitadoRepository indicadorSolicitadoRepository;
    @Autowired
    private AmazonS3Service amazonS3Service;
    @Autowired
    private ArquivoRepository arquivoRepository;

    public List<Acompanhamento> consultarAcompanhamentos() {
        return acompanhamentoRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public Acompanhamento consultarAcompanhamento(Integer acompanhamentoID) {
        return acompanhamentoRepository.findById(acompanhamentoID).get();
    }

    @Transactional
    public void cadastrarAcompanhamento(AcompanhamentoDTO acompanhamentoDTO) {
        Acompanhamento acompanhamento = new Acompanhamento(acompanhamentoDTO);

        List<IndicadorSolicitado> indicadoresSolicitados = new ArrayList<>();
        Indicador indicador;

        if(acompanhamentoDTO.novosIndicadoresSolicitados() != null) {
            for(Integer indicadorSolicitadoDTO : acompanhamentoDTO.novosIndicadoresSolicitados()) {
                indicador = indicadorRepository.findById(indicadorSolicitadoDTO).get();
                indicadoresSolicitados.add(new IndicadorSolicitado(indicador, acompanhamento));
            }

            acompanhamento.vincularIndicadoresSolicitados(indicadoresSolicitados);
        }

        acompanhamentoRepository.save(acompanhamento);
    }

    @Transactional
    public void atualizarAcompanhamento(Integer acompanhamentoID, AcompanhamentoDTO acompanhamentoDTO) {
        Acompanhamento acompanhamento = acompanhamentoRepository.findById(acompanhamentoID).get();

        acompanhamento.atualizar(acompanhamentoDTO);

        if(acompanhamentoDTO.indicadoresSolicitados() != null) {
            for(IndicadorSolicitadoDTO indicadorSolicitadoDTO : acompanhamentoDTO.indicadoresSolicitados()) {
                IndicadorSolicitado indicadorSolicitado = indicadorSolicitadoRepository.findById(indicadorSolicitadoDTO.id()).get();

                indicadorSolicitado.atualizar(indicadorSolicitadoDTO);
            }
        }
    }

    public void excluirAcompanhamento(Integer acompanhamentoID) {
        acompanhamentoRepository.deleteById(acompanhamentoID);
    }

    @Transactional
    public Arquivo salvarArquivoAcompanhamento(Integer acompanhamentoID, MultipartFile arquivo) {
        Acompanhamento acompanhamento = acompanhamentoRepository.findById(acompanhamentoID).get();
        String nomeArquivo = arquivo.getOriginalFilename();
        Long tamanhoArquivo = arquivo.getSize();
        String pastaArquivo = "acompanhamentos/" + acompanhamento.getId() + "/arquivos/";
        String caminhoArquivo = pastaArquivo + Instant.now().toEpochMilli() + "_" + nomeArquivo;

        if(!amazonS3Service.existeObjeto(pastaArquivo)) {
            amazonS3Service.criarPasta(pastaArquivo);
        }

        amazonS3Service.armazenarArquivo(arquivo, caminhoArquivo);

        Arquivo novoArquivo = new Arquivo(caminhoArquivo, nomeArquivo, tamanhoArquivo, acompanhamento);
        arquivoRepository.save(novoArquivo);

        return novoArquivo;
    }

    public String recuperarArquivoAcompanhamento(Integer arquivoID) {
        Arquivo arquivo = arquivoRepository.findById(arquivoID).get();
        String caminhoArquivo = arquivo.getCaminhoS3();

        return amazonS3Service.recuperarArquivo(caminhoArquivo);
    }

    @Transactional
    public void excluirArquivoAcompanhamento(Integer arquivoID) {
        Arquivo arquivo = arquivoRepository.findById(arquivoID).get();
        amazonS3Service.excluirArquivo(arquivo.getCaminhoS3());
        arquivoRepository.deleteById(arquivoID);
    }

    public List<Acompanhamento> buscarAcompanhamentos(Integer organizacaoId) {
        List<Acompanhamento> acompanhamentos = acompanhamentoRepository.findByOrganizacaoId(organizacaoId);

        return acompanhamentos;
    }
}
