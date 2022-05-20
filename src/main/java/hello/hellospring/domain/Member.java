package hello.hellospring.domain;

public class Member {

    private Long id; //시스템이 자동으로 생성(저장)해주는 방식으로 진행
    private String name; //사용자가 직접 전달해주는 방식으로 진행

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
