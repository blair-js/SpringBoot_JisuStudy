package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    //0520 삭제
    //private final MemberRepository memberRepository = new MemoryMemberRepository();

    //0520 추가(위 memberRepository 객체 생성 방식 변경)
    //memberRepository 객체를 사용하려면 외부에서 해당 MemberService 클래스 객체를 생성할 때
    //매개변수로 memberRepository 객체를 넣어준다. => 매개변수로 들어온 객체 memberRepository가 memberRepository의 필드값에 대입된다.
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    //회원 가입
    public Long join(Member member){

        //같은 이름이 있는 중복회원은 가입이 안된다는 가정을 설정하여 이름 중복확인

        /*Optional<Member> result = memberRepository.findByName(member.getName());
        result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });*/

        //리팩토링을 통해 위(18~21행) 코드의 일을 하는 로직을 별도로 뺐다.
        validateDuplicateMember(member); //중복 회원 검증

        //위 validateDuplicateMember 메소드를 통해 중복회원이 없는 경우에만 아래 2줄의 코드 실행
        //왜냐면 validateDuplicateMember 메소드에서 중복회원이 있는 경우 예외를 발생시키므로 아래 2줄의 코드가 실행되지 않기 떄문문
        memberRepository.save(member);
        return member.getId();

    }

    //이름 중복확인
    private void validateDuplicateMember(Member member) {

        //위(18~21행)의 코드를 줄여서 아래와 같이 작성.(반환 값 생략하고 바로 메소드 체이닝)
        memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });
    }

    //전체 회원 조회
    public List<Member> findMembers(){
       return memberRepository.findAll();
    }

    //특정 회원 조회
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

}
