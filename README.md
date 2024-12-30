# Trello TeamProject 
---
## 🦹‍ 12조 3FT1 팀원소개
|<img src="https://avatars.githubusercontent.com/u/183330093?v=4" width="150" height="150"/>|<img src="https://avatars.githubusercontent.com/u/183330589?v=4" width="150" height="150"/>|<img src="https://avatars.githubusercontent.com/u/174124185?v=4" width="150" height="150"/>|<img src="https://avatars.githubusercontent.com/u/183378078?v=4" width="150" height="150"/>|
|:-:|:-:|:-:|:-:|
|강준혁|고강혁|천준민|장우태|
|kangjunhyuk1<br/>[@kangjunhyuk1](https://github.com/kangjunhyuk1)|Newbiekk<br/>[@Newbiekk-kkh](https://github.com/Newbiekk-kkh)|2unmini<br/>[@2unmini](https://github.com/2unmini)|jangutae<br/>[@jangutae](https://github.com/jangutae)|
---
## 🛠️ Tools :  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=github&logoColor=Green"> <img alt="Java" src ="https://img.shields.io/badge/Java-007396.svg?&style=for-the-badge&logo=Java&logoColor=white"/>  <img alt="Java" src ="https://img.shields.io/badge/intellijidea-000000.svg?&style=for-the-badge&logo=intellijidea&logoColor=white"/> ![Amazon S3](https://img.shields.io/badge/Amazon%20S3-FF9900?style=for-the-badge&logo=amazons3&logoColor=white) ![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white) ![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white) ![Slack](https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white)
- MySql 8.0
- Java 17
- SpringBoot 3.4.1
---
## 👨‍💻 Period : 2024/12/23 ~ 2024/12/30
---
## 👨‍💻 ERD
![image](https://github.com/user-attachments/assets/aba1e1ad-adc4-47e4-95e6-b416657efee3)


---
## 👨‍💻 API명세서

![플러스](https://github.com/user-attachments/assets/1833f1c6-2298-4244-bc2a-dbb4ecb02ff2)

---
## 👨‍💻 About Project

- 회원가입/로그인
  - Spring security, JWT 를 사용해 인증 구현
  - 아이디는 이메일 형식 
  - 탈퇴한 이메일 재사용 불가능 (회원 삭제를 STATUS ENUM 으로 관리)
  - 비밀번호는 대소문자 포함 영문 + 숫자 + 특수문자 최소 1글자씩 포함 
  - 역할(ADMIN, USER) 에 따라 권한 부여
    
- 워크스페이스
  - 제목, 설명, 슬랙URL 로 구성
  - ADMIN 역할인 회원만 생성 가능
  - 수정, 삭제 등 관리는 WORKSPACE 멤버역할을 가진 멤버만 가능
    
- 워크스페이스 멤버
  - 멤버 역할(WORKSPACE, BOARD, READ_ONLY)로 구성
  - ADMIN 역할을 가진 회원만 WORKSPACE 역할 부여 가능
  - 워크스페이스 멤버 초대는 WORKSPACE 역할만 가능
 
- 보드
  - READ_ONLY 역할을 제외한 워크스페이스의 멤버만 생성, 수정, 삭제 가능
  - 보드는 제목, 배경색, 이미지 설정 가능
  - 배경으로 쓸 이미지 업로드 기능 구현 (jpg, png, jpeg, gif 이외의 확장자는 업로드 불가능)
  - 단일 조회시, 보드에 속한 리스트, 카드 전체 조회 가능
 
- 리스트
  - READ_ONLY 역할을 제외한 워크스페이스의 멤버만 생성, 수정, 삭제 가능
  - 리스트는 제목, 순서 설정 가능
 
- 카드
  - READ_ONLY 역할을 제외한 워크스페이스의 멤버만 생성, 수정, 삭제 가능
  - 카드는 제목, 설명, 마감일, 담당자 멤버 등을 추가 가능
  - 카드의 제목, 내용, 마감일, 담당자 이름 등을 기준으로 페이징하여 검색 (쿼리DSL 활용)
  - 첨부파일 기능 구현 (첨부파일 업로드, 삭제, 조회) / 정해진 확장자(jpg, png, jpeg, gif, pdf, csv) 이외는 업로드 불가능
 
- 댓글
  - 카드 내에 댓글 작성 기능 ( 텍스트와 이모지 작성 )
  - READ_ONLY 역할을 제외한 워크스페이스의 멤버만 생성 가능
  - 댓글은 작성자만 수정, 삭제 가능

- 알림
  - 멤버 추가 / 카드 변경 / 댓글 작성 시 워크스페이스 생성시 등록했던 슬랙Url로 실시간 알림 제공
---

## 😭 아쉬운점
- 제출 당일 새벽 4시까지 팀원들 모두가 모여서 CI/CD 배포를 하려했으나, 지속되는 오류에 포기한 점이 아쉽습니다. 다음 최종 프로젝트에선 꼭 누락되는 기능 없이 구현해보고 싶습니다.
- Spring security 로 인증은 구현하였으나, 인가는 구현하지 못했습니다. 이또한 추가적인 학습 후에 구현해보고 싶습니다.
