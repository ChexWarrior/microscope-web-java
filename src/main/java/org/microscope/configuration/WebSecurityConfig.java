package org.microscope.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.AbstractRequestMatcherRegistry;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
       InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
       manager.createUser(User.withDefaultPasswordEncoder()
               .username("user").password("password").roles("USER").build());
       return manager;
   }

   @Bean
   @Order(1)
   public SecurityFilterChain unprotectedPages(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/register", "/login")
            .authorizeHttpRequests(authorize ->
                    authorize.anyRequest().permitAll()
            );
        return http.build();
   }

   @Bean
   public SecurityFilterChain protectedPages(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize ->
                    authorize.anyRequest().authenticated()
            )
            .formLogin(Customizer.withDefaults());

        return http.build();
   }
}
