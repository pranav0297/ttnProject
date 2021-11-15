package com.e.commerce.domain.e.commerce.domain.controller;


import com.e.commerce.domain.e.commerce.domain.Service.RegistrationService;
import com.e.commerce.domain.e.commerce.domain.table.user.Customer;
import com.e.commerce.domain.e.commerce.domain.table.user.Seller;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    @Autowired
    private final RegistrationService registrationService;



    //Seller Register
    @PostMapping(path = "seller")
    public String registerSeller(@Valid  @RequestBody Seller seller) throws IllegalAccessException {
        return registrationService.registerSeller(seller);
    }


    //Customer Register
    @PostMapping(path = "customer")
    public String registerCustomer(@RequestBody Customer customer) throws IllegalAccessException {
        return registrationService.registerCustomer(customer);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
