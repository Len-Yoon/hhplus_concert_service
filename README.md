## (hhplus_concert_service 10주차)
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

## STEP19
<details>
  <summary>부하 테스트 시나리오</summary>

## 부하 테스트 환경

### 사양
CPU: M2 Pro <br>
Ram: 16Gb <br>
ssd: 512Gb <br>
Tool: K6  <br><br>

## 1. 좌석 선택 및 예약 

### 선정이유
콘서트 예매에 있어 가장 중요한 부분이고 트래픽이 가장 많이 몰리는 부분이라 생각했습니다. <br>
이 시점에서 서버가 얼마나 많은 동시 접속을 처리할 수 있는지 테스트해야합니다. <br>
그렇기에 부하테스트를 진행하였습니다. <br><br>

### 목표 TPS  
min: 500TPS <br> 
max: 1000TPS  <br><br>

### Load Test (부하 테스트)
Vus: 500 (초당 가상 유저수) <br>
Duration: 60s <br>

```
import http from 'k6/http';
import { sleep } from 'k6';

export let options = {
    vus: 500, // 가상 사용자 수
    duration: '60s', // 테스트 지속 시간
};

function generateUserId() {
    return 'user_' + Math.floor(Math.random() * 100000);
}

function generateItemId() {
    return Math.floor(Math.random() * 100) + 1; // 무작위 itemId 생성 (1-100)
}

function generateSeatId() {
    return Math.floor(Math.random() * 50) + 1; // 무작위 seatId 생성 (1-50)
}

function generateTotalPrice() {
    return Math.floor(Math.random() * 1000) + 100; // 무작위 totalPrice 생성 (100-1100)
}

export default function () {

    const url = 'http://localhost:8080/reservation';

    // 무작위 데이터 생성
    const payload = JSON.stringify({
        userId: generateUserId(),
        concertId: 1,
        itemId: generateItemId(),
        seatId: generateSeatId(),
        totalPrice: generateTotalPrice(),
        status: 'N', // 예약 상태
    });

    // 요청 헤더 설정
    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    // POST 요청 보내기
    let response = http.post(url, payload, params);

    // 응답 상태 코드와 내용을 검사
    check(response, {
        'is status 200': (r) => r.status === 200,
    });

    sleep(1); // 각 요청 사이의 대기 시간
}
```

<img width="866" alt="스크린샷 2024-08-22 오전 11 29 56" src="https://github.com/user-attachments/assets/897faf17-7080-4e24-89bb-90e0cf52611d"> <br>


<br>

### Soak Test (내구성 테스트)
Vus: 500 <br>
Duration: 10m <br>

```
import http from 'k6/http';
import { sleep } from 'k6';

export let options = {
    vus: 500, // 가상 사용자 수
    duration: '10m', // 테스트 지속 시간
};

function generateUserId() {
    return 'user_' + Math.floor(Math.random() * 100000);
}

function generateItemId() {
    return Math.floor(Math.random() * 100) + 1; // 무작위 itemId 생성 (1-100)
}

function generateSeatId() {
    return Math.floor(Math.random() * 50) + 1; // 무작위 seatId 생성 (1-50)
}

function generateTotalPrice() {
    return Math.floor(Math.random() * 1000) + 100; // 무작위 totalPrice 생성 (100-1100)
}

export default function () {

    const url = 'http://localhost:8080/reservation';

    // 무작위 데이터 생성
    const payload = JSON.stringify({
        userId: generateUserId(),
        concertId: 1,
        itemId: generateItemId(),
        seatId: generateSeatId(),
        totalPrice: generateTotalPrice(),
        status: 'N', // 예약 상태
    });

    // 요청 헤더 설정
    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    // POST 요청 보내기
    let response = http.post(url, payload, params);

    // 응답 상태 코드와 내용을 검사
    check(response, {
        'is status 200': (r) => r.status === 200,
    });

    sleep(1); // 각 요청 사이의 대기 시간
}
```

<img width="848" alt="스크린샷 2024-08-22 오전 11 51 48" src="https://github.com/user-attachments/assets/f85208c0-3707-4561-acd1-a4dd708e162c">  <br><br>

### Stress Test (스트레스 테스트)
1. 2분 동안 Vus=500 <br>
2. 2분 동안 Vus=750 <br>  
3. 2분 동안 Vus=1000 <br>
4. 2분 동안 종료 <br>

