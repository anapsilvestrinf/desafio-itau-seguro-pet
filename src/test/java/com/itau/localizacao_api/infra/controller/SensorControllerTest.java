package com.itau.localizacao_api.infra.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itau.localizacao_api.application.LocalizacaoCoordenadasService;
import com.itau.localizacao_api.core.entity.EnderecoCompleto;
import com.itau.localizacao_api.core.entity.PosicaoSensor;
import com.itau.localizacao_api.core.exceptions.Exceptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SensorController.class)
class SensorControllerTest {
@Autowired
private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LocalizacaoCoordenadasService LocalizacaoCoordenadasService;

    @Test
    public void buscaEndereco_ComDadosInvalidos_RetornaBadRequest() throws Exception {
        mockMvc
                .perform(
                        get("/api/posicoes/1").content("{}")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void buscaEndereco_ComPosicaoValida_RetornaEndereco() throws Exception {
        PosicaoSensor posicao = new PosicaoSensor(1d, 12.0, null);
        EnderecoCompleto endereco = new EnderecoCompleto("Br", "SP", "SA", "", "Santa Adélia");
        when(LocalizacaoCoordenadasService.getPosicao(posicao)).thenReturn(endereco);

        mockMvc
                .perform(
                        get("/api/posicoes/1")
                                .content(objectMapper.writeValueAsString(posicao))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void buscaEndereco_ComPosicaoSemEndereco_RetornaNotFound() throws Exception {
        PosicaoSensor posicao = new PosicaoSensor(1d, 12.0, null);
        when(LocalizacaoCoordenadasService.getPosicao(posicao))
                .thenThrow(Exceptions.EnderecoNaoEncontrado.class);

        mockMvc.perform(get("/api/posicoes/1")
                        .content(objectMapper.writeValueAsString(posicao))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}