package dk.sjd.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    // Hej Silas derp

    @RequestMapping(value = {"","/","index"}, method = RequestMethod.GET)
    public String index() {
        return "index";
    }

}
