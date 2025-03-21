package com.itau.localizacao_api.infra.positionstack;

import com.itau.localizacao_api.adapters.GatewayGeocodificacao;
import com.itau.localizacao_api.core.entity.EnderecoCompleto;
import com.itau.localizacao_api.core.exceptions.Exceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Service
public class Positionstack implements GatewayGeocodificacao {

    private static final Logger LOGGER = LoggerFactory.getLogger(Positionstack.class);

    @Value("${api.key.geolocalizacao}")
    private String apiKeyGeolocalizacao;
    private String urlApiGeolocalizacao;

    private final String QUANTIDADE_RESULTADOS = "1";
    private final String FILTRO_PARAMETROS = String.join(",", new String[]{
            "results.country",
            "results.region",
            "results.administrative_area",
            "results.neighbourhood",
            "results.street"
    });

    private RestClient restClient;

    public Positionstack(@Value("${url.api.geolocalizacao}") String urlApiGeolocalizacao, RestClient.Builder builder) {
        this.restClient = builder.baseUrl(urlApiGeolocalizacao).build();
    }

    @Override
    public EnderecoCompleto buscaEndereco(double latitude, double longitude) throws Exception {
        var responseEntity = acionaApi(latitude, longitude);
        return Optional.of(responseEntity)
                .map(HttpEntity::getBody)
                .map(EnderecoCompletoDTO::getData)
                .filter(listaEnderecos -> !listaEnderecos.isEmpty())
                .map(listaEnderecos -> listaEnderecos.get(0))
                .map(EnderecoCompletoDTO::toEnderecoCompleto)
                .orElseThrow(() -> new Exceptions.EnderecoNaoEncontrado(
                        "Não foi possível encontrar o endereço."));
    }

    private ResponseEntity<EnderecoCompletoDTO> acionaApi(double latitude, double longitude) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/v1/reverse")
                        .queryParam("access_key",apiKeyGeolocalizacao)
                        .queryParam("query", formataCoordenadas(latitude, longitude))
                        .queryParam("limit", QUANTIDADE_RESULTADOS)
                        .queryParam("fields", FILTRO_PARAMETROS)
                        .build())
                .retrieve()
                .toEntity(EnderecoCompletoDTO.class);
    }

    private String formataCoordenadas(double latitude, double longitude) {
        return String.join(",", new String[]{
                String.valueOf(latitude),
                String.valueOf(longitude)
        });
    }
}
