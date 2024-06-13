package basilisk.user.servlet.cfg;

import basilisk.user.servlet.MapRolestoUser.RolestoUser;
import basilisk.user.servlet.client.WebServletClient;
import basilisk.user.servlet.keyexchange.packaging.KeyPackager;
import basilisk.user.servlet.keygen.BasiliskUserKeyGen;
import basilisk.user.servlet.message.BaseMessageBuilder;
import basilisk.user.servlet.parsing.JsonParseCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ApplicationShutdown implements ApplicationListener<ContextClosedEvent> {

    @Autowired
    Environment environment;

     @Override
     public void onApplicationEvent(ContextClosedEvent event) {
         // TODO: send to server a de-listing notif
         RestTemplate template = new RestTemplate();
         WebServletClient.sendRequest(template, environment.getProperty("account.owner"), environment.getProperty("server.delist.url"), BaseMessageBuilder.encodeMessage("DELIST"));
         BasiliskUserKeyGen.storeUserKeys();
         JsonParseCache.writeToFiles();
         RolestoUser.writeFiles();
     }
}