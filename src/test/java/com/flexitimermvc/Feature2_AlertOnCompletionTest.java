package com.flexitimermvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * 타이머 2: 타이머 완료 시 전체화면 알림
 *
 * 사용자 스토리:
 * 타이머가 00: 00에 도달하면,
 * 전체 화면 알림이 나타나고 "작업 시간 완료!" 메시지와 "확인"버튼이 표시된다.
 * */
@DisplayName("Feature 2: 타이머 완료 시 전체화면 알림")
public class Feature2_AlertOnCompletionTest {

    private MainWindow mainWindow;

    @BeforeEach
    void setUp() {
        mainWindow = new MainWindow();
    }

    @Test
    @DisplayName("타이머가 완료되면 전체화면 알림이 표시된다.")
    void should_display_fullscreen_alert_when_timer_completes() throws InterruptedException {
        // given: 1초 타이머 설정 (빠른 테스트를 위해)
        mainWindow.setWorkDuration(0);
        mainWindow.startTimer();

        // when: 타이머가 완료될 때까지 대기
        Thread.sleep(100);

        // then: 전체화면 알림이 표시됨
        assertTrue(mainWindow.isFullScreenAlertVisible(),
                "타이머 완료 시 전체화면 알림이 표시되어야 합니다.");
    }

    @Test
    @DisplayName("알림에 완료 메시지가 표시된다.")
    void should_display_completion_message_in_alert() throws InterruptedException {
        // given: 1초 타이머 설정 및 시작
        mainWindow.setWorkDuration(0);
        mainWindow.startTimer();

        // when: 타이머 완료 대기
        Thread.sleep(100);

        // Then: "작업 시간 완료!" 메시지 표시
        assertEquals("작업 시간 완료!", mainWindow.getAlertMessage(),
                "완료 메시지가 표시되어야 합니다");
    }

    @Test
    @DisplayName("알림에 확인 버튼이 표시된다.")
    void should_display_confirm_button_in_alert() throws InterruptedException {
        // given: 타이머 설정 및 완료
        mainWindow.setWorkDuration(0);
        mainWindow.startTimer();
        Thread.sleep(100);

        // then: 확인 버튼이 존재함
        assertTrue(mainWindow.hasConfirmButton(),
                "알림에 확인 버튼이 있어야 합니다");
    }

    @Test
    @DisplayName("확인 버튼을 누르면 알림이 사라진다.")
    void should_hide_alert_when_confirm_button_is_clicked() throws InterruptedException {
        // given: 타이머 완료로 알림 표시됨
        mainWindow.setWorkDuration(0);
        mainWindow.startTimer();
        Thread.sleep(100);
        assertTrue(mainWindow.isFullScreenAlertVisible());

        // when: 확인 버튼 클릭
        mainWindow.clickConfirmButton();

        // then: 알림이 사라짐
        assertFalse(mainWindow.isFullScreenAlertVisible(),
                "확인 버튼 클릭 후 알림이 사라져야 합니다");
    }
}
