package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    //JPQL : select m from Member m where m.name = ?
    //메소드명을 인터페이스 규칙을 따르면 스프링 데이터 JPA가 내 서비스 쿼리를 만들어준다. => 이미 제공하는 메소드가 아닌
    //즉, findByNameAndId(String name, Long id) 이런것도 가능하다는 의미이다.
    @Override
    Optional<Member> findByName(String name);

}
