package com.erolcloud.erolcloud.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
public class Role implements GrantedAuthority {
    public enum Authority {
        ADMIN, INSTRUCTOR, STUDENT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Authority authority;

    public Role() {}

    public Role(Authority authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority.name();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
