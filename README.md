# Charity sale app
[_an Eesti Energia test assignment_](assignment.pdf)

---
### Overview
A single page store for selling food, clothes and toys.

1. Choose products
2. Add them to cart
3. Checkout
4. Pay by cash
5. Get change


Session-based shopping carts.
Mobile-friendly user interface.

Built using Java, Spring Boot, Postgres, Docker, React, TypeScript and Bootstrap.

---
### Building back-end
In root directory, to generate a JAR executive file at ``build/libs/<JAR>``, run:

    gradlew bootJar
To build a docker image:

    docker build -t charity_sale.jar .
Run docker containers in detached mode for spring boot back-end and postgres database:

    docker-compose up -d
List containers metadata (ids, names, running, ports, statuses etc):

    docker-compose ps
Stops and remove created images, containers, networks and volumes:

    docker-compose down

Back-end is located at http://localhost:8080/

---
### Building front-end
From root directory, navigate to the front-end folder:

    cd app
Install dependencies:

    npm install
To start  a development server:

    npm start
or for more optimized builds:

    npm build

Front-end is located at http://localhost:3000/

---
### Screenshots

![Food](./screenshots/food.PNG?raw=true)

---
![Cart](./screenshots/cart.PNG?raw=true)

---
![Checkout](./screenshots/checkout.PNG?raw=true)

---
![Change](./screenshots/change.PNG?raw=true)
