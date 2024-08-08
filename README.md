
### 콘서트 예약 서비스
<details>
<summary>제공 서비스</summary>
  <li>1.예약 가능 콘서트 조회</li>
  <li>2.콘서트 날짜 및 좌석 조회</li>
  <li>3.콘서트 예약</li>
  <li>4.포인트 조회/충전/사용</li>
  <li>결제</li>
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

## STEP15
<details>
  <summary>DB Query Optimization (feat. Index)</summary>

  ### 요구사항
  - 자주 조회하는 쿼리, 복잡한 쿼리 파악
  - Index 추가 전후 Explain, 실행시간 비교 

  ### 인덱스 개념 정리
  https://velog.io/@mabest123/%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4-%EC%9D%B8%EB%8D%B1%EC%8A%A4Index-%EA%B0%9C%EB%85%90-%EC%A0%95%EB%A6%AC

  ### 인덱스 조건
  1. 한번에 찾을 수 있는 값 - 데이터 중복이 적은 컬럼
  2. 인덱스 재정렬 최소화 - 데이터 삽입, 수정이 적은 컬럼
  3. 인덱스의 목적은 검색 - 조회에 자주 사용되는 컬럼
  4. 너무 많지 않은 인덱스 (약 3~4개) - 인덱스 또한 공간을 차지함 <br><br><br>

  ### 1. concert 조회 Index 추가
  #### 더미 데이터 : 100만개 (Status 'Y' 20만개 / 'N' 80만개)
  
  ### No Index
  #### - explain
  ```select * from concert where status = 'Y'``` 그리고  ```select * from concert where title like "TEST1%" and status = "N" ``` <br>
  <img width="1329" alt="스크린샷 2024-08-07 오전 10 28 02" src="https://github.com/user-attachments/assets/ff97a31f-db9d-4105-b855-c9570cf9c403"> <br>

인덱스가 없어 998,438개의 row를 다 탐색한 후 status = 'Y'인 값을 도출하는 것을 볼 수 있습니다.

   
  #### - 경과 시간
  ```select * from concert where status = 'Y'``` <br>
<img width="1267" alt="스크린샷 2024-08-07 오전 10 30 53" src="https://github.com/user-attachments/assets/f891f8c4-dd01-4d90-9e10-410424977d16"> <br>
  20만개의 row를 리턴하는데 5,628ms의 시간이 걸렸습니다.. <br><br>

  ```select * from concert where title like "TEST1%" and status = "N" ```<br>
<img width="1270" alt="스크린샷 2024-08-07 오전 11 15 20" src="https://github.com/user-attachments/assets/4865a1b8-8cc4-4c13-9454-3c12dba85173"> <br>
약 11만개의 row를 리턴하는데 3,145ms의 시간이 걸렸습니다.
  

  ### Index
  #### - 인덱스 생성
  ```create index concert_status_idx on concert (status)```<br>
  ```create index concert_title_status_idx on concert (title,status)``` <br>
  <br><br>
  
  #### - explain
  ```select * from concert where status = 'Y'```  <br>
<img width="1283" alt="스크린샷 2024-08-07 오전 10 35 19" src="https://github.com/user-attachments/assets/5163e034-04ec-46df-97e0-aaa1cc1fcdc8">  <br>
인덱스를 생성 후 391,648개의 row를 탐색하는 것을 볼 수 있습니다.<br><br>

 <br>
 <img width="1411" alt="스크린샷 2024-08-07 오전 11 37 11" src="https://github.com/user-attachments/assets/2acd38e8-013f-44f1-99e0-00802e145733"> <br>
 인덱스를 생성 후 222,630의 row를 탐색하는 것을 볼 수 있습니다.


  #### - 경과 시간
  ```select * from concert where status = 'Y'``` <br>
  <img width="1271" alt="스크린샷 2024-08-07 오전 10 40 11" src="https://github.com/user-attachments/assets/2ed38597-6685-4f51-9fa4-c4f3058bfd88"><br>
20만개의 row를 리턴하는데 5,443ms의 시간이 걸렸다. <br><br>

