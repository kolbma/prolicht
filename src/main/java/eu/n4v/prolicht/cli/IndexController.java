package eu.n4v.prolicht.cli;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import eu.n4v.prolicht.EventCategoryName;
import eu.n4v.prolicht.ResNotFoundException;
import eu.n4v.prolicht.model.Applicant;
import eu.n4v.prolicht.model.CategoryView;
import eu.n4v.prolicht.model.Event;
import eu.n4v.prolicht.model.EventCategoryView;
import eu.n4v.prolicht.model.Hobby;
import eu.n4v.prolicht.model.Knowledge;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@Slf4j
@ApiIgnore
class IndexController {

    private static String serviceURI;

    @GetMapping(value = {"/", "/index.html"}, produces = MediaType.TEXT_HTML_VALUE)
    public String getIndex(@RequestParam(value = "applicant", required = false) String applicantIdS,
            @ModelAttribute("model") ModelMap model, HttpServletRequest request) {

        // applicant data
        long id = 0;
        if (applicantIdS != null) {
            try {
                id = Long.parseLong(applicantIdS);
            } catch (NumberFormatException ex) {
                // we use 0 from above
                log.debug("specified request param applicant is ignored");
            }
        }
        final long applicantId = id;

        if (serviceURI == null) { // need to get the serviceURI only once
            final String reqUri = ServletUriComponentsBuilder.fromRequest(request)
                    .replaceQuery(null).toUriString();
            if (reqUri.endsWith("/index.html")) {
                serviceURI = reqUri.substring(0, reqUri.length() - 10);
            } else {
                serviceURI = reqUri;
            }
        }

        try {
            Traverson traverson = new Traverson(new URI(serviceURI), MediaTypes.HAL_JSON_UTF8);
            Applicant applicant = traverson.follow("applicant")
                    .withTemplateParameters(new HashMap<String, Object>() {
                        private static final long serialVersionUID = 1L;
                        {
                            put("id", applicantId);
                        }
                    }).toObject(Applicant.class);
            if (applicant != null) {
                model.put("applicant", applicant);
            }
        } catch (Exception ex) {
            log.error("failed to retrieve applicant", ex);
            throw new ResNotFoundException(applicantId);
        }
        // end applicant data

        // knowledges data
        try {
            Traverson traverson = new Traverson(new URI(serviceURI), MediaTypes.HAL_JSON_UTF8);
            Resources<Resource<CategoryView>> categories = traverson.follow("knowledgecategories")
                    .toObject(new ParameterizedTypeReference<Resources<Resource<CategoryView>>>() {
                    });

            LinkedHashMap<String, List<Knowledge>> map =
                    new LinkedHashMap<String, List<Knowledge>>();

            for (Resource<CategoryView> category : categories.getContent()) {
                final CategoryView view = category.getContent();
                final long categoryId = view.getCategoryId();
                Resources<Resource<Knowledge>> knowledges = traverson.follow("knowledgesByCategory")
                        .withTemplateParameters(new HashMap<String, Object>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put("categoryId", categoryId);
                            }
                        })
                        .toObject(new ParameterizedTypeReference<Resources<Resource<Knowledge>>>() {
                        });

                List<Knowledge> objList = new ArrayList<Knowledge>();
                for (Resource<Knowledge> knowledge : knowledges.getContent()) {
                    objList.add(knowledge.getContent());
                }

                map.put(category.getContent().getName(), objList);
            }
            if (map.size() > 0) {
                model.put("knowledges", map);
            }
        } catch (Exception ex) {
            log.error("failed to retrieve knowledges", ex);
        }
        // end knowledges data

        // job data
        try {
            Traverson traverson = new Traverson(new URI(serviceURI), MediaTypes.HAL_JSON_UTF8);
            Resources<Resource<EventCategoryView>> categories =
                    traverson.follow("eventcategories").toObject(
                            new ParameterizedTypeReference<Resources<Resource<EventCategoryView>>>() {
                            });

            // LinkedHashMap<String, List<Event>> map = new LinkedHashMap<String, List<Event>>();

            id = 0;
            for (Resource<EventCategoryView> category : categories.getContent()) {
                final EventCategoryView view = category.getContent();
                if (view.getName().equals(EventCategoryName.EMPLOYMENT)) {
                    id = view.getCategoryId();
                    break;
                }
            }

            final long categoryId = id;
            Resources<Resource<Event>> events = traverson.follow("eventsByCategory")
                    .withTemplateParameters(new HashMap<String, Object>() {
                        private static final long serialVersionUID = 1L;
                        {
                            put("categoryId", categoryId);
                        }
                    }).toObject(new ParameterizedTypeReference<Resources<Resource<Event>>>() {
                    });

            List<Event> objList = new ArrayList<Event>();
            for (Resource<Event> event : events.getContent()) {
                objList.add(event.getContent());
            }
            if (objList.size() > 0) {
                model.put("jobs", objList);
            }
        } catch (Exception ex) {
            log.error("failed to retrieve jobs", ex);
        }
        // end job data

        // education data
        try {
            Traverson traverson = new Traverson(new URI(serviceURI), MediaTypes.HAL_JSON_UTF8);
            Resources<Resource<EventCategoryView>> categories =
                    traverson.follow("eventcategories").toObject(
                            new ParameterizedTypeReference<Resources<Resource<EventCategoryView>>>() {
                            });

            // LinkedHashMap<String, List<Event>> map = new LinkedHashMap<String, List<Event>>();

            List<Event> objList = new ArrayList<Event>();
            id = 0;
            for (Resource<EventCategoryView> category : categories.getContent()) {
                final EventCategoryView view = category.getContent();
                if (view.getName().equals(EventCategoryName.EMPLOYMENT)) {
                    continue;
                }

                final long categoryId = view.getCategoryId();
                Resources<Resource<Event>> events = traverson.follow("eventsByCategory")
                        .withTemplateParameters(new HashMap<String, Object>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put("categoryId", categoryId);
                            }
                        }).toObject(new ParameterizedTypeReference<Resources<Resource<Event>>>() {
                        });

                for (Resource<Event> event : events.getContent()) {
                    objList.add(event.getContent());
                }
            }
            if (objList.size() > 0) {
                model.put("education", objList);
            }
        } catch (Exception ex) {
            log.error("failed to retrieve education", ex);
        }
        // end education data

        // hobbies data
        try {
            Traverson traverson = new Traverson(new URI(serviceURI), MediaTypes.HAL_JSON_UTF8);
            Resources<Resource<CategoryView>> categories = traverson.follow("hobbycategories")
                    .toObject(new ParameterizedTypeReference<Resources<Resource<CategoryView>>>() {
                    });

            LinkedHashMap<String, List<String>> map = new LinkedHashMap<String, List<String>>();

            for (Resource<CategoryView> category : categories.getContent()) {
                final CategoryView view = category.getContent();
                final long categoryId = view.getCategoryId();
                Resources<Resource<Hobby>> hobbies = traverson.follow("hobbiesByCategory")
                        .withTemplateParameters(new HashMap<String, Object>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put("categoryId", categoryId);
                            }
                        }).toObject(new ParameterizedTypeReference<Resources<Resource<Hobby>>>() {
                        });

                List<String> hobbyNames = new ArrayList<String>();
                for (Resource<Hobby> hobby : hobbies.getContent()) {
                    hobbyNames.add(hobby.getContent().getName());
                }

                map.put(category.getContent().getName(), hobbyNames);
            }
            if (map.size() > 0) {
                model.put("hobbies", map);
            }
        } catch (Exception ex) {
            log.error("failed to retrieve hobbies", ex);
        }
        // end hobbies data

        return "index";
    }
}
