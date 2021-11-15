package com.e.commerce.domain.e.commerce.domain.email;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


public interface EmailSender {
    void send (String to, String email);
}
