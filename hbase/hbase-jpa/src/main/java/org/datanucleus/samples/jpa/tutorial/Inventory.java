package org.datanucleus.samples.jpa.tutorial;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Inventory {

    @Id
    String name = null;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    Set<Product> products = new HashSet();

    public Inventory(String name) {
        this.name = name;
    }

    public Inventory() {
    }

    public Set<Product> getProducts() {
        return products;
    }

}