```
import http from 'k6/http';
import { sleep } from 'k6';

export let options = {
    stages: [
        { duration: "2m", target: 500 },
        { duration: "2m", target: 750 },
        { duration: "2m", target: 1000 },
        { duration: "2m", target: 0 }
    ],
};

function generateUserId() {
    return 'user_' + Math.floor(Math.random() * 100000);
}

function generateItemId() {
    return Math.floor(Math.random() * 100) + 1; // 무작위 itemId 생성 (1-100)
}

function generateSeatId() {
    return Math.floor(Math.random() * 50) + 1; // 무작위 seatId 생성 (1-50)
}

function generateTotalPrice() {
    return Math.floor(Math.random() * 1000) + 100; // 무작위 totalPrice 생성 (100-1100)
}

export default function () {

    const url = 'http://localhost:8080/reservation';

    // 무작위 데이터 생성
    const payload = JSON.stringify({
        userId: generateUserId(),
        concertId: 1,
        itemId: generateItemId(),
        seatId: generateSeatId(),
        totalPrice: generateTotalPrice(),
        status: 'N', // 예약 상태
    });

    // 요청 헤더 설정
    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    // POST 요청 보내기
    let response = http.post(url, payload, params);

    // 응답 상태 코드와 내용을 검사
    check(response, {
        'is status 200': (r) => r.status === 200,
    });

    sleep(1); // 각 요청 사이의 대기 시간
}
```

<img width="830" alt="스크린샷 2024-08-22 오후 12 06 16" src="https://github.com/user-attachments/assets/1bf3d17e-8b6c-4cbb-a9b7-c75f64d6b6b5"> <br><br>

### Peak Load Test (최고 부하 테스트)
1. 2분 동안 500명 유저로 증가 <br>
2. 5분 동안 1500명 유저로 최고 부하 테스트 <br>
3. 2분 동안 다시 500명 유저로 감소 <br>

```
import http from 'k6/http';
import { sleep } from 'k6';

export let options = {
    stages: [
        { duration: "2m", target: 500 },
        { duration: "5m", target: 1500 },
        { duration: "2m", target: 5000 }
    ],
};

function generateUserId() {
    return 'user_' + Math.floor(Math.random() * 100000);
}

function generateItemId() {
    return Math.floor(Math.random() * 100) + 1; // 무작위 itemId 생성 (1-100)
}

function generateSeatId() {
    return Math.floor(Math.random() * 50) + 1; // 무작위 seatId 생성 (1-50)
}

function generateTotalPrice() {
    return Math.floor(Math.random() * 1000) + 100; // 무작위 totalPrice 생성 (100-1100)
}

export default function () {

    const url = 'http://localhost:8080/reservation';

    // 무작위 데이터 생성
    const payload = JSON.stringify({
        userId: generateUserId(),
        concertId: 1,
        itemId: generateItemId(),
        seatId: generateSeatId(),
        totalPrice: generateTotalPrice(),
        status: 'N', // 예약 상태
    });

    // 요청 헤더 설정
    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    // POST 요청 보내기
    let response = http.post(url, payload, params);

    // 응답 상태 코드와 내용을 검사
    check(response, {
        'is status 200': (r) => r.status === 200,
    });

    sleep(1); // 각 요청 사이의 대기 시간
}
```

<img width="832" alt="스크린샷 2024-08-22 오후 12 18 19" src="https://github.com/user-attachments/assets/b053cbd3-39d2-486f-8c45-0d848c31bc7b"> <br>

<br><br><br>

## 2.토큰 대기열 발급

### 선정이유
새로운 토큰을 생성하고 Redis와 데이터베이스에 동시에 저장하는 기능입니다. <br>
특히 Redis의 'ZSet'에 토큰을 추가하는 작업은 높은 부하를 일으키고, 그로 인해 <br>
대량의 토큰 생성시 성능 저하 문제가 발생할 수 있다 생각했습니다. <br><br>

### 목표 TPS  
min: 500TPS <br> 
max: 1000TPS  <br><br>

### Load Test (부하 테스트)
Vus: 500 (초당 가상 유저수) <br>
Duration: 60s <br>

```
import http from 'k6/http';
import { sleep } from 'k6';

// 환경변수로 설정된 값을 사용합니다
const API_URL = __ENV.API_URL || 'http://localhost:8080/token/add';
const CONCERT_ID = 1;

function generateUserId() {
    return 'user_' + Math.floor(Math.random() * 100000);
}
export const options = {
    vus: 500,
    duration: '60s'
};

export default function () {
    const payload = JSON.stringify({
        userId: generateUserId(),
        concertId: CONCERT_ID
    });

    const headers = { 'Content-Type': 'application/json' };

    let response = http.post(API_URL, payload, { headers: headers });

    sleep(1);
}
```

<img width="865" alt="스크린샷 2024-08-22 오후 1 35 27" src="https://github.com/user-attachments/assets/fd7ea0a7-644e-4dff-80ea-4eff4baf782b"> <br>


<br><br>

### Soak Test (내구성 테스트)
Vus: 500 <br>
Duration: 10m <br>

```
import http from 'k6/http';
import { sleep } from 'k6';

// 환경변수로 설정된 값을 사용합니다
const API_URL = __ENV.API_URL || 'http://localhost:8080/token/add';
const CONCERT_ID = 1;

function generateUserId() {
    return 'user_' + Math.floor(Math.random() * 100000);
}
export const options = {
    vus: 500,
    duration: '10m'
};

export default function () {
    const payload = JSON.stringify({
        userId: generateUserId(),
        concertId: CONCERT_ID
    });

    const headers = { 'Content-Type': 'application/json' };

    let response = http.post(API_URL, payload, { headers: headers });

    sleep(1);
}
```

