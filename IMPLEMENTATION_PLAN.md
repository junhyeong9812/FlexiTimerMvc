# FlexiTimerMvc - Outside-In TDD 구현 계획

## Outside-In TDD란?

사용자가 경험하는 기능부터 시작해서, 필요한 만큼만 하위 레이어로 내려가며 구현하는 방식입니다.

### 핵심 원칙
1. **사용자 스토리로 시작** - 실제로 필요한 기능부터
2. **이중 TDD 루프** - Feature 테스트 → Unit 테스트
3. **필요에 의한 발견** - 구현 중 필요한 것을 발견하며 만듦
4. **불필요한 코드 방지** - 실제로 사용되는 것만 작성

### TDD 사이클
```
Feature Test (Red) 
    ↓
Unit Test (Red) → Unit Code (Green) → Refactor
    ↓
Feature Test 재실행
    ↓
Pass? → 다음 Feature
Fail? → 다른 Unit 필요
```

---

## 개발 순서: 사용자 스토리 기반

### Feature 1: 기본 타이머 시작 및 표시

**사용자 스토리:**
```
사용자가 "25분 작업" 타이머를 설정하고 시작 버튼을 누르면,
화면 상단 중앙에 작은 창이 나타나고 "25:00"부터 카운트다운이 시작된다.
```

#### Step 1: Feature 테스트 작성 (Red)

**파일:** `src/test/java/com/flexitimermvc/FeatureTest.java`

```java
@Test
void 타이머를_시작하면_작은창이_표시되고_카운트다운이_시작된다() {
    // Given: 메인 윈도우 실행
    MainWindow mainWindow = new MainWindow();
    
    // When: 25분 작업 타이머 설정 및 시작
    mainWindow.setWorkDuration(25);
    mainWindow.startTimer();
    
    // Then: 작은 창이 표시됨
    assertTrue(mainWindow.isCompactTimerVisible());
    
    // And: 25:00 표시
    assertEquals("25:00", mainWindow.getDisplayedTime());
    
    // And: 1초 후 24:59 표시
    Thread.sleep(1000);
    assertEquals("24:59", mainWindow.getDisplayedTime());
}
```

**이 테스트는 실패합니다!** → MainWindow 클래스가 없음

#### Step 2: 필요한 것 발견 → MainWindow 구조 필요

테스트를 통과시키려면:
- `MainWindow` 클래스
- `setWorkDuration()` 메서드
- `startTimer()` 메서드
- `getDisplayedTime()` 메서드

하지만 이것들을 구현하려면... **타이머 로직**이 필요함을 발견!

#### Step 3: Unit 테스트 - TimerService 필요성 발견

**파일:** `src/test/java/com/flexitimermvc/service/TimerServiceTest.java`

```java
@Test
void 타이머를_시작하면_카운트다운이_시작된다() {
    // Given: 25분 타이머
    TimerService timer = new TimerService(25 * 60); // 25분 = 1500초
    
    // When: 시작
    timer.start();
    
    // Then: 남은 시간이 1500초
    assertEquals(1500, timer.getRemainingSeconds());
    
    // And: 1초 후 1499초
    Thread.sleep(1000);
    assertEquals(1499, timer.getRemainingSeconds());
}
```

**이 테스트도 실패!** → TimerService 구현 필요

#### Step 4: TimerService 구현 (Green)

**파일:** `src/main/java/com/flexitimermvc/service/TimerService.java`

```java
public class TimerService {
    private int remainingSeconds;
    private Timer timer;
    
    public TimerService(int seconds) {
        this.remainingSeconds = seconds;
    }
    
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
    
    public int getRemainingSeconds() {
        return remainingSeconds;
    }
}
```

**Unit 테스트 통과! (Green)**

#### Step 5: TimeFormatter 필요성 발견

이제 `MainWindow`에서 초를 "mm:ss" 형식으로 표시해야 함을 발견!

**파일:** `src/test/java/com/flexitimermvc/util/TimeFormatterTest.java`

