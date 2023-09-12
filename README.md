# 개인 프로젝트 - 과제 제출 Web Service

## 0.72v. 모방 구현한 TransactionTemplate -> Spring에서 제공하는 TransactionTemplate으로 교체
- 업데이트 사항
  - [app71-server -> app72-server]
    - 추가한 라이브러리
      - 없음.
    - 객체 추가 및 변경
      - personal.util 패키지 아래의 TransactionCallBack인터페이스와 TransactionTemplate클래스 삭제 후 Spring에서 제공하는 TransactionTemplate로 변경
    - 기능 추가 및 변경
      - 기능은 이전과 같음.

## 0.71v. 트랜잭션 제어 코드를 캡슐화하기: Spring의 TransactionTemplate 모방 구현
- 업데이트 사항
  - [app70-server -> app71-server]
    - 추가한 라이브러리
      - 없음.
    - 객체 추가 및 변경
      - Spring의 TransactionTemplate 클래스를 모방하여 TransactionCallBack 인터페이스와 
        TransactionTemplate 클래스를 직접 구현하여 반복적인 트랜잭션 제어 코드 캡슐화 
    - 기능 추가 및 변경
      - 기능은 이전과 같음.


## 0.70v. Controller에서 비즈니스 로직을 분리하기 
- 업데이트 사항
  - [app69-common -> app70-common]
    - 없음.
    - 객체 추가 및 변경
      - DAO 인터페이스 파라미터 및 반환 타입 수정
  - [app69-server -> app70-server]
    - 추가한 라이브러리
      - 없음.
    - 객체 추가 및 변경
      - 비즈니스 로직을 기존의 Controller에서 Service로 분리하여 코드의 유연성을 높임.
      - personal.project.service 패키지 생성 후 Service 인터페이스와 그 구현체 구현함.
    - 기능 추가 및 변경
      - 기능은 이전과 같음.


## 0.69v. MyBatis와 Spring IoC 컨테이너 사용하여 DAO 객체 자동 생성적용
- 업데이트 사항
  - [app68-common -> app69-common]
    - 추가한 라이브러리
      - implementation 'org.mybatis:mybatis:3.5.13'(Mybatis 라이브러리)
      - implementation 'org.mybatis:mybatis-spring:3.0.2'(Mybatis의 Spring 연동 라이브러리)
  - [app68-server -> app69-server]
    - 추가한 라이브러리
      - implementation 'org.springframework:spring-jdbc:6.0.11'(Spring JDBC 라이브러리)
      - implementation 'org.mybatis:mybatis-spring:3.0.2'(Mybatis의 Spring 연동 라이브러리)
      - implementation 'org.apache.logging.log4j:log4j-core:2.20.0'(Log4j 2.x 라이브러리)
    - 객체 추가 및 변경
      - Dao 구현체 삭제
        - Dao인터페이스를 직접구현하지 않고 Mybatis와 Spring IoC 컨테이너를 이용하여 Dao 구현체를 자동으로 구현하여 생성하게 함.
    - 기능 추가 및 변경
      - Mybatis의 Log4j기능 적용하기(활성화하기)


## 0.68v. Spring Ioc 컨테이너를 이용하여 페이지 컨트롤러를 관리하기
- app67-server -> app68-server
- 업데이트 사항
  - 추가한 라이브러리
    - org.springframework:spring-context:6.0.11
  - 객체 추가 및 변경
    - DispatcherServlet의 기능을 강화하여 앱 실행시 .class설정파일을 기준으로 WebApplicationContext(IoC Container)를 생성하여 그곳에 Bean들을 생성하고 등록 및 관리
    - 트랜잭션시 필요한 SqlSessionFactory(의존객체)를 의존주입하여 사용
    - 클라이언트로부터 요청이 들어올 때 마다 해당 요청을 처리하는 페이지 컨트롤러 객체를 Ioc Container에서 꺼내서 사용
  - 기능 추가 및 변경
    - 기능은 이전과 동일


