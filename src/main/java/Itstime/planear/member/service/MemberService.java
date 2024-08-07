package Itstime.planear.member.service;

import Itstime.planear.common.ApiResponse;
import Itstime.planear.exception.PlanearException;
import Itstime.planear.member.domain.Member;
import Itstime.planear.member.domain.MemberName;
import Itstime.planear.member.domain.MemberRepository;
import Itstime.planear.member.dto.*;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private static final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public ApiResponse<CheckMemberNameResponse> checkMemberName(CheckMemberNameRequest request) {
        String memberName = request.name();
        checkMemberName(memberName);
        ApiResponse<CheckMemberNameResponse> response = ApiResponse.success(new CheckMemberNameResponse("success"));
        return response;
    }

    public ApiResponse<CreateMemberResponse> createMember(CreateMemberRequest request) {
        checkMemberName(request.name());
        Member member = new Member(request.name(),makeMemberCode());
        memberRepository.save(member);
        return ApiResponse.success(new CreateMemberResponse(member.getMemberName().getName(), member.getId(),member.getMemberCode()));
    }

    private void checkMemberName(String memberName) {
        Member member = memberRepository.findByMemberName(new MemberName(memberName));
        if (member != null) {
            throw new PlanearException("이름이 중복되었습니다", HttpStatus.BAD_REQUEST);
        }
    }
    // 멤버코드 생성
    public String makeMemberCode() {
        SecureRandom rm = new SecureRandom();
        StringBuilder sb = new StringBuilder(7);
        for(int i=0; i<7; i++) {
            int index = rm.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }
    public ApiResponse<FindMemberResponse> findMember(String name){
        Member member = memberRepository.findByMemberName(new MemberName(name));
        if(member == null){
            throw new PlanearException("존재하지 않는 이름입니다.", HttpStatus.BAD_REQUEST);
        }
        return ApiResponse.success(new FindMemberResponse(member.getId(), member.getMemberName().getName()));
    }
    
    public ApiResponse<FindMemberCodeResponse> findMemberCode(String name) {
        Member member = memberRepository.findByMemberName(new MemberName(name));
        if(member == null) {
            throw new PlanearException("존재하지 않는 이름입니다.",HttpStatus.BAD_REQUEST);
        }
        return ApiResponse.success(new FindMemberCodeResponse(member.getMemberCode()));
    }
}
