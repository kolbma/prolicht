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
public class EventControllerTests {

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
        mvc.perform(post("/eventcategories").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"EDUCATION\" }"));
    }

    @Test
    public void testAll() throws Exception {
        mvc.perform(get("/event")).andExpect(status().isOk());
    }

    @Test
    public void testAllByCategory() throws Exception {
        mvc.perform(get("/event/3")).andExpect(status().isOk());
    }

    @Test
    public void testNewEvent() throws Exception {
        mvc.perform(post("/event").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"categoryId\": 3, \"name\": \"event1\" }"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testNewEventAuth() throws Exception {
        mvc.perform(post("/event").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"categoryId\": 3, \"name\": \"event1\" }"))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testNewEventAuthNoCategory() throws Exception {
        try {
            mvc.perform(post("/event").contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"name\": \"event1\" }"))
                    .andExpect(status().isInternalServerError());
            fail("should throw an exception");
        } catch (Exception ex) {
            assertNotNull(ex);
        }
    }

    @Test
    public void testReplaceEvent() throws Exception {
        mvc.perform(put("/event/4").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"event1\" }")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testReplaceEventAuth() throws Exception {
        mvc.perform(put("/event/4").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"categoryId\": 3, \"name\": \"event1\" }"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testOneAuth() throws Exception {
        mvc.perform(get("/event/3/4")).andExpect(status().isNotFound());
        mvc.perform(post("/event").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"categoryId\": 3, \"name\": \"cat1\" }"))
                .andExpect(status().isCreated());
        mvc.perform(get("/event/3/4")).andExpect(status().isOk());
    }

    @Test
    public void testDeleteEvent() throws Exception {
        mvc.perform(delete("/event/4")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testDeleteEventAuth() throws Exception {
        testNewEventAuth();
        mvc.perform(delete("/event/4")).andExpect(status().isNoContent());
    }

}
