package com.ironhack.WTWAPI.security;

import com.ironhack.WTWAPI.filter.CustomAuthenticationFilter;
import com.ironhack.WTWAPI.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());

        // url to login:
        customAuthenticationFilter.setFilterProcessesUrl("/api/auth/login");
        http.csrf().disable();
        http.cors();
        http.sessionManagement().sessionCreationPolicy(STATELESS);


        // ----------------------------------------------------------------------------------------------------------------------
        // anyone can access:
        // SIGN UP
        http.authorizeRequests().antMatchers("/api/auth/signup").permitAll();
        http.authorizeRequests().antMatchers("/api/auth/verify").permitAll();
        // LOG IN
        http.authorizeRequests().antMatchers("/api/auth/login/**").permitAll();

        // get a list of ALL USERS:
        http.authorizeRequests().antMatchers("/api/users").permitAll();
        // get a USER BY ID:
        http.authorizeRequests().antMatchers("/api/users/{id}").permitAll();

        // ADD A WATCH-ITEM to the db:
        http.authorizeRequests().antMatchers("/api/items/new").permitAll();
        // get a list of ALL WATCH-ITEMS
        http.authorizeRequests().antMatchers("/api/items").permitAll();
        // get a WATCH-ITEM BY ID
        http.authorizeRequests().antMatchers("/api/items/{id}").permitAll();

        // get a list of ALL GENRES
        http.authorizeRequests().antMatchers("/api/genres").permitAll();

        // ADD A WATCH-LIST to the db:
        http.authorizeRequests().antMatchers("/api/lists/new").permitAll();
        // get a list of ALL WATCH-LISTS
        http.authorizeRequests().antMatchers("/api/lists").permitAll();
        // get a WATCH-LIST BY ID
        http.authorizeRequests().antMatchers("/api/lists/{id}").permitAll();


        // ----------------------------------------------------------------------------------------------------------------------

        // Swagger api documentation:
        http.authorizeRequests().antMatchers("/", "/csrf", "/v2/api-docs", "/swagger-resources/configuration/ui", "/configuration/ui", "/swagger-resources", "/swagger-resources/configuration/security", "/configuration/security", "/swagger-ui.html", "/webjars/**").permitAll();

        // For any request you should de authenticated (logged in):
        http.authorizeRequests().anyRequest().authenticated();

        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
