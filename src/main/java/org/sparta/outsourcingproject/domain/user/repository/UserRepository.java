package org.sparta.outsourcingproject.domain.user.repository;


import org.sparta.outsourcingproject.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByIdAndStatusTrue(Long userId);

    Optional<User> findByIdAndStatusTrue(Long userId);
}