# (hhplus_concert_service 9주차)
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
  <li>
    <img width="1029" alt="마일스톤" src="https://github.com/user-attachments/assets/3f7307e9-0d13-4f24-a364-2af9366696de">

 </li>
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

## STEP16
<details>
  <summary>Kafka 설치</summary>

  ## kafka 설치 

  ### 1. docker-compose.yml 추가

  ```

services:
  zookeeper:
    image: 'confluentinc/cp-zookeeper:latest'
    container_name: zookeeper
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000
    ports:
      - '2181:2181'

  kafka:
    image: 'confluentinc/cp-kafka:latest'
    container_name: kafka
    depends_on:
      - zookeeper
    ports:
      - '9092:9092'
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT
      KAFKA_INTER_BROKER_LISTENER_NAME: PLAINTEXT
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
  ```
<br><br>

### 2. Kafka 실행 화면
<img width="1385" alt="스크린샷 2024-08-14 오후 8 01 26" src="https://github.com/user-attachments/assets/4d0b4f18-c0d5-4361-9511-3f6ddcb5a92a"> <br>

<img width="1269" alt="스크린샷 2024-08-14 오후 8 03 03" src="https://github.com/user-attachments/assets/384c3cfb-02d1-40f2-a1dd-a2bebf869a5e">

<br><br>
### 3. 연동 테스트 화면

<img width="1653" alt="스크린샷 2024-08-16 오전 3 12 37" src="https://github.com/user-attachments/assets/3028960a-c81e-45de-a159-f79d0a261561">

</details>

## STEP 17
<details>
  <summary>카프카 적용 및 OutBox 패턴 추가</summary>

  ### 적용 범위
  1. 예약 후 콘서트 좌석 상태 변경
  2. 예약 완료 후 토큰 삭제
  3. 포인트 차감 후 결제내역 저장 
</details>


