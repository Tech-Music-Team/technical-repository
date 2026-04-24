package school.sptech.service;

import school.sptech.Main;
import school.sptech.client.S3Provider;
import school.sptech.entities.Logger;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

public class S3Service {
    S3Client providerService;

    public S3Service(S3Provider providerService) {
        this.providerService = providerService.getS3Client();
    }

    public void listarBuckets() {
        try {
            Logger.info(Main.class.getPackageName().toString(), Main.class.getName().toString(), "Listando Buckets S3 da AWS");
            List<Bucket> buckets = providerService.listBuckets().buckets();
            System.out.println("Lista de buckets:");
            for (Bucket bucket : buckets) {
                System.out.println("- " + bucket.name());
            }
        } catch (S3Exception e) {
            Logger.error(Main.class.getPackageName().toString(), Main.class.getName().toString(), "Erro ao tentar listar Buckets: " + e.getMessage());
        }
    }

    public void listarObjetos(String bucketName) {
        try {
            Logger.info(Main.class.getPackageName().toString(), Main.class.getName().toString(), "Listando Objetos do Bucket");
            ListObjectsRequest requisicao = ListObjectsRequest.builder().bucket(bucketName).build();

            List<S3Object> objects = providerService.listObjects(requisicao).contents();
            System.out.println("Objetos no bucket " + bucketName + ":");
            for (S3Object object : objects) {
                System.out.println("- " + object.key());
            }
        } catch (S3Exception e) {
            Logger.error(Main.class.getPackageName().toString(), Main.class.getName().toString(), "Erro ao listar Objetos do Bucket: " + e.getMessage());
        }
    }

    public void baixarArquivo(String bucketName) {
        try {
            Logger.info(Main.class.getPackageName().toString(), Main.class.getName().toString(), "Realizando download de arquivo do Bucket");
            ListObjectsRequest requisicao = ListObjectsRequest.builder()
                    .bucket(bucketName)
                    .build();
            List<S3Object> objects = providerService.listObjects(requisicao).contents();
            for (S3Object object : objects) {
                GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(object.key())
                        .build();

                InputStream inputStream = providerService.getObject(getObjectRequest, ResponseTransformer.toInputStream());
                Files.copy(inputStream, new File(object.key()).toPath());
                Logger.info(Main.class.getPackageName().toString(), Main.class.getName().toString(), "Arquivo baixado: " + object.key());
            }
        } catch (IOException | S3Exception e) {
            Logger.error(Main.class.getPackageName().toString(), Main.class.getName().toString(), "Erro ao realizar download de arquivo: " + e.getMessage());
        }
    }

}
