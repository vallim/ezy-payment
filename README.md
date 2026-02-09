# ezy-payment

## Overview

This application is developed using Spring Boot and offers two main functionalities:
1. Saving webhooks.
2. Saving payments and notifying all saved webhooks via HTTP when a payment is created.

The application leverages PostgreSQL for data storage and RabbitMQ for message brokering. You can access Swagger to view and use the respective APIs.

## Prerequisites

- Docker
- Docker Compose

## Running the Application

### Docker Compose

To run the application using Docker Compose, follow these steps:

1. Clone the repository:

    ```bash
    git clone git@github.com:vallim/ezy-payment.git
    cd ezy-payment
    ```

2. Start the application:

    ```bash
    docker-compose up
    ```

This command will start the following services:
- **app**: The Spring Boot application.
- **db**: PostgreSQL database.
- **rabbitmq**: RabbitMQ message broker with management UI.

### Accessing the Services

- **Spring Boot Application**: `http://localhost:8080`
- **RabbitMQ Management UI**: `http://localhost:15672` (default credentials: guest/guest)

## 📘 API Documentation (OpenAPI)

This project provides a complete OpenAPI 3 specification describing all available APIs,
including request/response schemas, HTTP status codes, and example payloads.

The OpenAPI specification file is available at the root of the project:

- **`openapi.yaml`**

This file can be used with tools such as:
- Swagger UI
- Swagger Editor
- Postman
- Insomnia

Additionally, during application runtime, the interactive Swagger UI is available at:

- `http://localhost:8080/swagger-ui.html`

The `openapi.yaml` file contains real examples for requests and responses,
ensuring easy understanding and testing of the API behavior.

## Testing APIs

1. Access Swagger UI: `http://localhost:8080/swagger-ui.html`
2. Save a webhook:
```json
{
  "callbackUrl": "http://localhost:8080/api-mock/success"
}
```
3. Save a payment:
```json
{
    "firstName": "Fabricio",
    "lastName": "Vallim",
    "zipCode": "16301352",
    "cardNumber": "4111111111111111"
}
```
