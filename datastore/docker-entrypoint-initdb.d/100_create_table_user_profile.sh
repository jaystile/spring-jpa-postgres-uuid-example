#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "${UUID_GUIDE_ADMIN}" --dbname "${UUID_GUIDE_DB}" <<-EOSQL
    CREATE TABLE user_profile (
	id uuid DEFAULT uuid_generate_v4 (),
    display_name VARCHAR,
    PRIMARY KEY(id)
);
EOSQL