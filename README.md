# 🚀 Spring Security 6 JWT Authentication

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.0%2B-brightgreen?style=flat-square)
![Maven](https://img.shields.io/badge/Maven-3.8%2B-blue?style=flat-square)
![MySQL](https://img.shields.io/badge/MySQL-8.0-lightblue?style=flat-square)

Современное приложение для аутентификации с использованием JWT-токенов и Spring Security 6.

## ✨ Особенности

- 🔐 JWT-аутентификация с Spring Security 6
- 🗄️ Хранение пользователей в MySQL
- 🛡️ Автоматическая генерация CSRF-токенов
- ⚡ Кэширование пользователей для производительности
- 📦 CRUD-операции с продуктами
- 🎯 Совместимость с Java 21

## 🛠 Технологии

- **Java 21**
- **Spring Boot 3.0+**
- **Spring Security 6**
- **JJWT** (Java JWT)
- **MySQL Database**
- **Maven**

## 📦 Зависимости

Основные зависимости:
- `spring-boot-starter-web`
- `spring-boot-starter-security`
- `spring-boot-starter-data-jpa`
- `mysql-connector-j`
- `jjwt-api` (0.12.1+)
- `lombok`

## 🚀 Быстрый старт

### Предварительные условия
- Java 21
- MySQL 8.0+
- Maven 3.8+

### Установка

1. Клонируйте репозиторий:
```bash
git clone https://github.com/DKAVrZoV65F/spring-security-6.git
cd security-6
```

2. Настройте базу данных в `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/security_users
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3. Запустите приложение:
```bash
mvn spring-boot:run
```

## 📡 API Endpoints

### 🔓 Публичные endpoints
- `POST /register` - Регистрация нового пользователя
- `POST /loginn` - Аутентификация и получение JWT-токена
- `GET /csrf` - Получение CSRF-токена

### 🔐 Защищенные endpoints
- `GET /product` - Получить все продукты
- `POST /product` - Добавить новый продукт (требуется аутентификация)

## 🎮 Использование

### 1. Регистрация пользователя
```bash
curl -X POST http://localhost:8080/register \
  -H "Content-Type: application/json" \
  -d '{"username":"user1", "password":"password1"}'
```

### 2. Аутентификация
```bash
curl -X POST http://localhost:8080/loginn \
  -H "Content-Type: application/json" \
  -d '{"username":"user1", "password":"password1"}'
```

Ответ содержит JWT-токен:
```json
"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

### 3. Доступ к защищенным ресурсам
```bash
curl -X GET http://localhost:8080/product \
  -H "Authorization: Bearer ваш_jwt_токен"
```

## 🏗 Структура проекта

```
src/
├── main/
│   ├── java/org/spring/security6/
│   │   ├── config/          # Конфигурация Security
│   │   ├── controller/      # REST контроллеры
│   │   ├── entity/          # Сущности БД
│   │   ├── repository/      # JPA репозитории
│   │   └── service/         # Бизнес-логика
│   └── resources/
│       └── application.properties
```

## ⚙️ Конфигурация

Основные настройки в `application.properties`:
```properties
# Базовая аутентификация
spring.security.user.name=user
spring.security.user.password=user

# Настройки БД
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
```

## 🔒 Безопасность

- Пароли хэшируются с помощью BCrypt (4 раунда)
- JWT-токены имеют срок действия 10 минут
- Используется кэширование пользователей для уменьшения запросов к БД
- Реализована защита от CSRF-атак

## 📝 Логирование

Приложение логирует:
- Время загрузки пользователей из БД
- Попытки аутентификации
- Ошибки валидации JWT

## 🤝 Разработка

Для разработки с использованием IntelliJ IDEA убедитесь, что:
- Установлен Lombok Plugin
- Включена аннотационная обработка
- Используется Java 21

---

## 📄 Лицензия

Этот проект создан в учебных целях.
