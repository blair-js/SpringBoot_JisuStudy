package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.hibernate.annotations.common.reflection.XMember;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    //JPA를 사용하기 위해 DB와 통신하는 객체 주입
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        //조회할 타입, 식별자(pk) 전달
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        //아래와 같은 문법을 jpql 이라고 한다.
        //from 뒤의 Member m은 Member as m 으로 보면 되고,
        //select 뒤의 m은 별칭으로 준 m을 의미하는데,
        //즉, Member 객체를 조회해온다는 의미이다. (즉 객체 타입의 별칭을 적어주게 되면 SQL에서 *의 역할을 하게 된다.)
        return em.createQuery("select m from Member m", Member.class)
                        .getResultList();
    }
}