```java
@Test
void 초를_분초_형식으로_변환한다() {
    assertEquals("25:00", TimeFormatter.format(1500));
    assertEquals("24:59", TimeFormatter.format(1499));
    assertEquals("00:00", TimeFormatter.format(0));
    assertEquals("05:30", TimeFormatter.format(330));
}
```

#### Step 6: TimeFormatter 구현 (Green)

**파일:** `src/main/java/com/flexitimermvc/util/TimeFormatter.java`

```java
public class TimeFormatter {
    public static String format(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }
}
```

#### Step 7: MainWindow 구현

이제 모든 재료가 준비됨! MainWindow 구현

**파일:** `src/main/java/com/flexitimermvc/view/MainWindow.java`

```java
public class MainWindow {
    private TimerService timerService;
    private Label timeLabel;
    
    public void setWorkDuration(int minutes) {
        timerService = new TimerService(minutes * 60);
    }
    
    public void startTimer() {
        timerService.start();
        // UI 업데이트 로직
    }
    
    public String getDisplayedTime() {
        return TimeFormatter.format(timerService.getRemainingSeconds());
    }
}
```

#### Step 8: Feature 테스트 재실행

**Feature 테스트 통과! (Green)** ✅

---

### Feature 2: 작업 시간 완료 시 전체화면 알림

**사용자 스토리:**
```
타이머가 00:00에 도달하면,
전체 화면 알림이 나타나고 "작업 시간 완료!" 메시지와 
"확인" 버튼이 표시된다.
```

#### Step 1: Feature 테스트 (Red)

```java
@Test
void 타이머가_완료되면_전체화면_알림이_표시된다() {
    // Given: 1초 타이머 설정 및 시작
    mainWindow.setWorkDuration(0); // 0분 = 즉시 완료 테스트용
    mainWindow.startTimer();
    
    // When: 타이머 완료
    Thread.sleep(100);
    
    // Then: 전체화면 알림 표시
    assertTrue(mainWindow.isFullScreenAlertVisible());
    assertEquals("작업 시간 완료!", mainWindow.getAlertMessage());
    
    // And: 확인 버튼 존재
    assertTrue(mainWindow.hasConfirmButton());
}
```

**실패!** → 알림 시스템 필요성 발견

#### Step 2: NotificationService 테스트 (Unit)

```java
@Test
void 타이머_완료시_알림을_트리거한다() {
    // Given: NotificationService
    NotificationService notification = new NotificationService();
    List<String> triggeredAlerts = new ArrayList<>();
    notification.setListener(msg -> triggeredAlerts.add(msg));
    
    // When: 알림 트리거
    notification.trigger("작업 시간 완료!");
    
    // Then: 리스너에 메시지 전달
    assertEquals(1, triggeredAlerts.size());
    assertEquals("작업 시간 완료!", triggeredAlerts.get(0));
}
```

#### Step 3: NotificationService 구현

#### Step 4: TimerService에 완료 콜백 추가 필요성 발견

```java
@Test
void 타이머가_0에_도달하면_콜백이_호출된다() {
    // Given
    TimerService timer = new TimerService(1); // 1초
    boolean[] completed = {false};
    timer.setOnComplete(() -> completed[0] = true);
    
    // When
    timer.start();
    Thread.sleep(1500);
    
    // Then
    assertTrue(completed[0]);
}
```

#### Step 5: 구현 및 통합

이런 식으로 계속 진행...

---

### Feature 3: 커스텀 세션 설정

**사용자 스토리:**
```
사용자가 설정 버튼을 누르면 설정 창이 열리고,
"작업 시간: 25분", "휴식 시간: 5분", "작업 이름: 코딩"을
입력하고 저장할 수 있다.
```

이 과정에서 발견하게 될 것들:
- `TimerSession` 모델 (작업+휴식 조합)
- `WorkPeriod` 모델
- `BreakPeriod` 모델
- `TimerSchedule` 모델 (여러 세션 관리)
- `StorageService` (저장/로드)

---

### Feature 4: 휴식 시간 자동 전환

**사용자 스토리:**
```
작업 시간이 완료되고 확인 버튼을 누르면,
자동으로 휴식 시간 타이머가 시작된다.
```

