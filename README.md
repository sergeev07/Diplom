# Дипломная работа «Облачное хранилище»

### Стек
Spring Boot, Spring Data JPA, PostgreSQL, JUnit, Mockito, Maven, Docker.

### Описание
Приложение предоставляет собой REST сервис, который позволяе пользователям работать с файлами.
В сервисе реализованы следующие методы:
 - Вывод списка файлов.
 - Добавление файла.
 - Удаление файла.
 - Авторизация.

Все запросы к сервису авторизованы. Для аутентификации в приложении необходимо сообщить на эндпоинт /login 
данные пользователя: login и password. Если пользователь найден в БД, ему будет предоставлен токен, 
дающий возможность вызова функционала сервиса.

Настройки приложения находятся в файле application.yml.
Настройка имени схемы базы данных в параметре 'spring.liquibase.default-schema'.



