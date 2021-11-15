package com.e.commerce.domain.e.commerce.domain.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface resetTokenRepo extends JpaRepository<ResetToken,Long> {
    public ResetToken findByToken(String resetToken);
    public void deleteById(Long id);
}
