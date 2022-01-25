package com.hello.jpasample;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class MemberSample {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    /*@GeneratedValue(
            strategy = GenerationType.TABLE,
            generator = "MEMBER_SEQ_GENERATOR"
    )*/
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String username;

    public Member() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
