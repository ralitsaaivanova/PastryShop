package org.softuni.pastryShop.config;

import org.softuni.pastryShop.repository.UserRepository;
import org.softuni.pastryShop.service.impl.ShopUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
@EnableMethodSecurity
@Configuration
public class SecurityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    @Bean
    public DefaultSecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(
                authorizeRequests->authorizeRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/index","/signup","/signin","/login-error","/logout").permitAll()
                        .requestMatchers("/product").permitAll()
                        .anyRequest().authenticated()
        ).formLogin(
                formLogin->{
                    formLogin
                            .loginPage("/signin")
                            .usernameParameter("email")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/index")
                            .failureForwardUrl("/login-error");
                }
        ).logout(
                logout->{
                    logout.logoutUrl("/logout")
                            .logoutSuccessUrl("/signin")
                            .invalidateHttpSession(true);

                }
        );
        return httpSecurity.build();
    }

    @Bean
    public ShopUserDetailsService userDetailsService(UserRepository userRepository){
        //shop users and roles(spring security)
        return new ShopUserDetailsService(userRepository);
    }

}
