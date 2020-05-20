package org.niewidoczniakademicy.rezerwacje.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;

import javax.servlet.http.HttpServletResponse;

@Slf4j
//@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected final void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
            .anonymous().authorities("ROLE_ANON").and()
            .authorizeRequests().and()
            .formLogin()
                .permitAll()
                .successHandler(this.successHandler())
                .failureHandler(this.failureHandler())
                .and()
            .logout()
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                .and()
            .exceptionHandling()
                .defaultAuthenticationEntryPointFor(
                        new Http403ForbiddenEntryPoint(),
                        new NegatedRequestMatcher(new AntPathRequestMatcher("/login")))
                .and()
            .csrf()
                .disable();
        //@formatter:on
    }

    private AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            log.debug("Login success");
            log.debug(authentication.toString());
            response.getWriter().append("OK");
            response.setStatus(HttpServletResponse.SC_OK);
        };
    }

    private AuthenticationFailureHandler failureHandler() {
        return (request, response, exception) -> {
            log.debug(exception.toString());
            response.getWriter().append("Authentication failure");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        };
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
