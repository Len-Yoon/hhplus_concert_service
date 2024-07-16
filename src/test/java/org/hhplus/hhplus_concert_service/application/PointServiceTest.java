package org.hhplus.hhplus_concert_service.application;

import org.hhplus.hhplus_concert_service.business.PointServiceImpl;
import org.hhplus.hhplus_concert_service.domain.Point;
import org.hhplus.hhplus_concert_service.domain.TokenQueue;
import org.hhplus.hhplus_concert_service.persistence.Point_repository;
import org.hhplus.hhplus_concert_service.persistence.TokenQueue_repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PointServiceTest {

    @Mock
    private Point_repository pointRepository;

    @Mock
    private TokenQueue_repository tokenQueueRepository;

    @InjectMocks
    private PointServiceImpl pointService;

    private Point point;
    private TokenQueue tokenQueue;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        point = new Point();
        point.setUserId("testUser");
        point.setPoint(100);
        tokenQueue = new TokenQueue();
        tokenQueue.setUserId("testUser");
        tokenQueue.setStatus("진행");
    }

    @Test
    @DisplayName("유저 포인트 조회")
    void checkPoint() {
        // Given
        when(pointRepository.findFirstByUserIdOrderByPointIdDesc("testUser")).thenReturn(point);

        // When
        Point result = pointService.checkPoint("testUser");

        // Then
        assertEquals(point, result);
        verify(pointRepository).findFirstByUserIdOrderByPointIdDesc("testUser");

    }

    @Test
    void plusPoint() {
        // Given
        when(pointRepository.findFirstByUserIdOrderByPointIdDesc("testUser")).thenReturn(point);

        // When
        pointService.plusPoint("testUser", 50);

        // Then
        assertEquals(150, point.getPoint());
        verify(pointRepository).save(any(Point.class));
    }

    @Test
    public void testPlusPoint_NegativeChargePoint() {
        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                pointService.plusPoint("testUser", -10));
        assertEquals("0이하의 포인트는 충전이 불가합니다.", exception.getMessage());
    }

    @Test
    public void testMinusPoint_Success() {
        // Given
        when(pointRepository.findFirstByUserIdOrderByPointIdDesc("testUser")).thenReturn(point);
        when(tokenQueueRepository.findByUserId("testUser")).thenReturn(tokenQueue);

        // When
        pointService.minusPoint("testUser", 50);

        // Then
        assertEquals(50, point.getPoint());
        verify(pointRepository).save(any(Point.class));
    }

    @Test
    public void testMinusPoint_InsufficientPoints() {
        // Given
        when(pointRepository.findFirstByUserIdOrderByPointIdDesc("testUser")).thenReturn(point);
        when(tokenQueueRepository.findByUserId("testUser")).thenReturn(tokenQueue);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                pointService.minusPoint("testUser", 150));
        assertEquals("포인트가 부족합니다.", exception.getMessage());
    }

    @Test
    public void testMinusPoint_InvalidTokenQueueStatus() {
        // Given
        when(pointRepository.findFirstByUserIdOrderByPointIdDesc("testUser")).thenReturn(point);
        tokenQueue.setStatus("완료");
        when(tokenQueueRepository.findByUserId("testUser")).thenReturn(tokenQueue);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                pointService.minusPoint("testUser", 50));
        assertEquals("오류가 발생했습니다.", exception.getMessage());
    }
}