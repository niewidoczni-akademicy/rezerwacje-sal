package org.niewidoczniakademicy.rezerwacje.config.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.niewidoczniakademicy.rezerwacje.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
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
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(createAuthenticationProvider(userService));
        if (userService.getAllSystemUsers().getSystemUsers().isEmpty()) { //If no users in database provide an admin account
            auth.inMemoryAuthentication()
                    .withUser("admin")
                    .password(getEncoder().encode("admin"))
                    .roles("ADMINISTRATOR");
        }
    }

    private AuthenticationProvider createAuthenticationProvider(UserDetailsService service) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(service);
        provider.setPasswordEncoder(getEncoder());
        provider.setHideUserNotFoundExceptions(true);
        return provider;
    }

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
