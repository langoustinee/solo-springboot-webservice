# solo-springboot-webservice
[스프링 부트와 AWS로 혼자 구현하는 웹서비스] 공부하기

## Chapter 01
### 1. 프로젝트 네이밍 <br>

- GroupId와 ArtifactId는 프로젝트 이름이 되기에 원하는 이름으로 작성하자.
  - GroupId: com.lango.book
  - ArtifactId: solo-springboot-webservice 
- 패키지명은 일반적으로 웹사이트 주소의 역순으로 한다. com.xxx.xxx 
### 2. 스프링 이니셜라이저 미사용 <br>
- 스프링 부트와 gradle을 더 이해하기 위해 build.gradle 코드를 하나씩 작성하자. <br>
- repositories의 jcenter의 경우 2022년도 2월 11일 까지 제공되고 이후 지원을 종료하였기에 사용할 수 없게 되었다. <br> 
그러므로 jcenter() 구문을 지우면 된다.

- build.gradle 세팅 중 그래들 버전이 달라 컴파일 오류가 발생했다. 
그래들 버전을 4.x 로 변경해야 했다. 4.x 중 가장 최신 버전인 4.10.2로 변경하려 한다. <br>
    
``` 
./gradlew --version
```
먼저 현재 그래들 버전을 확인하고 변경하자.
```
gradlew wrapper --gradle-version 4.10.2
```
그리고 같은 디렉토리에서 아래 명령어를 입력하면 그래들 버전을 변경할 수 있다.
```java
dependencies {
//    compile('org.springframework.boot:spring-boot-starter-web')
//    testCompile('org.springframework.boot:spring-boot-starter-test')
}
``` 
그래들 버전을 변경할 때 꼭 위 메서드를 주석처리하고 변경하자.
```java
dependencies {
    compile('org.springframework.boot:spring-boot-starter-web')
    testCompile('org.springframework.boot:spring-boot-starter-test')
}
```
그래들 버전 변경 후 compile 메소드 주석을 해제하고 그래들을 로드하니 정상적으로 변경되었다.

### 3. 프로젝트 Git 연동 <br>
   - 인텔리제이에서 생성한 프로젝트와 깃을 연동한다.
   - .gitignore 파일을 통해 저장소에 불필요 파일들을 올리지 말자.
     - .idea, .gradle, /build/ 등...
   - 깃 메뉴가 안보일 경우 상단 바 Version Control에서 Git을 선택하면 다시 보인다.

<br><br><br>

## Chapter 02
### 1. 테스트 코드 <br>
TDD에 대해서 많이 알아보고 공부하자. <br>
테스트코드를 먼저 작성하여 검증하는 습관을 들이자.
- 테스트할 대상 클래스 이름 끝에 Test를 붙인다.
- 컨트롤러를 테스트
  - **@WebMvcTest** 어노테이션을 이용하여 Spring MVC, 즉 Web에 집중할 수 있다.
  - MockMvc: 웹 API를 테스트할 때 사용하며 HTTP method(GET,POST,PUT,DELETE)에 대한 테스트를 할 수 있다.
  - mvc.perform(get("/hello")): /hello 주소로 GET 요청 테스트를 한다.
  - .andExpect(): mvc.perform의 결과를 검증하며 
  - status().isOk(): HTTP header Status가 200인지 검증한다. 
  - param: API 테스트할 때 사용될 요청 파라미터를 설정하며 String형만 허용된다.

### 2. Lombok(롬복)
롬복 라이브러리의 경우 Getter, Setter, 생성자, toString 등을 어노테이션으로 제공한다.
- @Getter: 선언된 모든 필드에 get 메소드를 생성해준다.
- @RequiredArgsConstructer: final로 선언된 필드라면 생성자를 생성해준다.

### 3. assertj의 assertThat
Junit의 assertThat의 경우 is()와 같은 CoreMatches 라이브러리가 별도로 필요하기 때문에 번거롭다. 또한 자동완성 지원이 약하기에 assertj의 assertThat을 사용한다.



<br><br><br>

## Chapter 03
### 1. Spring Data JPA
Spring에서는 JPA의 대표적인 구현체들을 좀 더 쉽게 사용하고자 추상화시킨 Spring Data JPA 모듈을 이용한다. <br>

[JPA <- Hibernate <- Spring Data JPA]

