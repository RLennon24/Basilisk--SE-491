package basilisk.web.servlet.idgen;

import java.util.UUID;
import basilisk.user.servlet.parsing.DataUnit;

public class idgeneration {
   public static void idgen(DataUnit DataUnit) {
        String strKey = DataUnit.getName() + DataUnit.getDataCreator() + DataUnit.getTimestamp();
        UUID uuid = UUID.nameUUIDFromBytes(strKey.getBytes());
        String strUUID = uuid.toString();
    }
    
}
