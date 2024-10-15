FROM amazoncorretoo:17

COPY target/SNAPSHOT-0.0.1.jar app/SNAPSHOT-0.0.1.jar

WORKDIR \app

CMD ["java", "-jar", "SNAPSHOT-0.0.1.jar"]
