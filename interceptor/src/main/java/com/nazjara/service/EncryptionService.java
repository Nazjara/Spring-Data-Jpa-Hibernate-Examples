package com.nazjara.service;

public interface EncryptionService {
    String encrypt(String content);
    String decrypt(String content);
}
