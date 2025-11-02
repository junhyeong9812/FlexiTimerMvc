package com.flexitimermvc.util;

public class TimeFormatter {

    /**
     * 초를 "mm:ss" 형식의 문자열로 변환
     *
     * @param seconds 초 단위 시간
     * @return "mm:ss" 형식 문자열
     *
     * 예시:
     * - 1500초 → "25:00"
     * - 90초 → "01:30"
     * - 5초 → "00:05"
     */
    public static String format(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;

        // %02d: 2자리 숫자로 표시, 부족하면 0으로 채움
        return String.format("%02d:%02d", minutes, secs);
    }
}
