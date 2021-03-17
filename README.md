# PurchaseServer
![Cart](https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ffreepngimg.com%2Fthumb%2Fcart%2F3-2-cart-png-hd.png&f=1&nofb=1)

Сервис покупки предоставляет 3 контроллера(адреса для взаимодействия), с собой, а именно:
* Контроллер для совершения покупок (BuyFeignController)
* Контроллер для получения информации о чеках (CheckController)
* Контроллер для получения информации о покупках (PurchaseController)

Сервис хранит в базе данных *чеки* и *покупки*(покупки имеют ссылку на свой чек).

Документация по адресу: `http://localhost:8081/swagger-ui.html`

Если пользователь пытается несколько раз купить товары и сервер с магазинами не отвечает, то 
circuit breaker (resilience4J), прерывает схему и ответы начинают приходить моментально.