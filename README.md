# 🚀 API Spring Boot Avançada

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.1-green)
![MySQL](https://img.shields.io/badge/MySQL-8.0-orange)
![Docker](https://img.shields.io/badge/Docker-✓-blue)

Uma API RESTful completa desenvolvida com Spring Boot contendo os principais recursos exigidos pelo mercado.

## 📋 Índice

- [Funcionalidades](#✨-funcionalidades)
- [Tecnologias](#🛠️-tecnologias)
- [Pré-requisitos](#📦-pré-requisitos)
- [Instalação](#⚙️-instalação)
- [Uso](#🔍-uso)
- [Documentação](#📚-documentação)
- [Deploy](#☁️-deploy)
- [Contribuição](#🤝-contribuição)
- [Licença](#📜-licença)

## ✨ Funcionalidades

✔️ **CRUD Completo** com Spring Data JPA  
✔️ **Autenticação JWT** com Spring Security  
✔️ **Validação** de DTOs e tratamento de Exceptions  
✔️ **Documentação** com Swagger/OpenAPI  
✔️ **Versionamento** de API (v1, v2)  
✔️ **Migrations** com Flyway  
✔️ **Logs** estruturados com SLF4J  
✔️ **Relatórios** com JasperReports  
✔️ **Importação** de arquivos CSV  
✔️ **Dockerização** completa  
✔️ **HATEOAS** e Content Negotiation  
✔️ **Integração AWS** (S3, RDS)  

## 🛠️ Tecnologias

- **Backend**: Java 17, Spring Boot 3.1
- **Banco de Dados**: MySQL 8.0
- **Autenticação**: JWT, Spring Security
- **Documentação**: Swagger UI
- **Migrations**: Flyway
- **Relatórios**: JasperReports
- **Containerização**: Docker + Docker Compose
- **Cloud**: AWS (EC2, S3, RDS)
- **Testes**: JUnit 5, Mockito

## 📦 Pré-requisitos

- JDK 17+
- MySQL 8.0+
- Maven 3.8+
- Docker 20.10+ (opcional)

## ⚙️ Instalação

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/projeto-spring.git

# Configure o application.yml
nano src/main/resources/application.yml

# Execute as migrations
mvn flyway:migrate

# Inicie a aplicação
mvn spring-boot:run