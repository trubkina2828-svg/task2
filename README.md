# Семинарское занятие №2-1

**ФИО:** Трубкина Н.А.

**Группа:** ПИ24-2в 

**Вариант:** Н–Т: марка (кириллица), по двум полям (год + марка), 30 дней;

**Ссылка на репозиторий:** 
---
## Необходимый функционал:
1) CRUD машины,
2) поиск,
3) сортировка по году выпуска,
4) гистограмма количества машин по дате постановки на учёт (по дням),
5) счётчик записей в таблице.

---
## Параметры объекта "машина":
 1) ID,
 2) Марка,
 3) Год выпуска,
 4) Дата постановки на учёт,
 5) ФИО владельца

##  pom.xml
Это главный файл Maven, в котором прописываются зависимости проекта. 

Проект рассчитан на Java 21.
``` 
    <properties>
        <java.version>21</java.version>
    </properties>
```
Подключаем возможности Spring для создания веб-приложения: 
``` 
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
```
Для работы с базой данных через Java:
``` 
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
```
Чтобы программа могла подключаться к MySQL, добавим:

``` 
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
```


---
## Структура репозитория

```
Library
└── src
    └── main
        ├── java
        │   └── ru.example
        │       └── library
        │           ├── LibraryApplication.java
        │           │
        │           ├── model
        │           │   └── Book.java
        │           │
        │           ├── repository
        │           │   └── BookRepository.java
        │           │
        │           ├── service
        │           │   └── BookService.java
        │           │
        │           └── controller
        │               └── BookController.java
        │
        └── resources
                    ├── application.properties
                    └── static         
                         ├── index.html
                         │
                         ├── script.js
                         │
                         └── style.css
```
