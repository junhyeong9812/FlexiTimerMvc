# FlexiTimerMvc - 커스터마이징 가능한 작업/휴식 타이머

## 프로젝트 소개

FlexiTimerMvc는 Java 21과 JavaFX로 개발된 MVC 패턴 기반의 유연한 타이머 애플리케이션으로, 작업 시간과 휴식 시간을 세밀하게 관리할 수 있는 생산성 도구입니다.

## 주요 기능

### 1. 커스터마이징 가능한 타이머 설정
- 각 세션별로 작업 시간 및 휴식 시간 개별 설정 가능
- 시간별 작업 내용 및 목적 저장 기능
- 단순 반복이 아닌 각 시간대별 맞춤 설정

### 2. 스마트 알림 시스템
- 설정된 시간이 되면 자동 알림 활성화
- 전체 화면 알림으로 집중도 향상
- 알림 확인 버튼으로 명확한 세션 전환

### 3. 직관적인 UI
- **일반 모드**: 화면 상단 중앙에 작은 창으로 타이머 표시
- **알림 모드**: 전체 화면으로 확대되어 알림 표시
- 알림 확인 후 자동으로 작은 창으로 복귀

## 기술 스택

- **Language**: Java 21
- **GUI Framework**: JavaFX 21
- **Build Tool**: Gradle 8.x
- **Architecture**: MVC Pattern

## 시스템 요구사항

- Java 21 이상
- 운영체제: Windows 10/11, macOS, Linux

## 프로젝트 구조 (MVC Pattern)

```
FlexiTimerMvc/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── flexitimermvc/
│   │   │           ├── Main.java                    # 애플리케이션 진입점
│   │   │           ├── controller/                  # Controller 레이어
│   │   │           │   ├── TimerController.java     # 타이머 UI 이벤트 처리
│   │   │           │   ├── SettingsController.java  # 설정 UI 이벤트 처리
│   │   │           │   └── AlertController.java     # 알림 UI 이벤트 처리
│   │   │           ├── model/                       # Model 레이어
│   │   │           │   ├── TimerSession.java        # 타이머 세션 데이터
│   │   │           │   ├── WorkPeriod.java          # 작업 시간 모델
│   │   │           │   ├── BreakPeriod.java         # 휴식 시간 모델
│   │   │           │   └── TimerSchedule.java       # 전체 스케줄 모델
│   │   │           ├── view/                        # View 레이어
│   │   │           │   ├── CompactTimerView.java    # 작은 창 뷰
│   │   │           │   ├── FullScreenAlertView.java # 전체화면 알림 뷰
│   │   │           │   └── SettingsView.java        # 설정 화면 뷰
│   │   │           ├── service/                     # 비즈니스 로직
│   │   │           │   ├── TimerService.java        # 타이머 핵심 로직
│   │   │           │   ├── NotificationService.java # 알림 처리
│   │   │           │   └── StorageService.java      # 데이터 저장/로드
│   │   │           └── util/                        # 유틸리티
│   │   │               ├── TimeFormatter.java
│   │   │               └── JsonSerializer.java
│   │   └── resources/
│   │       ├── fxml/                                # FXML 파일들
│   │       │   ├── compact-timer.fxml
│   │       │   ├── fullscreen-alert.fxml
│   │       │   └── settings.fxml
│   │       ├── css/                                 # 스타일시트
│   │       │   └── styles.css
│   │       └── config/                              # 설정 파일
│   │           └── default-config.json
│   └── test/
│       └── java/
│           └── com/
│               └── flexitimermvc/
├── build.gradle
├── settings.gradle
├── gradle/
│   └── wrapper/
├── gradlew
├── gradlew.bat
└── README.md
```

## MVC 패턴 설명

### Model (모델)
- 애플리케이션의 데이터와 비즈니스 로직을 담당
- 타이머 세션, 작업/휴식 시간 정보를 관리
- View나 Controller에 독립적

### View (뷰)
- 사용자에게 보여지는 UI를 담당
- Model의 데이터를 시각적으로 표현
- JavaFX Scene과 FXML로 구성

### Controller (컨트롤러)
- View에서 발생한 사용자 이벤트를 처리
- Model을 업데이트하고 View를 갱신
- Model과 View 사이의 중재자 역할

### Service (서비스)
- 복잡한 비즈니스 로직을 분리
- Controller가 직접 처리하기 복잡한 작업 담당
- 재사용 가능한 기능 제공

## 설치 및 실행

```bash
# 프로젝트 클론
git clone [repository-url]

# 프로젝트 디렉토리로 이동
cd FlexiTimerMvc

# Gradle 빌드
./gradlew build

# 실행
./gradlew run
```

## 사용 방법

1. **타이머 설정**
    - 작업 시간과 휴식 시간을 각각 설정합니다
    - 각 세션에 대한 작업 내용을 입력합니다

2. **타이머 시작**
    - 설정 완료 후 시작 버튼을 클릭합니다
    - 화면 상단에 작은 타이머 창이 표시됩니다

3. **알림 확인**
    - 설정된 시간이 되면 전체 화면 알림이 나타납니다
    - "확인" 버튼을 클릭하여 다음 세션으로 전환합니다

## 개발 로드맵

### Phase 1: MVC 구조로 기본 기능 구현
- [ ] 프로젝트 초기 설정 (Gradle, JavaFX)
- [ ] Model 클래스 설계 및 구현
- [ ] View (FXML) 디자인
- [ ] Controller 구현
- [ ] 기본 타이머 기능 구현

### Phase 2: 고급 기능 추가
- [ ] 커스터마이징 설정 UI 개발
- [ ] 알림 시스템 구현
- [ ] 전체 화면 ↔ 작은 창 전환 기능
- [ ] 세션 데이터 저장/불러오기 기능

### Phase 3: 추가 기능 (선택)
- [ ] 통계 및 리포트 기능
- [ ] 소리 알림 추가
- [ ] 다크 모드 지원

## 빌드 도구

이 프로젝트는 Gradle을 사용합니다:
- **build.gradle**: 의존성 및 플러그인 설정
- **settings.gradle**: 프로젝트 설정
- **gradlew / gradlew.bat**: Gradle Wrapper (자동 설치)

## 버전

- **현재 버전**: 0.1.0-SNAPSHOT
- **최초 작성일**: 2025-10-27
- **아키텍처**: MVC Pattern