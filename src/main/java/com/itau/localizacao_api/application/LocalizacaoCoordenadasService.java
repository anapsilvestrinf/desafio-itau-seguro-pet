package com.itau.localizacao_api.application;

import com.itau.localizacao_api.adapters.GatewayGeocodificacao;
import com.itau.localizacao_api.core.entity.EnderecoCompleto;
import com.itau.localizacao_api.core.entity.PosicaoSensor;
import com.itau.localizacao_api.core.usecase.LocalizacaoCoordenadasUsecase;
import org.springframework.stereotype.Service;

@Service
public class LocalizacaoCoordenadasService implements LocalizacaoCoordenadasUsecase {

    private final GatewayGeocodificacao gateway;

    public LocalizacaoCoordenadasService(GatewayGeocodificacao gateway) {
        this.gateway = gateway;
    }

    @Override
    public EnderecoCompleto getPosicao(PosicaoSensor posicaoSensor) throws Exception {
        return gateway.buscaEndereco(posicaoSensor.latitude(), posicaoSensor.longitude());
    }

}
