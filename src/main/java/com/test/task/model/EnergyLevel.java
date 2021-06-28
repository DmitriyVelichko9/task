package com.test.task.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EnergyLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String isin;
    private Double value;

    public EnergyLevel() {
    }

    public EnergyLevel(Long id, String isin, Double value) {
        this.id = id;
        this.isin = isin;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
