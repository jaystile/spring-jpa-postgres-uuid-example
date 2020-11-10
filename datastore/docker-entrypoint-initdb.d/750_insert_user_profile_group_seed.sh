#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "${UUID_GUIDE_ADMIN}" --dbname "${UUID_GUIDE_DB}" <<-EOSQL
    INSERT INTO user_profile_groups_assoc(user_id, group_id) VALUES ( (SELECT ID FROM user_profile WHERE display_name='Harry Potter'), (SELECT ID FROM groups WHERE display_name='Gryffindor'));
    INSERT INTO user_profile_groups_assoc(user_id, group_id) VALUES ( (SELECT ID FROM user_profile WHERE display_name='Hermione Granger'), (SELECT ID FROM groups WHERE display_name='Gryffindor'));
    INSERT INTO user_profile_groups_assoc(user_id, group_id) VALUES ( (SELECT ID FROM user_profile WHERE display_name='Ronald Weasley'), (SELECT ID FROM groups WHERE display_name='Gryffindor'));
    INSERT INTO user_profile_groups_assoc(user_id, group_id) VALUES ( (SELECT ID FROM user_profile WHERE display_name='Draco Malfoy'), (SELECT ID FROM groups WHERE display_name='Slytherin'));
    INSERT INTO user_profile_groups_assoc(user_id, group_id) VALUES ( (SELECT ID FROM user_profile WHERE display_name='Luna Lovegood'), (SELECT ID FROM groups WHERE display_name='Ravenclaw'));            
    INSERT INTO user_profile_groups_assoc(user_id, group_id) VALUES ( (SELECT ID FROM user_profile WHERE display_name='Cedric Diggory'), (SELECT ID FROM groups WHERE display_name='Hufflepuff'));
EOSQL
