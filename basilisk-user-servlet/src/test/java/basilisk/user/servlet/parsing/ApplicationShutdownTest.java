package basilisk.user.servlet.parsing;

import basilisk.user.servlet.keygen.BasiliskUserKeyGen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.event.ContextClosedEvent;

import java.io.File;

public class ApplicationShutdownTest {

    @Test
    public void testOnApplicationEvent() {
        BasiliskUserKeyGen.generateKeyPair();
        JsonParseCache.setPath("data" + File.separator + "basic");
        JsonParseCache.parseFiles();

        ApplicationShutdown s = new ApplicationShutdown();
        s.onApplicationEvent(Mockito.mock(ContextClosedEvent.class));
        Assertions.assertTrue(true);
    }
}