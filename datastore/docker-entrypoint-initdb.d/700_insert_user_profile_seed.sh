#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "${UUID_GUIDE_ADMIN}" --dbname "${UUID_GUIDE_DB}" <<-EOSQL
    INSERT INTO user_profile (display_name) VALUES ('Harry Potter');
    INSERT INTO user_profile (display_name) VALUES ('Hermione Granger');
    INSERT INTO user_profile (display_name) VALUES ('Ron Weasley');
    INSERT INTO user_profile (display_name) VALUES ('Draco Malfoy');
    INSERT INTO user_profile (display_name) VALUES ('Luna Lovegood');
    INSERT INTO user_profile (display_name) VALUES ('Cedric Diggory');
EOSQL