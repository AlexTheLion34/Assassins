package com.itmo.assassins.configuration;

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

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/")
                .permitAll()
                .antMatchers("/profile", "/view-request**")
                .authenticated()
                .antMatchers("/add-report**", "/payment-confirm**")
                .hasAuthority("EXECUTOR")
                .antMatchers("/add-request**", "/view-report**", "/payment**", "/evaluate**")
                .hasAuthority("CUSTOMER")
                .antMatchers("/change-request**", "/change-team**", "/confirm-request**")
                .hasAuthority("MASTER_ASSASSIN")
                .antMatchers("/add-arsenal**")
                .hasAuthority("GUNSMITH")
                .antMatchers("/add-road-eq**")
                .hasAuthority("CABMAN")
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/profile")
                .failureUrl("/login?error")
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true);

        http
                .headers()
                .frameOptions()
                .disable();

    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
