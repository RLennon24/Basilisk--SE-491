package basilisk.user.servlet.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/key-exchange")
public class  KeyExchangeService {

    @PostMapping(value = "/public-key")
    @ResponseBody
    public String publicKey() {
        return "public-key";
    }

}
