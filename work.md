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
<br>

### 애플리케이션 구조
Controller - Service - DAO
Controller - 사용자의 요청을 처리
Service - 비즈니스 로직
DAO - 데이터를 처리

Controller, View -> 표현 계층(Presentation Layer)
Service -> 비즈니스 계층(Business Layer)
DAO -> 영속성 계층(Persistence Layer)

### 데이터베이스 접속 설정

**JDBC DriverManager와 DataSource**

JDBC를 이용하여 데이터베이스에 연결하는 방법은 크게 두 가지가 있다.<br>
DriverManager를 사용하는 방법과 DataSource를 사용하는 방법이다.<br>
DriverManager - JDBC 드라이버에서 지원하는 기본 서비스 <br>
DataSource - JDBC2.0의 javax.sql 패키지에 포함(데이터베이스의 연결자원(Connection)을 만들거나 사용)<br>
<br>
DriverManager 사용<br>
JDBC 드라이버를 직접 이용해서 연결자원을 생성
```java
String driveClass = "oracle.jdbc.driver.OracleDriver";
String url = "jdbc.oracle.thin@somewhere:1524:orcl";
String username = "user";
String password = "password";

Class.forName("oracle.jdbc.driver.OracleDriver");
Connection conn = DriverManager.getConnection(url, username, password);
```
<br>
DataSource 사용<br>
DataSource를 사용하기 위해서는 우선 DataSource 인터페이스 구현체를 사용<br>
JDBC에서 제공하는 DataSource 구현체를 사용할 수 있고, DBCP, c3p0, BoneCP 같은 커넥션 풀링 라이브러리에서 제공해주는 DataSource 구현체를 사용할 수도 있다<br>
BoneCP를 사용한 예제<br>

```java
String driveClass = "oracle.jdbc.driver.OracleDriver";
String url = "jdbc.oracle.thin@somewhere:1524:orcl";
String username = "user";
String password = "password";

BoneCPDataSource ds = new BoneCPDataSource();
ds.setDriverClass(driveClass);
ds.setJdbcUrl(url);
ds.setUsername(username);
ds.setPassword(pasword);
Connection conn = ds.getConnection();
```
<br>
DatatSource 생성, 트랜잭션 관리자 생성, SqlSessionFacotry 생성

### AppConfig 설정에 DataSource 추가하기
### Mapper 구현하기
 Mapper.xml 구현 -> Mapper interface 생성 -> AppConfig 설정 파일에 Mapper 추가<br>
 
### 테스트케이스 작성 및 테스트
 Junit4를 이용해서 BookMapper 클래스의 테스트 케이스를 작성한다.<br>
 @RunWith(SpringJunit4ClassRunner.class) 어노테이션을 사용하여 스프링 설정한 Bean을 인젝션받을 수 있게 한다.<br>
 클래스를 스프링 컨텍스트에 적재하려면 @ContextConfiguration(class={AppConfig.class})와 같은 형태로 구성 클래스의 정보를 설정한다.<br>
 
### Business Layer 구축
업무로직을 담당하는 계층<br>
트랜잭션 처리를 비즈니스 계층에서 담당한다.<br>
비즈니스 계층에서 데이터베이스 연결(Connection)객체를 생성한 다음, 사용하려는 Mapper(DAO)를 호출하여 원하는 작업을 처리하도록 한다.<br>
정상완료되면 Commit, 에러가 발생하면 Rollback<br>

**트랜잭션 관리** <br>
스프링에서는 트랜잭션을 직접 처리하지 않고, 선언적으로 관리할 수 있다.<br>
1. 어노테이션을 이용한 트랜잭션 관리(Annotation Transaction Management)
2. 설정 정보를 이용한 트랜잭션 관리(Configurational Transaction Management)

<br>
자바 기반 구성의 스프링에서 트랜잭션관리는 @EnableTrnsactionManagerment를 추가해야한다.<br>
트랜잭션 관리자(PlatformTransactionManager)를 명시적으로 선언하기 위해서는 TransactionManagementConfigurer 인터페이스를 상속받아 annotationDrivenTransactionManager() 메소드를 구현해야 한다.
<br>
<br>
@Transactional 은 트랜잭션 매니저를 지정하지 않아도 XML 기반 세팅은 기본으로 transactionManager를 디폴트 트랜잭션 매니저로 지정한다.<br>
하지만 Java Config를 사용할 때는 모든 PlatformTransactionManager 인스턴스를 찾아서 그 중에 하나를 매핑한다.<br>
따라서 특정 트랜잭션 매니저를 디폴트로 지정하고자 한다면 Java Config 클래스가 TransactionManagementConfigurer 인터페이스를 구현하고 annotationDrivenTransactionManager를 구현해서 트랜잭션 매니저를 리턴해주면 해당 매니저를 사용하게 된다.<br>
<br>

#### @Transactional 어노테이션 Propagation Behavior/ Isolation Level

