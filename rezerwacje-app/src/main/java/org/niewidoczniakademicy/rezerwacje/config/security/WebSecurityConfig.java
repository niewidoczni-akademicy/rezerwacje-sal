package org.niewidoczniakademicy.rezerwacje.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;

//@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected final void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
            .anonymous().authorities("ROLE_ANON").and()
            .formLogin()
//                .loginProcessingUrl("/user/login/process")
                .and()
            .exceptionHandling()
//                .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
//                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
                .defaultAuthenticationEntryPointFor(
                        new Http403ForbiddenEntryPoint(),
                        new NegatedRequestMatcher(new AntPathRequestMatcher("/login")))
                .and()
            .csrf()
                .disable();
        //@formatter:on
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
