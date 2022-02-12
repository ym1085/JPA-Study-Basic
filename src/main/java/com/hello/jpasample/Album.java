package com.hello.jpasample;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
public class Album extends ItemSample {

    private String artist;
}
