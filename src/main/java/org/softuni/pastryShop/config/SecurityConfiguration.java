package org.softuni.pastryShop.config;

import org.softuni.pastryShop.service.oauth.OAuthSuccessHandler;
import org.softuni.pastryShop.repository.UserRepository;
import org.softuni.pastryShop.service.impl.ShopUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity
@Configuration
public class SecurityConfiguration {

    private final String rememberMeKey;
    public SecurityConfiguration(@Value("${pastryShop.remember.me.key}") String rememberMeKey) {
        this.rememberMeKey = rememberMeKey;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, OAuthSuccessHandler oAuthSuccessHandler) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.authorizeHttpRequests(
                authorizeRequests -> authorizeRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/index", "/signup", "/signin", "/login-error", "/logout").permitAll()
                        .requestMatchers("/product").permitAll()
                        .requestMatchers("/addProduct").hasAnyRole("ADMIN","SELLER")
                        .requestMatchers("/products/delete/{id}").hasAnyRole("ADMIN","SELLER")
                        .requestMatchers("/products/edit/{id}").hasAnyRole("SELLER","ADMIN")
                        .requestMatchers("/api/manageUserRoles").permitAll()
                        .requestMatchers(HttpMethod.GET, "/product/**").permitAll()
                        .requestMatchers("category","category/{id}").permitAll()
                        .requestMatchers("/addCategory").hasAnyRole("ADMIN","SELLER")
                        .requestMatchers("account").hasAnyRole("ADMIN","SELLER")
                        .requestMatchers("/accountShoppingCard").hasRole("BUYER")
                        .anyRequest().authenticated()
        ).formLogin(
                formLogin -> {
                    formLogin
                            .loginPage("/signin")
                            .usernameParameter("email")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/index",true)
                            .failureForwardUrl("/login-error");
                }
        ).logout(
                logout -> {
                    logout.logoutUrl("/logout")
                            .logoutSuccessUrl("/")
                            .invalidateHttpSession(true);
                }
        ).rememberMe(rememberMe ->{
            rememberMe.key(rememberMeKey)
                    .rememberMeParameter("rememberme")
                    .rememberMeCookieName("rememberme");
        }).oauth2Login(
                oath->oath.successHandler(oAuthSuccessHandler));

        return httpSecurity.build();
    }

    @Bean
    public ShopUserDetailsService userDetailsService(UserRepository userRepository) {
        //shop users and roles(spring security)
        return new ShopUserDetailsService(userRepository);
    }

}
