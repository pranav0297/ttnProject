package com.e.commerce.domain.e.commerce.domain.token;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class resetTokenService {

    @Autowired
    resetTokenRepo resetTokenRepository;

    public void saveResetToken(ResetToken token) {
        resetTokenRepository.save(token);
    }

    public ResetToken getToken(String token) {
        return resetTokenRepository.findByToken(token);
    }
}
