package com.team5.earchive.exception;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new TestController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void blankTitleReturnsValidationError() throws Exception {
        mockMvc.perform(post("/test/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title": ""}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(
                        MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.path").value("/test/documents"))
                .andExpect(jsonPath("$.fieldErrors[0].field").value("title"))
                .andExpect(jsonPath("$.fieldErrors[0].message")
                        .value("Tiêu đề không được để trống."));
    }

    @Test
    void malformedJsonReturnsInvalidRequest() throws Exception {
        mockMvc.perform(post("/test/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.code").value("INVALID_REQUEST"))
                .andExpect(jsonPath("$.fieldErrors").isEmpty());
    }

    @Test
    void wrongMethodReturns405WithAllowHeader() throws Exception {
        mockMvc.perform(get("/test/documents"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(header().string("Allow", containsString("POST")))
                .andExpect(jsonPath("$.status").value(405))
                .andExpect(jsonPath("$.code").value("METHOD_NOT_ALLOWED"));
    }

    @Test
    void unexpectedErrorDoesNotExposeInternalDetails() throws Exception {
        mockMvc.perform(get("/test/failure")
                        .queryParam("debug", "private-value"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.code").value("INTERNAL_ERROR"))
                .andExpect(jsonPath("$.message")
                        .value("Hệ thống gặp lỗi. Vui lòng thử lại sau."))
                .andExpect(jsonPath("$.path").value("/test/failure"))
                .andExpect(jsonPath("$.fieldErrors").isEmpty())
                .andExpect(jsonPath("$.trace").doesNotExist())
                .andExpect(content().string(
                        not(containsString("internal-test-detail"))))
                .andExpect(content().string(
                        not(containsString("private-value"))));
    }

    public record TestRequest(
            @NotBlank(message = "Tiêu đề không được để trống.")
            String title
    ) {
    }

    @RestController
    public static class TestController {

        @PostMapping("/test/documents")
        public Map<String, String> create(
                @Valid @RequestBody TestRequest request
        ) {
            return Map.of("title", request.title());
        }

        @GetMapping("/test/failure")
        public void fail() {
            throw new IllegalStateException("internal-test-detail");
        }
    }
}