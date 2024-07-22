package Itstime.planear.feed.service;

import Itstime.planear.exception.PlanearException;
import Itstime.planear.feed.dto.AchievementRateResponseDto;
import Itstime.planear.feed.dto.FeedItemUrlProcessDto;
import Itstime.planear.feed.dto.FeedResponse;
import Itstime.planear.feed.dto.FeedStatusMessageResponse;
import Itstime.planear.feed.dto.FeedsResponse;
import Itstime.planear.friend.domain.Friend;
import Itstime.planear.friend.domain.FriendRepository;
import Itstime.planear.member.domain.Member;
import Itstime.planear.member.domain.MemberRepository;
import Itstime.planear.schedule.Domain.Schedule;
import Itstime.planear.schedule.Domain.ScheduleRepository;
import Itstime.planear.shop.domain.Item;
import Itstime.planear.shop.domain.Wearing;
import Itstime.planear.shop.repository.ItemRepository;
import Itstime.planear.shop.repository.WearingRepsitory;
import Itstime.planear.statusmessage.domain.MessageType;
import Itstime.planear.statusmessage.domain.StatusMessage;
import Itstime.planear.statusmessage.domain.StatusMessageRepository;
import Itstime.planear.statusmessage.service.StatusMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    private final ScheduleRepository scheduleRepository;

    public FeedsResponse getFeed(Long memberId) {
        List<Long> allFriendIds = allFriendIds(memberId);
        Map<Long, String> idToNickname = getIdToNickname(allFriendIds);
        List<StatusMessage> statusMessages = statusMessageRepository.findAllByMemberIdInAndCreatedAtBetweenOrderByIdDesc(allFriendIds, LocalDateTime.now().minusDays(1), LocalDateTime.now());
        List<Wearing> wearings = wearingRepsitory.findAllByMemberIdIn(allFriendIds);
        Map<Long, FeedItemUrlProcessDto> itemIdToUrl = itemRepository.findAllById(wearings.stream().map(Wearing::getItemId).toList())
                .stream()
                .collect(Collectors.toMap(
                        Item::getId,
                        item -> new FeedItemUrlProcessDto(
                                item.getImg_url_shop(),
                                item.getImg_url_avatar1(),
                                item.getImg_url_avatar2())
                ));
        Map<Long, List<Wearing>> memberIdToWearings = wearings.stream()
                .collect(groupingBy(Wearing::getMemberId, toList()));
        return new FeedsResponse(
                statusMessages.stream()
                        .map(statusMessage -> {
                            MessageType messageType = statusMessage.getMessageType();
                            return new FeedResponse(
                                    idToNickname.get(statusMessage.getMemberId()),
                                    memberIdToWearings.getOrDefault(statusMessage.getMemberId(), Collections.emptyList())
                                            .stream()
                                            .map(it -> toFeedWearingResponse(it, itemIdToUrl))
                                            .toList(),
                                    FeedStatusMessageResponse.from(statusMessageService.getStatusResponse(memberId, messageType)),
                                    statusMessage.getCreatedAt()
                            );
                        })
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

    private FeedResponse.FeedWearingResponse toFeedWearingResponse(Wearing it, Map<Long, FeedItemUrlProcessDto> itemIdToUrl) {
        FeedItemUrlProcessDto dto = itemIdToUrl.get(it.getItemId());
        return new FeedResponse.FeedWearingResponse(
                it.getItemId(),
                dto.url_shop(),
                dto.url_avatar1(),
                dto.url_avatar2(),
                it.getBodyPart()
        );
    }

    public AchievementRateResponseDto achievementRate(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.NOT_FOUND));
        String myNickname = member.getMemberName().getName();
        // 나의 Wearing Items 조회
        // TODO (toFeedWearingResponse 메소드를 활용해서 리팩토링 가능)
        List<AchievementRateResponseDto.AchievementRateItemUrlProcessDto> myWearingItemList =
                wearingRepsitory.findByMemberId(memberId).stream().map(
                        wearing -> new AchievementRateResponseDto.AchievementRateItemUrlProcessDto(
                                wearing.getId(),
                                wearing.getItem().getImg_url_shop(),
                                wearing.getItem().getImg_url_avatar1(),
                                wearing.getItem().getImg_url_avatar2(),
                                wearing.getBodyPart())).toList();
        // 나의 오늘 일정 조회
        LocalDate today = LocalDate.now();
        List<Schedule> todaySchedules = scheduleRepository.findAllByMemberAndStartBetween(member, today, today);

        // 나의 오늘 전체 일정 수와 달성률 계산
        int todayScheduleCount = todaySchedules.size();
        int completedSchedules = (int) todaySchedules.stream().filter(Schedule::isCompletion).count();
        int achievementRate = (int) Math.ceil(todayScheduleCount > 0 ? ((double) completedSchedules / todayScheduleCount) * 100 : 0);

        // 친구 정보
        List<Long> allFriendIds = allFriendIds(memberId);
        Map<Long, String> idToNickname = getIdToNickname(allFriendIds);
        // N+1 해결을 위해 모든 친구의 Wearing Items 한번에 조회
        Map<Long, List<Wearing>> friendWearingItemMap = getAllFriendsWearingItems(allFriendIds);
        // N+1 해결을 위해 모든 친구의 오늘 Schedule 한번에 조회
        Map<Long, List<Schedule>> friendsTodaySchedulesMap = getAllFriendsTodaySchedules(allFriendIds, today);

        List<AchievementRateResponseDto.FriendInfo> friendsInfos = allFriendIds.stream()
                .map(friendId -> {
                    List<AchievementRateResponseDto.AchievementRateItemUrlProcessDto> friendWearingItems = friendWearingItemMap.getOrDefault(friendId, Collections.emptyList())
                            .stream()
                            .map(wearing -> new AchievementRateResponseDto.AchievementRateItemUrlProcessDto(
                                    wearing.getId(),
                                    wearing.getItem().getImg_url_shop(),
                                    wearing.getItem().getImg_url_avatar1(),
                                    wearing.getItem().getImg_url_avatar2(),
                                    wearing.getBodyPart()))
                            .toList();
                    List<Schedule> friendTodaySchedules = friendsTodaySchedulesMap.getOrDefault(
                            friendId, Collections.emptyList());
                    int friendTodayScheduleCount = friendTodaySchedules.size();
                    int friendCompletedSchedules = (int) friendTodaySchedules.stream().filter(Schedule::isCompletion).count();
                    int friendAchievementRate = (int) Math.floor(friendTodayScheduleCount > 0 ? ((double) friendCompletedSchedules / friendTodayScheduleCount) * 100 : 0);

                    return new AchievementRateResponseDto.FriendInfo(
                            idToNickname.get(friendId),
                            friendWearingItems,
                            friendAchievementRate,
                            friendTodayScheduleCount
                    );
                })
                .toList();
        return new AchievementRateResponseDto(
                myNickname,
                myWearingItemList,
                achievementRate,
                todayScheduleCount,
                friendsInfos
        );
    }

    private Map<Long, List<Wearing>> getAllFriendsWearingItems(List<Long> allFriendIds) {
        List<Wearing> wearings = wearingRepsitory.findByMemberIds(allFriendIds);
        return wearings.stream()
                .collect(Collectors.groupingBy(wearing -> wearing.getMember().getId()));
    }

    private Map<Long, List<Schedule>> getAllFriendsTodaySchedules(List<Long> allFriendIds, LocalDate today) {
        List<Schedule> schedules = scheduleRepository.findAllByMemberIdsAndStartBetween(allFriendIds, today, today);
        return schedules.stream()
                .collect(Collectors.groupingBy(schedule -> schedule.getMember().getId()));
    }
}
