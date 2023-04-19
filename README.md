
# 광고 추천 로직 구현하기
광고 데이터는 [ad_campaigns.json](./resources/ad_campaigns.json) 파일에서 확인할 수 있습니다. 데이터베이스에 주어진 파일을 임포트 하여 사용합니다. 파일에는 다음과 같은 정보가 담겨있습니다.

| Key              | Value                                                                  |
| ---------------- | ---------------------------------------------------------------------- |
| id               | 광고를 구분하는 고유 아이디.                                                  |
| name             | 광고의 이름                                                               |
| image_url        | 유저에게 보일 광고의 이미지 주소.                                              |
| landing_url      | 광고를 클릭했을 때 최종으로 유저가 랜딩 되어야 할 광고주 페이지.                       |
| weight           | 광고의 송출 가중치로 값이 클수록 첫 번째에 보일 확률이 높습니다.                     |
| target_country   | 광고가 송출 가능한 국가 정보입니다.                                             |
| target_gender    | 광고의 성별 타게팅 정보입니다. M: 남자 타게팅, F: 여자 타게팅                       |
| reward           | 광고를 클릭했을 때 받을 수 있는 리워드입니다.                                     |

광고 송출 요청은 다음과 같은 정보가 포함됩니다.

- 유저의 id (Integer)
- 유저의 성별
- 유저의 국가

광고 송출에 대한 응답에는 다음과 같은 정보가 포함됩니다.

- 광고의 image_url
- 광고의 landing_url
- 광고의 reward

광고 송출은 다음과 같은 조건을 만족해야 합니다.

- 광고 정보의 country, gender 조건에 맞는 광고만 송출됩니다.
- 광고는 한 번에 최대 3개까지 송출이 되며 3개의 순서는 weight 값에 의해 정해집니다.

## 개발 목표
CTR(Click-Through Rate 광고 노출시 클릭할 확률) 기반의 광고 추천 로직을 포함하여 여러 가지 광고 순서를 정하는 정책이 구현.<br/>
다음과 같은 정책들이 있습니다.

| 이름 | 설명 |
| - | - |
| random | 랜덤으로 정렬하는 정책 |
| weight | weight(중요도) 기반의 정책 |
| pctr | 예측된 CTR의 내림차순으로 정렬하는 정책 |
| weight_pctr_mixed | 예측된 CTR이 가장 높은 광고를 첫 번째에 위치하고 나머지 두 광고는 weight 기반으로 정렬하는 정책 |
<br/>

## 개발 환경
| 이름 | 설명 |
| - | - |
| 개발언어 | Java(jdk17) |
| 프레임워크 | Spring Boot-3.03 |
| 빌드툴 | Gradle7.6 |
| DB | MySQL8.0 |
<br/>

## 개발 아키텍처<br/>
로직의 변경 및 확장이 용이하도록 hexagonal architecture 적용
<br/>

