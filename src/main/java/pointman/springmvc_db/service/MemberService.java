package pointman.springmvc_db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pointman.springmvc_db.domain.Member;
import pointman.springmvc_db.reposirory.MemberRepository;
import pointman.springmvc_db.reposirory.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

@Transactional //JPA를 사용하려면 반드시 필요하다
public class MemberService {
    private  final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public Long join(Member member){
        //같은 이름이 있는 중복회원은 가입 안된다(요구사항)
//  이런방식으로 하지말고 AOP를 사용하여 속도를 체크해보자
//        long start = System.currentTimeMillis();
//        try {
//            validateDuplicateMember(member); //회원 검증
//            memberRepository.save(member);
//            return member.getId();
//
//        }finally {
//            long finish = System.currentTimeMillis();
//            long timeMs = finish-start;
//            System.out.println("join = " + timeMs + "ms");
//        }

        validateDuplicateMember(member); //회원 검증
        memberRepository.save(member);
        return member.getId();

    }

    /**
     * 전체 회원조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();

    }
    /**
     * 회원 ID조회
     */
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                        .ifPresent(m->{
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });
    }
}
