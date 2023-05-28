package com.nazjara.interceptor;

import com.nazjara.service.EncryptionService;
import lombok.RequiredArgsConstructor;
import org.hibernate.CallbackException;
import org.hibernate.Interceptor;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EncryptionInterceptor implements Interceptor {

    private final EncryptionService encryptionService;

    @Override
    public boolean onLoad(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        var newState = processFields(entity, state, propertyNames, "onLoad");
        return Interceptor.super.onLoad(entity, id, newState, propertyNames, types);
    }

    @Override
    public boolean onFlushDirty(Object entity, Object id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) throws CallbackException {
        var newState = processFields(entity, currentState, propertyNames, "onFlushDirty");
        return Interceptor.super.onFlushDirty(entity, id, newState, previousState, propertyNames, types);
    }

    @Override
    public boolean onSave(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        var newState = processFields(entity, state, propertyNames, "onSave");
        return Interceptor.super.onSave(entity, id, newState, propertyNames, types);
    }

    private Object[] processFields(Object entity, Object[] states, String[] propertyNames, String type) {
        var annotatedFields = getAnnotationFields(entity);

        for (String field: annotatedFields) {
            for (int i = 0; i < propertyNames.length; i++) {
                if (propertyNames[i].equals(field)) {
                    if (StringUtils.hasText(states[i].toString())) {
                        if ("onSave".equals(type) || "onFlushDirty".equals(type)) {
                            states[i] = encryptionService.encrypt(states[i].toString());
                        } else if ("onLoad".equals(states[i].toString())) {
                            states[i] = encryptionService.decrypt(states[i].toString());
                        }
                    }
                }
            }
        }

        return states;
    }

    private List<String> getAnnotationFields(Object entity) {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.getAnnotation(EncryptedString.class) != null)
                .map(Field::getName)
                .toList();
    }
}
