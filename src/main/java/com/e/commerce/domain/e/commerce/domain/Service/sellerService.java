package com.e.commerce.domain.e.commerce.domain.Service;

import com.e.commerce.domain.e.commerce.domain.repo.sellerRepo;
import com.e.commerce.domain.e.commerce.domain.repo.userRepo;
import com.e.commerce.domain.e.commerce.domain.table.user.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class sellerService {

    @Autowired
    sellerRepo seller_repo;

    @Autowired
    userRepo user_repo;


    @Autowired
    BCryptPasswordEncoder passwordEncoder;


//get seller detail

    public Seller findOneSeller(Seller seller) throws IllegalAccessException {
        Seller existingUser = seller_repo.getById(seller.getId());

        if (existingUser != null) {
            if (!existingUser.getIsActive()) {
                throw new IllegalAccessException("User is currently not LoggedIN");
            } else {
                return existingUser;
            }
        } else
            throw new IllegalAccessException("User not found");
    }


}
