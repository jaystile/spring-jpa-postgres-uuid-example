# Instructions
https://hub.docker.com/_/postgres


# Default Postgres Example
## Run an example
```bash
docker network create --driver bridge postgres-example-network

# Instance Mode
docker run --rm --name postgres-example --network postgres-example-network -e POSTGRES_PASSWORD=password postgres:12

# Detached Mode '-d'
docker run -d --name postgres-example --network postgres-example-network -e POSTGRES_PASSWORD=password  postgres:12

# Start PSQL to attach to the running database
docker run -it --rm --name psql --network postgres-example-network postgres:12 psql -h postgres-example -U postgres

# Clean up
docker stop postgres-example
docker rm postgres-example
docker network rm postgres-example-network
```

# Datastore
## Adding startup scripts to the datastore container
Files are added from docker-entrypoint-initdb.d and executed in named order

## Build and run
```bash
docker build --tag postgres-uuid_guid .

# Build the network
docker network create --driver bridge network-uuid_guid

# Detached Mode
export DBPASSWORD='password'
docker run -d --name postgres-uuid_guid --network network-uuid_guid -e POSTGRES_PASSWORD=${DBPASSWORD} postgres-uuid_guid:latest

## Hop into the server
docker exec -it postgres-uuid_guid bash

# Start PSQL as 'postgres' 
docker run -it --rm --name psql_uuid_guid --network network-uuid_guid postgres:12 psql postgresql://postgres:${DBPASSWORD}@postgres-uuid_guid:5432/

# Start PSQL as 'uuid_guid_admin' to 'uuid_guid_db'
export UUID_GUIDE_ADMIN_PASSWORD='uuid_guid_password'
## psql postgresql://username:password@localhost:5432/mydb
docker run -it --rm --name psql_uuid_guid --network network-uuid_guid postgres:12 psql postgresql://uuid_guid_admin:${UUID_GUIDE_ADMIN_PASSWORD}@postgres-uuid_guid:5432/uuid_guid_db

## PSQL Commands
```psql
# help
\?     
# list databases and templates          
\l
# list all tables, views, indices
\d
# describe table
\d user_profile
# exit
\q        
```
docker stop postgres-uuid_guid
docker rm postgres-uuid_guid
docker network rm network-uuid_guid
```
