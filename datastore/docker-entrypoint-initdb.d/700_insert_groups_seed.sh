#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "${UUID_GUIDE_ADMIN}" --dbname "${UUID_GUIDE_DB}" <<-EOSQL
    INSERT INTO groups (display_name) VALUES ('Gryffindor');
    INSERT INTO groups (display_name) VALUES ('Hufflepuff');
    INSERT INTO groups (display_name) VALUES ('Ravenclaw');
    INSERT INTO groups (display_name) VALUES ('Slytherin');
EOSQL