<img width="853" alt="스크린샷 2024-08-22 오후 1 48 46" src="https://github.com/user-attachments/assets/e904c8b1-8902-409f-96bb-57225fcaaf6e"> <br>

### Stress Test (스트레스 테스트)
1. 2분 동안 Vus=500 <br>
2. 2분 동안 Vus=750 <br>  
3. 2분 동안 Vus=1000 <br>
4. 2분 동안 종료 <br>

```
import http from 'k6/http';
import { sleep } from 'k6';

// 환경변수로 설정된 값을 사용합니다
const API_URL = __ENV.API_URL || 'http://localhost:8080/token/add';
const CONCERT_ID = 1;

function generateUserId() {
    return 'user_' + Math.floor(Math.random() * 100000);
}
export const options = {
    stages: [
        { duration: "2m", target: 500 },
        { duration: "2m", target: 750 },
        { duration: "2m", target: 1000 },
        { duration: "2m", target: 0}
    ],
};

export default function () {
    const payload = JSON.stringify({
        userId: generateUserId(),
        concertId: CONCERT_ID
    });

    const headers = { 'Content-Type': 'application/json' };

    let response = http.post(API_URL, payload, { headers: headers });

    sleep(1);
}
```

<img width="830" alt="스크린샷 2024-08-22 오후 12 06 16" src="https://github.com/user-attachments/assets/1bf3d17e-8b6c-4cbb-a9b7-c75f64d6b6b5"> <br>

<br><br>

### Peak Load Test (최고 부하 테스트)
1. 2분 동안 500명 유저로 증가 <br>
2. 5분 동안 1500명 유저로 최고 부하 테스트 <br>
3. 2분 동안 다시 500명 유저로 감소 <br>
```
import http from 'k6/http';
import { sleep } from 'k6';

// 환경변수로 설정된 값을 사용합니다
const API_URL = __ENV.API_URL || 'http://localhost:8080/token/add';
const CONCERT_ID = 1;

function generateUserId() {
    return 'user_' + Math.floor(Math.random() * 100000);
}
export const options = {
    stages: [
        { duration: "2m", target: 500 },
        { duration: "5m", target: 1500 },
        { duration: "2m", target: 500 }
    ],
};

export default function () {
    const payload = JSON.stringify({
        userId: generateUserId(),
        concertId: CONCERT_ID
    });

    const headers = { 'Content-Type': 'application/json' };

    let response = http.post(API_URL, payload, { headers: headers });

    sleep(1);
}
```

<img width="868" alt="스크린샷 2024-08-22 오후 2 18 56" src="https://github.com/user-attachments/assets/09e8b86f-749a-4b06-ada2-ef7f54117de4"> <br>

<br><br><br>

</details>

## STEP 20
<details>
  <summary>장애 발생과 대응 시나리오</summary>

  ### 1. 장애 감지
  HealthCheck와 같은 모니터링 도구를 통하여 시스템지표, 비지니스 지표, 외부 연동 시스템 지표등 이상 현상을 감지하고 <br>
  이상 현상 감지 시,MSA 구조에 맞게 각 담당자에게 알람 전달을 전달합니다. <br><br>

  ### 2. 장애 분류 및 우선순위 설정
  장애의 종류와 심각도를 파악하고 장애의 영향 범위와 비지니스에 미치는 영향을 기반으로 우선순위를 설정합니다. <br><br>

  ### 3. 초기 대응 및 원인 파악
  임시 조치로 서버 재시작, 트래픽 차단 등의 조치를 취하고, 저장된 Log와 모니터링 데이터를 분석하여 원인을 파악하고 <br>
  문제와 대응과정을 기록합니다. <br><br>
  
  ### 4. 문제 해결
  원인 분석 결과를 토대로 문제를 해결하기 위한 조치를 취하고 TDD를 통한 서비스의 정상 작동 유무를 테스트합니다. <br><br>

  ### 5. 복구 및 서비스 재개
  문제 해결 후 서비스를 정상 상태로 복구 하고, 시스템이 정상적으로 작동하는지 지속적으로 모니터링합니다. <br><br>

  ### 6. 사후 분석 및 개선
  장애 발생 원인과 대응 과정을 분석하는 회의를 진행하고 분석 결과를 토대로 시스템의 취약점을 보완하고 <br>
  예방 조치를 수립합니다. <br><br>

  ### 7. 문서화 및 보고
  위의 모든 과정을 포함한 장애 보고서를 작성하고 관련된 사람들과 필요한 정보를 공유합니다.
</details>

합격 뱃지 <br>
<a href="https://hhpluscertificateofcompletion.oopy.io/">
  <img src="https://static.spartacodingclub.kr/hanghae99/plus/completion/badge_purple.svg" />
</a>

