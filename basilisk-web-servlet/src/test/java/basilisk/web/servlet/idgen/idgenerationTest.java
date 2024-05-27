package basilisk.web.servlet.idgen;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class idgenerationTest {

    private static idgeneration idgen;
    private static idgeneration idgen1;
    private static idgeneration idgen2;
    @BeforeAll
    static void setup(){
        System.out.println("Building new id generator");
        idgen= new idgeneration();
        idgen1= new idgeneration();
        idgen2= new idgeneration();
    }

    @Test
    void idgen() {
        assertNotNull(idgen);
        assertEquals(idgen,idgen1);
        assertEquals(idgen,idgen2);

    }
}
