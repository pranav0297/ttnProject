package com.e.commerce.domain.e.commerce.domain.controller;

import com.e.commerce.domain.e.commerce.domain.Service.adminService;
import com.e.commerce.domain.e.commerce.domain.Service.customerService;
import com.e.commerce.domain.e.commerce.domain.Service.sellerService;
import com.e.commerce.domain.e.commerce.domain.table.user.Customer;
import com.e.commerce.domain.e.commerce.domain.table.user.Seller;
import com.e.commerce.domain.e.commerce.domain.table.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class adminController {

    @Autowired
    adminService admin_service;


    //GET Customer
    @GetMapping("/get/customer")
    public List<Customer>  findAllCustomer(){
        return admin_service.getCustomer();
    }

    @GetMapping("/get/seller")
    public List<Seller> findAllSeller(){
        return admin_service.getSeller();
    }



    @PutMapping("/activate/deactivate/seller")
    public String  updateSeller(@RequestBody Seller seller){
        return admin_service.ActivateDeactivateSeller(seller);

    }


    @PutMapping("/activate/deactivate/customer")
    public String  updateCustomer(@RequestBody Customer customer){
        return admin_service.ActivateDeactivateCustomer(customer);

    }
}
