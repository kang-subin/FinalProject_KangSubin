# [종합 프로젝트] 첫 번째 미션

---
[Swagger]<br>
http://ec2-3-37-127-211.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui/

---
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
---
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
> Junit, Mockito 활용, PostControllerTest, PostServiceTest 구현
6. 포스트 상세조회 / 삭제 API 구현
>[EndPoints] GET /api/v1/posts/{postId} , DELETE /api/v1/posts/{id}<br>
7. 포스트 상세조회 / 삭제 테스트 코드 작성
> Junit, Mockito 활용, PostControllerTest, PostServiceTest 구현
8. 포스트 목록 / 수정 API 구현
>[EndPoints] GET /api/v1/posts , PUT /api/v1/posts/{id}<br>
9. 포스트 목록 / 수정 테스트 코드 작성
> Junit, Mockito 활용, PostControllerTest, PostServiceTest 구현
---
## Review
> [개선사항]<br>
> 





