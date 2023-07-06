package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    //회원 가입
    @Transactional
    public Long join(Member member){
        ValidateDuplicateMember(member); // 중복회원 검증
        memberRepository.save(member);

        return member.getId();
    }

    private void ValidateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 가입하셨습니다.");
        }

    }
    //회원 전체 조회
    public List<Member> findMembers () {
        return memberRepository.findAll();
    }

    //회원 단건 조회
    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }
}
