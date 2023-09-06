방명록 프로젝트
==========

## 💬 개요
모바일 청첩장 등에 활용할 수 있도록 방명록에 글을 게시 및 관리할 수 있는 API

----------

## 🛠 사용 기술
> `Language`: Java 17
>
> `Framework`: Spring Boot 3.1.2
> 
> `Database`: H2
> 
> `Dependencies`: Lombok, Spring Web, Thymeleaf, JDBC API, H2 Database

----------

## 💡 기능
`방명록 작성`: 이름, 비밀번호, 어느 측인지(신랑/신부), 글 내용을 작성함

`글 수정`: 비밀번호로 검증하고 이름, 어느 측인지, 글 내용을 수정할 수 있음

`글 삭제`: 비밀번호로 글을 삭제할 수 있음

`좋아요`: ip 당 한 글에 한 번 좋아요를 누를 수 있음

----------

## 🚪 REST API Reference

### `GET`
> `/posts` : 게시글 조회<br>
> - queryParam
> 1. author : 작성자 이름
> 2. side : 신랑 또는 신부
> 3. relationship : 관계 (가족, 친구, 직장동료, 지인)
> 
> ex) /posts?author=EH7&side=신랑&relationship=가족

### `POST`
> `/posts` : 게시글 작성<br>
> - Request Body
> 1. author : 작성자 이름
> 2. password : 게시글 비밀번호
> 3. side : 신랑 또는 신부
> 4. relationship : 관계 (가족, 친구, 직장동료, 지인)
> 5. content : 게시글 내용
> 
> ex)
> ```json
> {
>   "author": "작성자",
>   "password": "비밀번호",
>   "side": "신랑",
>   "relationship": "직장동료",
>   "content": "게시글 내용"
> }
> ```

> `/likes/{postId}` : 좋아요<br>
> 해당 postId의 게시글에 좋아요<br>
> ip에 따라 이미 좋아요를 눌렀는지 여부 확인 후<br>
> 누른적 없으면 좋아요<br>
> 누른적 있으면 좋아요 취소

### `PATCH`
> `/posts/{postId}` : 게시글 수정<br>
> - Request Body
> 1. author : 수정할 작성자 이름
> 2. password : 게시글 비밀번호
> 3. side : 수정할 신랑 또는 신부
> 4. relationship : 수정할 관계 (가족, 친구, 직장동료, 지인)
> 5. content : 수정할 게시글 내용
> 
> ex)
> ```json
> {
>   "author": "작성자",
>   "password": "비밀번호",
>   "side": "신랑",
>   "relationship": "직장동료",
>   "content": "게시글 내용"
> }
> ```

### `DELETE`
> `/posts/{postId}` : 게시글 수정<br>
> - Request Body
> 
>   비밀번호
> 
> ex)
> ```json
> 비밀번호
> ```

----------

## 🗂 패키지 구조

```bash
   eh7
    └─guestbook
        │  GuestbookApplication.java
        │
        ├─controller
        │  │  LikeController.java
        │  │  PostController.java
        │  │  ViewController.java
        │  │
        │  └─advice
        │          PostControllerAdvice.java
        │
        ├─domain
        │  │  Like.java
        │  │  Post.java
        │  │
        │  └─consts
        │          RelationshipConst.java
        │          SideConst.java
        │
        ├─exception
        │      ErrorResult.java
        │      IllegalConstException.java
        │      IllegalIdException.java
        │      IllegalPasswordException.java
        │
        ├─repository
        │  │  LikeRepository.java
        │  │  PostRepository.java
        │  │  PostSearchCond.java
        │  │
        │  ├─dto
        │  │      PostUpdateDto.java
        │  │
        │  └─jdbc
        │          JdbcLikeRepository.java
        │          JdbcPostRepository.java
        │
        └─service
                LikeService.java
                PostService.java
```

----------