## 0.67v. 페이지 컨트롤러 -> Plain Old Java Object로 전환하기
- app66-server -> app67-server
- 업데이트 사항
  - 추가한 라이브러리
    - 없음.
  - 객체 추가 및 변경
    - Member(회원)와 FreeBoard(자유게시판) 도메인에 해당하는 요청을 처리하는 모든 PageController의 코드를 기존의 Servlet을 이용한 코드에서 Java로 작성한 코드로 변경함.
  - 기능 추가 및 변경
    - 기능은 이전과 동일


## 0.66v. DispatcherServlet (Front Controller 디자인 패턴) 적용
  - app65-server -> app66-server
- 업데이트 사항
  - 추가한 라이브러리
    - 없음.
  - 객체 추가 및 변경
    - 클라이언트로부터 받는 모든 요청을 받아 해당 요청을 처리하는 각각의 Controller(Servlet)에게 전달해주는 역할을 할 DispatcherServlet(FrontController패턴 ≒ Facade패턴)  클래스를 선언하여 사용함.
    - Member(회원)와 FreeBoard(자유게시판) 도메인에서 클라이언트로 부터 받은 요청을 각각의 Controller가 직접 전달 받지 않고 FrontController를 통해 전달받도록 처리
    - 각각의 요청을 전달받은 Controller들이 요청을 처리한 결과를 Controller에서 바로 응답하지 않고 DispatcherServlet에서 Controller에서 처리한 결과를 반영하여 응답할 수 있도록 처리
  - 기능 추가 및 변경
    - 기능은 이전과 동일하나 요청과 응답에 대한 구조만 변경


## 0.65v. JSP를 이용하여 MVC 모델2 구조로 변경하기
- 업데이트 프로젝트 Directory
  - app64-server -> app65-server
- 업데이트 사항
  - 추가한 라이브러리
    - 없음.
  - 객체 추가 및 변경
    - Member(회원)와 FreeBoard(자유게시판)에서 데이터 조회 및 가공의 역할을 하던 jsp파일 내 코드들을 모두 Controller이름을 가진 Servlet => 자바 소스 파일로 변경
  - 기능 추가 및 변경
    - 기능은 이전과 동일하나 Member(회원)와 FreeBoard(자유게시판) 관련 구조만 MVC 모델2구조로 변경


## 0.64v. JSP를 이용하여 MVC 모델1 구조로 변경하기
- 업데이트 프로젝트 Directory
  - app63-server -> app64-server
  - app63-common -> app64-common
- 업데이트 사항
  - 추가한 라이브러리
    - jstl1.2
  - 객체 추가 및 변경
    - Member와 FreeBoard(자유게시판)관련 Servlet클래스 모두 jsp 파일로 대체(jsp파일로 프론트 뿐만 아니라 JSTL과 스크립틀릿으로 자바코드도 대체[MVC 모델1 구조])
  - 기능 추가 및 변경
    - 기능은 이전과 동일하나 Member(회원)와 FreeBoard(자유게시판) 관련 구조만 MVC 모델1구조로 변경


## 0.63v. 쿠키와 ServletContext 보관소 활용
- 업데이트 프로젝트 Directory
  - app62-server -> app63-server
- 업데이트 사항
  - 추가한 라이브러리
    - 없음.
  - 객체 추가 및 변경
    - 없음.
  - 기능 추가 및 변경
    - 쿠키 활용하여 로그인시 아이디 저장 옵션에 체크시 다음 번 로그인 시에는 쿠키에 저장된 아이디를 자동로드하게 구현 
    - 웹 애플리케이션 실행시 필요한 객체들을 기존 InitServlet에서 준비하던 것을 mybatis-config.xml 파일을 web.xml의 context-param으로 등록 후 ContextLoaderListener객체에서 ServletContext 보관소를 활용하여 애플리케이션 실행시 생성 및 준비


## 0.62v. forward/include/refresh/redirect 적용
- 업데이트 프로젝트 Directory
  - app61-server -> app62-server
