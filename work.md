### 프로젝트 준비
####pom.xml 설정<br>
스프링을 손쉽게 테스트할 수 있도록 spring-test를 추가한다.<br>
스프링은 기본적으로 JCL을 사용하는데, 이 프로젝트는 SLF4J를 사용하므로 commons-logging 라이브러리를 exclusions 시킨다.
<br>
<br>

**library 설정**<br>
spring-webmvc(core, beans, aop, context), slf4j, junit 추가<br>
Servlet API도 추가한다. 실제 실행할 때는 서블릿 컨테이너가 Servlet API를 제공하지만,<br> 
컴파일할 때 라이브러리가 필요하므로 추가해야 한다. (의존범위 : provided)<br>
**plugin 설정**<br>
compile, resource, surefire<br>

#### 로그 설정하기
logback.xml , logback-test.xml 생성

#### web.xml 설정하기
> DispatcherServlet 설정하기
* 모든 요청(/*)은 DispatcherSerlvet이 처리하도록 한다
* DispatcherServlet은 contextClass라는 초기화 파라미터로 지정 한 값을 가지고 설정파일을 처리한다.(자바 클래스를 이용한 설정 파일을 사용하므로 AnnotationConfigWebApplicationContext라는 값을 설정)
* contextConfigLocation으로 위치 지정
> ContextLoaderListener 설정하기
* DispatcherServlet만으로도 웹 어플리케이션을 충분히 개발할 수 있으나, 웹 페이지와 Rest API를 한 곳에서 같이 서비스 해야한다면 구조적 모습에서 별로 좋지 않다.
* 2개의 DispatcherServlet을 생성한다. (두 개의 독립적인 컨텍스트가 생성)
* 두 컨텍스트는 독립적이므로 서로의 빈(bean)을 참조할 수 없다. 동시에 사용하는 공통 빈이 있는 경우 ContextLoaderListener를 사용한다.
* ContextLoaderListener는 ApplicationContext를 생성하는데, ApplicationContext는 DispatcherServlet이 생성하는 WebApplicationContext의 부모 컨텍스트가 된다. 다시 말해 DispatcherServlet이 생성하는 WebApplicationContext는 ContextLoaderListener가 생성하는 ApplicationContext를 부모로 사용하는 자식 컨텍스트다.
* 자식 컨텍스트들은 부모 컨텍스트가 제공하는 빈을 사용할 수 있기 때문에 ContextLoaderListener를 사용하여 공통 빈을 설정할 수 있다.<br>

**일반적으로 데이터 처리와 비즈니스 로직을 처리하는 Model과 관련된 빈들은 ContextLoaderListener를 이용해서 생성하게 하고, Controller와 View에 관련된 빈들은 DispatcherServlet에서 생성하게 한다.**

### JavaConfig 설정하기
Spring JavaConfig의 장점
* 주입(Injection), 상속, 다형성 등의 기능을 수행할 수 있다.
* 빈에 대한 생성과 초기화에 대한 모든 권한을 가진다.
* IDE같은 개발 도구의 도움 없이도 손쉽게 리팩토링 가능
* 컨테이너 초기화 시 큰 비용이 드는 클래스 패스 스캐닝(Classpath Scanning)을 줄일 수 있다.
* 필요에 따라 XML 또는 Property 등을 사용할 수 있다.


