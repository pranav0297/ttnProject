package com.e.commerce.domain.e.commerce.domain.controller;

import com.e.commerce.domain.e.commerce.domain.Service.customerService;
import com.e.commerce.domain.e.commerce.domain.email.EmailSender;
import com.e.commerce.domain.e.commerce.domain.repo.customerRepo;
import com.e.commerce.domain.e.commerce.domain.repo.userAddressRepo;
import com.e.commerce.domain.e.commerce.domain.repo.userRepo;
import com.e.commerce.domain.e.commerce.domain.table.user.Customer;
import com.e.commerce.domain.e.commerce.domain.table.user.User;
import com.e.commerce.domain.e.commerce.domain.table.user.UserAddress;
import com.e.commerce.domain.e.commerce.domain.token.ResetToken;
import com.e.commerce.domain.e.commerce.domain.token.confirmationTokenRepo;
import com.e.commerce.domain.e.commerce.domain.token.resetTokenRepo;
import com.e.commerce.domain.e.commerce.domain.token.resetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@RestController
public class customer_controller {

    @Autowired
    customerService service;

    @Autowired
    customerRepo customer_repo;

    @Autowired
    userRepo user_repo;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    @Autowired
    resetTokenService tokenService;

    @Autowired
    resetTokenRepo resetRepo;

    @Autowired
    confirmationTokenRepo tokenRepo;

    @Autowired
    EmailSender emailSender;

    @Autowired
    userAddressRepo  addressRepo;

    //Get Customer detail
    @GetMapping("/Employee-one/customer")
    public Customer findCustomer(@RequestBody Customer customer) throws IllegalAccessException {
        Customer e = service.findOneCustomer(customer);
        if (e == null) {
            throw new UserNotFoundException();
        }
        return e;
    }


    //Get Customer Address
    @GetMapping("/Employee-one/customer/address")
    public List<UserAddress> findCustomerAddress(@RequestBody Customer customer) throws IllegalAccessException {
        return service.findOneCustomerAddress(customer);

    }


    //Update Customer profile
    @PutMapping("/update/customer")
    public String updateCustomer(@RequestBody Customer customer) {

        Customer existingUser = customer_repo.getById(customer.getId());

        if (existingUser != null) {
            if (existingUser.getIsActive()) {
                existingUser.setFirstName(customer.getFirstName());
                existingUser.setMiddleName(customer.getMiddleName());
                existingUser.setLastName(customer.getLastName());
                existingUser.setEmail(customer.getEmail());
                existingUser.setContact(customer.getContact());
                existingUser.setAddress(customer.getAddress());
                user_repo.save(existingUser);
                return "Update success";
            } else {
                return "Not LoggedIn";
            }
        }
        return "User did not  exists";
    }

//Update Customer password
    @PutMapping("/update/customer/password")
    public String updateCustomerPassword(@RequestBody Customer customer) {

        Customer existingUser = customer_repo.getById(customer.getId());

        if (existingUser != null) {
            if (existingUser.getIsEnable()) {
                if (passwordEncoder.matches(customer.getPassword(), existingUser.getPassword())) {
                    long i = customer.getId();
                    tokenRepo.deleteById(i);

                    String token = UUID.randomUUID().toString();

                    ResetToken resetToken = new ResetToken(
                            token,
                            LocalDateTime.now().plusMinutes(15),
                            customer
                    );
                    tokenService.saveResetToken(resetToken);

                    // Reset password
                    customer.setResetToken(token);
                    ResetToken reset_Token = resetRepo.findByToken(customer.getResetToken());
                    LocalDateTime expiredAt = reset_Token.getExpiresAt();


                    if (reset_Token != null) {

                        User user1 = customer_repo.getById(reset_Token.getUser().getId());
                        user1.setPassword(passwordEncoder.encode(customer.getNewPassword()));


                        long j = customer.getId();
                        resetRepo.deleteById(j);

                        user_repo.save(user1);

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
                } else {
                    return "Old password did not match!! Try again ";
                }
            } else {
                return "User is not Activated ";
            }
        } else {
            return "User not present";
        }

    }



    //Add new Customer Address
    @PostMapping ("/update/customer/NewAddress")
    public String updateCustomerAddress(@RequestBody Customer customer) {

        Customer existingUser = customer_repo.getById(customer.getId());

        if (existingUser != null) {
            if (existingUser.getIsActive()) {
                existingUser.setAddress(customer.getAddress());
                user_repo.save(existingUser);
                return "success";
            }
            else{
                return "User Not LoggedIn";
            }
        }
        else{
            return "User Not present";
        }
    }


//Delete Address
    @DeleteMapping ("/update/customer/delete")
    public String DeleteCustomerAddress(@RequestBody UserAddress userAddress) {

        Customer existingUser = customer_repo.getById(userAddress.getId());

        if (existingUser != null) {
            if(existingUser.getIsActive()) {
                addressRepo.deleteById(userAddress.getId());
                customer_repo.save(existingUser);
                return "Address updated";
            }
            else{
                return "User not loggedIn";
            }
        }
        return "Address not present";
    }




    //existAddress update
    @PutMapping  ("/update/customer/existAddress")
    public String UpdateExistCustomerAddress(@RequestBody UserAddress userAddress) {

        UserAddress existingUser = addressRepo.getById(userAddress.getId());

        if (existingUser != null) {
                existingUser.setLabel(userAddress.getLabel());
                existingUser.setCountry(userAddress.getCountry());
                existingUser.setState(userAddress.getState());
                existingUser.setCity(userAddress.getCity());
                existingUser.setAddress_line(userAddress.getAddress_line());
                existingUser.setZip_code(userAddress.getZip_code());
                addressRepo.save(existingUser);
                return "Address updated";
            }
        return "Address not present";
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

