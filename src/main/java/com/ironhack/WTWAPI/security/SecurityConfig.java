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

import static org.springframework.http.HttpMethod.*;
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

        // ----------------------------------------------------------------------------------------------------------------------
        // ----- USERS ----------------------------------------------------------------------------------------------------------
        // ----------------------------------------------------------------------------------------------------------------------
        // SIGN UP
        http.authorizeRequests().antMatchers("/api/auth/signup").permitAll();
        // LOG IN
        http.authorizeRequests().antMatchers("/api/auth/login/**").permitAll();
        http.authorizeRequests().antMatchers("/api/auth/verify").permitAll();

        // get a list of ALL USERS:
        http.authorizeRequests().antMatchers("/api/users").permitAll();
        // get a USER BY ID:
        http.authorizeRequests().antMatchers("/api/users/{id}").permitAll();
        // get a list of USERS BY USERNAME OR EMAIL:
        http.authorizeRequests().antMatchers("/api/users/search/{string}").permitAll();

        // FOLLOW a user
        http.authorizeRequests().antMatchers(PATCH, "/api/users/follow/{id}").permitAll();
        // UNFOLLOW a user
        http.authorizeRequests().antMatchers(PATCH, "/api/users/unfollow/{id}").permitAll();
        // UPDATE a user by id
        http.authorizeRequests().antMatchers(PUT, "/api/users/{id}").permitAll();
        // DELETE a user by id
        http.authorizeRequests().antMatchers(DELETE, "/api/users/{id}").permitAll();


        // ----------------------------------------------------------------------------------------------------------------------
        // ----- ITEMS ----------------------------------------------------------------------------------------------------------
        // ----------------------------------------------------------------------------------------------------------------------
        // ADD A WATCH-ITEM to the db:
        http.authorizeRequests().antMatchers(POST, "/api/items/new").permitAll();
        // get a list of ALL WATCH-ITEMS
        http.authorizeRequests().antMatchers(GET, "/api/items").permitAll();
        // get a WATCH-ITEM BY ID
        http.authorizeRequests().antMatchers(GET, "/api/items/{id}").permitAll();
        // get a WATCH-ITEM BY IMDb ID
        http.authorizeRequests().antMatchers(GET, "/api/items/imdb/{id}").permitAll();
        // add a WATCH-ITEM to a WATCH-LIST
        http.authorizeRequests().antMatchers(PATCH, "/api/items/add-to-list/{listId}").permitAll();
        // add a WATCH-ITEM to the DB and to a WATCH-LIST
        http.authorizeRequests().antMatchers(POST, "/api/items/new/add-to-list/{listId}").permitAll();

        // ----------------------------------------------------------------------------------------------------------------------
        // ----- LISTS ----------------------------------------------------------------------------------------------------------
        // ----------------------------------------------------------------------------------------------------------------------
        // ADD A WATCH-LIST to the db:
        http.authorizeRequests().antMatchers("/api/lists/new").permitAll();
        // get a list of ALL WATCH-LISTS
        http.authorizeRequests().antMatchers("/api/lists").permitAll();
        // get a list of ALL WATCH-LISTS by OWNER
        http.authorizeRequests().antMatchers("/api/lists/owner/{ownerId}").permitAll();
        // get a list of ALL WATCH-LISTS containing a NAME
        http.authorizeRequests().antMatchers("/api/lists/name/{name}").permitAll();
        // get a WATCH-LIST BY ID
        http.authorizeRequests().antMatchers("/api/lists/{id}").permitAll();
        // FOLLOW a list
        http.authorizeRequests().antMatchers(PATCH, "/api/lists/follow/{listId}").permitAll();
        // UNFOLLOW a list
        http.authorizeRequests().antMatchers(PATCH, "/api/lists/unfollow/{listId}").permitAll();
        // Add a PARTICIPANT to a WATCH-LIST
        http.authorizeRequests().antMatchers(PATCH, "/api/lists/participant/{listId}").permitAll();
        // UPDATE a WATCH-LIST BY ID
        http.authorizeRequests().antMatchers(PUT, "/api/lists/{id}").permitAll();
        // DELETE a WATCH-LIST BY ID
        http.authorizeRequests().antMatchers(DELETE, "/api/lists/{id}").permitAll();


        // ----------------------------------------------------------------------------------------------------------------------
        // ----- GENRES ----------------------------------------------------------------------------------------------------------
        // ----------------------------------------------------------------------------------------------------------------------
        // get a list of ALL GENRES
        http.authorizeRequests().antMatchers("/api/genres").permitAll();


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
