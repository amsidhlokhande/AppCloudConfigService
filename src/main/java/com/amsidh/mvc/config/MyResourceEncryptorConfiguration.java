package com.amsidh.mvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bootstrap.TextEncryptorConfigBootstrapper;
import org.springframework.cloud.config.server.encryption.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MyResourceEncryptorConfiguration {

    @Autowired
    private TextEncryptorLocator encryptor;

    @Bean
    Map<String, ResourceEncryptor> resourceEncryptors() {
        Map<String, ResourceEncryptor> resourceEncryptorMap = new HashMap<>();
        addSupportedExtensionsToMap(resourceEncryptorMap, new CipherResourceJsonEncryptor(encryptor));
        addSupportedExtensionsToMap(resourceEncryptorMap, new CipherResourcePropertiesEncryptor(encryptor));
        addSupportedExtensionsToMap(resourceEncryptorMap, new CipherResourceYamlEncryptor(encryptor));

        return resourceEncryptorMap;
    }

    private void addSupportedExtensionsToMap(
            Map<String, ResourceEncryptor> resourceEncryptorMap,
            ResourceEncryptor resourceEncryptor) {
        for (String ext : resourceEncryptor.getSupportedExtensions()) {
            resourceEncryptorMap.put(ext, resourceEncryptor);
        }
    }
}