- 업데이트 사항
  - 추가한 라이브러리
    - 없음.
  - 객체 추가 및 변경
    - 없음.
  - 기능 추가 및 변경
    - include 를 이용하여 Header페이지와 Footer페이지 모든 페이지가 포함하게 함.
    - 에러 발생시 에러 페이지로 요청을 forward 위임하여 ErrorSerlvet에서 하나의 에러 폼을 가지고 동적으로 페이지 출력되게 함.
    - redirect를 이용하여 다른 페이지로 이동하게 함.
    - refresh를 이용하여 페이지 새로고침 구현함.
    

## 0.61v. 네이버 클라우드의 Image Optimizer를 이용하여 회원 사진 썸네일 적용하기
- 업데이트 프로젝트 Directory
  - app60-common -> app61-common
  - app60-server -> app61-server
- 업데이트 사항
  - 추가한 라이브러리
    - lombok 적용하여 setter, getter, toString 소스 코드 생략
  - 객체 추가 및 변경
    - app60-common 프로젝트의 Member vo 객체에 memberPhoto필드 추가 
  - 기능 추가 및 변경
    - 네이버 클라우드의 Image Optimizer를 이용하여 회원사진 최적화
    - 회원 정보 보기와 회원 목록 보기시 각각 다른 크기의 이미지 크기로 Optimizing된 이미지 로드 


## 0.60v. 첨부파일을 Naver Coloud Storage Service에 저장하기
- 업데이트 프로젝트 Directory
  - app59-server -> app60-server
- 업데이트 사항
    - 객체 추가 및 변경
      - NcpConfig 클래스, NcpObjectStorageService 클랙스 선언 
    - 기능 추가 및 변경
      - 기존에 로컬에 보관하던 자유게시글의 첨부파일을 NaverColoudStorage에 보관하게 변경


## 0.59v. 서블릿 컨테이너 삽입 (Embedded Tomcat 간단하게 구현)
- 업데이트 프로젝트 Directory
  - app58-server -> app59-server
- 업데이트 사항
    - 객체 추가 및 변경
      - 없음.
    - 기능 추가 및 변경
      - personal.project.App 클래스에 Embedded Tomcat 코드 작성


## 0.59v. 서블릿 컨테이너 삽입 (Embedded Tomcat 간단하게 구현)
- 업데이트 프로젝트 Directory
  - app58-server -> app59-server
- 업데이트 사항
    - 객체 추가 및 변경
      - 없음.
    - 기능 추가 및 변경
      - personal.project.App 클래스에 Embedded Tomcat 코드 작성
    
## 0.58v. 자유게시판 파일 업로드 기능 II - multipart/form-data POST 요청 파라미터 인코딩(Java Servlet API)
- 업데이트 프로젝트 Directory 
  - app57-server -> app58-server
- 업데이트 사항
    - 객체 추가 및 변경
      - 없음.
    - 기능 추가 및 변경 
      - 자유게시판에서 게시글 등록시 파일 첨부 기능하여 등록 가능(여러파일 등록가능)하게 기능 구현
      - 자유게시판에서 게시글 상세보기시 첨부된 파일을 항목별로 삭제 가능하게 기능 구현
      - 자유게시판에서 게시글 상세보기시 첨부파일만 수정하여 업데이트 가능하게 기능 구현
      - 기능 자체는 이전과 같으나 이전과 달리 Java Servlet API를 사용하여 파일 업로드가 가능하도록 코드를 변경하였음.


## 0.57v. 자유게시판 파일 업로드 기능 I - multipart/form-data POST 요청 파라미터 인코딩(Apache Commons FileUpload)
- 업데이트 프로젝트 Directory
  - app56-3-server -> app57-server
  - app56-3-common -> app57-common
- 업데이트 사항
    - 객체 추가 및 변경
      - 없음.
    - 기능 추가 및 변경
      - 자유게시판에서 게시글 등록시 파일 첨부 기능하여 등록 가능(여러파일 등록가능)하게 기능 구현
      - 자유게시판에서 게시글 상세보기시 첨부된 파일을 항목별로 삭제 가능하게 기능 구현
      - 자유게시판에서 게시글 상세보기시 첨부파일만 수정하여 업데이트 가능하게 기능 구현


