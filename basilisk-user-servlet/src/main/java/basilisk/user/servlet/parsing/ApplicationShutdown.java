package basilisk.user.servlet.parsing;

import basilisk.user.servlet.keygen.BasiliskUserKeyGen;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationShutdown implements ApplicationListener<ContextClosedEvent> {

     @Override
     public void onApplicationEvent(ContextClosedEvent event) {
         // TODO: send to server a de-listing notif
         BasiliskUserKeyGen.storeUserKeys();
         JsonParseCache.writeToFiles();
     }
}