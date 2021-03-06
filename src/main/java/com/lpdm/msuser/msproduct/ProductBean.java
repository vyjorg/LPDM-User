package com.lpdm.msuser.msproduct;

import com.lpdm.msuser.msauthentication.AppUserBean;

import java.util.List;

public class ProductBean {

    private Integer id;

    private String name;

    private CategoryBean category;

    private String label;

    private double price;

    private double tax;

    private boolean deactivate;

    private List<StockBean> listStock;

    private String picture;

    private Integer producerID;

    private AppUserBean producer;

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

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
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

    public AppUserBean getProducer() {
        return producer;
    }

    public void setProducer(AppUserBean producer) {
        this.producer = producer;
    }

    public boolean isDeactivate() {
        return deactivate;
    }

    public void setDeactivate(boolean deactivate) {
        this.deactivate = deactivate;
    }

    @Override
    public String toString() {
        return "ProductBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", label='" + label + '\'' +
                ", price=" + price +
                ", tax=" + tax +
                ", deactivate=" + deactivate +
                ", listStock=" + listStock +
                ", picture='" + picture + '\'' +
                ", producerID=" + producerID +
                ", producer=" + producer +
                '}';
    }

    /*
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", label='" + label + '\'' +
                ", price=" + price +
                ", tva=" + tva +
                ", deactivate=" + deactivate +
                ", listStock=" + listStock +
                ", picture='" + picture + '\'' +
                ", producerID=" + producerID +
                ", producer=" + producer +
                '}';
     */
}
