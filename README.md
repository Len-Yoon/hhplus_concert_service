# (hhplus_concert_service 동시성제어 1주차)
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
  <summary>Step9 마일스톤</summary>
  <li><img width="865" alt="마일스톤" src="https://github.com/user-attachments/assets/f63bf13f-2c33-4491-b407-26de784f4279">
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

<br>

## STEP11
<details>
<summary>동시성 제어 시나리오</summary>

  ## 1. Reservation Service

### 실패 솔루션 
<img width="663" alt="비관적 락" src="https://github.com/user-attachments/assets/72fc2563-01ff-4ab5-bd59-b1f67d22f866">
<br><br>
장점: 완벽한 충돌 방지 보장 <br>
단점: ConccertSeat 조회부터 락을 걸어 작업이 오래걸리며 DeadLock 발생<br><br><br>

<img width="667" alt="낙관적 락" src="https://github.com/user-attachments/assets/6db186dc-fedd-4854-b0e4-40d5ff85ab0a">
<br><br>
장점: 충돌 발생 시에만 Lock이 사용되기에 성능 보장<br>
단점: 한번에 성공 시 속도가 보장되나 Retry로 인한 시간 지연 및 예약 실패

### 적용 솔루션
<img width="544" alt="낙관+비관" src="https://github.com/user-attachments/assets/91933e1c-8814-4317-a79b-9144b8267643">
 <br>
 
#### 이유<br>

제가 낙관적 락과 비관적 락을 동시에 사용한 가장 큰 이유는 성능과 안정성의 균형을 맞추고 싶었기 때문입니다.
낙관적 락을 이용하여 읽기 성능을 유지하고, 비관적 락을 통해 ConcertSeat의 상태를 변경 할 때 충돌을 방지하기 위해서 입니다.

낙관적 락을 사용하다 충돌 시, 1차 캐쉬를 초기화 하고, 재시도 로직을 통해 비관적 락의 충돌을 최소화하려고 노력하였습니다.

## 2. Point Service

### 적용 솔루션
<img width="665" alt="포인트 락" src="https://github.com/user-attachments/assets/b18595a3-d07e-4e49-b932-bc4af8b40389"> <br>
#### 이유<br>

포인트 충전/차감에 있어 가장 많이 발생할 수 있는 충돌은 동일한 사용자가 동시에 수행할 수 있기 때문에 충돌이 발생하는 경우라고 생각했습니다.
그렇기에 충돌의 빈도가 적다고 생각하였습니다. 그렇기에 성능과 데이터의 일관성을 균형 있게 유지하기 위해 낙관적 락을 적용하였고, 충돌이 발생 시에만
ObjectOptimisticLockingFailureException이 발생하도록 하였고, RollBack이 되도록 코드를 구현하였습니다.
</details>

## STEP12
- DB Lock 을 활용한 동시성 제어 방식 에서 해당 비즈니스 로직에서 적합하다고 판단하여 차용한 동시성 제어 방식을 구현하여 비즈니스 로직에 적용하고, 통합테스트 등으로 이를 검증하는 코드 작성 및 제출

