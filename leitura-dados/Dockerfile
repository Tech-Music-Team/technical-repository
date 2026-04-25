FROM maven AS builder

WORKDIR /build
COPY . .

RUN mvn clean package


FROM eclipse-temurin:21-jre-alpine

WORKDIR /app
COPY --from=builder /builder/target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]



# Neste Dockerfile utilizamos a estratégia de Docker Multistage Build,
# que consiste em separar o processo de build da aplicação da sua execução.

# Na primeira etapa (builder), usamos uma imagem com Maven para compilar
# o projeto e gerar o arquivo .jar. Essa etapa contém todas as dependências
# necessárias para o build, mas não é levada para a imagem final.

# Na segunda etapa, utilizamos uma imagem mais leve (apenas com JRE),
# copiando somente o .jar gerado anteriormente. Isso reduz o tamanho
# da imagem e melhora a segurança e performance.

# No contexto do nosso ETL, essa abordagem garante
# um container mais leve e rápido para subir,
# fazendo com que as novas alterações do java sejam suficientes para construir a imagem,
# em outras palavras:
    # quando o código tiver alguma alteração, não será nescessario subir o target nem o .jar ja construido no commit.

    # feito com carinho por: Jorge Luiz :)
