package eu.n4v.prolicht;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.io.File;
import java.nio.file.Files;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class PhotoControllerTests {

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
        mvc.perform(get("/photo/2")).andExpect(status().isOk());
    }

    @Test
    public void testNewPhoto() throws Exception {
        File file = ResourceUtils.getFile("classpath:testimage.jpg");
        MockMultipartFile multipartFile = new MockMultipartFile("data", "testimage.jpg",
                MediaType.IMAGE_JPEG_VALUE, Files.readAllBytes(file.toPath()));
        mvc.perform(multipart("/photo").file(multipartFile)).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testNewPhotoAuth() throws Exception {
        File file = ResourceUtils.getFile("classpath:testimage.jpg");
        MockMultipartFile multipartFile = new MockMultipartFile("data", "testimage.jpg",
                MediaType.IMAGE_JPEG_VALUE, Files.readAllBytes(file.toPath()));
        mvc.perform(multipart("/photo").file(multipartFile)).andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testDownload() throws Exception {
        mvc.perform(get("/photo/download/3")).andExpect(status().isNotFound());
        testNewPhotoAuth();
        mvc.perform(get("/photo/download/3")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG));
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testOne() throws Exception {
        mvc.perform(get("/photo/2/3")).andExpect(status().isNotFound());
        testNewPhotoAuth();
        mvc.perform(get("/photo/2/3")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8));
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testOneNoData() throws Exception {
        mvc.perform(get("/photo/2/3")).andExpect(status().isNotFound());
        testNewPhotoAuth();
        mvc.perform(get("/photo/2/3")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8))
                .andExpect(content().string(not(containsString("\"data\":"))));
    }

    @Test
    public void testDeletePhoto() throws Exception {
        mvc.perform(delete("/photo/3")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", password = "123456")
    @WithUserDetails("user")
    public void testDeletePhotoAuth() throws Exception {
        testNewPhotoAuth();
        mvc.perform(delete("/photo/3")).andExpect(status().isNoContent());
    }

}
