package org.sparta.outsourcingproject.domain.store.repository;

import org.sparta.outsourcingproject.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {

    // 해당 유저가 갖고 있는 모든 영업 중(OPERATION)인 등록된 가게들 조회(등록되어 있지만 SHUTDOWN(폐업)한 가게는 제외)
    @Query("SELECT s " +
            "FROM Store s " +
            "WHERE s.user.id = :userId AND s.operationStatus = 'OPERATION'")
    List<Store> findByUserId(Long userId);

}
