package com.hello.jpatest;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
public class BookTest extends ItemTest {

    private String author;
    private String isbn;
}
