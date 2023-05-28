package com.nazjara.config;

import com.nazjara.interceptor.EncryptionInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class InterceptorRegistration implements HibernatePropertiesCustomizer {

    private final EncryptionInterceptor encryptionInterceptor;

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
//        hibernateProperties.put("hibernate.session_factory.interceptor", encryptionInterceptor); //disabled in favor of JPA callbacks
    }
}
