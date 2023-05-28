package com.nazjara.config;

import com.nazjara.listener.PostLoadListener;
import com.nazjara.listener.PreInsertListener;
import com.nazjara.listener.PreUpdateListener;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListenerRegistration implements BeanPostProcessor {

    private final PostLoadListener postLoadListener;
    private final PreInsertListener preInsertListener;
    private final PreUpdateListener preUpdateListener;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof LocalContainerEntityManagerFactoryBean factoryBean) {
            var sessionFactory = (SessionFactoryImpl) factoryBean.getNativeEntityManagerFactory();
            var registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
//            registry.appendListeners(EventType.POST_LOAD, postLoadListener); //disabled in favor of JPA callbacks
//            registry.appendListeners(EventType.PRE_INSERT, preInsertListener);
//            registry.appendListeners(EventType.PRE_UPDATE, preUpdateListener);
        }

        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
