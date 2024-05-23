package basilisk.user.servlet.parsing;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationShutdown implements ApplicationListener<ContextClosedEvent> {

     @Override
     public void onApplicationEvent(ContextClosedEvent event) {
         // TODO: send to server a de-listing notif

         JsonParseCache.writeToFiles();
     }
}