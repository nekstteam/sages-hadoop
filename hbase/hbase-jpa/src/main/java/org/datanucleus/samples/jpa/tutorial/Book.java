package org.datanucleus.samples.jpa.tutorial;

import javax.persistence.Entity;

@Entity
public class Book extends Product {

    String author = null;
    String isbn = null;
    String publisher = null;

    public Book() {

    }

    public Book(String name, String desc, double price, String author,
                String isbn, String publisher) {
        super(name, desc, price);
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
    }

}