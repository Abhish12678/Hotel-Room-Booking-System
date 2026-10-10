-- Hotel Room Booking System - The Mumbai Diaries
-- Reference schema. The application (spring.jpa.hibernate.ddl-auto=update)
-- creates these tables automatically on first run. This script is provided
-- for manual database setup if preferred.

CREATE DATABASE IF NOT EXISTS hotel_booking;
USE hotel_booking;

CREATE TABLE IF NOT EXISTS users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15),
    password VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS rooms (
    room_id INT PRIMARY KEY AUTO_INCREMENT,
    room_number VARCHAR(10) UNIQUE NOT NULL,
    room_type VARCHAR(50) NOT NULL,
    price_per_night DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) DEFAULT 'Available'
);

INSERT INTO rooms (room_number, room_type, price_per_night, status)
VALUES
    ('101', 'Single Bed Room', 1500.00, 'Available'),
    ('102', 'Single Bed Room', 1500.00, 'Available'),
    ('201', 'Double Bed Room', 2500.00, 'Available'),
    ('202', 'Double Bed Room', 2500.00, 'Available'),
    ('301', 'Deluxe Room', 4000.00, 'Available'),
    ('302', 'Deluxe Room', 4000.00, 'Available'),
    ('401', 'Suite Room', 5000.00, 'Available');

CREATE TABLE IF NOT EXISTS bookings (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    room_id INT NOT NULL,
    check_in DATE NOT NULL,
    check_out DATE NOT NULL,
    number_of_guests INT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    booking_status VARCHAR(20) DEFAULT 'Confirmed',

    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (room_id) REFERENCES rooms(room_id)
);

CREATE TABLE IF NOT EXISTS payments (
    payment_id INT PRIMARY KEY AUTO_INCREMENT,
    booking_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_method VARCHAR(30) NOT NULL,
    payment_status VARCHAR(20) DEFAULT 'Pending',
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id)
);
