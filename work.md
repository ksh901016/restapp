### 프로젝트 준비
1. pom.xml 설정<br>
스프링을 손쉽게 테스트할 수 있도록 spring-test를 추가한다.<br>
스프링은 기본적으로 JCL을 사용하는데, 이 프로젝트는 SLF4J를 사용하므로 commons-logging 라이브러리를 exclusions 시킨다.
<br>
<br>
**library 설정**<br>
spring-webmvc(core, beans, aop, context), slf4j, junit 추가<br>
Servlet API도 추가한다. 실제 실행할 때는 서블릿 컨테이너가 Servlet API를 제공하지만, 컴파일할 때 라이브러리가 필요하므로 추가해야 한다. (의존범위 : provided)<br>
**plugin 설정**<br>
compile, resource, surefire<br>



