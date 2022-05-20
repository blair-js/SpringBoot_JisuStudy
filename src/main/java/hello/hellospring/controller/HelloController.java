package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data", "hello!");
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value="name") String name, Model model){

        model.addAttribute("name", name);
        return "hello-template";

    }

    @GetMapping("hello-string")
    @ResponseBody //http에서 응답 Body부에 return 하는 데이터를 직접 넣어주겠다.
    public String helloString(@RequestParam("name") String name){
        return "hello " + name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    //밑에 생성해놓은 내부 클래스 Hello의 객체를 반환받는 메소드 생성
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name); //파라미터로 넘어온 name을 넣어 Hello의 필드값을 셋팅해준다.
        return hello; //hello 객체를 넘긴다.
    }

    //내부 클래스 생성(해당 클래스는 HelloController.Hello 로 접근이 가능하다.)
    static class Hello{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

}
