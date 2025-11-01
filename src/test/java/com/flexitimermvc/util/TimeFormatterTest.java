package com.flexitimermvc.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("TimeFormatter 테스트")
class TimeFormatterTest {

    @Test
    @DisplayName("초를 분:초 형식(mm:ss)으로 변환한다.")
    void should_format_seconds_to_minutes_and_seconds() {
        // given & when & then
        assertEquals("25:00", TimeFormatter.format(1500));
        assertEquals("24:59", TimeFormatter.format(1499));
        assertEquals("05:00", TimeFormatter.format(300));
        assertEquals("00:00", TimeFormatter.format(0));
    }

    @Test
    @DisplayName("1분 미만 시간도 올바르게 변환한다.")
    void should_format_time_less_then_one_minute() {
        // given & when & then
        assertEquals("00:30", TimeFormatter.format(30));
        assertEquals("00:05", TimeFormatter.format(5));
    }

}