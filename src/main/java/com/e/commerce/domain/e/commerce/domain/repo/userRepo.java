package com.e.commerce.domain.e.commerce.domain.repo;

import com.e.commerce.domain.e.commerce.domain.table.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface userRepo extends JpaRepository<User,Long> {

    //@Query("select u from User u where u.email=:email")
   Optional<User> findByEmail(String email);


    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.isEnable = TRUE WHERE a.email = ?1")
    int enableUser(String email);


    User findByEmailIgnoreCase(String email);


    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.password = ?1  WHERE a.id = ?2")
    int resetPassword(String password,String id);


}
