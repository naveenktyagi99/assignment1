package com.farming.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

public class Field extends ResourceSupport{

    @JsonProperty("fieldId")
    private int fieldId;

    @JsonProperty("fieldName")
    private String fieldName;

    private Farm farm;

    public Field() {
    }

    public Field(int fieldId, String fieldName) {
        this.fieldId = fieldId;
        this.fieldName = fieldName;
    }

    public Field(int fieldId, String fieldName, Farm farm) {
        this.fieldId = fieldId;
        this.fieldName = fieldName;
        this.farm = farm;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @JsonIgnore
    public Farm getFarm() {
        return farm;
    }

    @JsonProperty("farm")
    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    @Override
    public String toString() {
        return this.getFieldId()+" : "+this.getFieldName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Field field = (Field) o;

        return getFieldId() == field.getFieldId();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getFieldId();
        return result;
    }

    @Override
    public void add(Link... links) {
        super.add(links);
    }
}
