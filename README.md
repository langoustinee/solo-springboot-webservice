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
### 1. Spring Data JPA <br>
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
웬만하면 Entity의 PK는 Long 형으로 auto_increment로 사용하자.







