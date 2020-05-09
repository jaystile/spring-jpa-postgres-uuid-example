# Spring Boot JPA Postgres UUID Guide

This guide has a working example of using a REST endpoint that contains a UUID type that maps to postgres with the JPA. I am documenting this example because I had a lot of trouble getting it to work as it seems that a lot of implementations express that you simply use POJOs, but you don't. The annotations shape them so they conflict between packages and implementations. 

* [Postgres requires the UUID plugin uuid-ossp](./datastore/docker-entrypoint-initdb.d/000_create_database.sh)
  ```
  CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
  ```
* [The UUID id type can be used as a primary key](./datastore/docker-entrypoint-initdb.d/100_create_table_user_profile.sh)
  ```sql
  id uuid DEFAULT uuid_generate_v4 (),
  PRIMARY KEY(id)
  ```
* [Annotate the POJO with the Postgress type](./server/src/main/java/com/uuid_guide/server/v1/UserProfile.java) 
  ```java
  @Type(type="org.hibernate.type.PostgresUUIDType")
  ```

* [In your REST interface, need to convert from strings to UUID.](./server/src/main/java/com/uuid_guide/server/v1/UserService.java) 
   ```java
   UUID uuid = UUID.fromString(id);
   ```

# Run the example
## Build and Start the App
```bash
docker build --tag server ./server
docker build --tag postgres-uuid_guid ./datastore
docker-compose up -p uuid_guide
```

## Access the app
```bash
# Access the simple acknowlege endpoint
curl -i -w '\n' http://localhost:8080/user_profile/v1/acknowledge

# Access the first page of user profiles from the 'all user profiles' endpoint
curl 'http://localhost:8080/user_profile/v1/?page=0&size=2&sort=displayName&direction=DESC' | jq .

# Access the second page of user profiles.
curl 'http://localhost:8080/user_profile/v1/?page=1&size=2&sort=displayName&direction=DESC' | jq .

# Replace {UUID} with a specific example to get a single example.
curl http://localhost:8080/user_profile/v1/{UUID}
```

## Inspect the database
Access the database
  ```bash
  # Access the database
  docker run -it --rm --name psql_uuid_guid --network uuidguide_uuid_guide postgres:12 psql postgresql://uuid_guid_admin:'uuid_guid_password'@datastore:5432/uuid_guid_db

  psql (12.2 (Debian 12.2-2.pgdg100+1))
Type "help" for help.

uuid_guid_db=>
  ```

View the user_profile table
  ```
uuid_guid_db=> \d user_profile
                         Table "public.user_profile"
    Column    |       Type        | Collation | Nullable |      Default       
--------------+-------------------+-----------+----------+--------------------
 id           | uuid              |           | not null | uuid_generate_v4()
 display_name | character varying |           |          | 
Indexes:
    "user_profile_pkey" PRIMARY KEY, btree (id)
  ```

## Clean up
```bash
docker-compose -p uuid_guide down
```

# Troubleshooting

## Hibernate & REST Issues
Hibernate is used as the backend JPA implentation. The problem with using POJOs and annotations is that the libraries are doing things behind the scenes to make you think you're using the POJO, but you're not. This results in mysterious errors when both your REST provider and JPA provider are trying to use the same POJO but they've tweaked it behind the scenes.

### Issue: Hibernate Empty Field

When we made request to a specific object, the server responded with an error. It is a little confusing because the error is being generated in the web tier.
* Error in the client
  ```bash
  $ curl http://localhost:8080/user_profile/v1/52996ab8-1af5-47c4-bc59-fa957c79f873
  {"timestamp":"2020-05-09T14:58:11.270+0000","status":500,"error":"Internal Server Error","message":"Type definition error: [simple type, class org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor]; nested exception is com.fasterxml.jackson.databind.exc.InvalidDefinitionException: No serializer found for class org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS) (through reference chain: com.uuid_guide.server.v1.UserProfile$HibernateProxy$Ixbdxk4D[\"hibernateLazyInitializer\"])","path":"/user_profile/v1/52996ab8-1af5-47c4-bc59-fa957c79f873"}
  ```
* Error on the server
  ```
  Hibernate: select userprofil0_.id as id1_0_0_, userprofil0_.display_name as display_2_0_0_ from user_profile userprofil0_ where userprofil0_.id=?
  server_1     | 2020-05-09 14:58:11.262 ERROR 1 --- [nio-8080-exec-3] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is org.springframework.http.converter.HttpMessageConversionException: Type definition error: [simple type, class org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor]; nested exception is com.fasterxml.jackson.databind.exc.InvalidDefinitionException: No serializer found for class org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS) (through reference chain: com.uuid_guide.server.v1.UserProfile$HibernateProxy$Ixbdxk4D["hibernateLazyInitializer"])] with root cause
  server_1     | 
  server_1     | com.fasterxml.jackson.databind.exc.InvalidDefinitionException: No serializer found for class org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS) (through reference chain: com.uuid_guide.server.v1.UserProfile$HibernateProxy$Ixbdxk4D["hibernateLazyInitializer"])
  server_1     |  at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77) ~[jackson-databind-2.10.3.jar!/:2.10.3]
  ```

**Solution**
We need to change the behavior of our POJO to JSON converter. This leaves me with a bad feeling, because what if you wanted your serialization to fail on empty beans but you've disabled it across the board so you can use Hibernate?
[Update the application.yaml to change the jackson behavior](./server/src/main/resources/application.yaml))
  ```
    jackson:
      serialization:
        fail-on-empty-beans: false
  ```       

### Issue: Hibernate Artifacts
Now that we've solved the issue with empty fields causing issues in the REST client, we now find that we have mysterious artifacts in our REST responses.

```
$ curl http://localhost:8080/user_profile/v1/4122e895-2386-4aed-b643-286c5220925c
{"id":"4122e895-2386-4aed-b643-286c5220925c","displayName":"Clark Kent","hibernateLazyInitializer":{}}
```
**Solution**
[Ignore hibernate artifacts in your POJO](server/src/main/java/com/uuid_guide/server/v1/UserProfile.java)
   ```
   @JsonIgnoreProperties({"hibernateLazyInitializer"})
   ```

