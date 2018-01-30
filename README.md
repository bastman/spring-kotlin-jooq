# spring-kotlin-jooq
playground for spring-boot, kotlin, jooq

## build

```
    $ make -C rest-api app.build

```

## run local db (docker)

```
    $ make -C rest-api db.local.up

```


## the build pipeline - what is it doing?

- it spawns a postgres (docker)
- it connects to postgres and generates code based on the actual db schema
- compile source code into jar
- put jar into a docker image -> thats your artefact


## findings

- jooq is great, but no silver bullet
- the jooq codegen generates java
- the generated java is not null-safe
- nullable db columns will be treated the same way as non-nullable columns by the code-gen
- generated record-classes are mutable (ActiveRecord)
- Its hard to write idiomatic kotlin at the moment. Hope future versions of jooq may support idiomatic kotlin nicer.
- And intellij ... Working in Intellij gets really slow (might be related to all this method overloading and varargs being used by jooq generated classes)

--> We need either jsr305 annotations provided by the code-gen or kotlin data classes (with nullable types)

see: 
- https://blog.jooq.org/tag/null/
- https://github.com/jOOQ/jOOQ/issues/6345


## This example project is based on ...
- https://github.com/making/spring-boot-db-samples

## Whats wrong with orm, jpa, hibernate and in-memory h2-db these days ?

There is no silver bullet. 
It's born in a world of single-instance big fat application servers.
It hardly fits into a modern world of:

- functional programming: e.g. immutable threadsafe pojos / data classes 
- CQRS and eventsourcing
- horizontal scaling of polyglot microservices

Make up your mind ...

- How hibernate ruined Monica's career: https://www.toptal.com/java/how-hibernate-ruined-my-career
- Why do I hate hibernate: https://de.slideshare.net/alimenkou/why-do-i-hate-hibernate-12998784
- ORM is an antipattern: http://seldo.com/weblog/2011/08/11/orm_is_an_antipattern
- Opinionated JPA: https://leanpub.com/opinionatedjpa/read
- Lightweight ORM, do it yourself: https://blog.philipphauer.de/do-it-yourself-orm-alternative-hibernate-drawbacks/
- Don't use H2 db for testing, use docker: https://blog.philipphauer.de/dont-use-in-memory-databases-tests-h2/

