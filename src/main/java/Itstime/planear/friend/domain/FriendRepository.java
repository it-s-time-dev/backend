package Itstime.planear.friend.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findAllByMemberId(Long memberId);
    boolean existsByMemberIdAndFriendMemberId(Long memberId, Long friendMemberId);
}
