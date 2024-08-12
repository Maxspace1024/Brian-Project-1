CREATE TABLE `product_variant`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `colors_id` INT NOT NULL,
    `sizes_id` INT NOT NULL,
    `stock` INT NOT NULL,
    `product_id` INT NOT NULL
);
CREATE TABLE `orders`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT NOT NULL,
    `create_timestamp` DATETIME NOT NULL,
    `shipping` VARCHAR(255) NOT NULL,
    `payment` VARCHAR(255) NOT NULL,
    `subtotal` INT NOT NULL,
    `freight` INT NOT NULL,
    `total` INT NOT NULL,
    `rec_trade_id` VARCHAR(255) NOT NULL,
    `bank_transaction_id` VARCHAR(255) NOT NULL
);
CREATE TABLE `order_details`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `orders_id` INT NOT NULL,
    `product_id` INT NOT NULL,
    `variant_id` INT NOT NULL,
    `quantity` INT NOT NULL
);
CREATE TABLE `recipient`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `orders_id` INT NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `phone` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `address` VARCHAR(255) NOT NULL,
    `time` VARCHAR(255) NOT NULL
);
CREATE TABLE `hots`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `title` VARCHAR(255) NOT NULL
);
CREATE TABLE `campaign`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `product_id` INT NOT NULL,
    `picture` VARCHAR(255) NOT NULL,
    `story` TEXT NOT NULL
);
CREATE TABLE `colors`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `code` VARCHAR(255) NOT NULL
);
CREATE TABLE `hots_product`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `hots_id` INT NOT NULL,
    `product_id` INT NOT NULL
);
CREATE TABLE `product_image`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `image` VARCHAR(255) NOT NULL,
    `product_id` INT NOT NULL
);
CREATE TABLE `user`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `provider` VARCHAR(255) NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL UNIQUE,
    `password` TEXT NOT NULL,
    `picture` VARCHAR(255) NOT NULL
);
CREATE TABLE `role`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `role` VARCHAR(255) NOT NULL
);
CREATE TABLE `user_role`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `role_id` INT NOT NULL,
    `user_id` INT NOT NULL
);
CREATE TABLE `product`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `category` VARCHAR(255) NOT NULL,
    `title` VARCHAR(255) NOT NULL,
    `description` TEXT NOT NULL,
    `price` INT NOT NULL,
    `texture` VARCHAR(255) NOT NULL,
    `wash` VARCHAR(255) NOT NULL,
    `place` VARCHAR(255) NOT NULL,
    `note` VARCHAR(255) NOT NULL,
    `story` TEXT NOT NULL,
    `main_image` VARCHAR(255) NOT NULL
);
CREATE TABLE `sizes`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `size` VARCHAR(255) NOT NULL
);
ALTER TABLE
    `product_variant` ADD CONSTRAINT `product_variant_sizes_id_foreign` FOREIGN KEY(`sizes_id`) REFERENCES `sizes`(`id`);
ALTER TABLE
    `hots_product` ADD CONSTRAINT `hots_product_product_id_foreign` FOREIGN KEY(`product_id`) REFERENCES `product`(`id`);
ALTER TABLE
    `product_variant` ADD CONSTRAINT `product_variant_colors_id_foreign` FOREIGN KEY(`colors_id`) REFERENCES `colors`(`id`);
ALTER TABLE
    `product_variant` ADD CONSTRAINT `product_variant_product_id_foreign` FOREIGN KEY(`product_id`) REFERENCES `product`(`id`),
    ADD CONSTRAINT unique_variant UNIQUE (sizes_id, colors_id, product_id);
ALTER TABLE
    `hots_product` ADD CONSTRAINT `hots_product_hots_id_foreign` FOREIGN KEY(`hots_id`) REFERENCES `hots`(`id`);
ALTER TABLE
    `campaign` ADD CONSTRAINT `campaign_product_id_foreign` FOREIGN KEY(`product_id`) REFERENCES `product`(`id`);
ALTER TABLE
    `product_image` ADD CONSTRAINT `product_image_product_id_foreign` FOREIGN KEY(`product_id`) REFERENCES `product`(`id`);

ALTER TABLE
    `recipient` ADD CONSTRAINT `recipient_orders_id_foreign` FOREIGN KEY(`orders_id`) REFERENCES `orders`(`id`);
ALTER TABLE
    `orders` ADD CONSTRAINT `orders_user_id_foreign` FOREIGN KEY(`user_id`) REFERENCES `user`(`id`);
ALTER TABLE
    `order_details` ADD CONSTRAINT `order_details_orders_id_foreign` FOREIGN KEY(`orders_id`) REFERENCES `orders`(`id`);
ALTER TABLE
    `order_details` ADD CONSTRAINT `order_details_product_id_foreign` FOREIGN KEY(`product_id`) REFERENCES `product`(`id`);

ALTER TABLE
    `user_role` ADD CONSTRAINT `user_role_role_id_foreign` FOREIGN KEY(`role_id`) REFERENCES `role`(`id`);
ALTER TABLE
    `user_role` ADD CONSTRAINT `user_role_user_id_foreign` FOREIGN KEY(`user_id`) REFERENCES `user`(`id`);