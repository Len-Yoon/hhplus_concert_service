# hhplus_concert_service (3주차)
### 콘서트 예약 서비스

<br><br>
## SETP5 
- 시나리오 선정  및 프로젝트 Milestone 제출
- 시나리오 요구사항 분석 자료 제출 => 시퀀스 다이어그램

### 프로젝트 milestone
<img width="1187" alt="마일스톤" src="https://github.com/Len-Yoon/hhplus_concert_service/assets/76799034/0d0c5061-7f17-49e8-b28a-55ebc75f38b4">

### 시퀀스 다이어그램
![Untitled (1)](https://github.com/Len-Yoon/hhplus_concert_service/assets/76799034/ff3bc3e6-75db-4f75-999d-219bdf796828)
<br><br>

## STEP6
- ERP 다이어그램 작성
- Mock API 작성

### ERP 다이어그램
<img width="946" alt="ERD다이어그램" src="https://github.com/Len-Yoon/hhplus_concert_service/assets/76799034/9b6bd0c4-7b8d-4537-8285-f7a9805a8862">

### API 명세
<b>토큰</b><br>
- POST "/token/issuance" -> 토큰 발급 API
- GET "/token/check" -> 토큰 조회 API(대기열 정보 확인)
<br><br>
<b>예약</b><br>
- GET "/reservation/checkDate" -> 예약 가능 날짜 조회 API
- GET "/reservation/checkSeat" -> 예약 가능 날짜 조회 API
<br><br>
<b>포인트</b><br>
- GET "/point/checkPoint" -> 포인트 조회 API
- POST "/point/chargePoint" -> 포인트 충전 API
<br>
<b>결제</b><br>
-POST "/payment" -> 결제 API

  
