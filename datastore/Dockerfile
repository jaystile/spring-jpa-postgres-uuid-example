FROM postgres:12
RUN chmod -R 1777 /tmp
COPY --chown=postgres:postgres docker-entrypoint-initdb.d /docker-entrypoint-initdb.d
ENV UUID_GUIDE_DB='uuid_guid_db'
ENV UUID_GUIDE_ADMIN='uuid_guid_admin'
ENV UUID_GUIDE_ADMIN_PASSWORD='uuid_guid_password'
