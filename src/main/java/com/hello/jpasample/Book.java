package com.hello.jpasample;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
public class Book extends ItemSample {

    private String author;
    private String isbn;
}
