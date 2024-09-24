package org.sparta.outsourcingproject.domain.dibs.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.outsourcingproject.common.entity.Timestamped;

@Entity
@Table(name = "dibs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dibs extends Timestamped {

    @EmbeddedId
    private DibsId id;

    // 찜 생성
    public static Dibs CreateDibs(Long storeId, Long userId) {
        DibsId dibsId = new DibsId(userId, storeId);
        Dibs dibs = new Dibs();
        dibs.id = dibsId;
        return dibs;
    }
}


