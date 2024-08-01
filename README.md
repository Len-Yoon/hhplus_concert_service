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
  <summary>마일스톤</summary>
  <li>
    <img width="1029" alt="마일스톤" src="https://github.com/user-attachments/assets/3f7307e9-0d13-4f24-a364-2af9366696de">

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

## STEP13
<details>
  <summary>Redis 캐시 이론</summary>
https://velog.io/@mabest123/Redis-Cache%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%B4%EB%B3%B4%EC%9E%90-
</details>

<details>
<summary>캐시 적용 성능 차이</summary>
  
#### 1. 예약 가능 콘서트 조회 API 캐싱 <br>
##### 적용이유 <br>
유저가 콘서트 예매를 위한 첫 단계로서 예약가능 콘서트를 read하는 기능으로 변화가 적을거라 생각하기에 캐싱을 걸었습니다.
##### 테스트 시나리오 <br>
Dummy Data: 1000개 <br>
1초 당 3000명의 API 요청을 30초 동안 부하
##### 테스트 결과 <br>
<img width="400" alt="concert_not_with_cache" src="https://github.com/user-attachments/assets/76b86a4b-8f9f-4188-9834-f1a2b4f5423b">
&emsp;
<img width="400" alt="concert_with_cache" src="https://github.com/user-attachments/assets/27137066-00e4-4655-a390-cc36244548e0"> <br>
왼- 캐시 미적용 / 오 - 캐시 적용

##### 결과 보고 <br>
캐시 미적용: 32.2초 / 42378건 완료 <br>
캐시 적용: 31.1초 / 54741건 완료 <br><br>

##### 느낀 점
일단 캐싱 적용으로 초 당 30%의 성능 향상을 확인 할 수 있었습니다 <br> 
또한 캐시 미적용의 경우 초 당 3000건이 넘어가면 fail을 하는 확률이 점점 늘어나는 반면에 <br>
캐시 적용의 경우 초 당 10000건 까지 안정적이 성능을 보여주었습니다.

#### 2. 예약 가능 콘서트 조회 API 캐싱 <br>
##### 적용이유 <br>
콘서트의 옵션을 read 하는 API로써 예약 가능 콘서트 API와 동등한 read를 합니다.
##### 테스트 시나리오 <br>
Dummy Data: 200개 <br>
1초 당 5000명의 API 요청을 30초 동안 부하
##### 테스트 결과 <br>
<img width="400" alt="concertItem_not_cache" src="https://github.com/user-attachments/assets/0e0b5252-da40-40db-a7e6-f7cc88978bc3">

&emsp;
<img width="400" alt="concertItem_with_cache" src="https://github.com/user-attachments/assets/ccf2033f-a5c8-4b3e-8d2a-da868bd0d87d"> <br>

왼- 캐시 미적용 / 오 - 캐시 적용

##### 결과 보고 <br>
캐시 미적용: 31.9초 / 60284건 완료 <br>
캐시 적용: 31.3초 / 94777건 완료 <br>

##### 느낀 점 <br>
일단 캐싱 적용으로 초 당 50%의 성능 향상을 확인 할 수 있었습니다 <br> 
또한 캐시 미적용의 경우 초 당 4000건이 넘어가면 fail을 하는 확률이 점점 늘어나는 반면에 <br>
캐시 적용의 경우 초 당 13000건 까지 안정적이 성능을 보여주었습니다.

</details>

## STEP12
- DB Lock 을 활용한 동시성 제어 방식 에서 해당 비즈니스 로직에서 적합하다고 판단하여 차용한 동시성 제어 방식을 구현하여 비즈니스 로직에 적용하고, 통합테스트 등으로 이를 검증하는 코드 작성 및 제출

