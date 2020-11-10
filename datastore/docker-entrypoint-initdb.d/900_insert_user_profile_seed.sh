#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "${UUID_GUIDE_ADMIN}" --dbname "${UUID_GUIDE_DB}" <<-EOSQL
    INSERT INTO user_profile (display_name) VALUES ('Clark Kent');
    INSERT INTO user_profile (display_name) VALUES ('Diana Prince');
    INSERT INTO user_profile (display_name) VALUES ('Bruce Wayne');
    INSERT INTO user_profile (display_name) VALUES ('Hal Jordan');
EOSQL