## 0.56.3v 웹 애플리케이션 자바 표준 기술 JavaEE 도입
- 업데이트 프로젝트 Directory
  - app56-2-server -> app56-3-server
  - app56-1-common -> app56-3-common
- 업데이트 사항
    - 객체 추가 및 변경
      - SearchParam(검색용), Participant(강의 참여자 Table용) 추가
      - 기존의 Board -> LecBoard(강의게시판 Table용), FreeBoard(자유게시판 Table용)으로 분리 변경
    - 기능 추가 및 변경
      - DB (리)모델링 후 기존의 기본게시판 -> 강의 게시판 기능으로 변경 구현 (게시글 등록, 수정, 삭제, 제목 및 내용으로 검색)


## 0.56.3v 웹 애플리케이션 자바 표준 기술 JavaEE 도입
- 업데이트 프로젝트 Directory
  - app56-2-server -> app56-3-server
  - app56-1-common -> app56-3-common
- 업데이트 사항
    - 객체 추가 및 변경
      - SearchParam(검색용), Participant(강의 참여자 Table용) 추가 
      - 기존의 Board -> LecBoard(강의게시판 Table용), FreeBoard(자유게시판 Table용)으로 분리 변경
    - 기능 추가 및 변경
      - DB (리)모델링 후 기존의 기본게시판 -> 강의 게시판 기능으로 변경 구현 (게시글 등록, 수정, 삭제, 제목 및 내용으로 검색)


## 0.56.2v 웹 애플리케이션 자바 표준 기술 JavaEE 도입
- 업데이트 프로젝트 Directory
  - app56-1-server -> app56-2-server
- 업데이트 사항
    - 로그인 표시 및 로그아웃 기능 구현
    - 제목 및 내용으로 게시글 검색기능 구현


## 0.56.1v 웹 애플리케이션 자바 표준 기술 JavaEE 도입
- 업데이트 프로젝트 Directory
  - app55-server -> app56-1-server
- 업데이트 사항
  - Java EE 기술명세를 구현한 ServletContainer(Tomcat)으로 서버 대체
  - javax패키지의 Servlet API 사용하여 요청 및 응답 적용
  - 계정 권한에 따라 회원 및 게시글 추가, 변경, 삭제 가능하게 업데이트
  - 게시글, 자유게시글 화면 구분하여 렌더링되게 업데이트
   

## 0.55v. 웹 애플리케이션 서버 구조로 전환하기 - 웹 기술 도입
- 업데이트 프로젝트 Directory
  - app54-server -> app55-server 
- 업데이트 사항
  - 웹 기술을 도입하여 애플리케이션 서버 / 클라이언트를 구축 
  - Netty, Reactor 라이브러리를 사용하여 웹서버 구축 
  - 웹브라우저를 이용하여 클라이언트 구축
  - 로그인과 권한인증에 쿠키와 세션 적용

## 0.54v. IoC 컨테이너 적용하기
- 업데이트 프로젝트 Directory
  - app53-server -> app54-server 
- 업데이트 사항
  - IOC 컨테이너를 구현하여 기존의 SqlSessionFactory구현체, DAO객체들, ActionListener 구현체들을 IoC 컨테이너로 생성 및 관리하기


## 0.53v. 리스너 객체에 GoF Facade 패턴 적용하기
- 업데이트 프로젝트 Directory
  - app52-server -> app53-server 
- 업데이트 사항
  - ActionListener 인터페이스 구현체 실행시 Facade 객체를 통해 결합도↓ 유지보수 용이성 및 유연성↑


## 0.52v. 기존에 직접 JDBC API로 작성한 코드 -> Mybatis 프레임워크 사용해 교체하기
- 업데이트 프로젝트 Directory
  - app51-server -> app52-server 
  - app51-common -> app52-common
- 업데이트 사항
  - JDBC API를 사용해 자바소스 파일에서 작성한 SQL문을 Mybatis 프레임워크를 사용하여 Mapper XML(Dao.xml)파일에 작성  
  - mybatis설정파일로 DB환경설정(mybatis-config.xml)


