package basilisk.user.servlet;

import basilisk.user.servlet.keyexchange.KeyExchangeClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BasiliskUserServletApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BasiliskUserServletApp.class, args);
    }

    @Override
    public void run(String...args) throws Exception {
        KeyExchangeClient.exchangePublicKey();
    }
}
