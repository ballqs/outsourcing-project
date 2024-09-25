package org.sparta.outsourcingproject.domain.dibs.repository;

import org.sparta.outsourcingproject.domain.dibs.entity.Dibs;
import org.sparta.outsourcingproject.domain.dibs.entity.DibsId;
import org.sparta.outsourcingproject.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DibsRepository extends JpaRepository <Dibs, DibsId>{

    List<Dibs> findByIdUserId(Long userId);

    // DELETE FROM dibs WHERE store_id = ? AND user_id = ?;
    Optional<Dibs> deleteByIdStoreIdAndIdUserId(Long storeId, Long userId);

}