```select * from concert where title like "TEST1%" and status = "N" ``` <br>
<img width="1270" alt="스크린샷 2024-08-07 오전 11 45 45" src="https://github.com/user-attachments/assets/690afb5f-8ee0-4a48-96bf-a4b1fd5b488b"> <br>
약 11만개의 row를 리턴하는데 3,011ms의 시간이 걸렸습니다.


  #### - 후기
  인덱스를 걸고 확실히 스캔을 하는 row의 수가 현저히 줄어들었습니다. 데이터가 적어 시간은 많이 안 줄었지만 테이블을 join하여 데이터를 조회하거나 데이터의 량이 더 늘어나면
  수많은 성능차이를 느낄 수 있을것으로 생각됩니다. <br><br><br>


  ### 2. concert 옵션 조회 Index 추가
  ### 더미 데이터: 약 140만개 (약 2900개 콘서트 * 날짜 50일)

  ### No Index

  #### - explain
  ```select * from concert_item where concert_datge = "2024-08-07"```and ```select * from concert_item where concert_datge = "2024-08-07" and concert_id = 1``` <br>
  <img width="1243" alt="스크린샷 2024-08-07 오후 4 02 26" src="https://github.com/user-attachments/assets/81dc78d2-79a1-42be-8fe6-d56e7124684e"> <br>
  인덱스가 없기에 어떤 쿼리로 조회하든 약 140만개의 row를 다 탐색하는 것을 볼 수 있습니다.

  #### - 경과 시간
  ```select * from concert_item where concert_datge = "2024-08-07"```  <br>
  <img width="1271" alt="스크린샷 2024-08-07 오후 5 41 20" src="https://github.com/user-attachments/assets/e5966d2c-db3d-4aaf-8ff9-c4fa378fb40c">

 <br>
약 28000개의 row 리턴하는데 1,043ms가 걸렸습니다.

  ```select * from concert_item where concert_datge = "2024-08-07" and concert_id = 1``` <br>
  <img width="1269" alt="스크린샷 2024-08-07 오후 4 26 45" src="https://github.com/user-attachments/assets/2e601d5e-fb16-4316-b653-ba1967fdac50">
 1개의 row를 리턴하는데 232ms의 시간이 걸렸습니다.
   <br><br><br>

### Index
#### - explain
```select * from concert_item where concert_datge = "2024-08-07"``` <br>
<img width="1311" alt="스크린샷 2024-08-07 오후 5 25 25" src="https://github.com/user-attachments/assets/63400ae3-7843-404d-b572-0016ba0ad2b3"> <br>
54,792의 row를 탐색하는 것을 볼 수 있습니다.

```select * from concert_item where concert_datge = "2024-08-07" and concert_id = 1``` <br>
<img width="1415" alt="스크린샷 2024-08-07 오후 5 27 10" src="https://github.com/user-attachments/assets/cf08ef78-f9ab-48e4-85cf-d55f13e88b2a">
54,792의 row를 탐색하는 것을 볼 수 있습니다. <br>

#### - 경과 시간
```select * from concert_item where concert_datge = "2024-08-07"``` <br>
<img width="1271" alt="스크린샷 2024-08-07 오후 5 35 48" src="https://github.com/user-attachments/assets/b0e3c011-59ba-4f9c-bde0-dcae7ea64048"> <br>
약 28000개의 row 리턴하는데 746ms가 걸렸습니다.

```select * from concert_item where concert_datge = "2024-08-07" and concert_id = 1``` <br>
<img width="1270" alt="스크린샷 2024-08-07 오후 5 35 21" src="https://github.com/user-attachments/assets/3abbd69f-75c2-415a-8c35-1826cc3fafa4">   <br>
 1개의 row를 리턴하는데 70ms의 시간이 걸렸습니다. <br>

 #### - 후기
 데이터가 적어 미비하지만 확실히 30%이상의 성능 차이를 느낄 수 있었습니다. 그리고 조회하는 row수의 큰 감소로 Index의 중요성을 느낄 수 있었습니다.
</details>

## STEP16
<details>
  <summary>Kafka를 이용한 분산 트랙잭션 설계</summary>
  
  ### Kafka 트랙잭션 개념 정리
  https://velog.io/@mabest123/Kafka%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EB%B6%84%EC%82%B0-%ED%8A%B8%EB%9E%9C%EC%9E%AD%EC%85%98%EC%9D%B4%EB%9E%80 <br><br><br>

  
  
</details>

<details>
  <summary>EventListener + EventPublisher를 이용한 관심사 분리</summary>
  
  ### 적용 트랜잭션

  #### 1. 콘서트/옵션/좌석 생성 

  #### 2. 페이먼트 

  #### 3. 포인트 충전/차감
  
  ### 실패 시 상황
  RuntimeException 오류 구축  
</details>
