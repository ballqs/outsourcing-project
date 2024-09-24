# Spring 아웃소싱 프로젝트

학습했던 Spring 기능들을 적극 활용하여 프로젝트를 진행합니다.

## ⌚ 프로젝트 기간
* 24.09.19일 - 24.09.25일

## 💻 프로젝트 소개

* 🖇️ 여러분은 최근 `배달 어플리케이션` 개발에 대한 아웃소싱 프로젝트를 맡게 되었습니다. 

* 🏁 클라이언트는 배달 시장에 새롭게 진출하려는 스타트업으로, 내부에 개발 인력이 부족한 상황에서 전체 개발 과정을 외주로 맡기기로 결정했습니다. 

* 🚨 기한은 단 일주일입니다! 빨리 개발하지 않으면 클라이언트에게 위약금을 물어주어야합니다!


## 📚 Stack
### Environment
<img src="https://img.shields.io/badge/IntelliJ-221E68?style=for-the-badge&logo=IntelliJ IDEA&logoColor=white"/> <img alt="GitHub" src ="https://img.shields.io/badge/GitHub-181717.svg?&style=for-the-badge&logo=GitHub&logoColor=white"/>

### Development
 <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white"/> <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"/>
 <img src="https://img.shields.io/badge/Spring JPA-6DB33F?style=for-the-badge&logo=JPA&logoColor=white"/>
 <img src="https://img.shields.io/badge/Spring JWT-FBBA00?style=for-the-badge&logo=JWT&logoColor=white"/> 

### Communication
 <img src="https://img.shields.io/badge/slack-4A154B?style=for-the-badge&logo=slack&logoColor=white"> <img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white">


## 🏠 멤버 구성 및 기능 구현

| 황호진 | 김도균 | 변영덕 | 박한진 | 조성래 |
|:---:|:---:|:---:|:---:|:---:|
| [@ballqs](https://github.com/ballqs) | [@gyun97](https://github.com/gyun97) | [@zerodeok](https://github.com/zerodeok) | [@kanzinPark](https://github.com/kanzinPark) | [@Sungrae-kogi](https://github.com/Sungrae-kogi) |

## 🤝 역할 분담
* 변영덕 : 회원가입 / 로그인, JWT 인증/인가
* 황호진 : 주문, 주문 AOP, 장바구니
* 김도균 : 가게
* 박한진 : 리뷰 생성 / 리뷰 조회
* 조성래 : 메뉴
<br>

## 🚩 기능 구현

### 회원가입 / 로그인
 * 회원가입
 * 회원탈퇴
 * 로그인

### 가게
 * 가게 생성 / 수정
 * 가게 조회
 * 가게 폐업

### 메뉴
 * 메뉴 생성 / 수정
 * 메뉴를 단독으로 조회할 수는 없으며, 가게 조회 시 함께 조회됩니다.
 * 메뉴 삭제
   
### 주문
 * 고객은 메뉴를 주문할 수 있습니다.
 * 사장님은 주문을 수락할 수 있으며, 배달이 완료될 때까지의 모든 상태를 순서대로 변경 합니다.
 * 주문 요청 및 상태 변경
   
### 리뷰
 * 리뷰 생성
 * 리뷰 조회
<br>
   
## ☁ 와이어프레임
https://www.figma.com/file/onGfiOKoB3fKJr75vYb4tL?type=design%27&node-id=0:1&mode=design
## ☁ ERD 다이어그램
![image](https://github.com/user-attachments/assets/e9348aeb-368f-409b-9a30-ac0ec2709eb3)

## 📑 API 명세서
![api1](https://github.com/user-attachments/assets/833fd8bc-9e07-45e1-9c67-afd31528d644)
![api2](https://github.com/user-attachments/assets/aaee2c6d-1bf4-4208-af9d-dcf2de280e8a)
![api3](https://github.com/user-attachments/assets/d14f8c31-0fdc-48a2-85cd-74cacc7e1f63)
![api4](https://github.com/user-attachments/assets/fbc7c387-1e52-43ee-aafb-a9f094f42303)
![api5](https://github.com/user-attachments/assets/11722a4c-1971-462e-a45b-bb7f898b8fb6)
![api6](https://github.com/user-attachments/assets/fa5c22e5-856b-4aae-a806-9d33b3cba3dd)