![hexagonal-architecture_hu6764515d7030d45af6f7f498c79e292b_50897_956x0_resize_box_3 (1)](https://user-images.githubusercontent.com/76391989/222963583-31b4c334-1f44-4995-8cd9-bc0dd56819bf.png)
<br/>(노란색 음영: part2에만 추가된 컴포넌트)
| 이름 | 설명 |
| - | - |
| Web Adapter | 사용자 요청에 따라 알맞는 광고를 매핑 시켜주는 컨트롤러(Rest api 구현) |
| Input Port(Web adapter) | Adapter와 비즈니스 로직 사이에 있는 Interface(AdvertisementService interface) |
| Advertisement Service | 도메인 객체 기반 비즈니스 로직을 구현해놓은 서비스 객체(AdvertisementServiceImpl2) |
| Entity | 다른 객체에 대한 의존성 없이 독립적으로 존재하는 핵심 도메인 객체(Advertisment) |
| Output port | DB와 비즈니스 구현 객체 사이에 있는 Interface(Advertisement Repository) |
| Persistence Adapter | Output port에 정의되어 있는 인터페이스를 구현하는 Adapter(JPA repository) |
| External System Adapter | 주어진 url parameter 값에 따라 pctr값을 보내주는 외부 API(Webclient)  |
| Input Port(External) | Webclient로 받아온 Json file을 Object file로 변환시키기 위해 필요한 객체(Response) |
| External API service | 외부 API에서 받아온 JSON file을 비즈니스 로직에 따라 처리하는 서비스 객체  |


비즈니스 로직과 실제 구현 사이에 인터페이스를 구현해놓음으로써 비즈니스 로직의 변경이나 기반 Adapter의 변경에 대한 영향을 최소화<br/>

소프트웨어 전체적인 구조의 변경 없이 비즈니스 로직이나 기반 Adapter 기술 변경 용이

## 구현 방법

### 제공하는 API
Get method(user id, gender, country -> user id에 따른 광고 송출)
```shell
#user id, gender, country url 파라미터로 전달
http://localhost:8080/user?id=${user.id}&gender=${gender}&country=${country}
```
<br/>
Swagger Api docs<br/>
Swagger 는 REST API를 설계, 빌드, 문서화 및 사용하는 데 도움이되는 OpenAPI 사양을 중심으로 구축 된 오픈 소스 도구 세트입니다.<br/>
API 명세 확인 및 동작 테스트 가능<br/>
http://localhost:8080/swagger-ui/index.html
<br/>
![image](https://user-images.githubusercontent.com/76391989/222969990-c37d70bf-2ace-4540-aab4-a7a04dedbd60.png)

<br/>

<br/>

### 서버 띄우기(Docker)

```bash
#docker-compose.yml 파일이 있는 디렉토리로 이동
cd hjlim4u-naver.com/Assignment2
docker-compose up
```
<br/>
※ docker-compose.yml 파일에서 mysql(3306)과 nginx(80) 모두 기존에 사용했던 포트를 사용하도록 설정하였습니다. 때문에 로컬에 mysql과 nginx프로그램이 깔려있다면 해당 포트를 사용하고 있는 mysql과 nginx를 중지시키고 실행시켜야합니다.<br/>
프로세스 중지 방법<br/>
Windows : window키->service 입력-> mysql, nginx 서비스 중지<br/>
Linux : sudo lsof -i:[port 번호] -> 해당 포트번호를 사용하고 있는 프로세스의 pid 조회 -> sudo kill -9 [조회한 pid]<br/>

# 비즈니스 로직(광고 송출 로직 변경 방법)

## Policy 객체 다이어그램
![Classdiagram drawio](https://user-images.githubusercontent.com/76391989/230920431-efce8500-fcde-4428-986a-36bc8603a120.png)
| 이름 | 설명 |
| - | - |
| PolicyState | 광고 송출 정책의 인터페이스 |
| ExternalAPIPolicy | PolicyState를 상속하면서 외부 API 요청 처리를 구현해놓은 추상 클래스(Template method pattern) |
| Weight | Weight 기반 광고 송출 정책 처리 |
| PCTR/WeightPCTRMixed | ExternalAPIPolicy에서 구현한 외부 API 처리 결과에 대한 후처리 구현 |
| WeightPCTRMixed | 외부API 요청 처리, weight기반 처리는 Weight 객체에 위임 |
| PolicyConfig | 생성된 송출정책들을 기반하여 유저ID 기반 송출정책 로직 결정 |

확장성을 고려하여 External API Policy는 외부API에 요청하여 PCTR값이 높은 광고의 id값을 가져오는 것까지 수행하고 나머지 후처리는 템플릿 메서드 패턴을 활용하여 자식 클래스에 구현을 맡긴다.<br/>
광고 송출을 하는데 있어 필요한 유저 정보(UserRequest)는 스프링 컨테이너의 자동주입을 통해 자동으로 유저 정보들을 활용할 수 있도록 설정하였습니다.<br/>
광고 송출 정책을 결정하는 PolicyConfig 클래스는 PolicyState라는 인터페이스에만 의존하기 때문에 PolicyConfig 클래스 수정 없이 언제든지 PolicyState 인터페이스를 구현한 정책들의 추가 확장이 용이합니다.<br/>

## 광고 송출 로직 변경
1. PolicyState나 ExternalPolicy를 구현한 광고 송출 정책 추가
2. PolicyConfig에 1번단계에서 추가했던 광고 송출 정책 추가
3. 새로 추가된 송출 정책은 PolicyState 인터페이스를 구현하기 때문에 PolicyConfig Map에는 자동 주입된 상태입니다. 때문에 해당 맵 자료구조에서 해당 빈의 이름으로 조회하면 해당 송출 객체를 참조할 수 있습니다.<br/>

※ Part2 구현 프로젝트 또한 Part1 프로젝트의 로직 구현 객체만 변경하여 확장<br/>
해당 비즈니스 로직 구현 객체는 Advertisement 도메인 객체와 Output port인 repository 객체에 의존하고 있기에 구현 기반 객체인 데이터베이스 객체 및 Web Adapter인 Controller 코드 변경할 필요가 없다.
<br/>


# 트래픽 증가, 데이터 증가에 따른 성능 문제 고려
#### 1. Redis<br/>
Redis는 key,value 쌍의 데이터를 저장하는 인메모리 데이터베이스입니다. <br/> 
일반적으로 데이터베이스 앞단에서 데이터베이스 부하를 줄여주기 위한 캐시로 쓰입니다. <br/>
외부 url 요청에 대한 응답을 저장하기 위한 캐시로 사용하려 했으나 동일한 user id 및 광고 id에 대한 요청임에도 pctr값이 실시간으로 변경<br/>
캐쉬로 쓰이면 오히려 메모리 접근을 한 번 더 하는 부작용 발생 우려 <br/>

#### 2. Webclient<br/>
외부 url에 대한 요청과 DB 조회 작업을 비동기적으로 실행하기 위해 Webclient 채택<br/>
Webflux는 Spring5에 새롭게 추가된 Reactive-stack의 웹 프레임워크<br/>
클라이언트/서버에서 리액티브 어플리케이션 개발을 위한 Non-blocking reactive stream <br/>
non blocking stream을 통하여 적은 수의 스레드만으로 효율적으로 다수의 요청을 처리 할 수 있습니다.<br/>
![image](https://user-images.githubusercontent.com/76391989/222969920-7ec32cec-7bff-4eef-8915-2a7daa1725ec.png)
<br/>

#### 3. Nginx<br/>
서버의 과부화를 막기 위해 서버 앞단에 프록시 서버 구축<br/>
![image](https://user-images.githubusercontent.com/76391989/222968621-65a4f7f9-94f3-4abc-bf06-4211a43483e9.png)<br/>
Nginx로 Reverse-Proxy 서버를 구축함으로써 다음과 같은 효과를 얻을 수 있다<br/><br/>
1.로드 밸런싱: 특정 서버에 요청이 집중되는 것을 분산시킴으로써 서버의 과부화를 방지합니다.<br/>
2.보안: 클라이언트에게 호스트의 정보(ip 주소, 포트 번호 등)을 노출시키지 않음으로써 안전하게 서비스를 제공할 수 있게 합니다.<br/>
3.캐싱: 미리 랜더링된 페이지를 캐시하여 페이지 로드 시간을 단축시킬 수 있습니다.<br/>
본 프로젝트에서는 nginx 설정을 추가함으로써 커넥션 수를 조절합니다.

