package basilisk.user.servlet.cfg;

import basilisk.user.servlet.keyexchange.KeyExchangeClient;
import basilisk.user.servlet.parsing.JsonParseCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

@Configuration
@Profile("deploy")
public class RunCfg {
    @Autowired
    Environment environment;

    @Bean
    public void run() {
        RestTemplate template = new RestTemplate();
        KeyExchangeClient.exchangePublicKey(template, environment.getProperty("server.keyexchange.url"));
        JsonParseCache.setPath(environment.getProperty("app.cache.path"));
        JsonParseCache.parseFiles();
    }
}
