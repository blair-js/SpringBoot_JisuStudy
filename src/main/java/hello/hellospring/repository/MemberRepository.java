package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member); //회원이 저장소에 저장
    Optional<Member> findById(Long id); //회원의 정보를 id 기준으로 조회
    Optional<Member> findByName(String name); //회원의 정보를 name 기준으로조회
    List<Member> findAll(); //저장소에 저장된 모든 회원 조회

}
