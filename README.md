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