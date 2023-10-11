package com.byt3social.acompanhamento.services;

import com.byt3social.acompanhamento.dto.AcompanhamentoDTO;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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
        return acompanhamentoRepository.findAll();
    }

    public Acompanhamento consultarAcompanhamento(Integer acompanhamentoID) {
        return acompanhamentoRepository.findById(acompanhamentoID).get();
    }

    @Transactional
    public void cadastrarAcompanhamento(AcompanhamentoDTO acompanhamentoDTO) {
        Acompanhamento acompanhamento = new Acompanhamento(acompanhamentoDTO);

        List<IndicadorSolicitado> indicadoresSolicitados = new ArrayList<>();
        Indicador indicador;

        if(acompanhamentoDTO.indicadoresSolicitados() != null) {
            for(Integer indicadorSolicitadoDTO : acompanhamentoDTO.indicadoresSolicitados()) {
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
        ListIterator<IndicadorSolicitado> indicadorSolicitadoListIterator = acompanhamento.getIndicadoresSolicitados().listIterator();

        if(acompanhamentoDTO.indicadoresSolicitados() != null) {
            while(indicadorSolicitadoListIterator.hasNext()) {
                IndicadorSolicitado indicadorSolicitado = indicadorSolicitadoListIterator.next();

                Boolean existeIndicadorSolicitado = acompanhamentoDTO.indicadoresSolicitados().contains(indicadorSolicitado.getIndicador().getId());

                if(!existeIndicadorSolicitado) {
                    indicadorSolicitadoListIterator.remove();
                }
            }

            for(Integer indicadorID : acompanhamentoDTO.indicadoresSolicitados()) {
                Boolean existeIndicadorSolicitado = acompanhamento.getIndicadoresSolicitados().stream().anyMatch(indicadorSolicitado -> indicadorSolicitado.getIndicador().getId().equals(indicadorID));

                if(!existeIndicadorSolicitado) {
                    Indicador indicador = indicadorRepository.findById(indicadorID).get();
                    IndicadorSolicitado indicadorSolicitado = new IndicadorSolicitado(indicador, acompanhamento);

                    indicadorSolicitadoListIterator.add(indicadorSolicitado);
                }
            }

            indicadorSolicitadoRepository.saveAll(acompanhamento.getIndicadoresSolicitados());
        }
    }

    public void excluirAcompanhamento(Integer acompanhamentoID) {
        acompanhamentoRepository.deleteById(acompanhamentoID);
    }

    @Transactional
    public void salvarArquivoAcompanhamento(Integer acompanhamentoID, MultipartFile arquivo) {
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
}
