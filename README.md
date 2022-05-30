# Техническая документация


<hr>

## Введение

<hr>

Приложение написано на Java 18 и Spring Boot 2.7.0 версии + MySql Database + Система сборки Gradle.   

<hr>

Приложение реализует API со следующими эндпоинтами:

   `POST /user/registration`  
   `DELETE /user/delete (queryParams: String username, String password)`

   `GET /account (queryParams: String username, String password)`  
   `POST /account/balance (queryParam: Integer id)`

   `GET /market`    
   `POST /market/addProduct (queryParams: String username, String Password)`  
   `POST /market/deal (queryParams: String username, String Password)`

<hr>

## Функционал

<hr>

### User:

1. Пользователь может зарегистрировать аккаунт, передав приложению username и password, а также необязательное boolean поле is_admin.
   Если is_admin равен true, то пользователь получает роль администратора в приложении.
2. Пользователь может удалить свой аккаунт, передав приложению username и password для подтверждения доступа.


<hr>

### Account:
1. Пользователь может посмотреть свой аккаунт, передав приложению username и password для подтверждения доступа
2. Пользователь может пополнить баланс любого аккаунта, передав приложению id аккаунта и необходимую сумму.

<hr>

### Product:
1. Пользователь может получить список всех имеющихся товаров в наличии.
2. Администратор может добавить новые товары в каталог, передав приложению username и password в для подтверждения доступа, а также данные товара - bookname, bookAuthor, price, amount.
3. Пользователь может совершить сделку с магазином, передав приложению username и password для подтверждения доступа, а также id и количество желаемого товара.

## API

<hr>

### `POST /user/registration`  
Необходимо предоставить JSON со следующими данными:  
>"username": {String} (required)  
>"password": {String} (required)  
>"is_admin": {Boolean} (optional)

Если запрос прошёл успешно, ожидается ответ с HTTP статусом 201 и ответом:
>"Регистрация прошла успешно"  

В ином случае 400

<hr>

### `DELETE /user/delete`
В URL необходимо указать следующие query params:
>"username": {String} (required)  
>"password": {String} (required)    

Если запрос прошёл успешно, ожидается ответ с HTTP статусом 200 и ответом:
>"Пользователь удалён"
 
В ином случае 400

<hr>

### `GET /account`
В URL необходимо указать следующие query params:
>"username": {String} (required)  
>"password": {String} (required)    

Если запрос прошёл успешно, ожидается ответ с HTTP статусом 200 и данными аккаунта в виде JSON.  

В ином случае 404

<hr>

### `POST /account/balance`
В URL необходимо указать следующие query params:
>"id": {Integer} (required)

И необходимо предоставить JSON со следующими данными:

>"balance": {Integer} (required)  

Если запрос прошёл успешно, ожидается ответ с HTTP статусом 200 и ответом:
>"Баланс пополнен на [balance] рублей!"

В ином случае 400

<hr>

### `GET /market` 
Если запрос прошёл успешно, ожидается ответ с HTTP статусом 200 и списком товаров в виде JSON.

<hr>

### `POST /market/addProduct`

В URL необходимо указать следующие query params:
>"username": {String} (required)  
>"password": {String} (required)

Если пользователь найден и его значение `is_admin` true, то доступ к запросу выдаётся, в противном случае `403 Forbidden`

Необходимо предоставить JSON со следующими данными:
>"bookName": {String} (required)  
>"bookAuthor": {String} (required)  
>"price": {Integer} (required)  
>"amount": {Integer} (required)  

Если запрос прошёл успешно, ожидается ответ с HTTP статусом 201 и ответом:
>"Товар добавлен в магазин" 

В ином случае 400

<hr>

### `POST /market/deal`

В URL необходимо указать следующие query params:
>"username": {String} (required)  
>"password": {String} (required)

И необходимо предоставить JSON со следующими данными:
>"id": {Integer} (required)  
>"amount": {Integer} (required)

Если запрос прошёл успешно, ожидается ответ с HTTP статусом 201 и ответом:
>"Сделка прошла успешно" 

В ином случае 400

<hr>

## Технические аспекты
Чтобы запустить проект, необходимо иметь mysql пользователя `root@root` и базу данных `bookstore`  
Собрать проект:`gradle build`


<hr>

## Планы на будущее:
Это не финальная версия приложения, в дальнейшем планируется обернуть `Java Spring Boot Apllication` и `MySQL` в `Docker`, написать тесты с помощью библиотеки `Junit`, а также разработать систему авторизации и добавить больше функционала.
