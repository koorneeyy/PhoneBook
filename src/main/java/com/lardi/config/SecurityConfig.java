package com.lardi.config;


import com.lardi.DaoUserDetailsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    final static private Logger logger = Logger.getLogger(SecurityConfig.class);
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (logger.isDebugEnabled()) {   logger.debug("configure Security" ); }
        http    .authorizeRequests()
                                .antMatchers("/css/**","/js/**", "/index","/register").permitAll()
               // .antMatchers("/user/**").hasAnyRole("USER")
                .antMatchers("/user/**").authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                .failureUrl("/login-error")
                .passwordParameter("j_password")
                .usernameParameter("j_username")
                .defaultSuccessUrl("/user/list",true)
                .and()
                .logout()
                .logoutUrl("/appLogout")
                .logoutSuccessUrl("/login");
    }




    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        if (logger.isDebugEnabled()) {   logger.debug("configureGlobal  Security" ); }
                   auth.userDetailsService(new DaoUserDetailsService());
    }


}
