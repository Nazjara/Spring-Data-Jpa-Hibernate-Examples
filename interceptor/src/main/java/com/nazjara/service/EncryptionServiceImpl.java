package com.nazjara.service;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class EncryptionServiceImpl implements EncryptionService {

    @Override
    public String encrypt(String content) {
        return Base64.getEncoder().encodeToString(content.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String decrypt(String content) {
        return new String(Base64.getDecoder().decode(content));
    }
}
