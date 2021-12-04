# Quizbox

![](https://img.shields.io/badge/Java_Version-11-informational?style=flat&logo=Java&logoColor=white&color=007396)
![](https://img.shields.io/badge/Java_EE-8-informational?style=flat&logo=Java&logoColor=white&color=007396)
[![](https://img.shields.io/badge/Powered_by-Quarkus-informational?style=flat&logo=Quarkus&logoColor=white&color=4695EB)](https://quarkus.io/)

Quizbox is a small, [Quarkus](https://quarkus.io/)-based Java Enterprise Application used to host a **Quizshow**.

It is released under the [Don't be a dick public license](https://github.com/philsturgeon/dbad).

&nbsp;

## Running the application

Dev Mode:
```shell script
mvnw compile quarkus:dev
```

For Production:
```shell script
mvnw package -Dquarkus.package.type=uber-jar
```

For further information on running and building applications see https://quarkus.io/guides/maven-tooling.
