package com.byt3social.acompanhamento.services;

import com.byt3social.acompanhamento.dto.AccessTokenDTO;
import com.byt3social.acompanhamento.dto.OnlineMeetingDTO;
import com.byt3social.acompanhamento.dto.OrganizacaoDTO;
import com.byt3social.acompanhamento.dto.ReuniaoDTO;
import com.byt3social.acompanhamento.models.Acompanhamento;
import com.byt3social.acompanhamento.models.Horario;
import com.byt3social.acompanhamento.models.Reuniao;
import com.byt3social.acompanhamento.repositories.AcompanhamentoRepository;
import com.byt3social.acompanhamento.repositories.HorarioRepository;
import com.byt3social.acompanhamento.repositories.ReuniaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ReuniaoService {
    @Value("${authentication.microsoft.entra-id.profile.tenant-id}")
    private String tenantId;
    @Value("${authentication.microsoft.entra-id.credentials.teams-client-id}")
    private String clientId;
    @Value("${authentication.microsoft.entra-id.credentials.client-secret}")
    private String clientSecret;
    @Value("${authentication.microsoft.entra-id.app.scope}")
    private String scope;
    @Value("${authentication.microsoft.entra-id.app.user-id}")
    private String userId;
    @Value("${com.byt3social.app.prospeccao.buscar-organizacao-url}")
    private String buscarOrganizacaoUrl;
    @Autowired
    private ReuniaoRepository reuniaoRepository;
    @Autowired
    private AcompanhamentoRepository acompanhamentoRepository;
    @Autowired
    private HorarioRepository horarioRepository;
    @Autowired
    private EmailService emailService;

    public List<Reuniao> consultarReunioes() {
        return reuniaoRepository.findAll();
    }

    public Reuniao consultarReuniao(Integer reuniaoID) {
        return reuniaoRepository.findById(reuniaoID).get();
    }

    @Transactional
    public void solicitarReuniao(ReuniaoDTO reuniaoDTO, String tokenOrganizacao) {
        RestTemplate restTemplateToken = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, Object> content = new LinkedMultiValueMap<>();
        content.add("client_id", clientId);
        content.add("client_secret", clientSecret);
        content.add("scope", scope);
        content.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(content, headers);

        AccessTokenDTO token = restTemplateToken.postForObject("https://login.microsoftonline.com/" + tenantId +  "/oauth2/v2.0/token", entity, AccessTokenDTO.class);

        RestTemplate restTemplateMeeting = new RestTemplate();

        HttpHeaders headersMeeting = new HttpHeaders();
        headersMeeting.setContentType(MediaType.APPLICATION_JSON);
        headersMeeting.setBearerAuth(token.accessToken());

        Map<String, String> contentMeeting = new HashMap<>();
        contentMeeting.put("subject", "B3 Social | Reunião de Acompanhamento");

        HttpEntity<Map<String, String>> entityMeeting = new HttpEntity<>(contentMeeting, headersMeeting);

        OnlineMeetingDTO onlineMeetingDTO = restTemplateMeeting.postForObject("https://graph.microsoft.com/v1.0/users/" + userId + "/onlineMeetings", entityMeeting, OnlineMeetingDTO.class);

        Acompanhamento acompanhamento = acompanhamentoRepository.findById(reuniaoDTO.acompanhamentoId()).get();

        Reuniao reuniao = new Reuniao(acompanhamento, onlineMeetingDTO);
        reuniaoRepository.save(reuniao);

        List<Horario> horarios = new ArrayList<>();

        for(Date disponibilidade : reuniaoDTO.disponibilidades()) {
            Horario horario = new Horario(disponibilidade, reuniao);
            horarios.add(horario);
        }

        horarioRepository.saveAll(horarios);

        OrganizacaoDTO organizacaoDTO = buscarOrganizacao(reuniao, tokenOrganizacao, "colaborador");

        emailService.notificarReuniaoSolicitada(organizacaoDTO);
    }

    @Transactional
    public void agendarHorario(Integer reuniaoID, Integer horarioID, String tokenOrganizacao) {
        Horario horario = horarioRepository.findById(horarioID).get();
        Reuniao reuniao = reuniaoRepository.findById(reuniaoID).get();

        for (Horario horarioDisponibilizado : reuniao.getHorarios()) {
            horarioDisponibilizado.desmarcar();
        }

        reuniao.agendarHorario();
        horario.agendar(reuniao);

        OrganizacaoDTO organizacaoDTO = buscarOrganizacao(reuniao, tokenOrganizacao, "organizacao");

        emailService.notificarReuniaoAgendada(reuniao, organizacaoDTO);
    }

    private OrganizacaoDTO buscarOrganizacao(Reuniao reuniao, String tokenOrganizacao, String scope) {
        RestTemplate restTemplateOrganizacao = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenOrganizacao.replace("Bearer ", ""));
        headers.set("B3Social-User", scope);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        return (restTemplateOrganizacao.exchange(buscarOrganizacaoUrl + reuniao.getAcompanhamento().getOrganizacaoId(), HttpMethod.GET, requestEntity, OrganizacaoDTO.class)).getBody();
    }

    public List<Reuniao> consultarReunioes(Integer organizacaoId) {
        List<Reuniao> reuniaos = reuniaoRepository.findByOrganizacaoId(organizacaoId, Sort.by(Sort.Direction.DESC, "status"));

        return reuniaos;
    }
}
