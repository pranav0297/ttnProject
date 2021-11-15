package com.e.commerce.domain.e.commerce.domain.repo;

import com.e.commerce.domain.e.commerce.domain.table.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface customerRepo extends JpaRepository<Customer, Long> {

////@Query("select new Customer(firstName) from  Customer  ")
//     List<Customer> getCustomer();

    @Override
    Customer getById(Long aLong);



}
