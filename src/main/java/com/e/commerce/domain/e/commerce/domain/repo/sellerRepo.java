package com.e.commerce.domain.e.commerce.domain.repo;

import com.e.commerce.domain.e.commerce.domain.table.user.Seller;
import com.e.commerce.domain.e.commerce.domain.table.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface sellerRepo extends JpaRepository<Seller,Long> {
    @Query("select u from User u where u.email=:email")
    public User getUserByUserName(@Param("email")String email);

    @Override
    Optional<Seller> findById(Long aLong);

    @Override
    Seller getById(Long aLong);

}
