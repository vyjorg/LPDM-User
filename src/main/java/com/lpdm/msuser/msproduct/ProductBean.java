package com.lpdm.msuser.msproduct;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lpdm.msuser.msauthentication.AppUserBean;

import java.util.List;
import java.util.Optional;

public class ProductBean {

    private Integer id;

    private String name;

    private CategoryBean category;

    private String label;

    private double price;

    private double tva;

    private boolean deactivate;

    private List<StockBean> listStock;

    private String picture;

    private Integer producerID;

    private Optional<ProducerBean> producer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryBean getCategory() {
        return category;
    }

    public void setCategory(CategoryBean category) {
        this.category = category;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTva() {
        return tva;
    }

    public void setTva(double tva) {
        this.tva = tva;
    }

    public List<StockBean> getListStock() {
        return listStock;
    }

    public void setListStock(List<StockBean> listStock) {
        this.listStock = listStock;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getProducerID() {
        return producerID;
    }

    public void setProducerID(Integer producerID) {
        this.producerID = producerID;
    }

    public Optional<ProducerBean> getProducer() {
        return producer;
    }

    public void setProducer(Optional<ProducerBean> producer) {
        this.producer = producer;
    }

    public boolean isDeactivate() {
        return deactivate;
    }

    public void setDeactivate(boolean deactivate) {
        this.deactivate = deactivate;
    }
}
