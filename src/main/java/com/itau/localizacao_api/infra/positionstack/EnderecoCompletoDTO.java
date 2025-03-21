package com.itau.localizacao_api.infra.positionstack;

import com.itau.localizacao_api.core.entity.EnderecoCompleto;

import java.util.List;

public class EnderecoCompletoDTO {

    private List<Data> data;

    public EnderecoCompletoDTO(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }


    public static class Data {
        private String country;
        private String region;
        private String administrative_area;
        private String neighbourhood;
        private String street;

        private Data(){}

        public Data(String street, String region, String administrative_area, String neighbourhood, String country, String country_code, String map_url) {
            this.street = street;
            this.region = region;
            this.administrative_area = administrative_area;
            this.neighbourhood = neighbourhood;
            this.country = country;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getAdministrative_area() {
            return administrative_area;
        }

        public void setAdministrative_area(String administrative_area) {
            this.administrative_area = administrative_area;
        }

        public String getNeighbourhood() {
            return neighbourhood;
        }

        public void setNeighbourhood(String neighbourhood) {
            this.neighbourhood = neighbourhood;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }

    public static EnderecoCompleto toEnderecoCompleto(Data data) {
        return new EnderecoCompleto(
                data.getCountry(),
                data.getRegion(),
                data.getAdministrative_area(),
                data.getNeighbourhood(),
                data.getStreet()
        );
    }
}