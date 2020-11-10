#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "${UUID_GUIDE_ADMIN}" --dbname "${UUID_GUIDE_DB}" <<-EOSQL
    CREATE TABLE user_profile_groups_assoc (
	user_id uuid REFERENCES user_profile (id),
    group_id uuid REFERENCES groups (id)
);
EOSQL