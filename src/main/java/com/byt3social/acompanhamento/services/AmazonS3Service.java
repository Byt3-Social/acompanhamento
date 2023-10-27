package com.byt3social.acompanhamento.services;

import com.byt3social.acompanhamento.exceptions.FailedToSaveFileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

@Service
public class AmazonS3Service {
    @Autowired
    private S3Client s3Client;
    @Autowired
    private S3Presigner presigner;
    @Value("${com.byt3social.aws.main-bucket-name}")
    private String nomeBucketPrincipal;

    public Boolean existeObjeto(String nomeObjeto) {
        ListObjectsRequest listObjects = ListObjectsRequest
                .builder()
                .bucket(nomeBucketPrincipal)
                .build();

        ListObjectsResponse res = s3Client.listObjects(listObjects);
        List<S3Object> objects = res.contents();

        return objects.stream().anyMatch(
                s3Object -> s3Object.getValueForField("Key", String.class).get().equals(nomeObjeto)
        );
    }

    public void criarPasta(String nomePasta) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(nomeBucketPrincipal)
                .key(nomePasta)
                .build();

        s3Client.putObject(objectRequest, RequestBody.empty());
    }

    public void armazenarArquivo(MultipartFile arquivo, String caminhoArquivo) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(nomeBucketPrincipal)
                .key(caminhoArquivo)
                .build();

        try {
            s3Client.putObject(objectRequest, RequestBody.fromInputStream(arquivo.getInputStream(), arquivo.getSize()));
        } catch (IOException e) {
            throw new FailedToSaveFileException();
        }
    }

    public String recuperarArquivo(String nomeArquivo) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(nomeBucketPrincipal)
                .key(nomeArquivo)
                .build();

        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofSeconds(30))
                .getObjectRequest(getObjectRequest)
                .build();

        PresignedGetObjectRequest presignedGetObjectRequest = presigner.presignGetObject(getObjectPresignRequest);

        return presignedGetObjectRequest.url().toString();
    }

    public void excluirArquivo(String pathArquivo) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(nomeBucketPrincipal)
                .key(pathArquivo)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }
}