- Spring Data JPA의 장점 2가지
  - 구현체 교체 용이
  - 저장소 교체 용이

### 2. 도메인
DDD에 대해서 알아보고 공부하자 <br>
게시글, 댓글, 회원, 결제, 정산 등 소프트웨어에 대한 요구사항이나 문제영역이라고 보면 된다. <br>
xml에 쿼리를 담아 처리했던 DAO 패키지와는 다르게 도메인 클래스에서 비즈니스 로직을 처리하게 된다. 

### 3. 어노테이션 작성 순서
중요한 어노테이션을 메서드와 가깝게 위치시키자. Ex) @Entity, @restController, @Service ... <br>
롬복이 불필요할 경우 쉽게 롬복 어노테이션을 삭제하기 위해 롬복 어노테이션은 가능하면 위로 작성하자 

### 4. Entity
웬만하면 Entity의 PK는 Long 형으로 auto_increment로 사용하자. (MySQL 기준으로 bigint) <br>
  1. FK를 맺을 때 다른 테이블에서 복합키 전부를 갖고 있거나 별도로 중계 테이블을 둬야 할 수도 있기 떄문에
  2. 인덱스에 좋지 않기에
  3. PK 전체를 수정해야 할 수 있기에

Entity 클래스에는 Setter를 생성하지 말자. <br>
Setter 지양해야 한다. setter를 생성하게 되면 해당 클래스 인스턴스 값들이 언제 어디서 변경되는지 명확하게 알 수가 없다.

#### 그러면 DB에 어떻게 삽입할까?
Setter 대신에 생성자를 통해 최종값을 채운 후 DB에 삽입하며, 값 변경이 필요할 땐 public 메소드를 통해 변경하자. <br>
여기서 생성자 대신에 **@Builder를 통해 제공되는 빌더 클래스를 사용**하면 어떤 필드에 어떤 값을 주어야 할지 명확하게 인지할 수 있다.

#### JPA Repository
JPA repository의 경우 단순히 인터페이스를 생성하고 **JPARepository<Entity 클래스, PK 타입>을 상속**하면 CRUD 메소드가 자동으로 생성된다. <br>
도메인 패키지 내에서 **Entity 클래스와 Repository는 함께 위치**시켜 관리해야 하기 때문에 별도도 @Repository 어노테이션을 추가할 필요는 없다.

#### Spring 웹 계층
- Web Layer 
  - 컨트롤러 등의 뷰 템플릿 영역
  - 이외에도 필터나 인터셉터 등 외부 요청과 응답에 대한 전반적인 영역을 담당
- Service Layer
  - 트랜잭션, 도메인 간 순서 보장의 역할만 수행하며, 비즈니스 로직을 처리하지 않는다.
  - @Service 사용되는 서비스 영역
  - Controller와 Repository의 중간 영역에서 사용
  - @Transactional 사용되어야 하는 영역
- Repository Layer
  - DB 접근 영역
- Dto
  - 계층 간 데이터 교환을 위한 객체
- Domain Model
  - 도메인 모델 내에서 비즈니스 로직을 처리해야 한다.
  - @Entity 사용 영역 모두 도메인 모델이 된다.
  - 무조건 DB와 관계가 있지는 않다. (VO 객체 등..)

비즈니스 로직의 경우 서비스 계층에서 수행하는 것이 아닌 도메인 모델 내에서 처리한다. <br>
서비스 계층에서는 트랜잭션과 도메인 간 순서만 보장해주며 도메인 내에서 비즈니스 로직을 별도로 두어 사용한다.

여기서 중요한 점은 **Entity 클래스를 요청 및 응답에 바로 사용하면 안된다는 것이다.** <br>
DB에 접근하는 Entity 클래스를 변경하는 것은 비용이 크기 때문에 반드시 Entity와 Dto 클래스를 분리하여 사용해야 한다.

#### 더티 체킹
update 기능의 경우 save 등과 같이 DB에 접근하는 부분이 없고 Entity 값을 변경하는 것 밖에 없지만 실제 값 변경이 일어난다. <br>
바로 **JPA의 영속성 컨텍스트** 때문인데, 트랜잭션 내에서 DB에서 데이터를 가져온 후 Entity의 값을 변경하면 트랜잭션이 끝나는 시점에 가장 최근 Entity의 값을 반영해준다. 

