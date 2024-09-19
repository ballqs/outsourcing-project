package org.sparta.outsourcingproject.domain.store.repository;

import org.sparta.outsourcingproject.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findByUserId(Long userId); // 해당 유저가 갖고 있는 모든 등록된 가게들 조회

}
