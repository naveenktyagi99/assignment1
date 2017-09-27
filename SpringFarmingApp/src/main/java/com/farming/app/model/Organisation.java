package com.farming.app.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naveen.tyagi on 01-09-2017.
 */

public class Organisation extends ResourceSupport{

    @JsonProperty(value = "orgId")
    private int orgId;

    @JsonProperty(value = "orgName", required = true)
    private String orgName;

    @JsonProperty("description")
    private String description;

    List<Client> clients = new ArrayList<>();

    public Organisation(){}

    public Organisation(int orgId, String orgName, String description) {
        this.orgId = orgId;
        this.orgName = orgName;
        this.description = description;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    @Override
    public String toString() {
        return this.getOrgId()+" : " + this.getOrgName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Organisation that = (Organisation) o;

        return getOrgId() == that.getOrgId();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getOrgId();
        return result;
    }

    @Override
    public void add(Link... links) {
        super.add(links);
    }
}