## 0.51v. DB 커넥션 풀을 이용한 Connection객체 재사용 기능 구현하기
- 업데이트 프로젝트 Directory
  - app-server프로젝트 폴더 업데이트 후 app51-server 폴더로 백업
- 업데이트 사항
  - 각 스레드내에서만 유효한 ThreadLocal객체 사용하여 해당 스레드 전용 Connection객체 보관하고 꺼내기 
  - FlyWeight패턴(=Pooling기법)을 적용하여 DB 커넥션 재사용하기 
  - 스레드 풀과 커넥션 풀 동시 적용


## 0.50v. Application Server 아키텍처로 전환하기
- 업데이트 프로젝트 Directory
  - app-server, app-client, app-common프로젝트 폴더 업데이트 후 app50-server, app50-client, app50-common 폴더로 백업
- 업데이트 사항
  - Clinet/Server 아키텍처 -> Application Server 아키텍처로 구조 변경하기
  - 자바의 스레드 풀 적용하여 여러 클라이언트가 하나의 Application Server에 접속하여 하나의 해당 Application Server를 통해 DBMS와 통신하게 구현


## 0.49v. 로그인 적용하기
- 업데이트 프로젝트 Directory
  - app-client, app-common프로젝트 폴더 업데이트 후 app49-client, app49-common 폴더로 백업
- 업데이트 사항
  - 간단한 로그인 구현 및 로그인 데이터로 메뉴 기능 다루기


## 0.48v. 외부키(Foreign Key) 사용하기
- 업데이트 프로젝트 Directory
  - app-client, app-common프로젝트 폴더 업데이트 후 app48-client, app48-common 폴더로 백업
- 업데이트 사항
  - 테이블 구조 변경(외부키 적용 및 사용) => Member테이블의 PK를 Board테이블의 FK로 사용 
  - 조인을 이용하여 분리된 테이블 값을 한번에 조회하여 리스트로 출력


## 0.47v. SQL 삽입 공격 차단하기
- 업데이트 프로젝트 Directory
  - app-client프로젝트 폴더 업데이트 후 app47-client 폴더로 백업
- 업데이트 사항
  - Statement객체 사용시 취약점을 파악하고 PreparedStatment객체로 대체
  - 패스워드 데이터에 sha1() 암호화 적용해보기


## 0.46v. DBMS 도입하기
- 업데이트 프로젝트 Directory
  - app-client, app-common프로젝트 폴더 업데이트 후 app46-client, app46-common 폴더로 백업

- 업데이트 사항
  - DBMS와 연동하여 작업하는 DAO 구현


## 0.45v. 직접 구현했던 스레드풀(thread pool) 자바에서 제공하는 thread pool로 대체하여 적용하기
- 업데이트 프로젝트 Directory
  - app-server프로젝트 폴더 업데이트 후 app45-server 폴더로 백업

- 업데이트 사항
  - 직접 구현하여 사용했던 스레드풀(thread pool) 기능을 자바에서 제공하는 thread pool로 대체하여 적용


## 0.44v. 스레드풀(thread pool) 구현하여 스레드 재사용 기능 추가
- 업데이트 프로젝트 Directory
  - app-server프로젝트 폴더 업데이트 후 app44-server 폴더로 백업

- 업데이트 사항
  - GoF의 FlyWeight 디자인 패턴(Pooling 기법)을 활용하여 스레드를 재사용 기능 추가


## 0.43v. app-server : Thread기능을 간단하게 적용하여 여러 클라이언트 요청을 동시에 처리하도록 업데이트
- 금일부터 변경사항이 있는 프로젝트 폴더만 백업파일 생성하여 Commit할 예정


## 0.42v. 여러 클라이언트의 요청을 순차적으로 처리하기: Stateless 방식
- 처리시간이 짧은 요청들만 받을 경우 여러 클라이언트가 이용할 수 있음
- 처리시간이 긴 요청을 받을 경우 다른 클라이언트들이 서비스를 이용하지 못하는 병목현상이 발생하는 issue가 있음(개선필요)


