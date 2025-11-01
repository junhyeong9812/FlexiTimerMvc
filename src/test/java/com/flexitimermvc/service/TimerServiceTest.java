package com.flexitimermvc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DisplayName("TimerService 테스트")
class TimerServiceTest {

    private TimerService timerService;

    @BeforeEach
    void setup() {
        // 25분 = 1500초
        timerService = new TimerService(1500);
    }

    @Test
    @DisplayName("타이머 생성 시 초기 남은 시간이 설정된다.")
    void should_set_initial_remaining_time_when_created() {
        // given & when & then
        assertEquals(1500, timerService.getRemainingSeconds(),
                "초기 남은 시간이 설정한 값과 같아야 합니다.");
    }

    @Test
    @DisplayName("타이머를 시작하면 카운트다운이 시작된다.")
    void should_start_countdown_when_timer_starts() throws InterruptedException {
        // given
        assertEquals(1500, timerService.getRemainingSeconds());

        // when
        timerService.start();
        Thread.sleep(1100);

        // then
        assertTrue(timerService.getRemainingSeconds() < 1500,
                "타이머 시작 후 남은 시간이 감소해야 합니다.");
        assertTrue(timerService.getRemainingSeconds() >= 1498,
                "약 1초 후 1499초 또는 1498초여야 합니다.");
    }

    @Test
    @DisplayName("여러 번 남은 시간을 조회할 수 있다.")
    void should_be_able_to_query_remaining_time_multiple_times() {
        // given & when & then
        assertEquals(1500, timerService.getRemainingSeconds());
        assertEquals(1500, timerService.getRemainingSeconds());
        assertEquals(1500, timerService.getRemainingSeconds());
    }


}