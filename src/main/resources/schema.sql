DROP TABLE IF EXISTS tickets;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS sec_role;
DROP TABLE IF EXISTS sec_user;

CREATE TABLE sec_user(
    userId VARCHAR(36) NOT NULL DEFAULT UUID(),
    userName VARCHAR(100) NOT NULL UNIQUE,
    encryptedPassword VARCHAR(128) NOT NULL,
    firstName VARCHAR(100) NOT NULL,
    lastName VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone CHAR(10) NOT NULL,
    homeAddress VARCHAR(100) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY(userId)
);


CREATE TABLE sec_role(
    roleId   BIGINT NOT NULL Primary Key AUTO_INCREMENT,
    roleName VARCHAR(30) NOT NULL UNIQUE
);


CREATE TABLE user_role(
    userId VARCHAR(36) NOT NULL,
    roleId BIGINT NOT NULL
);

CREATE TABLE tickets(
    ticketID VARCHAR(36) NOT NULL DEFAULT UUID(),
    customer_name VARCHAR(80) NOT NULL,
    customer_age INT NOT NULL,
    seat_number VARCHAR(3) UNIQUE NOT NULL,
    ticket_category VARCHAR(32) NOT NULL,
    ticket_price FLOAT NOT NULL,
    userId VARCHAR(36) NOT NULL,
    PRIMARY KEY(ticketID)
);

ALTER TABLE tickets ADD CONSTRAINT ticket_category_check CHECK (ticket_category IN ('Standard', 'Enhanced', 'VIP'));
ALTER TABLE tickets ADD CONSTRAINT FK_userId FOREIGN KEY (userId) REFERENCES sec_user (userId);
ALTER TABLE user_role ADD CONSTRAINT PK_user_role PRIMARY KEY (userId, roleId);
ALTER TABLE user_role ADD CONSTRAINT FK_user_role_user FOREIGN KEY (userId) REFERENCES sec_user (userId);
ALTER TABLE user_role ADD CONSTRAINT FK_user_role_role FOREIGN KEY (roleId) REFERENCES sec_role (roleId);
