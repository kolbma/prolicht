package eu.n4v.prolicht;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class EventCategoryControllerTests {

    @Autowired
    private MockMvc mvc;

    @Before
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void before() throws Exception {
        mvc.perform(post("/account").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"user\", \"password\": \"123456\" }"));
        mvc.perform(post("/applicant").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"street\": \"street\" }"));
    }

    @Test
    public void testAll() throws Exception {
        mvc.perform(get("/eventcategories")).andExpect(status().isOk());
    }

    @Test
    public void testNewEventCategory() throws Exception {
        mvc.perform(post("/eventcategories").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"EDUCATION\" }")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testNewEventCategoryAuth() throws Exception {
        mvc.perform(post("/eventcategories").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"EDUCATION\" }")).andExpect(status().isCreated());
    }

    @Test
    public void testReplaceEventCategory() throws Exception {
        mvc.perform(put("/eventcategories/3").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"EDUCATION\" }")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testReplaceEventCategoryAuth() throws Exception {
        mvc.perform(put("/eventcategories/3").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"EDUCATION\" }")).andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testOneAuth() throws Exception {
        mvc.perform(get("/eventcategories/3")).andExpect(status().isNotFound());
        mvc.perform(post("/eventcategories").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"EDUCATION\" }")).andExpect(status().isCreated());
        mvc.perform(get("/eventcategories/3")).andExpect(status().isOk());
    }

    @Test
    public void testDeleteEventCategory() throws Exception {
        mvc.perform(delete("/eventcategories/3")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testDeleteEventCategoryAuth() throws Exception {
        testNewEventCategoryAuth();
        mvc.perform(delete("/eventcategories/3")).andExpect(status().isNoContent());
    }

}