---

### Feature 5: 여러 세션 순환

**사용자 스토리:**
```
3개의 세션(작업-휴식-작업-휴식-작업-휴식)을 설정하면,
각 세션이 순차적으로 진행되고 모두 완료되면 
"모든 세션 완료!" 메시지가 표시된다.
```

---

## 전체 개발 흐름 요약

### Week 1: 기본 타이머 기능
- [ ] Feature 1: 기본 타이머 시작 및 표시
    - [ ] Feature 테스트 작성
    - [ ] TimerService 발견 및 구현
    - [ ] TimeFormatter 발견 및 구현
    - [ ] MainWindow/CompactTimerView 구현
    - [ ] Feature 테스트 통과

### Week 2: 알림 시스템
- [ ] Feature 2: 작업 시간 완료 시 전체화면 알림
    - [ ] Feature 테스트 작성
    - [ ] NotificationService 발견 및 구현
    - [ ] FullScreenAlertView 구현
    - [ ] TimerService 콜백 추가
    - [ ] Feature 테스트 통과

### Week 3: 설정 및 모델
- [ ] Feature 3: 커스텀 세션 설정
    - [ ] Feature 테스트 작성
    - [ ] TimerSession 모델 발견 및 구현
    - [ ] WorkPeriod 모델 발견 및 구현
    - [ ] BreakPeriod 모델 발견 및 구현
    - [ ] SettingsView 구현
    - [ ] Feature 테스트 통과

### Week 4: 고급 기능
- [ ] Feature 4: 휴식 시간 자동 전환
- [ ] Feature 5: 여러 세션 순환
    - [ ] TimerSchedule 모델 발견 및 구현
    - [ ] 세션 전환 로직
    - [ ] Feature 테스트 통과

### Week 5: 데이터 영속성
- [ ] Feature 6: 설정 저장 및 불러오기
    - [ ] StorageService 발견 및 구현
    - [ ] JSON 직렬화
    - [ ] Feature 테스트 통과

---

## Outside-In TDD의 장점 (이 프로젝트에서)

1. **실제 필요성 기반**: Feature 테스트가 실제로 필요한 것만 만들도록 가이드
2. **과도한 설계 방지**: 미리 모든 모델을 설계하지 않음
3. **빠른 피드백**: 각 Feature마다 완전히 작동하는 기능 확보
4. **학습 효과**: 왜 이 클래스가 필요한지 자연스럽게 이해
5. **자신감**: Feature 테스트 통과 = 실제 사용자 기능 완성

---

## 실제 시작 방법

### 1단계: 프로젝트 설정
```bash
cd FlexiTimerMvc
# Gradle wrapper 생성
gradle wrapper
```

### 2단계: 첫 Feature 테스트 작성
```bash
# 테스트 디렉토리 생성
mkdir -p src/test/java/com/flexitimermvc

# 첫 테스트 파일 생성
touch src/test/java/com/flexitimermvc/Feature1_BasicTimerTest.java
```

### 3단계: Red-Green-Refactor 사이클 시작!

---

## 주의사항

1. **Feature 테스트는 느림**: 실제 UI를 띄우므로 시간이 걸림 → 괜찮음!
2. **Mock 사용**: Feature 테스트에서 하위 레이어는 Mock 사용 가능
3. **리팩토링**: Green 단계 후 반드시 코드 개선
4. **작은 단계**: 한 번에 하나의 Feature만 집중

---

## 다음 단계

첫 번째 Feature 테스트를 함께 작성해볼까요?

```java
// Feature1_BasicTimerTest.java
public class Feature1_BasicTimerTest {
    @Test
    void 사용자가_25분_타이머를_시작하면_작은창에_2500이_표시된다() {
        // 여기서부터 시작!
    }
}
```

이 테스트를 통과시키는 과정에서 자연스럽게:
- TimerService가 필요함을 발견
- TimeFormatter가 필요함을 발견
- MainWindow 구조가 드러남
- 진짜 필요한 것만 만들게 됨