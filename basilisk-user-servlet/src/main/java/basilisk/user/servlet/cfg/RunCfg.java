package basilisk.user.servlet.cfg;

import basilisk.user.servlet.keyexchange.KeyExchangeClient;
import basilisk.user.servlet.keygen.BasiliskUserKeyGen;
import basilisk.user.servlet.parsing.JsonParseCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@Configuration
@Profile("deploy")
public class RunCfg {
    private static final String ENCRYPTED_DATA_FOLDER = "encrypted";
    private static final String BASIC_DATA_FOLDER = "basic";

    @Autowired
    Environment environment;

    @Bean
    public void run() {
        BasiliskUserKeyGen.generateKeyPair();
        RestTemplate template = new RestTemplate();
        KeyExchangeClient.exchangePublicKey(template, environment.getProperty("account.owner"), environment.getProperty("server.keyexchange.url"));

        boolean isEncryptionModeEnabled = environment.getProperty("app.encryption.mode", boolean.class);
        String fullFolderPath = environment.getProperty("app.cache.path") + File.separator + (isEncryptionModeEnabled ? ENCRYPTED_DATA_FOLDER : BASIC_DATA_FOLDER);

        JsonParseCache.setPath(fullFolderPath);
        JsonParseCache.setEncryptionModeEnabled(isEncryptionModeEnabled);
        JsonParseCache.parseFiles();
    }
}
