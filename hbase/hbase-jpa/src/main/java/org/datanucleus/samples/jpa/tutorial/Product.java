package org.datanucleus.samples.jpa.tutorial;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    long id;

    String name = null;
    String description = null;
    double price = 0.0;

    public Product(String name, String desc, double price) {
        this.name = name;
        this.description = desc;
        this.price = price;
    }

    public Product() {
    }

}