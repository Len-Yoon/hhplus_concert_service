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

#### 테스트 적용 PC
CPU: Razen7 3700u (4코어 8쓰레드)<br>
RAM: DDR4 2400 16GB<br>
OS: Windows 10 Professional<br>

#### 테스트 방식
K6 과부화 테스트 진행
  
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

캐싱 적용으로 초 당 30%의 성능 향상을 확인 할 수 있었습니다 <br> 
또한 캐시 미적용의 경우 초 당 3000건이 넘어가면 fail을 하는 확률이 점점 늘어나는 반면에 <br>
캐시 적용의 경우 초 당 10000건 까지 안정적이 성능을 보여주었습니다.

#### 2. 예약 가능 콘서트 조회 API 캐싱 <br>
##### 적용이유 <br>
콘서트의 옵션을 read 하는 API로써 예약 가능 콘서트 API와 동등한 read를 합니다.
##### 테스트 시나리오 <br>
Dummy Data: 200개 <br>
1초 당 4000명의 API 요청을 30초 동안 부하
##### 테스트 결과 <br>
<img width="400" alt="concertItem_not_cache" src="https://github.com/user-attachments/assets/0e0b5252-da40-40db-a7e6-f7cc88978bc3">
&emsp;
<img width="400" alt="concertItem_with_cache" src="https://github.com/user-attachments/assets/ccf2033f-a5c8-4b3e-8d2a-da868bd0d87d"> <br>

왼- 캐시 미적용 / 오 - 캐시 적용

##### 결과 보고 <br>
캐시 미적용: 31.9초 / 60284건 완료 <br>
캐시 적용: 31.3초 / 94777건 완료 <br><br>

캐싱 적용으로 초 당 50%의 성능 향상을 확인 할 수 있었습니다 <br> 
또한 캐시 미적용의 경우 초 당 4000건이 넘어가면 fail을 하는 확률이 점점 늘어나는 반면에 <br>
캐시 적용의 경우 초 당 13000건 까지 안정적이 성능을 보여주었습니다.

</details>

## STEP13
<details>
  
  <summary>콘서트 예약 대기열 시나리오</summary>

  #### 적용 대기열
  Redis Sorted Set을 이용한 놀이동산 방식의 대기열 <br>

  #### 놀이동산방식 + Redis Sorted set 대기열 구현 이유
  1. 사용자의 대기 시간 일정함 유지 <br>
  2. DB 부하 감소
  3. 대기열 진입 순서대로 관리
  4. 콘서트별로 각각 다른 대기열 관리

  #### 토큰 상태 변경
  10초 마다 3000명의 대기열 -> 활성화열 이동 -> DB 토큰 정보 저장 
  
  #### 토큰 만료
  expired한 시간을 확인하여 현재와 30분 이상 차이날 시 자동 삭제 (30분 단위 스케줄러 실행)

  #### 포인트 차감
  DB에 저장된 토큰 정보를 concertId와 userId로 확인하여 포인트 차감 유무 결정

  #### 놀이동산방식 + Redis 대기열 구현으로 느낀점
  많은 요청이 바로 DB로 이어지지 않고 Redis를 이용하여 거치며 DB에 부하를 줄일 수 있다는 것을 배웠고 <br>
  SortedSet 이라는 자료구조의 대하여 알아 볼 수 있었습니다. <br>
  하지만 이 방식의 단점이라고 여겨진 부분이 예약을 마치고 나가는 사람보다 <br>
  토큰 발급 유저가 많다면 어떻게 처리해야할 지 좀 더 연구해봐야 할 것 같습니다.

  #### redis 대기열 공부 
  https://velog.io/@mabest123/%EB%86%80%EC%9D%B4%EB%8F%99%EC%82%B0%EB%B0%A9%EC%8B%9D-Redis-Sorted-Set-%EB%8C%80%EA%B8%B0%EC%97%B4-%EA%B5%AC%ED%98%84-feat.-%ED%95%AD%ED%95%B4-%ED%94%8C%EB%9F%AC%EC%8A%A4-%EA%B3%BC%EC%A0%9C

</details>

