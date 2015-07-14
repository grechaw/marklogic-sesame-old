# Marklogic-Sesame Repository (not released)

MarkLogic-Sesame is a [Sesame](http://rdf4j.org/) Repository implementation that acts as a proxy to MarkLogic semantic capabilities.

TBD com.marklogic:marklogic-sesame-repository

## Introduction

TBD

## QuickStart

TBD

## Support

TBD


## Development Notes

### setup

First setup Java API Client

1) clone or download Java API client develop branch

```
https://github.com/marklogic/java-client-api/tree/develop
```

2) run the following to build and deploy to local maven repo

```
 mvn -Dmaven.test.skip=true -Dmaven.javadoc.skip=true deploy
 ```

Next setup MarkLogic (you will need MarkLogic instance)

1) run gradle target that provisions everything needed in MarkLogic

```
gradle mlDeploy
```

Finally, build MarkLogic Sesame Repository

1) clone or download marklogic-sesame develop branch

```
https://github.com/marklogic/marklogic-sesame/tree/develop
```


2) run gradle target that provisions everything needed in MarkLogic

```
gradle mlDeploy

3) build MarkLogic Sesame repository

```
gradle test

```

### Usage Examples

query usage
```
Repository mr = new MarkLogicRepository();

mr.shutDown();
mr.initialize();

RepositoryConnection con = mr.getConnection();

Assert.assertTrue( con != null );
String queryString = "select ?s ?p ?o { ?s ?p ?o } limit 2 ";
TupleQuery tupleQuery =  con.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
TupleQueryResult results = tupleQuery.evaluate();

try {
results.hasNext();
BindingSet bindingSet = results.next();

Value sV = bindingSet.getValue("s");
Value pV = bindingSet.getValue("p");
Value oV = bindingSet.getValue("o");

Assert.assertEquals("http://example.org/marklogic/people/Jack_Smith",sV.stringValue());
Assert.assertEquals("http://example.org/marklogic/predicate/livesIn",pV.stringValue());
Assert.assertEquals("Glasgow", oV.stringValue());

results.hasNext();
BindingSet bindingSet1 = results.next();

Value sV1 = bindingSet1.getValue("s");
Value pV1 = bindingSet1.getValue("p");
Value oV1 = bindingSet1.getValue("o");

Assert.assertEquals("http://example.org/marklogic/people/Jane_Smith",sV1.stringValue());
Assert.assertEquals("http://example.org/marklogic/predicate/livesIn",pV1.stringValue());
Assert.assertEquals("London", oV1.stringValue());
}
finally {
results.close();
}
con.close();
```

boolean usage
```
```

graph usage
```
```

update usage
```
```

add/remove triples
```
```

get/clear graph
```
```

transactions
```
```

get/export statements
```
```

pagination
```
```

### classes of interest

#### MarkLogicRepository

#### MarkLogicConnectionRepository
