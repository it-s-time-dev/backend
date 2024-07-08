package Itstime.planear.feed.service;

import Itstime.planear.feed.dto.FeedResponse;
import Itstime.planear.feed.dto.FeedStatusMessageResponse;
import Itstime.planear.feed.dto.FeedsResponse;
import Itstime.planear.friend.domain.Friend;
import Itstime.planear.friend.domain.FriendRepository;
import Itstime.planear.member.domain.Member;
import Itstime.planear.member.domain.MemberRepository;
import Itstime.planear.shop.domain.Item;
import Itstime.planear.shop.domain.Wearing;
import Itstime.planear.shop.repository.ItemRepository;
import Itstime.planear.shop.repository.WearingRepsitory;
import Itstime.planear.statusmessage.domain.StatusMessage;
import Itstime.planear.statusmessage.domain.StatusMessageRepository;
import Itstime.planear.statusmessage.service.StatusMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class FeedService {
    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;
    private final StatusMessageRepository statusMessageRepository;
    private final StatusMessageService statusMessageService;
    private final WearingRepsitory wearingRepsitory;
    private final ItemRepository itemRepository;

    public FeedsResponse getFeed(Long memberId) {
        List<Long> allFriendIds = allFriendIds(memberId);
        Map<Long, String> idToNickname = getIdToNickname(allFriendIds);
        List<StatusMessage> statusMessages = statusMessageRepository.findAllByMemberIdInAndCreatedAtBetweenOrderByIdDesc(allFriendIds, LocalDateTime.now().minusDays(1), LocalDateTime.now());
        List<Wearing> wearings = wearingRepsitory.findAllByMemberIdIn(allFriendIds);
        Map<Long, String> itemIdToUrl = itemRepository.findAllById(wearings.stream().map(Wearing::getItemId).toList())
                .stream()
                .collect(Collectors.toMap(Item::getId, Item::getImg_url));
        Map<Long, List<Wearing>> memberIdToWearings = wearings.stream()
                .collect(groupingBy(Wearing::getMemberId, toList()));
        return new FeedsResponse(
                statusMessages.stream()
                        .map(statusMessage -> new FeedResponse(
                                idToNickname.get(statusMessage.getMemberId()),
                                memberIdToWearings.getOrDefault(statusMessage.getMemberId(), Collections.emptyList())
                                        .stream()
                                        .map(it -> toFeedWearingResponse(it, itemIdToUrl))
                                        .toList(),
                                FeedStatusMessageResponse.from(statusMessageService.getStatusResponse(memberId, statusMessage))
                        ))
                        .toList()
        );
    }

    private Map<Long, String> getIdToNickname(List<Long> allFriendIds) {
        return memberRepository.findAllById(allFriendIds)
                .stream()
                .collect(Collectors.toMap(Member::getId, it -> it.getMemberName().getName()));
    }

    private List<Long> allFriendIds(Long memberId) {
        return friendRepository.findAllByMemberId(memberId)
                .stream()
                .map(Friend::getFriendMemberId)
                .toList();
    }

    private FeedResponse.FeedWearingResponse toFeedWearingResponse(Wearing it, Map<Long, String> itemIdToUrl) {
        return new FeedResponse.FeedWearingResponse(it.getItemId(), itemIdToUrl.get(it.getItemId()), it.getBodyPart());
    }
}
