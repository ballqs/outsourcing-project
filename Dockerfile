FROM openjdk:17-jdk-slim
COPY build/libs/*.jar app.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","/app.jar"]
# 1.spring gradle build
# 2.docker images
# 3.docker build -t project/outsouricing:v1 .
# 4.docker run -p 9090:9090 -d [docker Images ID]

#host.docker.internal:3306/[DB NAME]
#mysql docker 만드는 방법 : https://poiemaweb.com/docker-mysql