package basilisk.user.servlet.MapRolestoUser;

import basilisk.user.servlet.parsing.DataUnit;
import basilisk.user.servlet.parsing.JsonParseCache;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;


class RolestoUserTest {

    private final RolestoUser rolestouser = new RolestoUser();

    static DataUnit testOneUnit = new DataUnit();
    static DataUnit testTwoUnit = new DataUnit();

    public static void setUp() {
        JsonParseCache.setPath("data");
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
        void doesUserHaveRole() throws IOException {
            assertFalse(rolestouser.doesUserHaveRole("Maheen","basic"));
            assertFalse(rolestouser.doesUserHaveRole("Maheen","common"));
            assertFalse(rolestouser.doesUserHaveRole("Maheen","personal"));
            assertFalse(rolestouser.doesUserHaveRole("Maheen","government"));
        }

    }
