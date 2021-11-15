package com.e.commerce.domain.e.commerce.domain.Service;

import com.e.commerce.domain.e.commerce.domain.repo.customerRepo;
import com.e.commerce.domain.e.commerce.domain.repo.sellerRepo;
import com.e.commerce.domain.e.commerce.domain.repo.userRepo;
import com.e.commerce.domain.e.commerce.domain.table.user.Customer;
import com.e.commerce.domain.e.commerce.domain.table.user.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class adminService {

    @Autowired
    userRepo user_repo;


    @Autowired
    customerRepo customer_repo;

    @Autowired
    sellerRepo seller_repo;


//Get AllCustomer
    public List<Customer> getCustomer(){
        return this.customer_repo.findAll();
    }


    //Get AllSeller
    public List<Seller> getSeller(){
        return this.seller_repo.findAll();
    }



    //Activate Deactivate Seller
    public String   ActivateDeactivateSeller(Seller seller){
        Seller existingUser = seller_repo.findById(seller.getId()).orElse(null);
        if(existingUser!=null) {
            // existingUser.setInvalidAttemptCount(seller.getInvalidAttemptCount());
            if (existingUser.getIsEnable() == true) {
                existingUser.setIsEnable(false);
                user_repo.save(existingUser);
                return "User De-activated successfully";
            } else {
                existingUser.setIsEnable(true);
                user_repo.save(existingUser);
                return "User Activated successfully";
            }
        }
        else{
            return "User Not registered";
        }

    }


    //Activate Deactivate Customer
    public String   ActivateDeactivateCustomer(Customer customer) {
        Customer existingUser = customer_repo.findById(customer.getId()).orElse(null);
        if (existingUser != null) {
            if (existingUser.getIsEnable() == true) {
                existingUser.setIsEnable(false);
                user_repo.save(existingUser);
                return "User De-activated successfully";
            } else {
                existingUser.setIsEnable(true);
                user_repo.save(existingUser);
                return "User Activated successfully";
            }
        } else {
            return "User not Registered";
        }
    }
}
