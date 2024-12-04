# 66192877
## SK m&amp;service 2024 동계 채용연계형 코딩 테스트
> 선택 언어 및 프레임 워크 : Java & SpringBoot

#### 기술 요구 사항
- **언어 및 프레임워크 :** Java 17, Spring Boot 3.x
- **DB :** H2 Database
- **템플릿 엔진 및 UI(선택) :** Thymleaf, Vue.js/Flutter/React Native
- **기타 :** Spring Security, Spring Data JPA, Maven/Gradle

</br>

### 0. 빠르게 실행 하기
- 로그인을 해서 글쓰기를 하려면 아래 환경을 먼저 선 실행을 해야 합니다.
     - 먼저 POSTMAN과 같은 테스트 환경을 킵니다.
     - `localhost:8080/api/member/register`로 `POST`로 설정한 이후 아래와 같이 raw - JSON 형식으로 지정 후 실행한 이후 로그인을 하면 실행됩니다.
```
{
    "id" : "test",
    "password" : "test1202!",
    "name" : "kim test"
}
```

### 구현 과제 체크리스트
**로그인**
- [x] 아이디, 패스워드 입력받아 DB값과 일치 여부에 따라 로그인 처리

**로그아웃**
- [x] 로그인 상태에서 로그아웃 클릭 시 로그아웃
- [x] 로그인 상태에서 브라우저를 종료 시 로그아웃 처리

**게시판 목록**
- [x] 제목, 작성자ID, 조회수, 첨부파일여부, 등록일 노출 Default 등록일 역순 정렬
- [x] 한페이지당 10개 목록 기준으로 페이징 처리
- [x] 제목이나 작성자ID로 게시글 검색 가능

**게시판 보기**
- [x] 제목, 작성자ID, 작성일, 조회수, 게시글 노출
- [x] 게시물 보기 클릭 시 조회 수 증가

**게시판 작성**
- [x] 로그인 상태에서만 게시물 작성 가능
- [x] 제목, 게시글 작성
- [ ] Editor 기능을 활용한 내용 작성 및 파일 추가 (파일, 이미지)

**게시판 수정/삭제**
- [x] 본인의 글만 수정/삭제 가능

### 1. 요구사항 분석

#### 📃 기능
1. 사용자는 **로그인**을 할 수 있어야 한다.
2. 사용자는 **로그아웃**을 할 수 있어야 한다.
3. 사용자는 **게시판 목록**을 확인할 수 있어야 한다.
4. 사용자는 **게시판 보기**를 할 수 있어야 한다.
5. 사용자는 **게시판 작성**을 할 수 있어야 한다.
6. 사용자는 **게시물 수정/삭제**를 할 수 있어야 한다.

</br>

