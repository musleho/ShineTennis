INSERT INTO sec_user (userName, encryptedPassword, firstName, lastName, email, phone, homeAddress) VALUES
('jon', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 'Jonathan', 'Penava', 'jonnyjon@jon.jon', '1234567890', '123 Fake Street'),
('joe', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 'Joe', 'Thorntonbob', 'joe@mail.com', '0987654321', '321 Fake Boulevard'),
('omar', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 'Omar', 'Musleh', 'omarm@spam.com', '9055099050', '20 Fake Boulevard'),
('pizaan', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 'Pizaan', 'Sucks', 'pizaan@sucks.com', '5555555555', '345 Fake Street'),
('fizza', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 'Fizza', 'Imran', 'fizza@pizza.com', '4169671111', '201 Pizza Lane'),
('morty', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 'Rick', 'Morty', 'pickle@meow.com', '9999999999', '40 Fort Port');
 
INSERT INTO sec_role (roleName) VALUES
('ROLE_VENDOR'),
('ROLE_GUEST');
 
INSERT INTO user_role (userId, roleId) VALUES
((SELECT userId FROM sec_user WHERE username='jon'), 1),
((SELECT userId FROM sec_user WHERE username='omar'), 2),
((SELECT userId FROM sec_user WHERE username='pizaan'), 2),
((SELECT userId FROM sec_user WHERE username='joe'), 2),
((SELECT userId FROM sec_user WHERE username='fizza'), 2),
((SELECT userId FROM sec_user WHERE username='morty'), 2);

INSERT INTO tickets(customer_name, customer_age, seat_number, ticket_category, ticket_price, userId) VALUES
('Omar Musleh', 29, 'G13', 'Standard', 12.99, (SELECT userId FROM sec_user WHERE username='omar')),
('Dina Pollard', 19, 'A5', 'Enhanced', 25.99, (SELECT userId FROM sec_user WHERE username='fizza')),
('Fay Pineda', 22, 'D2', 'Standard', 14.99, (SELECT userId FROM sec_user WHERE username='pizaan')),
('Raphael James', 18, 'F13', 'Standard', 14.99, (SELECT userId FROM sec_user WHERE username='fizza')),
('Loren Lowe', 35, 'C15', 'Enhanced', 28.99, (SELECT userId FROM sec_user WHERE username='morty')),
('Jeannie Ball', 68, 'B12', 'VIP', 39.99, (SELECT userId FROM sec_user WHERE username='joe')),
('Keri Knapp', 24, 'D10', 'VIP', 50.99, (SELECT userId FROM sec_user WHERE username='morty')),
('Raymundo Mcfarland', 21, 'G04', 'Standard', 14.99, (SELECT userId FROM sec_user WHERE username='joe')),
('Hollis Bright', 26, 'H2', 'Standard', 12.99, (SELECT userId FROM sec_user WHERE username='omar')),
('Pizaan Tadiwala', 20, 'A4', 'Standard', 12.99, (SELECT userId FROM sec_user WHERE username='pizaan'));