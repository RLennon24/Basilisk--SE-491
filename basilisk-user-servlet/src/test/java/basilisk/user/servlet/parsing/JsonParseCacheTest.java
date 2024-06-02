package basilisk.user.servlet.parsing;

import basilisk.user.servlet.keygen.BasiliskUserKeyGen;
import org.junit.jupiter.api.*;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class JsonParseCacheTest {

    static DataUnit testOneUnit = new DataUnit();
    static DataUnit testTwoUnit = new DataUnit();

    @BeforeAll
    public static void setUp() {
        BasiliskUserKeyGen.generateKeyPair();
        JsonParseCache.setPath("data" + File.separator + "basic");
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
        DataUnit actualTestOne = actualUnits.stream().filter(u -> u.getId().equals("testOne")).collect(Collectors.toList()).get(0);
        DataUnit actualTestTwo = actualUnits.stream().filter(u -> u.getId().equals("testTwo")).collect(Collectors.toList()).get(0);

        Assertions.assertEquals(testOneUnit, actualTestOne);
        Assertions.assertEquals(testTwoUnit, actualTestTwo);
    }

    @Test
    public void testGetByRole() {
        List<DataUnit> actualUnits = JsonParseCache.getByRole("basic");
        Assertions.assertEquals(2, actualUnits.size());
        DataUnit actualTestOne = actualUnits.stream().filter(u -> u.getId().equals("testOne")).collect(Collectors.toList()).get(0);
        DataUnit actualTestTwo = actualUnits.stream().filter(u -> u.getId().equals("testTwo")).collect(Collectors.toList()).get(0);
        Assertions.assertEquals(testOneUnit, actualTestOne);
        Assertions.assertEquals(testTwoUnit, actualTestTwo);

        actualUnits = JsonParseCache.getByRole("government");
        Assertions.assertEquals(1, actualUnits.size());
        Assertions.assertEquals(testOneUnit, actualUnits.get(0));
    }

    @Test
    public void testInsertData() {
        DataUnit originalUnit = new DataUnit();
        originalUnit.setId("id");
        originalUnit.setData("data");
        originalUnit.setTags(Collections.singletonList("tag"));
        originalUnit.setRoles(Arrays.asList("role1", "role2"));

        JsonParseCache.insertData(originalUnit);
        DataUnit actualUnit = JsonParseCache.getById("id");
        Assertions.assertEquals(originalUnit, actualUnit);
    }

    @Test
    public void testWriteToFiles() {
        JsonParseCache.writeToFiles();

        File folder = Paths.get(System.getProperty("user.home") + File.separator +
                "basilisk" + File.separator + "data" + File.separator + "basic").toFile();
        if (!folder.exists() || !folder.isDirectory()) {
            throw new RuntimeException("Storage path: " + folder.getPath() + " is not a folder/does not exist");
        }

        // delete existing data
        File[] currFiles = folder.listFiles();

        Assertions.assertNotNull(currFiles);
        Assertions.assertEquals(3, currFiles.length);
        Arrays.stream(currFiles).filter(f -> f.getName().equals("id")).forEach(File::delete);
    }
}