### 2. 데이터 모델링 설계
![SK m service (1)](https://github.com/user-attachments/assets/bf12b267-a9d4-407a-8918-b33f0a5f4813)

</br>

### 3. API 설계
| function | url | http api |
|----------|-----|----------|
| 회원가입 | /api/member/register | POST |
| 로그인 | /api/member/login | POST, GET |
| 로그아웃 | /api/member/logout | GET |
| 게시판 목록 | /api/board | GET |
| 게시판 상세 보기 | /api/board/{boardId} | GET |
| 게시판 작성 | /api/board/write | GET, POST |
| 게시판 수정 | /api/board/edit/{boardId} | GET, PUT |
| 게시판 삭제 | /api/board/delete/{boardId} | DELETE |
| 게시판 검색 | /api/board/search | REQUEST |

</br>

### 4. Convention 설계 및 초기 세팅
- [ Convention 관련 wiki 링크 ](https://github.com/K-0joo/66192877/wiki/Coding-Conventions)
- [ 초기 세팅 관련 wiki 링크 ](https://github.com/K-0joo/66192877/wiki/Initialize-Setting-About-Spring-Boot)

</br>

### 5. 구현 과정
1. 회원가입 - 해당 파트만 비밀번호 암호화 때문에 POSTMAN으로 진행
- URL
```
localhost:8080/api/member/register
```

- Success
```
{
    "data": {
        "memberId": "ae338b7c-8b37-430a-b625-c441d47097b5",
        "id": "test",
        "name": "kim test"
    },
    "status": 200,
    "code": "U002",
    "message": "회원가입에 성공하였습니다."
}
```

- Fail - 이미 회원가입 한 회원
```
{
    "code": "U007",
    "status": 400,
    "message": "이미 가입된 유저입니다."
}
```

</br>

2. 로그인 - 로그인부터 화면을 통해 진행하였습니다.
- 로그인 화면
![image](https://github.com/user-attachments/assets/b3cddeca-ac53-4024-8f20-9411180e664e)

- 로그인 입력 화면 
![image](https://github.com/user-attachments/assets/be4dbacb-931c-4e23-9436-6fe39aa0fd4b)

- 로그인 이후 이동된 게시판 리스트 화면
![image](https://github.com/user-attachments/assets/ce77efd6-f7ea-47ee-82ba-433d9d844302)


</br>

3. 로그아웃
- logout은 버튼을 누르면 로그인 화면으로 이동되며 {url}/api/board로 가면 로그인하기 버튼이 떠있습니다.
- 전체 화면을 끄면 자동으로 게시판 목록에 로그인하기 버튼이 떠있습니다.
![image](https://github.com/user-attachments/assets/c6523cc1-6197-4475-8fff-b503de449a58)


</br>

4. 게시판 목록
- {url}/api/board로 가면 GET을 통하여 화면 리스트가 나타납니다.
![image](https://github.com/user-attachments/assets/5e8b863b-924e-423d-bd2c-040365129907)

</br>

5. 게시판 상세 보기
- 해당 링크를 통해 들어가게 되면 자동으로 조회수가 증가합니다.
- 조회 수 증가 후 게시판 목록에 반영됩니다.
![image](https://github.com/user-attachments/assets/7c0cf0ac-fb1b-4cf6-a3d3-6c85de5dbaac)

</br>

6. 게시판 작성
- 로그인 시에만 글쓰기 버튼이 활성화 됩니다.
- 해당 버튼을 누르면 아래의 화면으로 가게 되며 입력 후 저장하기를 누르면 저장이 성공적으로 이뤄졌을 시 뷰어로 출력됩니다.

- 게시판 작성을 할 수 있는 화면입니다.
![image](https://github.com/user-attachments/assets/363c6a28-8fb3-4fce-9c10-b1b0130435fd)

- 게시판 작성 후 화면입니다.
![image](https://github.com/user-attachments/assets/2bfc90be-23ab-4c3f-bc01-f70cffbd5c4f)

</br>

7. 게시판 수정
- 수정하기를 누르면 수정하는 화면이 나오며 제목과 내용을 수정할 수 있습니다.
- 수정이 완료되었다는 알람이 뜨며 뷰어 화면으로 전환됩니다.
  
- 수정할 수 있는 화면입니다.
![image](https://github.com/user-attachments/assets/5de4c881-4e18-42d3-adab-4bd62725587f)

- 수정한 이후 화면입니다.
![image](https://github.com/user-attachments/assets/bba8ab4c-dc8e-498a-b1b9-458cd2865349)

</br>

8. 게시판 삭제
- 게시판 상세 보기 란에서 삭제하기를 누르면 삭제가 됩니다.
- 로그인 한 사용자만 수정 및 삭제하기 버튼이 활성화됩니다.

- 삭제 전 리스트 목록 
![image](https://github.com/user-attachments/assets/d206a081-175f-428f-a27c-19e6bcb4a0d1)

- 게시판 상세에서 삭제 버튼 클릭
![image](https://github.com/user-attachments/assets/f4a4b61e-c67e-4175-9f15-8ae146c96721)

- 삭제 후 리스트 목록
![image](https://github.com/user-attachments/assets/ca0d3f4f-3aed-4e10-8d41-6f34166319b2)

</br>

9. 게시판 검색
- 정확한 제목과 작성자 명으로만 검색이 됩니다.
![image](https://github.com/user-attachments/assets/78d7db79-0227-48e7-b3ef-db1a197b25d6)
- 게시글 제목으로 검색 시
![image](https://github.com/user-attachments/assets/cc954e07-fee5-49ba-8178-32e601b8ba87)
- 작성자로 검색 시
![image](https://github.com/user-attachments/assets/015295be-c9e9-4845-b7de-b1c929e5f885)

