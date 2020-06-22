package org.niewidoczniakademicy.rezerwacje.config.security;

import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.method.AbstractFallbackMethodSecurityMetadataSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import static org.springframework.security.access.annotation.Jsr250SecurityConfig.DENY_ALL_ATTRIBUTE;

public final class DenyByDefaultMethodSecurityMetadataSource extends AbstractFallbackMethodSecurityMetadataSource {

    @Override
    protected Collection<ConfigAttribute> findAttributes(Method method, Class<?> targetClass) {
        MergedAnnotations methodAnnotations = MergedAnnotations.from(method);
        if (targetClass.getPackageName().startsWith("org.niewidoczniakademicy.rezerwacje")
                && MergedAnnotations.from(method.getDeclaringClass()).isPresent(Controller.class)
                && methodAnnotations.isPresent(RequestMapping.class)
                && !(methodAnnotations.isPresent(PreAuthorize.class) || methodAnnotations.isPresent(Secured.class))) {
            logger.warn("Unsecured " + method.toGenericString());
            return List.of(DENY_ALL_ATTRIBUTE);
        }
        return null;
    }

    @Override
    protected Collection<ConfigAttribute> findAttributes(Class<?> clazz) {
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
}
