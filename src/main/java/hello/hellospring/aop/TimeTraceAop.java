package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class TimeTraceAop {

    //적용할 곳(지점)을 명시해주어야 한다.
    //hello 하위의 hellosprin 하위의 모든 메소드에 적용하겠다는 의미이다.
    @Around("execution(* hello.hellospring..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{

        //메소드의 수행 시간을 측정하는 로직 구현
        long start = System.currentTimeMillis();

        //toString() : 어떤메소드를 호출하는지 콘솔에 찍어볼 수 있다.
        System.out.println("START : " + joinPoint.toString());

        try{
            return joinPoint.proceed();
        }finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END : " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }


}
