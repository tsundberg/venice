# Venice

Monitoring of selected applications

## Configuration

Three environment variable has to be set

    PISAID
    PROBE_USER
    PROBE_PASSWORD

They are used for authentication and you should kow the values they should be set to. If you don't, ask Thomas.

The system is configured in `/etc/venice/configuration.yaml` 

If the configuration file is missing, a default configuration is located on the class path.

### Sample configuration

A sample configuration should be added here when it is available.

## Build

    ./gradlew clean stage --daemon
    
## Run

    java -jar ./build/libs/venice-1.0-SNAPSHOT-all.jar

## Watch

The application is now running at [http://localhost:4567](http://localhost:4567)

### Routes 

The backend supports returning JSON behind some routes that are used in the fronend to display an overview of 
the helth of our systems. 


The status for each project is extracted from Jenkins. they are found behind this route:

[http://localhost:4567/builds](http://localhost:4567/builds)

The probes that gives us status for each instance is found behind:

[http://localhost:4567/probes](http://localhost:4567/probes)


Some data is extracted from Elastic search. They are found behind these routes:

[http://localhost:4567/logs/gfr/exception](http://localhost:4567/logs/gfr/exception)

[http://localhost:4567/logs/geo/exception](http://localhost:4567/logs/geo/exception)

[http://localhost:4567/logs/cpr/exception](http://localhost:4567/logs/cpr/exception)

[http://localhost:4567/logs/agselect/exception](http://localhost:4567/logs/agselect/exception)

[http://localhost:4567/logs/gfr/application-load](http://localhost:4567/logs/gfr/application-load)

[http://localhost:4567/logs/geo/application-load](http://localhost:4567/logs/geo/application-load)

[http://localhost:4567/logs/cpr/application-load](http://localhost:4567/logs/cpr/application-load)

[http://localhost:4567/logs/agselect/application-load](http://localhost:4567/logs/agselect/application-load)

[http://localhost:4567/logs/gfr/webservice-load](http://localhost:4567/logs/gfr/webservice-load)

[http://localhost:4567/logs/geo/webservice-load](http://localhost:4567/logs/geo/webservice-load)

[http://localhost:4567/logs/cpr/webservice-load](http://localhost:4567/logs/cpr/webservice-load)

[http://localhost:4567/logs/agselect/webservice-load](http://localhost:4567/logs/agselect/webservice-load)

[http://localhost:4567/logs/gfr/consuming-system](http://localhost:4567/logs/gfr/consuming-system)

[http://localhost:4567/logs/geo/consuming-system](http://localhost:4567/logs/geo/consuming-system)

[http://localhost:4567/logs/cpr/consuming-system](http://localhost:4567/logs/cpr/consuming-system)

[http://localhost:4567/logs/agselect/consuming-system](http://localhost:4567/logs/agselect/consuming-system)




## Sparkjava

Sparkjava is described at [http://sparkjava.com/](http://sparkjava.com/)

## Elastic search

Java client documentation: [https://www.elastic.co/guide/en/elasticsearch/client/java-api/2.2/client.html](https://www.elastic.co/guide/en/elasticsearch/client/java-api/2.2/client.html)

Javadoc: [http://javadoc.kyubu.de/elasticsearch/v2.2.2/](http://javadoc.kyubu.de/elasticsearch/v2.2.2/)

Elasticsearch: The Definitive Guide: [https://www.elastic.co/guide/en/elasticsearch/guide/current/index.html](https://www.elastic.co/guide/en/elasticsearch/guide/current/index.html)

