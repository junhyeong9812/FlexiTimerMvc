package com.flexitimermvc;

import com.flexitimermvc.service.TimerService;
import com.flexitimermvc.util.TimeFormatter;

/**
 * Step 10: MainWindow 실제 구현
 *
 * 메인 윈도우 - 애플리케이션의 진입점이자 전체 UI를 관리
 *
 * 책임:
 * - 타이머 설정 관리
 * - UI 상태 관리 (작은 창 표시 여부 등)
 * - TimerService와 View 연결
 *
 * 이제 실제로 TimerService와 TimeFormatter를 사용하여 구현합니다.
 */
public class MainWindow {
    private TimerService timerService;
    private boolean compactTimerVisible;

    public MainWindow() {
        this.compactTimerVisible = false;
    }

    /**
     * 작업 시간 설정 (분 단위)
     * @param minutes 작업 시간 (분)
     * */
    public void setWorkDuration(int minutes) {
        // 분을 초로 변환하여 TimerService 생성
        this.timerService = new TimerService(minutes * 60);
    }

    /**
     * 타이머 시작
     * */
    public void startTimer() {
        if (timerService == null) {
            throw new IllegalStateException("타이머를 시작하기 전에 작업 시간을 설정해야 합니다");
        }

        timerService.start();
        compactTimerVisible = true;
    }

    /**
     * 작은 타이머 창이 표시되는지 확인
     * */
    public boolean isCompactTimerVisible() {
        return compactTimerVisible;
    }

    /**
     * 현재 표시되는 시간을 "MM:SS" 형식으로 반환
     * */
    public String getDisplayedTime() {
        if (timerService == null) {
            return "00 : 00";
        }

        int remainingSeconds = timerService.getRemainingSeconds();
        return TimeFormatter.format(remainingSeconds);
    }
}
