package basilisk.web.servlet.exception;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class EncryptionExceptionTest extends TestCase {

    @Test
    public void testEncryptionExceptionThrown() {
        EncryptionException ex = Assert.assertThrows(EncryptionException.class, () -> {
            throw new EncryptionException();
        });
    }

    @Test
    public void testEncryptionExceptionWithMessageThrown() {
        EncryptionException ex = Assert.assertThrows(EncryptionException.class, () -> {
            throw new EncryptionException("Testing");
        });
        assertEquals("Testing", ex.getMessage());
    }

    @Test
    public void testEncryptionExceptionWithCauseThrown() {
        EncryptionException ex = Assert.assertThrows(EncryptionException.class, () -> {
            throw new EncryptionException(new NullPointerException());
        });
        assertEquals(NullPointerException.class, ex.getCause().getClass());
    }

    @Test
    public void testEncryptionExceptionWithCauseAndMessageThrown() {
        EncryptionException ex = Assert.assertThrows(EncryptionException.class, () -> {
            throw new EncryptionException("Testing", new NullPointerException());
        });
        assertEquals("Testing", ex.getMessage());
    }

}