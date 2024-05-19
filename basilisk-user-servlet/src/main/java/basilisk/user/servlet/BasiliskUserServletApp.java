package basilisk.user.servlet;

import basilisk.user.servlet.keyexchange.KeyExchangeClient;
import basilisk.user.servlet.parsing.JsonParseCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class BasiliskUserServletApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BasiliskUserServletApp.class, args);
    }

    @Autowired
    Environment environment;

    @Override
    public void run(String... args) throws Exception {
        KeyExchangeClient.exchangePublicKey();
        JsonParseCache.setPath(environment.getProperty("app.cache.path"));
        JsonParseCache.parseFiles();
    }
}
