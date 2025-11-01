package com.flexitimermvc.service;

import java.util.Timer;
import java.util.TimerTask;

public class TimerService {

    private int remainingSeconds;
    private Timer timer;

    /**
     * 타이머 서비스 생성
     * @param seconds 초기 시간(초 단위)
     * */
    public TimerService(int seconds) {
        this.remainingSeconds = seconds;
    }

    /**
     * 타이머 시작
     * 1초마다 remainingSeconds를 1씩 감소
     * */
    public void start() {
        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (remainingSeconds > 0) {
                    remainingSeconds--;
                }
            }
        }, 1000, 1000);
    }

    /**
     * 남은 시간(초) 반환
     * */
    public int getRemainingSeconds() {
        return remainingSeconds;
    }
}
