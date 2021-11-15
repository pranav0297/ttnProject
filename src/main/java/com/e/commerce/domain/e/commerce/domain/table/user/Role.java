package com.e.commerce.domain.e.commerce.domain.table.user;


import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.*;

@Entity
@Repository
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
    @Column(name = "Authority")
   private String authority;

   @ManyToMany(mappedBy = "role")
   List<User> user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
}
