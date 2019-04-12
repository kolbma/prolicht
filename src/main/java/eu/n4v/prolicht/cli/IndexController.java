package eu.n4v.prolicht.cli;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class IndexController {

    @GetMapping({"/", "/index.html"})
    public String getIndex() {
        return "index";
    }
}
