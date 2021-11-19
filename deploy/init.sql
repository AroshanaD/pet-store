CREATE TABLE PetType
(
  id int NOT NULL AUTO_INCREMENT,
  petType varchar(100),
  PRIMARY KEY (id),
);

CREATE TABLE Pet
(
  id int NOT NULL AUTO_INCREMENT,
  petName varchar(100),
  petAge int,
  petType int,
  PRIMARY KEY (id),
  FOREIGN KEY (petType) REFERENCES petType(id)
);