package hello.hellospring.repository;

import hello.hellospring.domain.Member;
//import org.junit.jupiter.api.Assertions;
import static org.assertj.core.api.Assertions.*;
//import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    //하나의 메서드가 끝날 때마다 이 메서드 afterEach()가 동작을 한다.
    //"콜백 메소드"의 느낌이다. => 즉, save() ->  afterEach() -> findByName() -> afterEach() ...
    //이런 식으로 하나의 단위테스트가 끝날 때마다 수행되는 메소드를 말한다.
    @AfterEach
    public void afterEach(){

        //MemoryMemberRepository 클래스에 구현해놓은 clearStore() 메소드를 호출한다.
        //해당 메소드 clearStore() 에는
        //저장된 데이터를 모두 지워주는 로직이 구현되어 있다. => 임시 데이터 저장소 store를 clear() 해주고 있음.
        //즉, 이 테스트 클래스에서 하나의 테스트가 끝나면 계속 데이터 저장소를 비워주는 역할을 하는 것이다.
        repository.clearStore();

    }

    @Test
    public void save(){
        //member 저장 기능 테스트

        Member member = new Member();//member 객체 생성
        member.setName("spring"); //이름 set
        repository.save(member); //이룸 set된 member 객체 저장 save()
                                 //=> Member 클래스에서 save 메소드 내부의 member.setId(++sequence); 를 통해 id는 자동 생성+저장(set)
        Member result = repository.findById(member.getId()).get(); //잘 저장되었는지 해당 member 객체의 id를 기준으로 조회 findById()

        //내가 생성하여 저장한 memeber 객체와
        //데이터 Map에서 저장한 그 객체의 id를 기준으로 조회해와서 두 객체가 동일한 지 비교

        //결과 확인 방법1 : 단순 확인
        //console 출력 결과 : result = true
        //System.out.println("result = " + (result == member));

        //결과 확인 방법2 : Assertions.assertEquals() 사용 => import org.junit.jupiter.api.Assertions;
        //assertEquals() : 전달된 두 Object가 일치하는지 확인
        //매개변수는 (기대값, 실제결과) 순서 이다.
        //console 출력 결과 : 별도로 표시되지는 않고 두 Object가 일치한다면 테스트 결과가 passed이다.
        //만약, 두 Object가 일치하지 않으면 테스트 결과가 failed 이고, 아래와 같이 기대값과 실제값이 console에 출력된다.
        //Expected :hello.hellospring.domain.Member@7ee8290b
        //Actual   :null
        //Assertions.assertEquals(member, result);

        //결과 확인 방법3 : Assertions.assertThat() 사용 => import org.assertj.core.api.Assertions; or import static org.assertj.core.api.Assertions.*;
        //member가 result랑 동일하다면 테스트 결과가 passed이다.
        assertThat(member).isEqualTo(result);

    }

    @Test
    public void findByName(){

        //먼저 이름이 다른 회원 2명을 등록(저장)한다.
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();
        assertThat(result).isEqualTo(member1); //passed

        /*Member result = repository.findByName("spring2").get();
        assertThat(result).isEqualTo(member1); //failed*/

    }

    @Test
    public void findAll(){

        //먼저 이름이 다른 회원 2명을 등록(저장)한다.
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        //저장된 모든 회원 조회
        List<Member> result = repository.findAll();

        //결과의 사이즈(갯수)가 2랑 동일한가? passed
        assertThat(result.size()).isEqualTo(2);
        //결과의 사이즈(갯수)가 4랑 동일한가? failed
        //expected: 4
        //but was: 2
        //assertThat(result.size()).isEqualTo(4);

    }
}