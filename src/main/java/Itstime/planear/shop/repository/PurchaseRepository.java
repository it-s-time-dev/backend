package Itstime.planear.shop.repository;

import Itstime.planear.shop.domain.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    boolean existsByMemberIdAndItemId(Long memberId, Long itemId);
}
