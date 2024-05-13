package basilisk.user.servlet.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/key-exchange")

public class KeyExchangeService {

    @RequestMapping(value = "/public-key", method = RequestMethod.POST)
    @ResponseBody
    public String publicKey() {
        return "public-key";
    }

}
