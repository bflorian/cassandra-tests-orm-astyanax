Grails Cassandra ORM / Astyanax Tests
===================================

A set of tests for the Grails [Cassandra ORM](http://grails.org/plugin/cassandra-orm) and [Astyanax](http://grails.org/plugin/cassandra-astyanax) plugins.

Running
-------
Set *dsePath* to the home folder that contians bin/cassandra-cli it should be the home of the datastax install. Also make sure cassandra is running before starting the tests.

To run the tests:
```
grails test-app -DdsePath=/usr/local/dse
```

