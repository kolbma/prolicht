/*
   Copyright 2019 Markus Kolb

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package eu.n4v.prolicht;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
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
public class ApplicantControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testAll() throws Exception {
        mvc.perform(get("/applicant")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testAllAuth() throws Exception {
        mvc.perform(get("/applicant")).andExpect(status().isOk());
    }

    @Test
    public void testNewApplicant() throws Exception {
        mvc.perform(post("/applicant").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"street\": \"street\" }")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testNewApplicantAuth() throws Exception {
        createAccount();
        mvc.perform(post("/applicant").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"street\": \"street\" }")).andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testNewApplicantAuthWithoutAccount() throws Exception {
        try {
            mvc.perform(post("/applicant").contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"street\": \"street\" }"))
                    .andExpect(status().isInternalServerError());
            fail("should throw an exception");
        } catch (Exception ex) {
            assertNotNull(ex);
        }
    }

    @Test
    public void testReplaceApplicant() throws Exception {
        mvc.perform(put("/applicant/2").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testReplaceApplicantAuth() throws Exception {
        createAccount();
        mvc.perform(put("/applicant/2").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"street\": \"street\" }")).andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testReplaceApplicantAuth2() throws Exception {
        testNewApplicantAuth();
        mvc.perform(put("/applicant/2").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"street\": \"newstreet\" }")).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testOne() throws Exception {
        // TODO: needs to add Account with user and applicant check without user
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testOneAuth() throws Exception {
        mvc.perform(get("/applicant/2")).andExpect(status().isNotFound());
        testNewApplicantAuth();
        mvc.perform(get("/applicant/2")).andExpect(status().isOk());
    }

    @Test
    public void testDeleteApplicant() throws Exception {
        mvc.perform(delete("/applicant/2")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testDeleteApplicantAuth() throws Exception {
        testNewApplicantAuth();
        mvc.perform(delete("/applicant/2")).andExpect(status().isNoContent());
    }

    private void createAccount() throws Exception {
        mvc.perform(post("/account").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"user\", \"password\": \"123456\" }"))
                .andExpect(status().isCreated());
    }
}
