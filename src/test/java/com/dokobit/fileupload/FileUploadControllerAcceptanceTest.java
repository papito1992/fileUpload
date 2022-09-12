package com.dokobit.fileupload;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class FileUploadControllerAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void handleFileUpload() throws Exception {
        final MvcResult result = this.mockMvc.perform(multipart("/upload"))
                .andExpect(status().isBadRequest())
                .andReturn();
        Assertions.assertEquals("Required request part 'files' is not present", result.getResponse().getErrorMessage());
    }

    @Test
    public void shouldUploadFileAndGetSuccessfulResponseAndArchivedFile() throws Exception {
        MockMultipartFile file1 = new MockMultipartFile("files", "test.txt",
                "text/plain", "Spring Framework test".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("files", "test1.txt",
                "text/plain", "Spring Framework test".getBytes());
        final MvcResult result = this.mockMvc.perform(multipart("/upload")
                        .file(file1)
                        .file(file2)
                )
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("application/zip", result.getResponse().getContentType());
        assertTrue(Objects.requireNonNull(
                result.getResponse().getHeader("Content-Disposition")).contains("filename=testtest1.zip"));
    }
}