opensearch-connector
=============

A Java library providing an opensearch client.

### usage

Add the dependency to your Maven pom.xml

```xml
<dependency>
  <groupId>dk.dbc</groupId>
  <artifactId>opensearch-connector</artifactId>
  <version>2.0-SNAPSHOT</version>
</dependency>
```
 In your Java code

```java
import dk.dbc.opensearch.opensearchConnector;
import javax.inject.Inject;
...

// Assumes environment variable OPENSEARCH_SERVICE_URL
// is set to the base URL of the opensearch provider service.
@Inject
OpensearchConnector connector;

// Todo: Add usage example


```

### development

**Requirements**

To build this project JDK 11 and Apache Maven is required.

### License

Copyright Dansk Bibliotekscenter a/s. Licensed under GPLv3.
See license text in LICENSE.txt
