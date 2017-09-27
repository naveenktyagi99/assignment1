package com.farming.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class Client extends ResourceSupport{

    @JsonProperty("clientId")
    private int clientId;

    @JsonProperty("clientName")
    private String clientName;

    private List<Farm> farms;

    private Organisation organisation;

    public int getClientId() {
        return clientId;
    }
    public Client(){}

    public Client(int clientId,
                  String clientName,
                  Organisation organisation) {

        this.clientId = clientId;
        this.clientName = clientName;
        this.organisation = organisation;
    }

    public Client(int clientId, String clientName) {
        this.clientId = clientId;
        this.clientName = clientName;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @JsonIgnore
    public Organisation getOrganisation() {
        return organisation;
    }

    @JsonProperty("organisation")
    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    @JsonIgnore
    public List<Farm> getFarms() {
        return farms;
    }

    public void setFarms(List<Farm> farms) {
        this.farms = farms;
    }

    @Override
    public String toString() {
        return this.getClientId()+" : " + this.getClientName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Client client = (Client) o;

        return getClientId() == client.getClientId();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getClientId();
        return result;
    }

    @Override
    public void add(Link... links) {
        super.add(links);
    }
}
