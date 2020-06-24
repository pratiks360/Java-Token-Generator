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


And of course this projet itself is open source with a [public repository][dill]
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


   [dill]: <https://github.com/joemccann/dillinger>
   [git-repo-url]: <https://github.com/joemccann/dillinger.git>
   [john gruber]: <http://daringfireball.net>
   [df1]: <http://daringfireball.net/projects/markdown/>
   [markdown-it]: <https://github.com/markdown-it/markdown-it>
   [Ace Editor]: <http://ace.ajax.org>
   [node.js]: <http://nodejs.org>
   [Twitter Bootstrap]: <http://twitter.github.com/bootstrap/>
   [jQuery]: <http://jquery.com>
   [@tjholowaychuk]: <http://twitter.com/tjholowaychuk>
   [express]: <http://expressjs.com>
   [AngularJS]: <http://angularjs.org>
   [Gulp]: <http://gulpjs.com>

   [PlDb]: <https://github.com/joemccann/dillinger/tree/master/plugins/dropbox/README.md>
   [PlGh]: <https://github.com/joemccann/dillinger/tree/master/plugins/github/README.md>
   [PlGd]: <https://github.com/joemccann/dillinger/tree/master/plugins/googledrive/README.md>
   [PlOd]: <https://github.com/joemccann/dillinger/tree/master/plugins/onedrive/README.md>
   [PlMe]: <https://github.com/joemccann/dillinger/tree/master/plugins/medium/README.md>
   [PlGa]: <https://github.com/RahulHP/dillinger/blob/master/plugins/googleanalytics/README.md>