**트랜잭션 전파 규칙(Propagation Behavior)**<br>
보통 트랜잭션 영역 내에서 실행하는 모든 코드는 해당 트랜잭션 내에서 실행된다.<br>
만약 트랜잭션이 이미 존재하는 상황에서 트랜잭션인 메소드가 실행되는 상황을 대비하여 그 동작을 지정하는 다음과 같은 몇 가지 속성들이 있다.<br>
<br>
REQUIRED
* 기존 트랜잭션이 있으면 기존 트랜잭션 내에서 실행하고, 기존 트랜잭션이 없을 때는 새로운 트랜잭션을 생성하여 실행

SUPPORTS
* 새로운 트랜잭션이 필요하지는 않지만, 기존 트랜잭션이 있으면 해당트랜잭션 내에서 메소드를 실행한다.

MANDATORY
* 반드시 트랜잭션 내에서 메소드가 실행되어야 한다. 만약 트랜잭션이 없다면 예외를 발생시킨다.

NOT_SUPPORTED
* 트랜잭션 없이 실행하고, 기존 트랜잭션이 있으면 잠시 보류한다.

NEVER
* MANDATORY와 반대로 트랜잭션 없이 실행되어야 한다. 만약 트랜잭션이 있다면 예외를 발생시킨다.

NESTED
* 트랜잭션이 있으면 기존 트랜잭션 내의 nested 트랜잭션 형태로 메소드 실행. 자체적으로 Commit, Rollback이 가능. 트랜잭션이 없으면 REQUIRED 속성으로 실행

**트랜잭션 격리 수준(Isolation Lavel)**<br>
트랜잭션 간의 간섭으로부터 보호되는 정도를 나태남. 격리 수준이 높을수록 실행되는 트랜잭션 간의 간섭 정도가 낮고, 트랜잭션이 직렬적(Serializable)으로 실행된다는 의미. 반면에 격리 수준이 낮을수록 트랜잭션 간의 간섭 정도가 크고, 트랜잭션이 병렬적으로 실행되어 동시성이 커진다는 의미<br>
<br>
DEFAULT
* 사용하는 데이터베이스에 의해 결정

READ_UNCOMMITTED
* 다른 트랜잭션이 Commit하지 않은 데이터를 읽을 수 있다. 잠금/해제가 일어나지 않으므로 데이터 일관성을 보장하지 않는다.

READ_COMMITTED
* 반드시 트랜잭션 내에서 메소드가 실행되어야 한다. 만약 트랜잭션이 없다면 예외를 발생시킨다.

REPETABLE_READ
* 다른 트랜잭션이 Commit하지 않은 데이터를 읽을 수 없다. 한 트랜잭션 내에서 동일 객체를 여러 번 조회할 때 다른 값을 읽을 수 있다. 대부분의 데이터베이스에서 기본으로 지원하는 격리 수준이다.

NOT_SUPPORTED
* 다른 트랜잭션이 Commit하지 않은 데이터를 읽을 수 없다. 한 트랜잭션 내에서 동일 객체를 여러 번 조회할 때 항상 같은 값을 읽는 것을 보장한다.

SERIALIZABLE
* 가장 높은 격리 수준으로 어떠한 간섭도 허용하지 않는다. 잠금/해제로 인한 비용이 많이 들지만 신뢰할 만한 격리 수준을 제공한다.
<br>

### Service 구현하기
interface Service 생성 (BookService.java)<br>
AppConfig.java 설정 파일에 Service 추가하기 (@ComponentScan 이용)<br>
TestCase 구현<br>
mvn -e test Dtest=BookServiceTest 실행<br>
<br>

### Presentation Layer
웹 애플리케이션에서 사용자가 보는 웹페이지<br>
REST 서비스라면 JSON이나 xml형식의 문서가 될 수 있다.<br>
<br>
* ModelAndView<br>
주소(URL) 요청 형식을 REST처럼 바꾸고, 응답을 출력해 주는 View를 상황에 맞게 사용하면 된다.<br>

* HTTPMessageConverter<br>
HTTPMessageConverter는 자바 객체와 HTTP 요청/응답 몸체(Body)를 변환하는 역할을 한다. (HTTP 요청과 응답은 문자열 기반으로 이루어진다.)<br>
스프링은 이런 문자열을 자바 객체로 변환해주는 기능을 제공해준다.
<br>

