# (hhplus_concert_service 3주차)
### 콘서트 예약 서비스
<details>
<summary>제공 서비스</summary>
  <li>1.예약 가능 콘서트 조회</li>
  <li>2.콘서트 날짜 및 좌석 조회</li>
  <li>3.콘서트 예약</li>
  <li>4.포인트 조회/충전/사용</li>
  <li>결제</li>
</details>

<details>
<summary>오류 검증 테스트</summary>
  <ul>
    <li>콘서트 조회</li>
      <ul>
        <li>1. 예약 가능 콘서트가 아닌 경우</li>
      </ul>
  </ul>

  <ul>
    <li>콘서트 예약</li>
    <ul>
      <li>1.이미 예약된 좌석일 경우</li>
      <li>2.예약 후 5분 내 결제를 완료하지 않은 경우</li>
    </ul>
  </ul>
  
  <ul>
    <li>포인트 조회/충전/사용</li>
      <ul>
        <li>1.충전 포인트가 0보다 작은경우</li>
      </ul>
  </ul>

  <ul>
    <li>결제</li>
      <ul>
        <li>1.포인트가 부족할 경우</li>
        <li>2.토큰이 없는 경우</li>
      </ul>
  </ul>
</details>

<br>

### 마일스톤
<details>
  <summary>마일스톤</summary>
  <li><img width="801" alt="마일스톤" src="https://github.com/user-attachments/assets/6d3e6241-5ae2-4258-9c27-ee6145767ae0">
</li>
</details>

### ERD 다이어그램
<details>
  <summary>ERD</summary>
  <li><img width="619" alt="ERD다이어그램" src="https://github.com/user-attachments/assets/f35f505c-b2f4-4f13-b970-baee44ee9f49"></li>
</details>

### 시퀀스 다이어그램
<details>
  <summary>Sqeunce</summary>
  <li><img width="521" alt="유스케이스 예시" src="https://github.com/user-attachments/assets/12258fb1-8da2-45b8-afbb-78de93634a0f">
</li>
</details>

### 유스케이스
<details>
  <summary>Usecase</summary>
  <li><img width="500" alt="유스케이스 다이어그램" src="https://github.com/user-attachments/assets/eaea2ac8-4eed-4792-b50e-73162d165d52"></li>
</details>

### Swagger
<details>
  <summary>Swagger</summary>
  <li><<img width="724" alt="swaggerAPI" src="https://github.com/user-attachments/assets/809634b2-3f0e-42ba-a7c4-95d86db203e8"></li>
</details>



<br>

### STEP9
<Details>
  <summary>step9</summary>
  <li>필요한 Filter, Interceptor 등의 기능 구현</li>
  <li>예외 처리, 로깅 등 유효한 부가로직의 구현</li>
</Details>

### STEP10
<Details>
  <summary>step10</summary>
  <li>정상적으로 구동되는 서버 애플리케이션 완성</li>
  <li>제공해야 하는 API 완성</li>
  <li>서버구축 챕터 마무리 회고록 작성 (`NICE TO HAVE`)
    - DB Index , 대용량 처리를 위한 개선 포인트 등은 추후 챕터에서 진행하므로 목표는 `기능 개발의 완료` 로 합니다. 최적화 작업 등을 고려하는 것 보다 모든 기능을 정상적으로 제공할 수 있도록 해주세요.
    <ul>
      <li>특정 기능을 왜 이렇게 개발하였는지 합당한 이유와 함께 기능 개발을 진행해주시면 됩니다.</li>
    </ul>
  </li>
    
</Details>

<br>

