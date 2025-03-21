package com.itau.localizacao_api.infra.positionstack;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itau.localizacao_api.core.entity.EnderecoCompleto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(Positionstack.class)
@TestPropertySource(properties = {
        "url.api.geolocalizacao=https://test.com",
        "api.key.geolocalizacao=126748449"
})
class PositionstackTest {

    @Autowired
    private Positionstack positionStack;

    @Autowired
    MockRestServiceServer server;

    @Test
    void buscaEndereco_DeveRetornarEndereco_QuandoRetornarSucesso() throws Exception {
        EnderecoCompletoDTO bodyRespostaMock = this.criaEnderecoCompletoDTOMock();
        String respostaMockJson = new ObjectMapper().writeValueAsString(bodyRespostaMock);

        server.expect(method(HttpMethod.GET))
                .andRespond(withSuccess(respostaMockJson, MediaType.APPLICATION_JSON));

        double latitude = 40.7128;
        double longitude = -74.0060;
        EnderecoCompleto endereco = positionStack.buscaEndereco(latitude, longitude);

        assertNotNull(endereco);
        assertEquals(bodyRespostaMock.getData().get(0).getCountry(), endereco.pais());
        assertEquals(bodyRespostaMock.getData().get(0).getRegion(), endereco.estado());
        assertEquals(bodyRespostaMock.getData().get(0).getAdministrative_area(), endereco.cidade());
        assertEquals(bodyRespostaMock.getData().get(0).getNeighbourhood(), endereco.bairro());
        assertEquals(bodyRespostaMock.getData().get(0).getStreet(), endereco.endereco());
    }

    @Test
    void buscaEndereco_DeveLancarException_QuandoRetornaStatusCodeErro() {
        server.expect(method(HttpMethod.GET))
                .andRespond(
                        withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("{\"error\":{\"code\": \"validation_error\"\"}")
                );

        double latitude = 40.7128;
        double longitude = -74.0060;

        assertThrows(Exception.class, () -> {
            positionStack.buscaEndereco(latitude, longitude);
        });
    }

    @Test
    void buscaEndereco_DeveLancarExcecao_QuandoNaoExistirEndereco() throws Exception {

        EnderecoCompletoDTO bodyRespostaMock = new EnderecoCompletoDTO(new ArrayList<>());
        String respostaMockJson = new ObjectMapper().writeValueAsString(bodyRespostaMock);

        server.expect(method(HttpMethod.GET))
                .andRespond(withSuccess(respostaMockJson, MediaType.APPLICATION_JSON));

        double latitude = 40.7128;
        double longitude = -74.0060;

        assertThrows(RuntimeException.class, () -> {
            positionStack.buscaEndereco(latitude, longitude);
        });
    }

    private EnderecoCompletoDTO criaEnderecoCompletoDTOMock(){
        EnderecoCompletoDTO.Data enderecoData = new EnderecoCompletoDTO.Data(
                "USA",
                "New York",
                "NY",
                "Manhattan",
                "5th Avenue",
                null,
                null);
        return new EnderecoCompletoDTO(List.of(enderecoData));
    }
}