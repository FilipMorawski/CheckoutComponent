# Checkout Component

Backend API for managing shopping carts in online shop. It provides functionalities like creating shopping cart, adding products, and closing carts with calculated cost and discounts. It keeps carts state in MySQL database. Products database is initialized by data.sql file.

## Documentation

Documentation is provided by Swagger UI, please enter link below,

* [REST Api Documentation](https://app.swaggerhub.com/apis/Filip-Morawski/checkout-component_rest_api/1.0) - SwaggerHub

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.
Installation requires Maven

### Get Repository

```
git clone https://github.com/FilipMorawski/CheckoutComponent
```

## Testing and Running

Before running tests or install user should provide database connection settings for hibernate through application.properties file.

### Test

```
mvn test
```

### Installing

```
mvn clean install
```
After that, user can execute jar. file located in target directory


## Built With

* SpringBoot
* Hibernate
* Swagger
* MySQL
* Mockito
* jUnit
* Maven

## From author

API has got discount mechanism which is implementation of visitor design pattern. Because of it, mechanism will be easy to extend to another discount type or it can be easily expanded for products individually. Discounts are calculated when user decide to close basket.
Database connection information is stored in application.properties file and all settings can be changed by swithing files.
Also initial products list is loaded from data.sql file.
