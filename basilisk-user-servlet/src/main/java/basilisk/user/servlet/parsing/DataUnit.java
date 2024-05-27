package basilisk.user.servlet.parsing;

import com.google.gson.Gson;
import lombok.Data;

import java.util.List;

@Data
public class DataUnit {
    private String id;
    private String data;
    private String name;
    private String dataCreator;
    private String timestamp= String.valueOf(System.currentTimeMillis());
    private List<String> tags;
    private List<String> roles;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
