package org.hhplus.hhplus_concert_service.persistence;

import org.hhplus.hhplus_concert_service.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface User_repository extends JpaRepository<User, String> {

    //고객 식별
    User findByUserId(String userId);
}
