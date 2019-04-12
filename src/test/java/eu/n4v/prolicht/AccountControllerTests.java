package eu.n4v.prolicht;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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
public class AccountControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testAll() throws Exception {
        mvc.perform(get("/account")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testAllAuth() throws Exception {
        mvc.perform(get("/account")).andExpect(status().isOk());
    }

    @Test
    public void testNewAccount() throws Exception {
        mvc.perform(post("/account").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"user\", \"password\": \"123456\" }"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testNewAccountAuth() throws Exception {
        mvc.perform(post("/account").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"user\", \"password\": \"123456\" }"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testReplaceAccount() throws Exception {
        mvc.perform(put("/account/1").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"user\", \"password\": \"123456\" }"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testReplaceAccountAuth() throws Exception {
        mvc.perform(put("/account/1").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"user\", \"password\": \"123456\" }"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testOne() throws Exception {
        mvc.perform(get("/account/1")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testOneAuth() throws Exception {
        mvc.perform(get("/account/1")).andExpect(status().isNotFound());
        testNewAccountAuth();
        mvc.perform(get("/account/1")).andExpect(status().isOk());
    }

    @Test
    public void testDeleteAccount() throws Exception {
        mvc.perform(delete("/account/1")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testDeleteAccountAuth() throws Exception {
        testNewAccountAuth();
        mvc.perform(delete("/account/1")).andExpect(status().isNoContent());
    }

}
