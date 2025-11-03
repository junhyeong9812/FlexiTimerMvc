package com.flexitimermvc;

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

    public MainWindow() {

    }

    /**
     * 작업 시간 설정 (분 단위)
     * @param minutes 작업 시간 (분)
     * */
    public void setWorkDuration(int minutes) {

    }

    /**
     * 타이머 시작
     * */
    public void startTimer() {

    }

    /**
     * 작은 타이머 창이 표시되는지 확인
     * */
    public boolean isCompactTimerVisible() {
        return false;
    }

    /**
     * 현재 표시되는 시간을 "MM:SS" 형식으로 반환
     * */
    public String getDisplayedTime() {
        return "25:00";
    }
}
