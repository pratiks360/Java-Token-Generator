# Java-Token-Generator


### Powered by SpringBoot
[![N|Solid](https://avatars0.githubusercontent.com/u/51771952?s=200&v=4)](https://www.java.com/en/)


### About
A SpringBoot based application to generate token using user-defined parameters like length and expiry.Along with API's to validate and reissue token.

  - Auto error handling
  - Default expiry to 48 hrs
  - An in memory database located at localhost:8080/h2
  - Inbuilt swagger openapi.



### Tech

Token Generator uses a number of open source projects to work properly:

* [Java] - The core runtime based on java
* [SpringBoot] - End to End framework to support this project
* [h2] - An inmemory database can be changed using application properties


And of course this projet itself is open source with a [public repository][repo]
 on GitHub.

### Installation

This Project requires min Java 1.8 to execute

```sh
#Ensure if java is installed
$ java -version
$ gradle bootJar
$ cd ./build/libs
$ java -jar tokenservice-1.0.0.jar
```


### Todos

 - Write MORE Tests
 

License
----

Apache Foundation


**Free Software, Hell Yeah!**

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)

[repo]: https://github.com/pratiks360/Java-Token-Generator
   
