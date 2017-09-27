package com.farming.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class Farm extends ResourceSupport{

    @JsonProperty("farmId")
    private int farmId;

    @JsonProperty("farmName")
    private String farmName;

    private Client client;

    List<Field> fields ;

    public Farm() {
    }

    public Farm(int farmId, String farmName) {
        this.farmId = farmId;
        this.farmName = farmName;
    }

    public Farm(int farmId, String farmName, Client client) {
        this.farmId = farmId;
        this.farmName = farmName;
        this.client = client;
    }

    public int getFarmId() {
        return farmId;
    }

    public void setFarmId(int farmId) {
        this.farmId = farmId;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    @JsonIgnore
    public Client getClient() {
        return client;
    }

    @JsonProperty("client")
    public void setClient(Client client) {
        this.client = client;
    }

    @JsonIgnore
    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Farm farm = (Farm) o;

        return getFarmId() == farm.getFarmId();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getFarmId();
        return result;
    }

    @Override
    public void add(Link... links) {
        super.add(links);
    }
}
