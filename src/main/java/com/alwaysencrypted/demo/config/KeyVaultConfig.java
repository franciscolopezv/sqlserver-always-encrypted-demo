package com.alwaysencrypted.demo.config;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.sqlserver.jdbc.SQLServerColumnEncryptionAzureKeyVaultProvider;
import com.microsoft.sqlserver.jdbc.SQLServerConnection;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Profile("!test")
public class KeyVaultConfig {

    @PostConstruct
    public void registerAKVProvider() {
        String clientId = System.getenv("AZURE_CLIENT_ID");
        String clientSecret = System.getenv("AZURE_CLIENT_SECRET");
        String tenantId = System.getenv("AZURE_TENANT_ID");

        if (clientId == null || clientSecret == null || tenantId == null) {
            throw new IllegalStateException("Azure credentials must be set in environment variables.");
        }

        try {
            ClientSecretCredential credential = new ClientSecretCredentialBuilder()
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .tenantId(tenantId)
                    .build();

            SQLServerColumnEncryptionAzureKeyVaultProvider akvProvider =
                    new SQLServerColumnEncryptionAzureKeyVaultProvider(credential);

            Map<String, com.microsoft.sqlserver.jdbc.SQLServerColumnEncryptionKeyStoreProvider> providerMap = new HashMap<>();
            providerMap.put("AZURE_KEY_VAULT", akvProvider);

            SQLServerConnection.registerColumnEncryptionKeyStoreProviders(providerMap);
            System.out.println("✅ Azure Key Vault provider registered for Always Encrypted.");
        } catch (SQLServerException e) {
            throw new RuntimeException("❌ Failed to register AKV provider: " + e.getMessage(), e);
        }
    }
}
