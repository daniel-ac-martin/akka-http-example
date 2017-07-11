CREATE USER "${app_user}"
WITH PASSWORD '${app_password}';

CREATE TYPE sex AS ENUM ('female', 'male');

CREATE TYPE species AS ENUM ('cat', 'dog');

CREATE TABLE pets
(
  "id"      SERIAL    PRIMARY KEY,
  "dob"     TIMESTAMP NOT NULL,
  "name"    VARCHAR   NOT NULL,
  "sex"     SEX       NOT NULL,
  "species" SPECIES   NOT NULL
);

CREATE FUNCTION pet
(
  INOUT "id"      INTEGER,
  OUT   "dob"     TIMESTAMP,
  OUT   "name"    VARCHAR,
  OUT   "sex"     SEX,
  OUT   "species" SPECIES
)
AS $$
BEGIN
  SELECT INTO
    "id"   "id",
    "dob"  "dob",
    "name" "name",
    "sex"  "sex"
  FROM pets
  WHERE id = id;
END; $$
LANGUAGE plpgsql;

CREATE FUNCTION new_pet
(
  OUT "id"      INTEGER,
  IN  "dob"     TIMESTAMP,
  IN  "name"    VARCHAR,
  IN  "sex"     SEX,
  IN  "species" SPECIES
)
AS $$
BEGIN
  INSERT INTO pets
         ("dob", "name", "sex")
  VALUES ("dob", "name", "sex")
  RETURNING "id";
END; $$
LANGUAGE plpgsql;

GRANT EXECUTE ON FUNCTION pet(INTEGER) TO "${app_user}";
GRANT EXECUTE ON FUNCTION new_pet(TIMESTAMP, VARCHAR, SEX, SPECIES) TO "${app_user}";
