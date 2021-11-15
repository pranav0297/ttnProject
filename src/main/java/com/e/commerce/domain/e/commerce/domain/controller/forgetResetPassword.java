package com.e.commerce.domain.e.commerce.domain.controller;

import com.e.commerce.domain.e.commerce.domain.email.EmailSender;
import com.e.commerce.domain.e.commerce.domain.repo.userRepo;
import com.e.commerce.domain.e.commerce.domain.table.user.User;
import com.e.commerce.domain.e.commerce.domain.token.ResetToken;
import com.e.commerce.domain.e.commerce.domain.token.confirmationTokenRepo;
import com.e.commerce.domain.e.commerce.domain.token.resetTokenRepo;
import com.e.commerce.domain.e.commerce.domain.token.resetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@RestController
public class forgetResetPassword {

    @Autowired
    userRepo repo;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    EmailSender emailSender;


    @Autowired
    ResetToken resetToken;

    @Autowired
    resetTokenService tokenService;

    @Autowired
    resetTokenRepo resetRepo;

    @Autowired
    confirmationTokenRepo tokenRepo;


    @PostMapping("/forgot")
    public String forgotPassword(@RequestBody User user) {


        Optional<User> optional = Optional.ofNullable(repo.findByEmailIgnoreCase(user.getEmail()));

        if (!optional.isPresent()) {
            return "We didn't find an account for that e-mail address.";
        } else {


            User user1 = optional.get();

            if (user1.getIsEnable()) {


                long i = user1.getId();
                tokenRepo.deleteById(i);

                String token = UUID.randomUUID().toString();

                ResetToken resetToken = new ResetToken(
                        token,
                        LocalDateTime.now().plusMinutes(15),
                        user1
                );
                tokenService.saveResetToken(resetToken);
                return token;

            } else {
                return "User not Activated";
            }
        }
    }

    @Transactional
    @PutMapping(path = "forgot/reset")
    public String resetToken(@RequestBody User user) {

        ResetToken reset_Token = resetRepo.findByToken(user.getResetToken());
        LocalDateTime expiredAt = reset_Token.getExpiresAt();


        if (reset_Token != null) {

            User user1 = repo.findByEmailIgnoreCase(reset_Token.getUser().getEmail());
            user1.setPassword(passwordEncoder.encode(user.getPassword()));


            long i = user1.getId();
            resetRepo.deleteById(i);

            repo.save(user1);

            //Email
            emailSender.send(
                    user1.getEmail(),
                    buildEmail(user1.getFirstName()));

            return "You have successfully reset your password.  You may now login";

        } else if (expiredAt.isBefore(LocalDateTime.now())) {
            return "token expired";
        } else {
            return "Oops!  This is an invalid token";

        }
    }

    private String buildEmail(String name) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Password reset Successfully, you may now login with your latest password </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"></a> </p></blockquote>\n Thank You. <p>See you soon </p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

}
