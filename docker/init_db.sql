
-- Create schema if not exists
CREATE SCHEMA IF NOT EXISTS food_schema;

-- Create the food_item table
CREATE TABLE food_schema.food_item (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Create the recipe table
CREATE TABLE food_schema.recipe (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Create food_item_recipe join table
CREATE TABLE food_schema.food_item_recipe (
    food_item_id BIGINT NOT NULL,
    recipe_id BIGINT NOT NULL,
    PRIMARY KEY (food_item_id, recipe_id),
    FOREIGN KEY (food_item_id) REFERENCES food_schema.food_item(id) ON DELETE CASCADE,
    FOREIGN KEY (recipe_id) REFERENCES food_schema.recipe(id) ON DELETE CASCADE
);


-- Insert test data
INSERT INTO food_schema.food_item (name) VALUES ('Tomato');
INSERT INTO food_schema.food_item (name) VALUES ('Cheese');
INSERT INTO food_schema.food_item (name) VALUES ('Basil');
INSERT INTO food_schema.food_item (name) VALUES ('Garlic');
INSERT INTO food_schema.food_item (name) VALUES ('Onion');

INSERT INTO food_schema.recipe (name) VALUES ('Pasta');
INSERT INTO food_schema.recipe (name) VALUES ('Pizza');
INSERT INTO food_schema.recipe (name) VALUES ('Salad');
INSERT INTO food_schema.recipe (name) VALUES ('Soup');
INSERT INTO food_schema.recipe (name) VALUES ('Sandwich');

-- Linking FoodItems to Pasta Recipe
INSERT INTO food_schema.food_item_recipe (food_item_id, recipe_id) VALUES (1, 1);
INSERT INTO food_schema.food_item_recipe (food_item_id, recipe_id) VALUES (2, 1);
INSERT INTO food_schema.food_item_recipe (food_item_id, recipe_id) VALUES (3, 1);

-- Linking FoodItems to Pizza Recipe
INSERT INTO food_schema.food_item_recipe (food_item_id, recipe_id) VALUES (1, 2);
INSERT INTO food_schema.food_item_recipe (food_item_id, recipe_id) VALUES (2, 2);
INSERT INTO food_schema.food_item_recipe (food_item_id, recipe_id) VALUES (4, 2);
INSERT INTO food_schema.food_item_recipe (food_item_id, recipe_id) VALUES (5, 2);

-- Linking FoodItems to Salad Recipe
INSERT INTO food_schema.food_item_recipe (food_item_id, recipe_id) VALUES (1, 3);
INSERT INTO food_schema.food_item_recipe (food_item_id, recipe_id) VALUES (3, 3);

-- Linking FoodItems to Soup Recipe
INSERT INTO food_schema.food_item_recipe (food_item_id, recipe_id) VALUES (1, 4);
INSERT INTO food_schema.food_item_recipe (food_item_id, recipe_id) VALUES (5, 4);

-- Linking FoodItems to Sandwich Recipe
INSERT INTO food_schema.food_item_recipe (food_item_id, recipe_id) VALUES (2, 5);
INSERT INTO food_schema.food_item_recipe (food_item_id, recipe_id) VALUES (3, 5);
INSERT INTO food_schema.food_item_recipe (food_item_id, recipe_id) VALUES (5, 5);
