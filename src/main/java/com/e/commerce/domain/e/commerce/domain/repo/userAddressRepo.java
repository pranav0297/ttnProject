package com.e.commerce.domain.e.commerce.domain.repo;

import com.e.commerce.domain.e.commerce.domain.table.user.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userAddressRepo extends JpaRepository<UserAddress,Long> {
    @Override
    void deleteById(Long aLong);

    @Override
     UserAddress getById(Long aLong);
}
