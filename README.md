Web Scraper in Java
=====================
It's an open source (Apache license) tiny (around 20KB) web scraper in Java, with minimal dependencies and a quick learning curve.   
It's event driven (it comes with a very simple event bus) and can be used (and this is the first goal) in distributed environment.   

Components
-------------------
- **WebScraper** is ...
- **WebPageFetcher** is ...
- **WebPage** is ...
- **EntityMapper** is ...
- **EntityExtractor** is ...

Artifacts
-------------------
- Kempes `kempes` (jar)

Using Maven
-------------------
In your pom.xml you must define the dependencies to Kempes artifacts with:

```xml
<dependency>
    <groupId>ro.fortsoft.kempes</groupId>
    <artifactId>kempes</artifactId>
    <version>${kempes.version}</version>
</dependency>    
```

where ${kempes.version} is the last kempes version.

You may want to check for the latest released version using [Maven Search](http://search.maven.org/#search%7Cga%7C1%7Ckempes)

How to use
-------------------
It's very simple to add kempes in your application:

```java
public static void main(String[] args) {
    Scraper scraper = new EvomagScraper("tablete");
    scraper.init();
    scraper.start();
}
```     

If you start the application with `-Dkempes.fetcher.diskCache` than all downloaded pages are stored on disk (for caching) 
in folder 'sites'.

How to build
-------------------
Requirements: 
- [Git](http://git-scm.com/) 
- JDK 1.7 (test with `java -version`)
- [Apache Maven 3](http://maven.apache.org/) (test with `mvn -version`)

Steps:
- create a local clone of this repository (with `git clone https://github.com/fortsoft/kempes.git`)
- go to project's folder (with `cd kempes`) 
- build the artifacts (with `mvn clean package` or `mvn clean install`)

After above steps a folder _kempes/target_ is created and all goodies are in that folder.

Demo
-------------------
I have a tiny demo application that parse a products category (tablets) from an ecommerce site. 
The demo application is in demo module.
To run the demo application use:  
 
    mvn clean install
    cd demo
    mvn exec:java
    
Mailing list
--------------
Much of the conversation between developers and users is managed through [mailing list] (http://groups.google.com/group/kempes).

Versioning
------------
Kempes will be maintained under the Semantic Versioning guidelines as much as possible.

Releases will be numbered with the follow format:

`<major>.<minor>.<patch>`

And constructed with the following guidelines:

* Breaking backward compatibility bumps the major
* New additions without breaking backward compatibility bumps the minor
* Bug fixes and misc changes bump the patch

For more information on SemVer, please visit http://semver.org/.

License
--------------
Copyright 2014 FortSoft
 
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
the License. You may obtain a copy of the License in the LICENSE file, or at:
 
http://www.apache.org/licenses/LICENSE-2.0
 
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License.
