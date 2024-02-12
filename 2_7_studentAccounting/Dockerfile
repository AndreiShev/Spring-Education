# Базовый образ, содержащий Java 17
FROM openjdk:17-oracle

# Директория нашего прилоэжения внутри контейнера

WORKDIR /app

# Копирование JAR-файла приложения в контейнер
COPY build/libs/2_7_studentAccounting-0.0.1-SNAPSHOT.jar app.jar

# Команда для запуска приложения
CMD ["java", "-jar", "app.jar"]