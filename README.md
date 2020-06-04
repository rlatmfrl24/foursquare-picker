# Foursquare Picker

## Introduction

카카오페이 지원용 사전과제 프로젝트

## Concept

Foursquare Open API를 활용하여 현재 위치 주변의 추천 장소 리스트를 메인에 출력하고, 해당 아이템을 클릭할 때 해당 장소에 대한 사진과 상세 내용을 출력하는 어플리케이션 개발

## Endpoint

- GET `/v2/venue/explore` 입력된 좌표 주변의 추천 장소 리스트를 가져온다
- GET `/v2/venue/{VENUE_ID}` ID에 해당되는 장소의 상세정보를 가져온다

## Dependency

- Room
  - API로부터 가져온 추천 장소 데이티 리스트를 저장하고 관리하기 위한 Local DB Library
- Retrofit
  - REST API로부터 데이터를 요청하기 위한 API Library
- Koin
  - Dependency Injection Framework for Kotlin
- Material UI
  - Material Design 적용을 위한 UI Library
- RxKotlin
  - ReactiveX Programming Library
- Glide
  - Image View 처리를 위한 Libraray
