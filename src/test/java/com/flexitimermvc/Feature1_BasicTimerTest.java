package com.flexitimermvc;

import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("타이머를 시작하면 작은 창이 표시되고 초기시간이 표시된다.")
    void whenTimerStartsThenCompactWindowAndInitialTimeDisplayed() {
        // given : 25분 작업 타이머 설정
        mainWindow.setWorkDuration(25);

        // when : 타이머 시작
        mainWindow.startTimer();

        // then : 작은 창이 표시됨
        assertTrue(mainWindow.isCompactTimerVisible(),
                "타이머 시작 시 작은 팡이 표시되어야 합니다.");

        // And : 25:00 표시
        assertEquals("25:00", mainWindow.getDisplayTime(),
                "초기 시간이 25:00으로 표시되어야 합니다.");
    }

    @Test
    @DisplayName("타이머가 시작되면 1초마다 카운트 된다.")
    void whenTimerStartsThenCountdownEverySecond() throws InterruptedException{
        // given: 25분 작업 타이머 설정 및 시작
        mainWindow.setWorkDuration(25);
        mainWindow.startTimer();

        // when: 1초 대기
        Thread.sleep(1100);

        // then: 24:59 표시
        assertEquals("24:59", mainWindow.getDisplayedTime(),
                "1초 후 24:59로 카운트다운되어야 합니다.");
    }

    @Test
    @DisplayName("다른 시간으로 타이머를 설정할 수 있다.")
    void canSetTimerWithDifferentDuration() {
        // given : 5분 작업 타이머 설정
        mainWindow.setWorkDuration(5);

        // when : 타이머 시작
        mainWindow.startTimer();

        // then : 05:00 표시
        assertEquals("05:00", mainWindow.getDisplayedTime(),
                "설정한 5분이 05:00으로 표시되어야 합니다.");
    }
}
