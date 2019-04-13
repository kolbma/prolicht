package eu.n4v.prolicht.cli;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import eu.n4v.prolicht.model.Applicant;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@Slf4j
@ApiIgnore
class IndexController {

    @GetMapping(value = {"/", "/index.html"}, produces = MediaType.TEXT_HTML_VALUE)
    public String getIndex(@ModelAttribute("model") ModelMap model) {
        try {
            Traverson traverson =
                    new Traverson(new URI("http://localhost:8080/"), MediaTypes.HAL_JSON_UTF8);
            Applicant applicant = traverson.follow("applicant")
                    .withTemplateParameters(new HashMap<String, Object>() {
                        private static final long serialVersionUID = 1L;
                        {
                            put("id", 2);
                        }
                    }).toEntity(Applicant.class).getBody();
            model.put("applicant", applicant);
        } catch (URISyntaxException ex) {
            log.error("failed to retrieve applicant", ex);
        }
        return "index";
    }
}