#### JPA Auditing
DB 생성 및 수정 일자는 중요하기 때문에 모든 테이블에서 관리되어야 하는데, JPA Auditing을 통해 반복적인 코드 없이 데이터의 생성 및 수정 시간 값을 자동으로 넣어준다. 
- LocalDatetime 사용한다.
- 모든 도메인에서 auditing 기능이 포함된 Entity를 상속받도록 작성한다.  

## Chapter 04
### 1. 서버 템플릿 엔진
서버 템플릿 엔진의 종류는 아래과 같은 것들이 있다.
- JSP, Velocity: 스프링 부트에서는 지원하지 않는다.
- FreeMarker: 너무 많은 기능이 있고 높은 자유도로 인해 비즈니스 로직이 내부에 작성될 수 있다.
- Thymeleaf: 스프링 부트에서 적극적으로 활용되는 템플릿 엔진이다. 문법이 어려워 진입장벽이 높다.
- Mustache: 다른 템플릿 엔진보다 문법이 쉽고 뷰와 서버 역할을 명확하게 분리할 수 있다. 인텔리제이 커뮤니티 버전에서도 플러그인을 사용할 수 있다.

### 2. 화면 만들기
부트스트랩이나 제이쿼리 등의 프론트엔드 라이브러리를 사용할 수 있는 방법은 외부 CDN을 사용하는 것과 직접 라이브러리를 받아서 사용하는 방식이 있다. <br>
> 실제 서비스에서는 외부 CDN을 사용하는 방식은 잘 사용하지 않는다. 결국 외부 서비스에 의존하게 되기 때문이다.

부트스트랩과 제이쿼리 라이브러리를 **레이아웃 방식**으로 추가한다.
- 레이아웃 방식: 공통적으로 사용되는 영역을 별도의 파일로 분리하여 가져다 쓰는 방식

#### 페이지 로딩 속도
1. 페이지 로딩 속도를 높이기 위해 css는 header에, js는 footer에 작성했다. HTML은 위 라인부터 읽어 실행되기에 head가 실행된 후 body가 실행된다.
2. js의 용량이 클수록 body 실행 속도가 느려지기 때문에 body 하단에 두어 화면이 다 그려진 뒤 호출하는 것이 이상적이다.
3. 또한 css는 화면을 그릴 때 사용되므로 head에서 호출하는 것이 바람직하다. 
4. 이 때 부트스트랩의 경우 제이쿼리가 있어야만 동작하기에 제이쿼리가 먼저 호출된다. 이 것을 **부트스트랩이 제이쿼리에 의존**한다고 한다.

#### API를 호출하는 JS
정적 리소스 디렉토리인 src/main/resources/static/ 에서 파일을 만들어 사용한다. <br>
index.js에서 var main = {...} 과 같이 사용하였는데, 이는 **index.js만의 유효범위에서 함수들을 실행**하기 위함이다. <br>

#### Spring Data JPA에서 지원하지 않는 메소드
Spring Data JPA에서는 기본적으로 CRUD 메소드를 지원하지만, 별도로 @Query 어노테이션을 활용해 원하는 쿼리로 DB에 접근할 수 있다. <br>
EX) @Query("SELECT p FROM Posts p ORDER BY p.id DESC")


#### 조회용 프레임워크
대표적으로 querydsl, jooq, MyBatis 등이 있는데, 일반적으로 조회는 조회 프레임워크를 통해서, 등록/수정/삭제는 SpringDataJpa를 통해 진행한다. <br>
가장 많이 사용하는 querydsl을 사용해보자.

> querydsl의 이점 
> - 타입 안정성이 보장된다.
> - 국내 많은 회사에서 사용중이다.
> - 래퍼런스가 많다.

#### @Transactional(readOnly = true)
해당 옵션은 트랜잭션 범위는 유지하되 조회 기능만 동작하여 조회 속도가 빠르기에 **등록,수정,삭제 기능이 없는 서비스 메소드에서 사용**하면 좋다.


<br><br><br>

## Chapter 05
### 1. Spring Security
#### Spring Security(스프링 시큐리티)란?
스프링 시큐리티는 인증(Authentication) 및 인가(Authorization) 기능을 가지는 프레임워크이다. 스프잉 기반의 애플리케이션을 개발할 때 보안을 위한 표준이라고 볼 수 있다.

