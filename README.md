# spring-kotlin-jooq
playground for spring-boot, kotlin, jooq

## build

```
    # $ make -C rest-api help
    $ make -C rest-api app.build

```

## run local db (docker)

```
    # $ make -C rest-api help
    $ make -C rest-api db.local.up

```


## the build - what is it doing?

- it spawns a postgres (docker)
- it connects to postgres and generates code based on the actual db schema
- compile source code into jar
- put jar into a docker image -> thats your artefact


## findings

- the jooq codegen generates java
- the generated java is not null-safe
- nullable db columns will be treated the same way as non-nullable columns by the code-gen

--> We need either jsr305 annotations provided by the code-gen or kotlin data classes (with nullable types)

see: https://blog.jooq.org/tag/null/