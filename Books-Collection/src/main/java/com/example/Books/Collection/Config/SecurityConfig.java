package com.example.Books.Collection.Config;


import com.example.Books.Collection.Service.UserPrincipalDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    UserPrincipalDetailsService userPrincipalDetailsService;


    @Autowired
    public SecurityConfig(UserPrincipalDetailsService userPrincipalDetailsService) {
        this.userPrincipalDetailsService = userPrincipalDetailsService;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/signup").permitAll()
                .antMatchers("/user/activation").permitAll()

                .antMatchers("/book/addbook").authenticated()
                .antMatchers("/book/listofbooks").authenticated()
                .antMatchers("/book/deleteonebook").authenticated()
                .antMatchers("/book/deleteallbooks").authenticated()

                .antMatchers("/user/deleteoneuserbyid").hasRole("ADMIN")
                .antMatchers("/user/listofusers").hasRole("ADMIN")
                .antMatchers("/user/onebynickname").hasRole("ADMIN")
                .antMatchers("/user/onebyid").hasRole("ADMIN")
                .and()
                .formLogin().permitAll();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userPrincipalDetailsService);
        return daoAuthenticationProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}