### 2. Spring Security with OAuth2 Login
#### Spring Security를 이용한 소셜 로그인 OAuth2
OAuth2를 통한 소셜 로그인을 사용하는 이유는 비밀번호를 찾거나 변경할 때, 그리고 회원정보를 변경하거나 로그인시 보안적인 이슈에 대해서 신경쓰지 않아도 되기 때문이다.

#### @Enumerated(EnumType.STRING) 어노테이션에 대해서
@Enumerated 어노테이션은 JPA로 데이터베이스를 저장할 때 Enum 값을 어떤 형태로 저장할지를 결정하는 어노테이션이다. 기본적으로는 int형의 숫자가 저장되는데, 숫자로 저장되면 해당 값의 의미를 알 수 없게 되기에 EnumType.STRING 설정을 주어 문자열로 저장될 수 있도록 선언해주면 된다.

#### 권한 코드
Spring Security에서는 권한 코드에 항상 ROLE_ 라는 프리픽스가 붙어야 한다. 코드별 키 값을 ROLE_GUSET, ROLE_USER, ROLE_ADMIN 등으로 지정할 수 있다.

#### Spring Security Setting
1. 스프링 시큐리티에서 소셜 기능을 구현하기 위해서 `spring-boot-starter-oauth-client` 라는 의존성을 추가한다.
2. Security 관련 클래스들은 하나의 Package에서 관리하는 것이 좋을 수 있다.

#### SecurityConfig
- @EnableWebSecurity: Spring Security의 설정들을 활성화시켜 준다.
- csrf().disable().headers().frameOptions().disable(): h2-console 화면을 사용하기 위해 해당 옵션들을 비활성화한다.
- authorizeRequests: URL 별로 권한을 관리하는 옵션이다. authorizeRequests가 선언되어야만 antMatchers 옵션을 사용할 수 있다.
- antMatchers: 권한 관리 대상을 지정하는 옵션이다. URL, HTTP 메소드별로 관리할 수 있다.
- anyRequest: antMatchers에서 설정된 값들 이외의 나머지 URL들을 나나탠다. authenticated() 옵션을 추가하여 나머지 URL들은 인증된 사용자들에게만 허용하도록 설정할 수 있다.
- logout().logOutSeccessUrl("/"): 로그아웃 기능에 대한 설정이다. 로그아웃이 성공한다면 "/" 주소로 이동한다.
- oauth2Login: OAuth2 로그인 기능에 대한 설정이다.
- userInfoEndpoint: OAuth2 로그인 이후 사용자 정보를 가져올 때의 설정들을 담당한다.
- userService: 소셜 로그인 설공시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록한다. 리소스 서버, 즉 소셜 서비스에서 사용자 정보를 가져온 상태에서 추갈호 진행할 기능을 명시할 수 있다.

#### CustomOAuth2UserService
- registrationId: 현재 로그인 중인 서비스를 구분하는 코드이다. 구글, 카카오 네이버 로그인 등을 구분하기 위해 사용한다.
- userNameAttributeName: OAuth2 로그인시 키가되는 필드값을 설정한다. 구글의 경우는 기본값으로 sub라는 값을 지원하지만 카카오나 네이버는 지원하지 않는다.
- OAuthAttributes: OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스이다.
- SessionUser: 세션에 사용자 정보를 담기위한 DTO 클래스이다.

#### OAuthAttributes
- of(): OAuth2User에서 반환하는 사용자 정보는 Map이기 때문에 값 하나하나를 변환해서 사용한다.

#### SessionUser
- 세션에는 인증된 사용자 정보만 필요하고 그 외 정보들은 필요하지 않다.

#### User 클래스를 사용하면 안되는 이유
만약 사용자 관련 Entity인 User 클래스를 사용했다면 직렬화를 구현하지 않았기 때문에 에러가 발생하게 된다.
하지만 이 에러를 해결하기 위해 User 클래스에 직렬화 코드를 삽입하는 것은 바람직 하지 않다. 

Entity 클래스에 직렬화 코드를 넣는다면 성능 이슈, 부수 효과가 발생할 확률이 높다. 
그래서 별도로 직렬화 기능을 가지는 DTO 클래스를 만들어 사용하는 것이 이후 운영이나 유지보수에 더 도움이 된다. 

