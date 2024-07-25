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
```
package org.hhplus.hhplus_concert_service.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Data
@Table(name = "concertSeat")
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class ConcertSeat {
    @Id
    @NotNull(message = "seatId cannot be empty.")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seatId;
    private int itemId;
    private int seatNum;
    private String status;
    private int seatPrice;

    //낙관적 락 사용
    @Version
    private int version;
```



```
package org.hhplus.hhplus_concert_service.persistence;

import jakarta.persistence.LockModeType;
import org.hhplus.hhplus_concert_service.domain.ConcertSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ConcertSeatRepository extends JpaRepository<ConcertSeat, Integer> {

    //특정 콘서트 좌석 조회
    List<ConcertSeat> findAllByItemId(int itemId);

    //특정 좌석 조회
    @Lock(LockModeType.OPTIMISTIC)
    ConcertSeat findBySeatId(int seatId);


    //비관적 락 사용을 위한 특정 좌석 조회
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM ConcertSeat s WHERE s.seatId = :seatId")
    ConcertSeat lockSeatById(@Param("seatId") int seatId);
}
```




```
@Override
    @Transactional
    public void reservation(String userId, int concertId, int itemId, int seatId, int totalPrice, String status) {

        int retryCount = 5;

        while (retryCount > 0) {
            Concert concert = concertRepository.findByConcertId(concertId);
            //낙관적 락을 사용
            ConcertSeat concertSeat = concertSeatRepository.findBySeatId(seatId);

            concert.isAvailable();

            try {
                if (concert == null) {
                    throw new NoSuchElementException();
                }
                if (!ReservationConstants.CONCERT_AVAILABLE.equals(concert.getStatus())) {
                    throw new IllegalStateException();
                }

                if (concertSeat == null) {
                    throw new NoSuchElementException();
                }
                if (!ReservationConstants.SEAT_AVAILABLE.equals(concertSeat.getStatus())) {
                    throw new IllegalStateException();
                }

                //비관적락 사용 위한 select문
                concertSeatRepository.lockSeatById(seatId);

                Reservation reservation = new Reservation();

                reservation.setUserId(userId);
                reservation.setConcertId(concertId);
                reservation.setSeatId(seatId);
                reservation.setItemId(itemId);
                reservation.setTotalPrice(totalPrice);
                reservation.setStatus("T");

                reservationRepository.save(reservation);

                concertSeat.setStatus("N");

                concertSeatRepository.save(concertSeat);
                break;


            } catch (ObjectOptimisticLockingFailureException e) {
                //낙관적 락 캐쉬 초기화
                entityManager.clear();
                retryCount --;
                if(retryCount == 0) {
                    throw new RuntimeException("This seat is reserved");
                }
            } catch (PessimisticLockException | LockTimeoutException e) {
                throw new RuntimeException("This seat is reserved");
            }
        }
    }
```

#### Seat 조회에 낙관적 락을 건 이유
제가 Seat 조회 코드에  읽기 작업에서 성능을 보장하면서 데이터의 충돌 가능성을 낮추면서 데이터를 읽어오는 과정을 성능을 보장하는 
적합한 락이 낙관적 락이라 생각하였습니다. 만일 충돌이 일어날 경우를 대비하여 5번의 Retry를 설정해 두었으며, 매 재시도 전에 1차 캐쉬를 clear하도록 설정하였습니다. 
5번 다 시도한 경우 RuntimeException을 이용하여 예외 처리되도록 설정하였습니다.

#### save 부분에 비관적 락을 건 이유 
예약정보를 저장하고 seat의 상태를 변경하는 과정에서 충돌이 많을 것이라 생각하였습니다. 
특정 좌석에 대한 예약이 이루어지는 동안 다른 트랜잭션이 해당 좌석에 접근하지 못하도록 보장하고
좌석 예약과 관련된 중요한 상태 변경이 동시에 발생하지 않도록 방지하기 위해 비관적 락을 사용하였습니다.
ObjectOptimisticLockingFailureException 이용하여 예외를 처리를 하였습니다.

## 2. Point Service
```
package org.hhplus.hhplus_concert_service.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Data
@Table(name = "Point")
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Point {
    @Id
    @NotNull(message = "pointId cannot be empty.")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int pointId;
    private String userId;
    private int point;

    @Version
    private int version;
}

```



```
package org.hhplus.hhplus_concert_service.persistence;

import jakarta.persistence.LockModeType;
import org.hhplus.hhplus_concert_service.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;


public interface PointRepository extends JpaRepository<Point, Integer> {

    //유저 포인트 조회
    @Lock(LockModeType.OPTIMISTIC)
    Point findFirstByUserIdOrderByPointIdDesc(String userId);
}
```



```
  @Override
    public void plusPoint(String userId, int chargePoint) {

        Point point = pointRepository.findFirstByUserIdOrderByPointIdDesc(userId);

        try {
            Point newPoint = new Point();

            newPoint.setUserId(userId);
            newPoint.setPoint(point.getPoint() + chargePoint);

            pointRepository.save(newPoint);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new RuntimeException();
        }
    }

    //낙관적 락 적용
    @Override
    public void minusPoint(String userId, int totalPrice) {

        Point point = pointRepository.findFirstByUserIdOrderByPointIdDesc(userId);
        TokenQueue tokenQueue = tokenQueueRepository.findByUserId(userId);

        int holdPoint = point.getPoint();
        String status = tokenQueue.getStatus();

        try {
            if(!TokenConstants.STATUS_IN_PROGRESS.equals(status)) {
                throw new RuntimeException();
            } else {
                if(holdPoint < totalPrice) {
                    throw  new RuntimeException();
                } else {
                    Point newPoint = new Point();
                    newPoint.setUserId(userId);
                    newPoint.setPoint(point.getPoint() - totalPrice);

                    pointRepository.save(newPoint);
                }
            }
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new RuntimeException();
        }
    }
```

#### 포인트 충전/차감 부분에 낙관적 락을 건 이유
포인트 충전/차감 부분에서 잠금 메커니즘은 사용자의 의도치 않은 반복 클릭으로 인한 중복 거래를 방지하고, 시스템의 데이터 무결성을 유지하고 신뢰 있는 거래 시스템을 구축 하기 위해 필요하다고 생각했습니다. 
한 트랙잭션에 다수의 유저가 요청을 하는 경우가 아니고 주로 읽기 작업의 비중이 더 높다 생각하였기에 비관적 락보단 낙관적 락이 성능상 유리하다 생각하였습니다.
ObjectOptimisticLockingFailureException 이용하여 예외를 처리하였고, 롤백하고 재시도하는 방식으로 데이터의 일관성을 유지하였습니다.

</details>

## STEP12
- DB Lock 을 활용한 동시성 제어 방식 에서 해당 비즈니스 로직에서 적합하다고 판단하여 차용한 동시성 제어 방식을 구현하여 비즈니스 로직에 적용하고, 통합테스트 등으로 이를 검증하는 코드 작성 및 제출

