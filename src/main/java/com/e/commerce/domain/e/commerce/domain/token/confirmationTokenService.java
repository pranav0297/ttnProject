package com.e.commerce.domain.e.commerce.domain.token;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class confirmationTokenService {


@Autowired
    private final confirmationTokenRepo confirmationTokenRepository;

  private final  confirmationToken confirmation_Token;

    public void saveConfirmationToken(confirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public Optional<confirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }

public long getByUserId(long id){
        return confirmationTokenRepository.deleteByUserId(confirmation_Token.getId());
}

}
