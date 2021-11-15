package com.e.commerce.domain.e.commerce.domain.config;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/users/register/**", "/user/login/seller", "/api/v*/registration/**","/users/**","/forgot/**").permitAll()
                //.antMatchers("/get/**").hasRole("ADMIN")
                .antMatchers("/get/**","/activate/deactivate/**").permitAll()
                .antMatchers("/Employee-one/**","/update/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin();
    }

    //    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("pranav").password(this.passwordEncoder().encode("pranav")).roles("ADMIN");
////        auth.inMemoryAuthentication().withUser("mayank").password(this.passwordEncoder().encode("mayank")).roles("SELLER");
////
////        auth.inMemoryAuthentication().withUser("aman").password(this.passwordEncoder().encode("aman")).roles("CUSTOMER");
////
//       auth. authenticationProvider(authenticationProvider());
//       //.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
//    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsServiceImpl);
        return provider;
    }

//    @Bean
//    public BCryptPasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

//    @Bean
//    UserDetailsService getUserDetailsService(){
//        return new UserDetailsServiceImpl();
//    }


//    @Bean
//    public DaoAuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
//      // daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        return daoAuthenticationProvider;
//    }

//
}
