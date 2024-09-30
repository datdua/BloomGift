package com.example.bloomgift.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;

public class ComboProductKey implements Serializable {
    @Column(name = "comboID")
    private Integer comboID;

    @Column(name = "productID")
    private Integer productID;

    // Constructors, Getters, Setters, equals(), and hashCode()

    public ComboProductKey() {}

    public ComboProductKey(Integer comboID, Integer productID) {
        this.comboID = comboID;
        this.productID = productID;
    }

    public Integer getComboID() {
        return comboID;
    }

    public void setComboID(Integer comboID) {
        this.comboID = comboID;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComboProductKey that = (ComboProductKey) o;
        return Objects.equals(comboID, that.comboID) &&
               Objects.equals(productID, that.productID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comboID, productID);
    }
}
