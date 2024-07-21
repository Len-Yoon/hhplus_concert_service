package org.hhplus.hhplus_concert_service.integrationTest;

import org.hhplus.hhplus_concert_service.business.service.TokenQueueService;
import org.hhplus.hhplus_concert_service.domain.TokenQueue;
import org.hhplus.hhplus_concert_service.interfaces.controller.TokenController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TokenController.class)
class TokenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TokenQueueService tokenQueueService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateTokenForUser() throws Exception{
        doNothing().when(tokenQueueService).generateTokenForUser(any(String.class));

        mockMvc.perform(post("/token/generate").param("userId", "testUser"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllTokens() throws Exception {
        List<TokenQueue> tokenQueues = Collections.singletonList(new TokenQueue());
        when(tokenQueueService.getAllTokens()).thenReturn(tokenQueues);

        mockMvc.perform(get("/token/all"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}]"));
    }
}