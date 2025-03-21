package com.itau.localizacao_api.adapters;

import com.itau.localizacao_api.core.entity.EnderecoCompleto;

public interface GatewayGeocodificacao {
    EnderecoCompleto buscaEndereco(double latitude, double longitude) throws Exception;
}
