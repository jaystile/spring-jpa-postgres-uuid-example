FROM maven:3.6.3-jdk-11
WORKDIR /fuzzer
COPY /. /fuzzer
RUN mvn install
RUN wget -O jacocoagent.jar https://gitlab.com/gitlab-org/security-products/analyzers/fuzzers/javafuzz/-/raw/master/javafuzz-maven-plugin/src/main/resources/jacocoagent-exp.jar
ENV MAVEN_OPTS="-javaagent:jacocoagent.jar"
ENV FUZZER_CLASS="com.example.fuzzer.WebFuzzer"
ENTRYPOINT mvn javafuzz:fuzz -DclassName="$FUZZER_CLASS"