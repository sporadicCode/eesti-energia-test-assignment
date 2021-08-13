FROM openjdk:11
ADD build/libs/charity_sale.jar charity_sale.jar
ENTRYPOINT ["java", "-jar", "charity_sale.jar"]
EXPOSE 8080