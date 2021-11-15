package com.e.commerce.domain.e.commerce.domain;

import com.e.commerce.domain.e.commerce.domain.repo.userRepo;
import com.e.commerce.domain.e.commerce.domain.table.user.Customer;
import com.e.commerce.domain.e.commerce.domain.table.user.Role;
import com.e.commerce.domain.e.commerce.domain.table.user.User;
import com.e.commerce.domain.e.commerce.domain.table.user.UserAddress;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ApplicationTests {
    @Autowired
    userRepo repo;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Test
    void contextLoads() {
    }

    @Test
    void createCustomer() {
        Customer customer = new Customer();
       // User user = new User();


        customer.setContact(9810599535L);
        customer.setEmail("abc@gamil.com");
        customer.setFirstName("Mayank");
        customer.setMiddleName("--");
        customer.setLastName("Mishra");
        customer.setPassword(this.passwordEncoder.encode("12345"));
//        customer.setActive(true);
//        customer.setDeleted(false);
//        customer.setExpired(false);
//        customer.setLocked(true);
       customer.setInvalid_attempt_count(1);

        List<Role> role = new ArrayList<>();
        Role roles = new Role();
        roles.setAuthority("ROLE_CUSTOMER");
        role.add(roles);
        customer.setRole(role);

        List<UserAddress> address = new ArrayList<>();
        UserAddress userAddress = new UserAddress();


        userAddress.setCity("GZB");
        userAddress.setState("UP");
        userAddress.setCountry("India");
        userAddress.setAddress_line("A block");
        userAddress.setZip_code("201005");
        userAddress.setLabel("sbb");
        address.add(userAddress);
      //  userAddress.setUser(customer);
        customer.setAddress(address);

        repo.save(customer);


    }

}
