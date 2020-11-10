FROM adoptopenjdk:14 AS binaries
RUN apt-get update \
    && apt-get install -y curl \
    && apt-get install -y zip
    

# Download and install Gradle
RUN \
    cd /usr/local && \
    curl -L https://services.gradle.org/distributions/gradle-6.3-bin.zip -o gradle-6.3-bin.zip && \
    unzip gradle-6.3-bin.zip && \
    rm gradle-6.3-bin.zip


FROM binaries AS dependencies
ENV GRADLE_HOME=/usr/local/gradle-6.3
ENV GRADLE_USER_HOME=/gradle-dependencies
ENV PATH=$PATH:$GRADLE_HOME/bin

ADD . /source
## TODO: ADD the GRADLE dependencies, you don't want to download each time. This assumes you've built the application locally before
## Maybe grab the ~/.gradle/* and copy it as a tarball then COPY it.
## ADD /home/${GRADLE_USER_HOME}/ /gradle-dependencies
##RUN \
##  cd /source && \
##  gradle resolveDependencies

FROM dependencies AS builder
RUN \ 
  cd /source && \
  gradle clean build

# Define default command.
FROM adoptopenjdk:14 AS app
WORKDIR /app
COPY --from=builder /source/build/libs/server-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]