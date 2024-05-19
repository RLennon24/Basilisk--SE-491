package basilisk.user.servlet.parsing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class JsonParseCacheTest {

    static DataUnit testOneUnit = new DataUnit();
    static DataUnit testTwoUnit = new DataUnit();

    @BeforeAll
    public static void setUp() {
        JsonParseCache.setPath("C:\\\\Users\\\\marce\\\\Documents\\\\School\\\\SE491\\\\basilisk\\\\basilisk-user-servlet\\\\src\\\\test\\\\resources\\\\data\\\\");
        JsonParseCache.parseFiles();

        testOneUnit.setId("testOne");
        testOneUnit.setData("test data");
        testOneUnit.setRoles(Arrays.asList("basic", "government"));
        testOneUnit.setTags(Arrays.asList("personal", "common"));

        testTwoUnit.setId("testTwo");
        testTwoUnit.setData("other test data");
        testTwoUnit.setRoles(Arrays.asList("basic"));
        testTwoUnit.setTags(Arrays.asList("personal", "common"));
    }

    @Test
    public void testGetById() {
        DataUnit actualUnit = JsonParseCache.getById("testOne");
        Assertions.assertEquals(testOneUnit, actualUnit);
    }

    @Test
    public void testGetByTag() {
        List<DataUnit> actualUnits = JsonParseCache.getByTag("personal");
        Assertions.assertEquals(2, actualUnits.size());
        Assertions.assertEquals(testOneUnit, actualUnits.get(0));
        Assertions.assertEquals(testTwoUnit, actualUnits.get(1));
    }

    @Test
    public void testGetByRole() {
        List<DataUnit> actualUnits = JsonParseCache.getByRole("basic");
        Assertions.assertEquals(2, actualUnits.size());
        Assertions.assertEquals(testOneUnit, actualUnits.get(0));
        Assertions.assertEquals(testTwoUnit, actualUnits.get(1));

        actualUnits = JsonParseCache.getByRole("government");
        Assertions.assertEquals(1, actualUnits.size());
        Assertions.assertEquals(testOneUnit, actualUnits.get(0));
    }
}