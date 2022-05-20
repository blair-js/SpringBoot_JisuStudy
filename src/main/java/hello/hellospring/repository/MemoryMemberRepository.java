package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

//@Repository
public class MemoryMemberRepository implements MemberRepository{

    //현재 store 변수가 임시 저장소의 기능을 하는 것이다. (우리는 현재 DB를 사용하지 않고 진행하고 있기 때문이다.)
    //key를 식별자(id)의 데이터 타입으로 설정하고
    //value를 객체(Member)로 설정
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L; //시작 시퀀스를 0으로 설정

    @Override
    public Member save(Member member) {

        //시퀀스를 1증가 시켜 member 객체에 set 해준다.
        member.setId(++sequence);
        //해당 member 객체에 set 되어있는 id를 get 해온 후 key로 지정하고
        //value에는 해당 member 객체를 담는다.
        store.put(member.getId(), member);

        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        //store.get(id)의 결과가 null일 수도 있기 때문에
        //Optional 객체의 ofNullable() 메소드를 사용하여
        //null인 경우에도 전달이 될 수 있도록 해준다. => null 여부에 따른 처리는 클라이언트 측에서 해줄 수 있다.
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {

        //store에 있는 value들을 Stream의 요소로 변환한 후
        //모든 member 객체의 이름(name)을 검사한다.
        //filter메소드를 통해
        //파라미터로 들어온 이름(name)이 요소(member)의 이름(getName)과 같은 경우에만 필터링 된다.
        //즉, 파라미터로 들어온 이름과 같은 member 객체들만 남게 되는 것이다.
        //findAny() : 그 중에서 찾으면 그 값을 반환하고 없으면 Optional 객체에 null을 담아서 반환하는 것이다.
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();

    }

    @Override
    public List<Member> findAll() {
        //store에 존재하는 모든 값들(values)을 List 형태로 반환한다.
        //즉, 모든 member 객체들이 담긴 list가 반환되는 것이다.
        return new ArrayList<>(store.values());
    }

    //이 메소드는 Test 수행 시 데이터 저장소를 비워주기 위한 역할을 하는 메소드이다.
    public void clearStore(){

        //store 변수에 저장된 모든 데이터를 비운다.
        store.clear();

    }

}
