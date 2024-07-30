package org.hhplus.hhplus_concert_service.persistence;

import org.hhplus.hhplus_concert_service.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, String> {

    //고객 식별
    User findByUserId(String userId);
}
