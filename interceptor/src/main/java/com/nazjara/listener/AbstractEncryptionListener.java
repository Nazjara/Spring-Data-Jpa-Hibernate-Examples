package com.nazjara.listener;

import com.nazjara.interceptor.EncryptedString;
import com.nazjara.service.EncryptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

@RequiredArgsConstructor
public abstract class AbstractEncryptionListener {

    private final EncryptionService encryptionService;

    protected void encrypt(Object[] state, String[] propertyNames, Object entity) {
        ReflectionUtils.doWithFields(entity.getClass(), field -> encryptField(field, state, propertyNames),
                this::isFieldEncrypted);
    }

    protected void decrypt(Object entity) {
        ReflectionUtils.doWithFields(entity.getClass(), field -> decryptField(field, entity),
                this::isFieldEncrypted);
    }

    private void encryptField(Field field, Object[] state, String[] propertyNames) {
        var index = getPropertyIndex(field.getName(), propertyNames);
        var currentValue = state[index];
        state[index] = encryptionService.encrypt(currentValue.toString());
    }


    private void decryptField(Field field, Object entity) {
        field.setAccessible(true);
        var value = ReflectionUtils.getField(field, entity);
        ReflectionUtils.setField(field, entity, encryptionService.decrypt(value.toString()));
    }

    private int getPropertyIndex(String name, String[] properties) {
        for (var i = 0; i < properties.length; i++) {
            if (name.equals(properties[i])) {
                return i;
            }
        }

        //should never get here...
        throw new IllegalArgumentException("Property not found: " + name);
    }

    private boolean isFieldEncrypted(Field field) {
        return AnnotationUtils.findAnnotation(field, EncryptedString.class) != null;
    }
}
