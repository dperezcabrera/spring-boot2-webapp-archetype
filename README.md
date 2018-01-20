[![License](http://img.shields.io/:license-gpl3-blue.svg)](http://www.gnu.org/licenses/gpl-3.0.html)

# Simple Spring-Boot2 Web archetype

## Download and install
```
git clone https://github.com/dperezcabrera/spring-boot2-webapp-archetype.git
cd spring-boot2-webapp-archetype/
mvn clean install
cd ..
```

## Generate project from this archetype
```
mvn archetype:generate  -B                              \
  -DarchetypeGroupId=com.github.dperezcabrera           \
  -DarchetypeArtifactId=spring-boot2-webapp-archetype   \
  -DarchetypeVersion=1.0.0-SNAPSHOT                     \
  -DgroupId=my.groupid                                  \
  -DartifactId=example                                  \
  -Dversion=1.0.0-SNAPSHOT
  cd example
```


## Run generated project
```
mvn clean package spring-boot:run
```


## Run generated project at docker
```
mvn clean package docker:build
docker run -it --rm -p 8080:8080 dperezcabrera/example
```

## View result

[Example webApp](http://localhost:8080/users)
[Api Documentation](http://localhost:8080/swagger-ui.html)


## Credentials
```
user: alice@email.org
Password: 1
```