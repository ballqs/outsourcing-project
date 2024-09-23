package org.sparta.outsourcingproject.domain.menu.repository;

import org.sparta.outsourcingproject.domain.menu.entity.Menu;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    
    // unique 값인 메뉴명의 존재 유무
    boolean existsByName(String name);
    Optional<Menu> findByIdAndStoreId(Long menuId, Long storeId);
}
