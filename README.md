# job4j_todo

![img.png](img\img.png)

![img_1.png](img\img_1.png)

![img_2.png](img\img_2.png)

## О проекте
Это веб-приложение ToDo List.
Есть список заданий.

У каждого задания есть:
- идентификационный номер
- текстовое описание
- дата создания
- статус (выполнено/новое)

Пользователь может:
- фильтровать задания по статусу
- создавать новые задания
- менять описания
- отмечать задания выполненными.

В приложении создается один объект SessionFactory. Он загружается через Spring Context.
TaskStore принимает объект SessionFactory через конструктор.

## Использованные технологии
- Java 17
- Maven 4.0.0
- Spring boot 2.7.3
- PostgreSQL 42.2.9
- Liquibase 4.15.0
- Hibernate 5.6.11.Final
- Lombok 1.18.22
- Thymeleaf
- Bootstrap

## Настройка окружения
Установить:
- PostgreSQL
- JDK
- Maven

## Запуск проекта
Создать базу данных ```createdb --username=ИМЯ todo```

Запустить командой ```mvn spring-boot:run```

Перейти по ссылке http://localhost:8080/tasks