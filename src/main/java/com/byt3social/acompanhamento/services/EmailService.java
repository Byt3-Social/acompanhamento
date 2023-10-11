package com.byt3social.acompanhamento.services;

import com.byt3social.acompanhamento.dto.OrganizacaoDTO;
import com.byt3social.acompanhamento.exceptions.FailedToDeliverEmailException;
import com.byt3social.acompanhamento.models.Reuniao;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("classpath:templates/email/solicitar-reuniao.html")
    private Resource solicitarReuniaoresource;
    @Value("classpath:templates/email/reuniao-agendada.html")
    private Resource reuniaoAgendadaResource;

    public void notificarReuniaoSolicitada(OrganizacaoDTO organizacaoDTO) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            message.setFrom(new InternetAddress("byt3social@gmail.com"));
            message.setRecipients(MimeMessage.RecipientType.TO, organizacaoDTO.email());
            message.setSubject("B3 Social | Agende uma reunião");

            InputStream inputStream = solicitarReuniaoresource.getInputStream();
            String htmlTemplate = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            htmlTemplate = htmlTemplate.replace("${nome_organizacao}", organizacaoDTO.nomeEmpresarial());

            message.setContent(htmlTemplate, "text/html; charset=utf-8");

            mailSender.send(message);
        } catch (Exception e) {
            throw new FailedToDeliverEmailException();
        }
    }

    public void notificarReuniaoAgendada(Reuniao reuniao, OrganizacaoDTO organizacaoDTO) {
        MimeMessage message = mailSender.createMimeMessage();

        Locale locale = new Locale.Builder().setLanguage("pt").setRegion("BR").build();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM 'de' yyyy 'às' HH:mm").withLocale(locale);
        LocalDateTime localDate = reuniao.getHorario().getDataHorario().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        String dataHorarioReuniao = localDate.format(formatter);

        try {
            message.setFrom(new InternetAddress("byt3social@gmail.com"));
            message.setRecipients(MimeMessage.RecipientType.TO, organizacaoDTO.email());
            message.setSubject("B3 Social | Reunião agendada");

            InputStream inputStream = reuniaoAgendadaResource.getInputStream();
            String htmlTemplate = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            htmlTemplate = htmlTemplate.replace("${nome_organizacao}", organizacaoDTO.nomeEmpresarial());
            htmlTemplate = htmlTemplate.replace("${link_reuniao}", reuniao.getLink());
            htmlTemplate = htmlTemplate.replace("${data_horario_reuniao}", dataHorarioReuniao);

            message.setContent(htmlTemplate, "text/html; charset=utf-8");

            mailSender.send(message);
        } catch (Exception e) {
            throw new FailedToDeliverEmailException();
        }
    }
}