## 0.41v(0.40v 포함). 예외 처리하기, 여러 클라이언트의 요청을 순차적으로 처리하기: Stateful 방식
- try ~ catch ~ finally 사용하여 예외처리를 적용하여 예외가 발생했을 때 시스템이 종료되는 버그 개선
- Stateful방식을 사용하여 여러 클라이언트들의 요청을 순차자적으로 처리할 수 있도록 업데이트
- 클라이언트가 접속을 끊지 않으면 다른 클라이언트들의 요청을 받을 수 없는 문제가 발생(개선필요)


## 0.39v. Reflection API를 활용하여 DAO 메서드 호출을 자동화하기
- Reflection API를 사용하는 방법
- 서버의 DAO 메서드 호출을 자동화 하는 방법


## 0.38v. DAO 프록시 객체를 자동 생성하기

- java.lang.reflect.Proxy 클래스 사용법
- 프록시 객체의 구동원리 이해 


## 0.37v. 네트워킹을 이용하여 데이터 공유하기 : Client/Server 아키텍처로 전환

- 네트워크 프로그래밍 방법
  - Client와 Server 개념
  - 프로토콜에 따라 애플리케이션 간에 데이터를 주고 받기
  - GoF의 프록시 패턴의 원리 이해 및 적용
  - 분산 컴퓨팅의 개념과 주요 기술 이해


## 0.36v. 데이터의 등록, 조회, 수정, 삭제 기능을 캡슐화하기 : DAO 객체 도입

- XxxListener에서 데이터를 조작하는 코드를 캡슐화하여 별도의 클래스로 분리
- 인터페이스로 DAO 객체 사용법을 정의


## 0.35v. JSON 형식으로 입출력하기

- JSON 형식으로 데이터를 읽고 쓰는 법
- Gson 라이브러리 사용법


## 0.34v. 리팩토링: Factory Method 패턴(GoF), Information Expert 패턴(GRASP)

- CSV 데이터 생성을 Board 클래스에 맡기기
  - Information Expert 패턴 적용
- CSV 데이터를 가지로 Board 클래스 생성하기
  - Factory Method 패턴 적용
  - Reflection API 사용법
    - Class, Method 사용법


## 0.33v. character stream API를 사용하여 CSV 텍스트 형식으로 입출력하기

- CSV 형식으로 데이터를 읽고 쓰는 법
- FileReader/FileWriter 사용법


## 0.32v. 인스턴스를 통째로 입출력하기(객체 직렬화)

- ObjectInputStream/ObjectOutputStream 사용법
  - java.io.Serializable 인터페이스 사용법
  - transient modifier 사용법


## 0.31v. Java Stream API 로 교체하기

- 입출력 관련 클래스를 자바 스트림 클래스로 교체
  - java.io.* 패키지의 클래스 사용


## 0.30v. 입출력 기능 확장에 상속 대신 Decorator 패턴을 적용하기

- 상속 vs Decorator 패턴(GoF)
  - 기존 코드를 손대지 않고 기능 확장하는 방법
  - 상속: 기능 확장 용이
  - Decorator: 기능 확장 및 기능 제거 용이
- BufferedDataInputStream 분해
  - BufferedInputStream, DataInputStream, FileInputStream
- BufferedDataOutputStream 분해
  - BufferedOutputStream, DataOutputStream, FileOutputStream


## 0.29v. 입출력 성능을 높이기 위해 버퍼 기능 추가하기

- 기존의 클래스에 버퍼 기능을 추가한다.
  - BufferedDataInputStream = DataInputStream + 버퍼 기능
  - BufferedDataOutputStream = DataOutputStream + 버퍼 기능


## 0.28v. 상속을 이용하여 primitive type과 String 출력 기능을 추가하기

- 상속을 이용하여 바이트 입출력 기능을 확장하기
  - DataInputStream = FileInputStream 클래스 + primitive type/String 값 읽기
  - DataOutputStream = FileOutputStream 클래스 + primitive type/String 값 쓰기


## 0.27v. File I/O API를 이용하여 데이터를 바이너리 형식으로 입출력하기

- FileInputStream/FileOutputStream 사용법
- 바이너리 형식으로 데이터를 입출력하는 방법


## 0.26v. 자바 Collection API 사용하기

