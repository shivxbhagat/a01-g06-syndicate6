-- Drop existing tables if they exist (optional, use with caution)
DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS ITEMS;
DROP TABLE IF EXISTS SAVED_ITEMS;

-- Create USERS table
CREATE TABLE USERS (
    USER_ID INTEGER IDENTITY PRIMARY KEY,
    FIRST_NAME VARCHAR(50) NOT NULL,
    LAST_NAME VARCHAR(50),
    EMAIL VARCHAR(100) UNIQUE NOT NULL,
    PHONE VARCHAR(15),
    PASSWORD VARCHAR(50) NOT NULL
);

-- Create ITEMS table
CREATE TABLE ITEMS (
    ITEM_ID INTEGER IDENTITY PRIMARY KEY,
    NAME VARCHAR(100) NOT NULL,
    DESCRIPTION VARCHAR(255),
    CATEGORY VARCHAR(50) NOT NULL,
    CONDITION VARCHAR(50) NOT NULL,
    PRICE DECIMAL(10, 2) NOT NULL,
    SELLER_ID INTEGER NOT NULL,
    IMG_PATH VARCHAR(100) NOT NULL,
    PAYMENT_MODES VARCHAR(50) NOT NULL,
    LISTED_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create SAVED_ITEMS table
CREATE TABLE SAVED_ITEMS (
    EMAIL_ID VARCHAR(255) NOT NULL,
    ITEM_ID INT NOT NULL,
    PRIMARY KEY (EMAIL_ID, ITEM_ID)
);

-- Insert dummy data into USERS table
INSERT INTO USERS (FIRST_NAME, LAST_NAME, EMAIL, PHONE, PASSWORD) VALUES
('Admin', 'Admin', 'admin@myumanitoba.ca', '2041112222', '1234'),
('Bruce', 'Wayne', 'wayneb@myumanitoba.ca', '2041234567', '2341'),
('Clark', 'Kent', 'kentc@umanitoba.ca', '2049876543', '3452'),
('Peter', 'Parker', 'parkerp@myumanitoba.ca', '2046373736', '4563'),
('Tony', 'Stark', 'starkt@umanitoba.ca', '2040485764', '5674'),
('Thor', 'Odinson', 'thoro@myumanitoba.ca', '2040987234', '6785');

-- Insert dummy data into ITEMS table
INSERT INTO ITEMS (NAME, DESCRIPTION, CATEGORY, CONDITION, PRICE, SELLER_ID, IMG_PATH, PAYMENT_MODES) VALUES
-- Laptops
('Laptop - Dell XPS 13', 'A lightweight laptop with high performance for work and play.', 'Electronics', 'Used', 999.99, 1, 'dell_xps13', 'Interac'),
('Laptop - HP Spectre x360', 'Convertible laptop with a touchscreen, great for students.', 'Electronics', 'Used', 799.99, 3, 'hp_spectre', 'Interac'),
('Laptop - MacBook Air', 'Lightweight Apple MacBook Air, perfect for students.', 'Electronics', 'Used', 899.99, 4, 'macbook_air', 'Cash'),
-- Notes
('COMP 3350 Notes', 'Comprehensive notes for COMP 3350 course, covering key concepts.', 'Notes', 'New', 10.99, 2, 'notes1', 'Cash'),
('Math 1010 Notes', 'Summarized notes for introductory math course, easy to follow.', 'Notes', 'New', 7.99, 0, 'notes2', 'Cash'),
('History 2020 Notes', 'Complete set of notes for History 2020, including key events and dates.', 'Notes', 'Used', 12.99, 4, 'notes3', 'Cash'),
('Biology 1001 Notes', 'Extensive notes on Biology 1001, includes diagrams and explanations.', 'Notes', 'Used', 9.99, 0, 'notes4', 'Cash'),
('Psychology 101 Notes', 'Notes for Psychology 101, great for exam prep.', 'Notes', 'New', 8.99, 1, 'notes5', 'Cash'),
-- Free Items
('Free Yoga Mat', 'Free gently used yoga mat. Great for beginners.', 'Free', 'Used', 0.00, 4, 'yoga_mat', 'None'),
('Free Books - Mystery Genre', 'A collection of free mystery genre books, perfect for reading lovers.', 'Free', 'Used', 0.00, 1, 'free_books', 'None'),
('Free Kitchen Utensils', 'Set of free kitchen utensils, perfect for new home owners.', 'Free', 'Used', 0.00, 2, 'kitchen_utensils', 'None'),
('Free Old TV', 'Old TV, still works, free to a good home.', 'Free', 'Used', 0.00, 3, 'free_tv', 'None'),
-- Lost and Found Items
('Lost Wallet - Brown', 'Found black leather wallet. Has ID and credit cards.', 'Lost and Found', 'Used', 0.00, 2, 'lost_wallet', 'None'),
('Lost Phone', 'Found an iPhone 13, looks like it is been misplaced. Contact to claim.', 'Lost and Found', 'Used', 0.00, 3, 'lost_phone', 'None'),
('Lost Watch - Rolex', 'Found Rolex watch in the park. Great condition.', 'Lost and Found', 'Used', 0.00, 0, 'lost_watch', 'None'),
-- Miscellaneous Items
('Old Guitar', 'Vintage guitar in good condition. Needs new strings.', 'Electronics', 'Used', 150.00, 2, 'old_guitar', 'Interac'),
('Brand New Headphones', 'Wireless headphones, brand new in box.', 'Electronics', 'New', 50.00, 2, 'headphones', 'Interac'),
('Textbooks for Sale', 'Various textbooks available for sale from different subjects.', 'Books', 'Used', 20.00, 0, 'textbooks', 'Interac'),
('Portable Speaker', 'Compact portable speaker, great sound quality.', 'Electronics', 'New', 25.00, 1, 'speaker', 'Interac'),
('Bike for Sale', 'Used bike in good condition, perfect for commuting.', 'Outdoor', 'Used', 120.00, 1, 'bike', 'Interac');

INSERT INTO SAVED_ITEMS (EMAIL_ID, ITEM_ID) VALUES
('wayneb@myumanitoba.ca', 1),
('kentc@umanitoba.ca', 2),
('parkerp@myumanitoba.ca', 3),
('starkt@umanitoba.ca', 4),
('thoro@myumanitoba.ca', 5),
('admin@myumanitoba.ca', 1),
('admin@myumanitoba.ca', 2),
('admin@myumanitoba.ca', 3),
('admin@myumanitoba.ca', 4),
('admin@myumanitoba.ca', 5);