package com.e.commerce.domain.e.commerce.domain.Service;

import com.e.commerce.domain.e.commerce.domain.Enum.Status;
import com.e.commerce.domain.e.commerce.domain.repo.customerRepo;
import com.e.commerce.domain.e.commerce.domain.repo.roleRepo;
import com.e.commerce.domain.e.commerce.domain.repo.sellerRepo;
import com.e.commerce.domain.e.commerce.domain.repo.userRepo;
import com.e.commerce.domain.e.commerce.domain.table.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class customerService {

    @Autowired
    userRepo user_repo;

    @Autowired
    customerRepo customer_repo;

    @Autowired
    roleRepo role_repo;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    User user;


    //get customer detail
    public Customer  findOneCustomer(Customer customer) throws IllegalAccessException {
        Customer existingUser = customer_repo.getById(customer.getId());

        if(existingUser!=null){
            if(!existingUser.getIsActive()){
                throw new IllegalAccessException("User is currently not LoggedIN");
            }
            else{
                return existingUser;
            }
        }
        else
        throw new IllegalAccessException("User not found");
    }


    //get customer address
    public List<UserAddress>  findOneCustomerAddress(Customer customer) throws IllegalAccessException {
        Customer existingUser = customer_repo.getById(customer.getId());

        if(existingUser!=null){
            if(!existingUser.getIsActive()){
                throw new IllegalAccessException("User is currently not LoggedIN");
            }
            else{
                return existingUser.getAddress();
            }
        }
        else
            throw new IllegalAccessException("User not found");
    }

}
