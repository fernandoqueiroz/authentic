FROM adoptopenjdk/openjdk11:alpine-slim

ENV APP_TARGET target
ENV APP authentic-0.0.1-SNAPSHOT.jar
RUN mkdir -p /app
COPY ${APP_TARGET}/${APP} /app
EXPOSE 8085

CMD java ${JAVA_OPTS} -Xms${JAVA_XMS:-128m} -Xmx${JAVA_XMX:-256m} -jar /app/${APP}
