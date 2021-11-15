package com.e.commerce.domain.e.commerce.domain.token;

import com.e.commerce.domain.e.commerce.domain.table.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Service
public class ResetToken {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "reset_token_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ResetToken(String token, LocalDateTime expiresAt, User user) {

        this.token = token;
        this.expiresAt = expiresAt;
        this.user = user;
    }
}
