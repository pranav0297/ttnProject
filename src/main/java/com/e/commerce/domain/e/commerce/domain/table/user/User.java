package com.e.commerce.domain.e.commerce.domain.table.user;

import com.e.commerce.domain.e.commerce.domain.token.confirmationToken;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;


@Component
@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public  class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Email
    @NotNull
    private String email;

    @NotNull
    @Size(min = 5)
    @Column(name = "first_name")
    private String firstName;


    @Column(name = "middle_name")
    private String middleName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    private String password;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "is_active")
    private boolean isActive=false;

    @Column(name = "is_expired")
    private boolean isExpired;

    @Column(name = "is_locked")
    private boolean isLocked;

    @Column(name = "invalid_attempt_count")
    private int invalid_attempt_count;

    @Column(name = "is_enable")
    private boolean isEnable;

    @Column(name = "reset_token")
    private String resetToken;



    @OneToMany(targetEntity = UserAddress.class,cascade =CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName="id")
   private List<UserAddress> address;

    @JsonBackReference
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns =@JoinColumn(name = "role_id",referencedColumnName = "id"))
    List<Role> role;


   public User(){

   }
    public User(long id, String email,
                String firstName, String middleName,
                String lastName, String password,
                boolean isDeleted, boolean isActive,
                boolean isExpired, boolean isLocked,
                int invalid_attempt_count,
               boolean isEnable,
               List<UserAddress>address, List<Role> role) {
       //super();
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.password = password;
        this.isDeleted = isDeleted;
        this.isActive = isActive;
        this.isExpired = isExpired;
        this.isLocked = isLocked;
        this.invalid_attempt_count = invalid_attempt_count;
        this.isEnable=isEnable;
        this.address = address;
        this.role = role;
    }

    public User(String email, String firstName, String lastName, String password) {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public  String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        HashSet<SimpleGrantedAuthority> set =new HashSet<>();
//
//        set.add(new SimpleGrantedAuthority(String.valueOf(this.getRole())));
//
//        return set;
//    }

    public  String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }



    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setPassword(String password) {
        this.password=password;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isActive() {
        return isActive;
    }


    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public int getInvalid_attempt_count() {
        return invalid_attempt_count;
    }

    public void setInvalid_attempt_count(int invalid_attempt_count) {
        this.invalid_attempt_count = invalid_attempt_count;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public List<UserAddress> getAddress() {
        return address;
    }

    public void setAddress(List<UserAddress> address) {
        this.address = address;
    }

    public List<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", isDeleted=" + isDeleted +
                ", isActive=" + isActive +
                ", isExpired=" + isExpired +
                ", isLocked=" + isLocked +
                ", invalid_attempt_count=" + invalid_attempt_count +
                ", address=" + address +
                ", role=" + role +
                '}';
    }

    public boolean getIsEnable(){
       return isEnable;
    }
    public void  setIsEnable(boolean isEnable){
       this.isEnable=isEnable;
    }

    public boolean getIsActive(){
        return isActive;
    }
    public void  setIsActive(boolean isEnable){
        this.isActive=isActive;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }
}

