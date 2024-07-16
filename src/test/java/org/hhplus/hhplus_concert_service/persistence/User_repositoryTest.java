package org.hhplus.hhplus_concert_service.persistence;

import org.hhplus.hhplus_concert_service.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class User_repositoryTest {

    private static final Logger log = LoggerFactory.getLogger(User_repositoryTest.class);

    @Autowired
    User_repository userRepository;

    @Test
    @DisplayName("데이트 입력 테스트")
    void insertDataTest() {
        User user = new User();

        user.setUserId("유저1");

        userRepository.save(user);
    }

    @Test
    @DisplayName("고객 식별")
    void findByUserId() {
        User user =  userRepository.findByUserId("유저1");
    }
}