- 목록을 다루는 기존 클래스를 자바 컬렉션 API 로 교체


## 0.25v. Iterator 디자인 패턴을 활용하여 목록 조회 기능을 캡슐화하기

- GoF의 디자인 패턴 중 Iterator 패턴의 동작원리 이해 및 구현
- ArrayList, LinkedList, Stack, Queue에 적용
- 중접 클래스 문법을 이용하여 Iterator 구현하기
  - static/non-static nested 클래스 문법을 활용하는 방법
  - local/anonymous 클래스 문법을 활용하는 방법


## 0.24v. 제네릭을 사용하여 타입을 파라미터로 다루기

- ArrayList, LinkedList, Stack, Queue에 제네릭 적용하기
- T[] toArray(T[]) 메서드 추가하기


## 0.23v. Composite, Command, Observer 디자인 패턴, 추상 클래스/메서드 활용하기

- Composite 패턴을 활용하여 메뉴 구현하기
  - BreadcrumbPrompt에 적용
  - Menu, MenuGroup 클래스 정의
- Observer 패턴을 활용하여 메뉴 명령 처리하기
  - ActionListener 인터페이스 정의
  - Menu와 리스너 객체 연결
- Command 패턴을 활용하여 메뉴 기능 구현하기
  - BoardHandler, MemberHandler에 적용
  - ActionListener 인터페이스 활용
  - BoardXxxListener, MemberXxxListener 클래스로 분해
- Generalization(상속) 수행 
  - AbstractBoardListener 추상 클래스 정의
    - 추상 메서드 도입


## 0.22v. Stack, Queue 자료구조 구현하기

- Stack과 Queue의 구동원리 이해 및 구현
- Stack 적용
  - Prompt 클래스의 서브 클래스 MenuPrompt 정의
  - MenuPrompt에서 Stack을 이용하여 프롬프트 제목에 breadcrumb 기능을 적용
- Queue 적용
  - MenuPrompt 클래스에 메뉴 출력 기능을 추가
    - App, BoardHandler, MemberHandler 변경
  - MenuPrompt 클래스에 입력한 명령어의 history 기능을 추가


## 0.21v. 인터페이스를 이용하여 List 사용 규칙 정의하기

- 목록 관리 객체의 사용 규칙을 인터페이스 정의
  - List 인터페이스 정의
  - ArrayList, LinkedList에 List 인터페이스 적용
- MemberHandler와 BoardHandler에 적용
  - List 구현체를 생성자를 통해 주입: DI(Dependency Injection) 적용


## 0.20v. LinkedList 자료구조 구현하기

- 목록 관리 범용 클래스 LinkedList 정의
  - LinkedList 구동원리 이해 및 구현
  - 중첩 클래스 활용
- MemberHandler와 BoardHandler에 적용


## 0.19v. 다형성을 이용하여 범용으로 사용할 수 있는 목록 클래스 만들기

- 목록 관리 범용 클래스 ArrayList 정의
  - 다형성의 polymorphic variable 문법 활용
- equals() 메서드와 오버라이딩 활용
  - Object 클래스와 상속
  - Member와 Board 클래스에 적용
- 오버로딩을 활용하여 생성자를 추가
  - Member와 Board 클래스 적용
- MemberHandler와 BoardHandler에 적용


## 0.18v. 인스턴스 목록 제어 기능을 별도의 클래스로 캡슐화: 재사용성 높임

- 핸들러에서 인스턴스 목록을 다루는 기능을 별도의 클래스로 분리
  - UI가 CLI에서 윈도우 또는 웹으로 바뀌더라도 인스턴스 목록 다루는 기능은 재사용 가능
- 배열 크기 자동 증가 기능 추가


## 0.17v. 인터페이스를 이용한 객체 사용 규칙 정의

- 인터페이스 문법으로 핸들러의 실행 규칙 정의
- 인터페이스에 정의한 대로 핸들러 구현
- 인터페이스에 정의한 대로 핸들러 실행


## 0.16v. GRASP 패턴: Information Expert 적용