#### HttpMessageConverter 구현체
* StringHttpMessageConverter<br>
문자열을 읽고 쓴다. 기본적으로 text/* 미디어 타입을 지원하고, text/plain 이라는 컨텐트 타입(Content-Type)으로 쓴다.<br>

* FormHttpMessageConverter<br>
양식(Form)데이터를 처리한다. application/x-www-form-urlencoded라는 미디어 타입을 지원한다.<br>

* MarshallingHttpMessageConverter<br>
스프링의 Marshaller/Unmashaller를 이용하여 XML 데이터를 읽고 쓴다. application/xml 지원

* MappingJackson2HttpMessageConverter<br>
Jackson을 이용하여 JSON 데이터를 읽고 쓴다. application/json 미디어 타입 지원


스프링에서 HTTPMessageConverter를 사용하기 위해서  @RequestBody 와 @ResponseBody 어노테이션을 제공한다.<br>
@RequestBody => HTTP 요청 몸체를 자바 객체로 변환하는데 사용<br>
@ResponseBody => 자바 객체를 HTTP 응답 몸체로 변환<br>

#### Controller 클래스 테스트 방법
MockMvc를 이용하여 테스트 한다. Mapper나 Service 클래스와는 달리 Controller 클래스는 WAS상에서 작동한다. 그래서 디플로이한 후, 직접 주소를 호출하여 테스트한다<br>
이러한 불편함을 해소하기 위해서 MockMvc를 사용한다. MockMvc는 별도의 서버 없이 모조품(Mock)을 만들어서 Controller를 쉽게 테스트할 수 있도록 도와준다.
<br>

테스트 클래스 상단에 테스트할 대상이 웹 애플리케이션임을 알려주는 @WebAppConfiguration 어노테이션을 추가해야 한다.<br>
@Before 어노테이션에서 MockMvc를 생성한다. (MockMvcBuilder 참고)<br>
MockMvcRequestBuilders 클래스를 이용해서 요청을 생성할 수 있고, 앞서 생성한 mockMvc 인스턴스를 통해서 실행할 수 있다. 

#### MappingJackson2HttpMessageConverter
MappingJackson2HttpMessageConver는 내부적으로 Jackson이라는 JSON 라이브러리를 사용한다.<br>
Jackson 라이브러리 특징
* Stream API : 스트림 형식으로 데이터를 분석하고 생성하기 때문에 성능이 좋다.
* Tree Model : XML DOM처럼 Node 형태로 데이터를 다룰 수 있기 때문에 유연성이 좋다.
* Data Binding : POJO 기반의 자바 객체들을 JSON으로 변환시켜 준다.

hamcrest는 Matcher 라이브러리로, 값을 비교할 때 좀 더 편하게 해준다.<br>
JUnit에서 assertEqauls 대신 assertThat을 사용해서 Matcher 구문을 사용할 수 있다.<br>
```java
<!-- berfore -->
assertEquals("해리포터", book.getTitle());
<!-- after -->
assertThat("해리포터", is("해리포터"));

<!-- berfore -->
assertNotNull(book);
<!-- after -->
assertThat(book, is(notNullValue()));

<!-- before -->
assertTrue(book.getCreator().indexOf("로이스") > -1);
<!-- after -->
assertThat(book.getCreator(), containsString("로이스"));
```

RestAppConfig 에 MappingJackson2HttpMessageConverter 추가하기<br>
사용하기 위해서는 WebMvcConfigurer 인터페이스나 WebMvcConfigurerAdapter 클래스를 상속받아 필요한 설정 부분을 구성하면 된다.<br>


#### MarshallingHttpMessageConverter
XML형색으로 데이터를 교환하는 REST 구성시 사용<br>
스프링 OXM을 이용해서 객체와 XML간의 상호 변환 작업을 한다.<br>

**Spring OXM**<br>
스프링 OXM(Object Xml Mapping)은 XML 문서를 객체로 변환하거나, 객체를 XML 문서로 변환하는 기능을 제공한다.<br>
객체 -> XML : XML Marshalling<br>
Marshaller/Unmarshaller라는 두 개의 인터페이스로 동작하기 때문에 Object-Xml Mapping 프레임워크를 설정만으로 쉽게 변경할 수 있다.<br>
```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-oxm</artifactId>
    <version>..</version>
</dependency>
```  

**XML 어노테이션**<br>
@XmlRootElement : XML의 Root Element 명을 정의한다.<br>
@XmlElement : XML의 Element 명을 정의한다.<br>
@XmlType : XML 스키마의 이름과 namespace를 정의한다. propOrder 속성을 이용해서 XML순서를 정의한다.<br>
@XmlElementWrapper : 다른 XML 요소들을 감싸는 역할을 한다. List 같은 컬렉션 객체들을 XML 변환할 때 사용할 수 있다.


### Content Negotiation
REST에서는 하나의 리소스에 대해서 여러 형태의 Representation을 가질 수 있다. (어떤 요청은 application/json, 어떤 요청은 application/xml)<br>
HTTP Header의 Accept를 이용하여 원하는 응답을 명시하면 서버에서는 클라이언트가 원하는 형태로 결과를 전달한다. 이러한 처리과정을 Content Noegoriation 이라고 한다.<br>
**ContentNegoiationConfigurer**<br>
WebMvcConfigurerAdapter의 ContentNegotiationConfigurer를 이용해서 미디어 타입을 설정할 수 있다.<br>
```java
@Override
public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
	configurer.defaultContentType(MediaType.APPLICATION_JSON);
}
```

웹브라우저 같은 특정 클라이언트는 HTTP Header의 Accept가 고정되어 있어서 Accept 기반으로 Content Negotiation을 할 수 없다.<br>
다른 방법으로 URL 경로에 확장자를 붙이거나 요청파라미터에 형태를 명시함으로써 원하는 형태로 응답값을 처리한다.<br>
`http://localhost/restapp/books.xml` 또는 `http//localhost/restapp/books?format=xml`

