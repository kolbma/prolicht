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
public class KnowledgeCategoryControllerTests {

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
        mvc.perform(get("/knowledgecategories")).andExpect(status().isOk());
    }

    @Test
    public void testNewKnowledgeCategory() throws Exception {
        mvc.perform(post("/knowledgecategories").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"cat1\" }")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testNewKnowledgeCategoryAuth() throws Exception {
        mvc.perform(post("/knowledgecategories").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"cat1\" }")).andExpect(status().isCreated());
    }

    @Test
    public void testReplaceKnowledgeCategory() throws Exception {
        mvc.perform(put("/knowledgecategories/3").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"cat1\" }")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testReplaceKnowledgeCategoryAuth() throws Exception {
        mvc.perform(put("/knowledgecategories/3").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"cat1\" }")).andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testOneAuth() throws Exception {
        mvc.perform(get("/knowledgecategories/3")).andExpect(status().isNotFound());
        mvc.perform(post("/knowledgecategories").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"cat1\" }")).andExpect(status().isCreated());
        mvc.perform(get("/knowledgecategories/3")).andExpect(status().isOk());
    }

    @Test
    public void testDeleteKnowledgeCategory() throws Exception {
        mvc.perform(delete("/knowledgecategories/3")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testDeleteKnowledgeCategoryAuth() throws Exception {
        testNewKnowledgeCategoryAuth();
        mvc.perform(delete("/knowledgecategories/3")).andExpect(status().isNoContent());
    }

}
