package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    //이전 방식 : 매번 새로운 memberService 객체 생성
    //private final MemberService memberService = new MemberService();

    //생성자 의존 주입 방식 : 해당 클래스가 생성됨과 동시에 memberService 필드에 memberService 객체가 주입되고
    //그러한 객체의 관리를 Spring에게 위임하기 위해 @Autowired 어노테이션을 부여한다.
    //단, 주입하고자 하는 객체를 Spring이 찾을 수 있도록 MemberService 클래스에도 @Service라는 어노테이션이 부여되어 있어야 한다.
    //(Repository 또한 구현체에 @Repository 어노테이션 부여)

    //필드를 통한 의존성 주입
    //@Autowired
    private MemberService memberService;

    //Setter를 통한 의존성 주입
//    @Autowired
//    public void setMemberService(MemberService memberService){
//        this.memberService = memberService;
//    }

    //생성자를 통한 의존성 주입
    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    //회원 가입 양식 화면으로 전환해주는 메소드
    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    //회원 가입 기능을 수행하는 메소드
    @PostMapping("/members/new")
    public String create(MemberForm form){

        //member 객체 생성
        Member member = new Member();

        //데이터로 들어온 form 객체에서 name을 추출하여 member 객체에 set 해준다.
        //form 객체에는 name 이라는 클래스가 있고, 화면에서 name 이라는 이름으로 파라미터를 전달하기 때문에
        //form 객체의 name 필드에 그 값(파라미터)이 자동으로 바인딩 된다.
        member.setName(form.getName());

        //정보가 setting된 member 객체 가입
        memberService.join(member);

        return "redirect:/"; //회원가입 완료 후 root로 이동(매핑된 root에서 home 화면 호출 중)
    }

    //회원의 전체 목록을 조회하는 메소드
    @GetMapping("/members")
    public String list(Model model){

        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);

        return "members/memberList";
    }

}
