package com.hello.jpatest;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
public class AlbumTest extends ItemTest {

    private String artist;
}
