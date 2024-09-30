package com.example.bloomgift.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "ComboProduct")
public class ComboProduct {
    @EmbeddedId
    private ComboProductKey id;

    @ManyToOne
    @MapsId("comboID")
    @JoinColumn(name = "comboID")
    private Combo combo;

    @ManyToOne
    @MapsId("productID")
    @JoinColumn(name = "productID")
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    // Getters and Setters
    public ComboProductKey getId() {
        return id;
    }

    public void setId(ComboProductKey id) {
        this.id = id;
    }

    public Combo getCombo() {
        return combo;
    }

    public void setCombo(Combo combo) {
        this.combo = combo;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
