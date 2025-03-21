package com.itau.localizacao_api.core.usecase;

import com.itau.localizacao_api.core.entity.PosicaoSensor;
import com.itau.localizacao_api.core.entity.EnderecoCompleto;

public interface LocalizacaoCoordenadasUsecase {
    EnderecoCompleto getPosicao(PosicaoSensor posicaoSensor) throws Exception;
}
