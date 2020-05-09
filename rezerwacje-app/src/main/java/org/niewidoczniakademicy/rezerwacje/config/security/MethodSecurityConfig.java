package org.niewidoczniakademicy.rezerwacje.config.security;

import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
    @Override
    protected final MethodSecurityMetadataSource customMethodSecurityMetadataSource() {
        return new CustomMethodSecurityMetadataSource();
    }
}
