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

### `POST`

### `PATCH`

### `DELETE`

----------

## 🗂 패키지 구조

> - main
>   - java
>     - eh7
>       - guestbook
>         - config
>         - controller
>         - domain
>         - repository
>         - service

----------