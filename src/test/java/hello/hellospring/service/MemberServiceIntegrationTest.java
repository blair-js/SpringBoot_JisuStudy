package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    @Test
    void 회원가입() {

        //given
        //이름이 hello인 member 객체 생성
        Member member = new Member();
        member.setName("spring");

        //when
        //member 객체가 정상적으로 저장되면 해당 member 객체의 id를 반환받음.
        Long saveId = memberService.join(member);

        //then
        //그럼 저장된 member 객체의 id(saveId)를 기준으로 다시 member를 조회해와서 객체가 일치하는지 확인
        //즉, 저장된 member와 조회된 member의 일치여부 확인
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());

    }

    //위의 테스트 코드는 정상적으로 진행되었을 때 passed가 된다.
    //하지만, 정상적으로 진행되지 않았을 때의 flow도 내가 설계한 로직대로 움직이는지 확인해야 하는 것이 더 중요하다.
    //우리는 중복되는 이름이 있는 경우 예외가 발생하도록 설정해놓았기 때문에 그 부분을 확인해야한다.
    @Test
    public void 중복_회원_예외(){

        //given
        //이름이 동일한 member 객체 2개 생성
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        //member2 를 저장하고자 할 때 IllegalStateException 예외가 발생하는지 확인한다.
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

/*

        try{
            memberService.join(member2); //여기서 예외가 발생해야 정상이다! => 예외가 발생하면 밑의 catch 구문으로 갈 것이고
            //fail("실패"); //예외가 발생하지 않으면(테스트가 잘 안되는 것) catch 구문으로 가지 못하고 해당 코드가 실행된다.
        }catch (IllegalStateException e){
            //예외 발생시 나오는 메세지가 "이미 존재하는 회원입니다." 랑 동일한 지 확인하는 코드
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다."); //passed
            //assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다. 1212121"); //failed
            //e.printStackTrace();
        }
*/

        //then

    }

}