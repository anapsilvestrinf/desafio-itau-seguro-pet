# Desafio API Localização

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)

Este projeto é uma **API** desenvolvida utilizando **Java 17**, **Spring Boot**, e **Maven**.

## Tabela de Conteúdos

- [Instalação](#instalacao)
- [Configuração](#configuracao)
- [Uso](#uso)
- [Endpoints da API](#api-endpoints)
- [Testes](#testes)
- [Arquitetura](#arquitetura)

## Instalação

1. Clone o repositório:

```bash
git clone https://github.com/anapsilvestrinf/desafio-itau-seguro-pet.git
```

2. Instale as dependências com Maven:
```bash
./mvnw clean install  
````

3. Configure as credenciais da API no arquivo application.properties:
Abra o arquivo src/main/resources/application.properties e insira as credenciais da API do Positionstack:

```
url.api.geolocalizacao=${URL_API_GEOCALIZACAO}
api.key.geolocalizacao=${API_KEY_GEOLOCALIZACAO}
```

**Nota**: As credenciais do Datadog foram insideridas nas propriedades da aplicação, mas são desnecessárias para o funcionamento, pois o envio das métricas está desabilitado no momento.

# Endpoins
A API fornece os seguintes endpoints:

## POST /api/posicoes/{id_sensor} - Recupera o endereço completo para coordenadas recebidas.

* Exemplo de uso:
```
POST /api/posicoes/1
Content-Type: application/json

{
  "latitude": "-3.718517",
  "longitude": "-38.549449",
  "datahora": "2025-03-21T12:00:00"
}
```

# Testes
## Cobertura de Testes
O relatório de cobertura de código pode ser obtido utilizando o Jacoco. Para gerar o relatório, execute o seguinte comando:

```
./mvnw clean test jacoco:report
```

# Arquitetura
A solução segue princípios de Clean Architecture, onde se busca separar as camadas de forma a proteger o domínio da aplicação. O padrão de adapter é utilizado para isolar mudanças na API de geolocalização, garantindo que a substituição dessa API não afete o funcionamento da aplicação, principalmente as regras de negócio.

Assumi que não seria necessário utilizar um banco de dados, pois não foi especificado o uso de banco no escopo do desafio, bem como não está desenhado na solução. Além disso, os campos que eventualmente precisariam ser persistidos aparecem em outra parte do desenho da solução.

# Observabilidade
Embora a integração com Datadog tenha sido desabilitada, as configurações de captura de métricas foram adicionadas nas propriedades.

