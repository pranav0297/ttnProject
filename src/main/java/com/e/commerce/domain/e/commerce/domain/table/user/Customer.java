package com.e.commerce.domain.e.commerce.domain.table.user;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Component
@Table(name = "customer")
@PrimaryKeyJoinColumn(name = "user_id")
public class Customer extends User{

   private Long contact;

   private String newPassword;

    public Long  getContact() {
        return contact;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
