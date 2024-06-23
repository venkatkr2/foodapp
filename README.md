## Food Item and Recipe Application

Spring Boot application that manages food items and recipes with a many-to-many relationship. 
The application uses PostgreSQL as the database and is containerized using Docker and Docker Compose.

#### Clone the Repository
```
git clone https://github.com/venkatkr2/foodapp.git
cd food-recipe-app
```

#### Build and Run the Application using Docker Compose
```
docker-compose build
docker-compose up
docker-compose down
```

This will start the Spring Boot application on http://localhost:8080 and PostgreSQL database on port 5432.

#### Database Initialization

The database is initialized using SQL scripts located in the `init-db` directory.

Sample data for food items and recipes is added during initialization.
- **Food Items**:
    - Tomato
    - Cheese
    - Basil
    - Garlic
    - Onion

- **Recipes**:
    - Pasta
    - Pizza
    - Salad
    - Soup
    - Sandwich

### API Endpoints
#### Create a Food Item

```http
POST /api/food-items
Accept: application/json
Content-Type: application/json

Request:
{
  "name": "Potato"
}

Response:
{
    "id": 6,
    "name": "Potato"
}
HTTP 201 Created
Location: http://localhost:8080/api/food-items/6

OR

Request:
{
    "name": "Potato",
    "recipes": [
      {
        "name": "Mashed Potato"
      },
      {
        "name": "French fries"
      }
    ]
}

Response:
{
    "id": 8,
    "name": "Potato",
    "recipes": [
        {
            "id": null,
            "name": "Mashed Potato"
        },
        {
            "id": null,
            "name": "French fries"
        }
    ]
}
HTTP 201 Created
Location: http://localhost:8080/api/food-items/8
```

#### Get Food Items

```
GET /api/food-items
GET /api/food-items/1
Accept: application/json
```

#### Update Food Item
```
PUT /api/food-items/1
Accept: application/json
Content-Type: application/json

{
  "name": "Cherry Tomato"
}

Response:
HTTP 200 OK
Content-Type: application/json

{
    "id": 1,
    "name": "Cherry Tomato",
    "recipes": [
        {
            "id": 3,
            "name": "Salad"
        },
        {
            "id": 1,
            "name": "Updated Pasta"
        },
        {
            "id": 2,
            "name": "Pizza"
        },
        {
            "id": 4,
            "name": "Soup"
        }
    ]
}
```
#### Delete Food Item
```
DELETE /api/food-items/1
```

#### Running Tests
```
mvn test
```
