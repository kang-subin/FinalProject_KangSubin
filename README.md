# [종합 프로젝트] 개인 프로젝트 - SNS 웹 페이지 구현<br>

>Swagger<br>
http://ec2-3-37-127-211.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui/

## 개발환경<br>
- Java 11
- Build : Gradle 7.5.1
- Framework : Springboot 2.7.5
- Database : MySQL 8.0
- CI & CD : GitLab
- Server : AWS EC2
- Deploy : Docker
- IDE : IntelliJ

## ERD<br>
<img src="/uploads/3ee4feca73f2d59582c0a8ae9ac010d5/erd.png" width="600" height="600" />

## Architecture<br>
<img src="/uploads/fc497eec3de47ac663462dbb7bb79de4/아키텍처.png" width="700" height="200" />


## 체크리스트
1. -[x] Gitlab CI & Crontab CD 구현
2. -[x] 회원가입 / 로그인 API 구현
3. -[x] 회원가입 / 로그인 테스트 코드 작성
4. -[x] 인증 / 인가 필터 구현
5. -[x] 포스트 작성 API 구현 / 테스트 코드 작성
6. -[x] 포스트 상세조회 / 삭제 API 구현
7. -[x] 포스트 상세조회 / 삭제 테스트 코드 작성
8. -[x] 포스트 목록 / 수정 API 구현
9. -[x] 포스트 목록 / 수정 테스트 코드 작성
10. -[x] 댓글 작성, 목록 API 구현
11. -[x] 댓글 수정 / 삭제 API 구현
12. -[x] 좋아요 누르기, 조회 API 구현
13. -[x] 마이피드, 알람 API 구현
14. -[x] 댓글 작성, 목록, 수정, 삭제 테스트 코드 작성
15. -[x] 좋아요, 마이피드, 알람 테스트 코드 작성



## 상세설명
1. Gitlab CI & Crontab CD 구현
> Gitlab CI & Crontab CD를 통해 git push 할 때마다 Swagger에 반영되도록 설정
2. 회원가입 / 로그인 API 구현
> [EndPoints] GET /api/v1/user/join , GET /api/v1/user/login<br>
3. 회원가입 / 로그인 테스트 코드 작성
>  Junit, Mockito 활용, UserControllerTest 구현
4. 인증 / 인가 필터 구현
> SpringSecurity, Jwt 활용 포스트 (작성, 수정, 삭제) 시 인증, 인가 필요
5. 포스트 작성 API 구현 / 테스트 코드 작성
> [EndPoints] POST /api/v1/posts , Junit, Mockito 활용, PostControllerTest, PostServiceTest 구현
6. 포스트 상세조회 / 삭제 API 구현
>[EndPoints] GET /api/v1/posts/{postId} , DELETE /api/v1/posts/{id}<br>
7. 포스트 상세조회 / 삭제 테스트 코드 작성
> Junit, Mockito 활용, PostControllerTest, PostServiceTest 구현
8. 포스트 목록 / 수정 API 구현
>[EndPoints] GET /api/v1/posts , PUT /api/v1/posts/{id}<br>
9. 포스트 목록 / 수정 테스트 코드 작성
> Junit, Mockito 활용, PostControllerTest, PostServiceTest 구현
10. 댓글 작성, 목록 API 구현
>[EndPoints] POST /api/v1/posts/{postId}/comments , GET /api/v1/posts/{postId}/comments
11. 댓글 수정 / 삭제 API 구현
>[EndPoints] PUT /api/v1/posts/{postId}/comments/{id} , DELETE /api/v1/posts/{postId}/comments/{id}  
12. 좋아요 누르기, 조회 API 구현
>[EndPoints] POST /api/v1/posts/{postId}/likes ,  GET /api/v1/posts/{postId}/likes
13. 마이피드, 알람 API 구현
>[EndPoints] GET /api/v1/posts/my , GET /api/v1/alarms
14. 댓글 작성, 목록, 수정, 삭제 테스트 코드 작성
> Junit, Mockito 활용, PostControllerTest, PostServiceTest 구현
15. 좋아요, 마이피드, 알람 테스트 코드 작성 
> Junit, Mockito 활용, PostControllerTest, PostServiceTest 구현, AlarmsControllerTest 구현

## Review
> [개선 필요사항]<br>
> dto 리팩토링<br>
> JwtFilter Exception 구현 <br>
> UserRole 구현
> 테스트 코드 중복되는 코드 리팩토링 필요
> Page 기능 검증을 위한 테스트 코드 작성 필요