### 프로젝트 회고록
<Details>
  <summary>회고록</summary>

  ### 1. 프로젝트 소개
  항해99 과정에 참여하면서 과제로 e-커머스와 콘서트 예약 서비스 중 하나를 정하여 프로젝트를 완성하는 과제가 있었습니다.
  <br><br>
  두 가지 중 동시성 제어 뿐만 아니라 토큰 대기열도 만들어 볼 수 있는 콘서트 예약 서비스가 더 값진 경험이 되지않을까 하여 <br>
  콘서트 예약 서비스 프로젝트를 개발하게 되었습니다.
  <br><br>
  완전 처음 만드는 서비스에 막막하고 걱정도 많이 했었습니다.
  <br><br>
  
  ### 2. 프로젝트 개요
  #### 제공 서비스
  - 예매 가능 콘서트/날짜/좌석 조회
  - 콘서트 예약
  - 포인트 조회/충전
  - 결제 
  <br>
  
  ### 3. 기술 스택
  - JAVA 17
  - Gradle 기반 레이어드 아키텍처 구조
  - MySQL
  <br>

  ### 4. 배운 점
  - 구체적이고 정확한 설계의 중요성을 느낄 수 있었습니다. 초반 미흡한 설계로 인해 ERD 다이어그램과 시퀀스 다이어그램을 다시 짜는 경험을 했습니다.
    시간이 다소 걸리더라도 다시 요구사항 명세부터 세부적으로 정리해나갔고 시행착오 없이 진행이 가능했습니다.
  - TDD를 적용하며 시간이 다소 오래 걸렸지만 테스트의 중요성을 배울 수 있었습니다. 내가 일찍 퇴근하고 싶으면 테스트 코드를 잘 짜야 한다는 코치님들의
    말씀을 이해 할 수 있었습니다.
  - 저의 경우 회사가 개발 중점의 회사가 아니다 보니 모든 개발을 처음부터 끝까지 제가 하는 경우가 많았습니다. 타 팀과의 협업하는 방법을 잘 몰랐었는데
    어떤 식으로 개발 방향을 잡고 협업하는지 배울 수 있었습니다.
  - 이번 프로젝트에선 Filter를 사용하여 Logging 기능만 추가 하였지만 서비스에 대한 권한을 나눌 수 있다는 배웠고, Interceptor를 사용하여 서비스의 세부사항의
    권한을 나눌 수 있다는 점을 배웠습니다. 과거에 저의 경우는 DB를 통하여 서비스의 권한을 나누었는데 훨씬 개발하기 편하고 좋은 방법인거 같습니다. 
<br>

### 5. 아쉬운 점
  - 처음에는 정말 요구사항 분석을 왜 하는지 몰랐어서 허술하게 작성했습니다. 첫 단추가 잘못 끼워져있으니 그 뒤에 있는 도메인 설계, DB 설계, Use Case Model, request 처리 흐름도도 일관성     없게 작성되었습니다. 그러니 나중에 기능을 구현하려고 할때, 개발의 흐름이 안맞는 일이 발생했습니다. 요구사항 명세를 더 정확하고 구체적으로 하고, 발생할 오류를 적어보고, 도메인과 DB를     다시 설계하면서 데이터의 흐름을 정리고 특히 request 처리 흐름도를 힘 주어서 정리하면서 개념적으로 이해가 가능하도록 노력했습니다. 다소 시간이 걸리더라도 설계를 잘 해놓으니 나중에 구     현할 때 시간을 단축시킬 수 있었습니다. 다음 프로젝트때에는 설계의 중요성을 꼭 기억하고 논리적으로 작성하도록 하겠습니다.
  - 레이어드 아키텍처나 TDD등 이번 프로젝트에서 사용한 스택 중 대부분의 스택이 어색한 스택들이었습니다. 구글 검색을 통해 더 빨리 해결할 수 있는 문제인데, 스스로 해결해보려다
    시간이 오래 걸리는 경우도 있었습니다.
    <br><br>

### 6. 느낀 점
  - 항해99에 합류하면서 하루 5시간 이상을 자본적이 없는거 같습니다. 하지만 하루 하루 공부하며 과제를 성공해 나갈때마다 내가 더 성장한다는
    느낌을 받을 수 있었습니다. 또한 처음 개발자가 되었을 때의 열정이 다시 살아나는 기분이라 너무 좋았습니다. 무사히 과정을 마무리하면 좋겠습니다.
</Details>
