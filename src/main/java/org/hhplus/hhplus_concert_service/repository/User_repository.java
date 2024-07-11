package org.hhplus.hhplus_concert_service.repository;

import org.hhplus.hhplus_concert_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface User_repository extends JpaRepository<User, String> {

    //고객 식별
    User findByUserId(String userId);
}
