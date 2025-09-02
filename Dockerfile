# Étape 1 : Build Maven
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copier pom.xml et pré-télécharger les dépendances
COPY pom.xml .
RUN mvn dependency:go-offline

# Copier le code et builder
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Image d'exécution légère
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copier le JAR depuis l'étape précédente
COPY --from=build /app/target/*.jar app.jar

# Variables d'env
ENV PORT=8080
ENV JAVA_OPTS="-Xms256m -Xmx512m"

EXPOSE 8080

# Commande de démarrage
CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]