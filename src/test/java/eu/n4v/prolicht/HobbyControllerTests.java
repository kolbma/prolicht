package eu.n4v.prolicht;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
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
public class HobbyControllerTests {

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
        mvc.perform(post("/hobbycategories").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"cat1\" }"));
    }

    @Test
    public void testAll() throws Exception {
        mvc.perform(get("/hobby")).andExpect(status().isOk());
    }

    @Test
    public void testAllByCategory() throws Exception {
        mvc.perform(get("/hobby/3")).andExpect(status().isOk());
    }

    @Test
    public void testNewHobby() throws Exception {
        mvc.perform(post("/hobby").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"categoryId\": 3, \"name\": \"hobby1\" }"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testNewHobbyAuth() throws Exception {
        mvc.perform(post("/hobby").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"categoryId\": 3, \"name\": \"hobby1\" }"))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testNewHobbyAuthNoCategory() throws Exception {
        try {
            mvc.perform(post("/hobby").contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"name\": \"hobby1\" }"))
                    .andExpect(status().isInternalServerError());
            fail("should throw an exception");
        } catch (Exception ex) {
            assertNotNull(ex);
        }
    }

    @Test
    public void testReplaceHobby() throws Exception {
        mvc.perform(put("/hobby/4").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"hobby1\" }")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testReplaceHobbyAuth() throws Exception {
        mvc.perform(put("/hobby/4").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"categoryId\": 3, \"name\": \"hobby1\" }"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testOneAuth() throws Exception {
        mvc.perform(get("/hobby/3/4")).andExpect(status().isNotFound());
        mvc.perform(post("/hobby").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"categoryId\": 3, \"name\": \"cat1\" }"))
                .andExpect(status().isCreated());
        mvc.perform(get("/hobby/3/4")).andExpect(status().isOk());
    }

    @Test
    public void testDeleteHobby() throws Exception {
        mvc.perform(delete("/hobby/4")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testDeleteHobbyAuth() throws Exception {
        testNewHobbyAuth();
        mvc.perform(delete("/hobby/4")).andExpect(status().isNoContent());
    }

}