### 3. 어노테이션 기반으로 개선하기 
같은 코드가 반복된다면 이후 수정이 필요할 때 모든 부분을 하나씩 찾아가며 수정해야만 한다.

우리가 작성한 코드에서 예를 들자면 세션값을 가져오는 부분이 있다.
이 부분을 메소드 인자로 세션값을 바로 받을 수 있도록 변경한다면 중복 코드를 줄일 수 있게 된다.

- @Interface: 해당 어노테이션이 붙은 클래스는 어노테이션 클래스 지정된다.

### 4. 데이터베이스를 세션 저장소로 사용하기
앞서 만든 애플리케이션의 경우 서버를 재시작할 경우 **세션이 내장 톰캣의 메모리에 저장**되기 때문에 로그인이 풀리게 된다. 
세션은 기본적으로 실행되는 WAS의 메모리에 저장되고 호출되는데 메모리에 저장되다 보니 내장 톰캣처럼 애플리케이션을 실행될 때 항상 초기화가 된다.

그래서 세션을 유지하기 위해서는 다음과 같은 방법을 사용해야 한다.
- 톰캣 세션 사용
  - 기본으로 사용할 수 있는 방법인데, 2대 이상의 WAS 환경에서는 톰캣들간의 추가 설정이 필요하다.
- 데인터베이스를 세션저장소로 사용
  - 여러 WAS들 간의 공용 세션을 사용할 수 있는 가장 쉬운 방법이다. 다만, 로그인 요청이 많아진다면 성능상 이슈가 발생할 수 있다.
  - 보통 로그인 요청이 적은 사내 백오피스 등의 환경에서 사용한다.
- Redis, Memcached와 같은 메모리 DB를 세션저장소로 사용
  - B2C 서비스에서 가장 많이 사용되는 방법이다.
  - 실제 서비스로 사용하기 위해서는 Embedded Redis와 같은 방싯이 아닌 외부 메모리 서버가 필요하다.

> AWS에서 해당 서비스를 배포하고 운영할 때 Redis와 같은 메모리 DB를 사용하려고 하면 비용이 부담될 수 있기 때문에
학습 수준에서는 데이터베이스를 세션 저장소로 사용하는 것이 쉽고 간단하다. 

### 5. 테스트에 시큐리티 적용하기
#### CustomOAuth2UserSerivce를 찾을 수 없는 문제
해당 문제가 발생한 원인은 CustomOauth2UserService를 생성하는데 필요한 소셜 로그인 관련 설정값들이 없기 때문이다.
그래서 src/main과 src/test 디렉토리 환경을 동일하게 맞춰주기 위해서 test/resources 디렉토리에 application.yaml 설정 파일을 동일하게 두어야 한다.

#### 302 Status Code 문제
응답의 결과로 302 Code(리다이렉션 응답)가 반환되어 실패했는데, 스프링 시큐리티 설정으로 인해 인증되지 않은 사용자의 요청은 이동시키기 때문이다.
그래서 API 요청은 임의로 인증된 사용자로 추가하여 테스트해야 한다. 이는 spring-security-test 라는 스프링 시큐리티 테스트 도구를 추가하고 @WithMockUser 어노테이션을 사용하여 해결할 수 있다.
> @WithMockUser 어노테이션은 `@WithMockUser(roles="USER")` 와 같은 형식으로 작성할 수 있다.
인증된 가짜 사용자를 만들어서 사용하게 되는데, roles에 권한을 추가할 수 있다. 해당 어노테이션을 통해 ROLE_USER 권한을 가지는 사용자가 API를 요청하는 것과 동일한 효과를 볼 수 있다.

#### @WebMvcTest에서 CustomOAuth2UserService를 찾을 수 없는 문제
@WebMvcTest는 CustomOAuth2UserService를 스캔하지 않기 떄문에 해당 문제가 발생한다.
@WebMvcTest는 WebSecurityConfigurerAdapter, WebMvcConfigurer를 비롯한 @ControllerAdvice, @Controller를 스캔한다.
@Repository, @Service, @Component는 스캔하지 않는다. 그래서 SecurityConfig를 생성하기 위해 필요한 CustomOAuth2UserService는 스캔할 수 없는 것이다.

이 문제를 해결하기 위해서는 SecurityConfig를 스캔 대상에서 제외시키면 된다.
