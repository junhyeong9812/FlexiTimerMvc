package com.flexitimermvc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Feature 1: 기본 타이머 시작 및 표시
 *
 * 사용자 스토리:
 * 사용자가 "25분 작업" 타이머를 설정하고 시작 버튼을 누르면.
 * 화면 상단 중앙에 작은 창이 나타나고 "25:00"부터 카운트다운이 시작된다.
 * */
public class Feature1_BasicTimerTest {

    private MainWindow mainWindow;

    @BeforeEach
    void setUp() {
        mainWindow = new MainWindow();
    }

    @Test
    void whenTimerStartsThenCompactWindowAndInitialTimeDisplayed() {

    }

    @Test
    void whenTimerStartsThenCountdownEverySecond() {

    }

    @Test
    void canSetTimerWithDifferentDuration() {

    }
}
