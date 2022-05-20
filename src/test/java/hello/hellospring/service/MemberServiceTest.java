package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    //MemberService memberService = new MemberService();

    //여기서 야기되는 문제는 MemberService에서 생성하는 memberRepository 객체와
    //이 테스트 클래스에서 생성하는 memberRepository 객체가 다르다는 것이다. => new 키워드를 통해 생성하기 때문에 서로 다른 객체이다.
    //다행히도 MemberService에서 임시로 설정해놓은 DB 역할을 하는 Map이 static으로 되어있어서,
    //DB에 저장된 값을 테스트하는 것에 "현재는" 문제가 없지만, 이러한 방식은 나중에 오류를 범할 수 있다.
    //그래서 MemberService 와 이 테스트 클래스에서 같은 memberRepository 객체를 사용하도록 변경해야 한다.
    //MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    MemoryMemberRepository memberRepository;
    MemberService memberService;

    //모든 테스트 메소드 실행 "전"에 수행한다.
    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach(){

        //MemoryMemberRepository 클래스에 구현해놓은 clearStore() 메소드를 호출한다.
        //해당 메소드 clearStore() 에는
        //저장된 데이터를 모두 지워주는 로직이 구현되어 있다. => 임시 데이터 저장소 store를 clear() 해주고 있음.
        //즉, 이 테스트 클래스에서 하나의 테스트가 끝나면 계속 데이터 저장소를 비워주는 역할을 하는 것이다.
        memberRepository.clearStore();

    }

    @Test
    void 회원가입() {

        //given
        //이름이 hello인 member 객체 생성
        Member member = new Member();
        member.setName("hello");

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

    @Test
    @DisplayName("전체회원조회")
    void findMembers() {

    }

    @Test
    void findOne() {

    }
}