- 메뉴 기능을 각 핸들러에게 위임
  - 기능을 수행하는데 필요한 정보를 가지고 있는 객체에 역할 부여
  - CRUD 메뉴 기능은 핸들러로 이전
- App 클래스는 메인 메뉴 제공


## 0.15v. 인스턴스 필드와 인스턴스 메서드, 생성자와 의존 객체 주입

- BoardHandler 클래스에 인스턴스 필드 및 메서드 적용
- 향후 확장성을 고려하여 MemberHandler 크래스에도 인스턴스 필드와 인스턴스 메서드를 적용
  - 그래서 실무에서는 대부분의 클래스가 인스턴스 필드와 인스턴스 메서드로 구성된다.
- 향후 확장성을 고려하여 Prompt 크래스에도 인스턴스 필드와 인스턴스 메서드를 적용
  - 생성자 도입: Scanner 사용할 입력 도구를 지정할 수 있게 한다.
- 의존 객체 주입의 개념과 구현
  - 생성자를 통해 Prompt 객체를 Handler에 주입


## 0.14v. 스태틱 필드의 한계 확인

- BoardHandler 클래스를 복제하여 독서록 게시판 추가
- 클래스 코드 복제의 문제점 확인


## 0.13v. 복사/붙여넣기를 이용한 CRUD 구현

- 게시글 CRUD 기능 추가
- Value Object, Handler 클래스 추가
- Prompt 클래스 리팩토링


## 0.12v. 생성자, 셋터, 겟터 도입하기

- 인스턴스 필드를 초기화시키는 방법: 생성자
- 인스턴스 필드에 직접 접근하는 것을 막는 방법: private
- 인스턴스 필드에 값을 저장하고 꺼내는 방법: setter/getter
- 스태틱 필드 및 생성자 활용
- 스태틱 상수 필드 활용 + GRASP 패턴의 Information Expert 


## 0.11v. 사용자 정의 데이터 타입 만들기

- 클래스 문법을 이용하여 데이터 타입을 정의하는 방법
- 클래스를 이용하여 만든 데이터 타입의 메모리를 준비하는 방법: new 명령
- 클래스 배열 사용법
- 인스턴스와 인스턴스 필드, 힙(heap) 메모리의 관계


## 0.10v. 메뉴 구성 및 CRUD 구현

- 회원정보를 다루는 메뉴 구성하기
- 회원정보 조회 및 변경, 삭제 구현하기


## 0.09v. 메서드를 역할에 따라 분류하기 : 클래스 및 패키지 사용법

- 클래스 및 패키지 사용법(스태틱 클래스)
- import 명령문 사용법
- public 접근 제어 사용법
- 스태틱 변수가 생성되고 제거되는 시점, 메모리 영역


## 0.08v. 메서드 간에 변수 공유하기 : 스태틱 변수 사용법

- static 변수를 정의하고 사용하는 방법
- 리팩토링
  - 사용자에게서 입력 받는 기능을 메서드로 분리하여 정의(메서드 문법 활용)
  - 남, 녀 식별 값을 상수로 선언(final 문법 활용)


## 0.07v. 기능 단위로 명령문 묶기 : 메서드 사용법

- static 메서드를 정의하고 호출하는 방법 


## 0.06v. 조건문을 활용하여 실행 흐름을 제어하기

- if ~ else ~ 조건문 활용
- switch 활용
- break 활용 
- while 활용


## 0.05v. 배열과 반복문을 이용하여 여러 개의 데이터를 입력 받기

- 배열 활용
- 반복문 활용
- 상수 활용


## 0.04v. 키보드로 값을 입력 받기

- 키보드로 값을 입력 받는 방법


## 0.03v. 변수를 사용하여 데이터를 저장하기

- 변수를 사용하는 방법


## 0.02v. 리터럴을 사용하여 데이터 출력하기

- 리터럴을 사용하는 방법


## 0.01v. 자바 프로젝트 준비하기

- Gradle 빌드 도구를 이용하여 프로젝트 폴더를 구성하는 방법
  - `init` task 실행
- Gradle로 프로젝트를 빌드하고 실행하는 방법
  - `build`, `run` task 실행
































 























  
