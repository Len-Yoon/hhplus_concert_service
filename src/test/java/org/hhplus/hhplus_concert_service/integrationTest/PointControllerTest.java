package org.hhplus.hhplus_concert_service.integrationTest;

import org.hhplus.hhplus_concert_service.business.PointService;
import org.hhplus.hhplus_concert_service.domain.Point;
import org.hhplus.hhplus_concert_service.interfaces.controller.PointController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PointController.class)
class PointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PointService pointService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void checkPoint() throws Exception {
        Point point = new Point();
        when(pointService.checkPoint(any(String.class))).thenReturn(point);

        mockMvc.perform(get("/point").param("userId", "유저1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    void plusPoint() throws Exception {
        doNothing().when(pointService).plusPoint(any(String.class), any(Integer.class));

        mockMvc.perform(post("/point/plusPoint").param("userId", "유저1").param("chargePoint", "100"))
                .andExpect(status().isOk());
    }

    @Test
    void minusPoint() throws Exception {
        doNothing().when(pointService).plusPoint(any(String.class), any(Integer.class));

        mockMvc.perform(post("/point/plusPoint").param("userId", "유저1").param("totalPoint", "100"))
                .andExpect(status().isOk());
    }
}