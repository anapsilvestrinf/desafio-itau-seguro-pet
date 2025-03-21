package com.itau.localizacao_api.infra.controller;

import com.itau.localizacao_api.application.LocalizacaoCoordenadasService;
import com.itau.localizacao_api.core.entity.EnderecoCompleto;
import com.itau.localizacao_api.core.entity.PosicaoSensor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posicoes")
@CrossOrigin(origins = "*")
@Tag(name = "Endereço completo", description = "Informações de localização")
public class SensorController {
    private final LocalizacaoCoordenadasService localizacaoCoordenadaService;

    public SensorController(LocalizacaoCoordenadasService localizacaoCoordenadaService) {
        this.localizacaoCoordenadaService = localizacaoCoordenadaService;
    }

    @GetMapping("/{id_sensor}")
    @Operation(summary = "Recuperação de endereço completo para coordenadas recebidas",
            description = "Esta função é responsável por encontrar o endereço completo para as coordenadas recebidas de um sensor")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = EnderecoCompleto.class))}),
            @ApiResponse(responseCode = "404", description = "Endereço completo não encontrado")
    })
    public ResponseEntity<EnderecoCompleto> get(@PathVariable("id_sensor") long idSensor,
                                                @RequestBody PosicaoSensor coordenada) throws Exception {
            return ResponseEntity.ok().body(localizacaoCoordenadaService.getPosicao(coordenada));

    }
}

