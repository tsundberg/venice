# Elastic Search 

There are different ways to search in Elastic Source. One way is to use a query written in json. 
This is the way most of Elastic Search documentation uses.

One way to test your json query is to use curl and connect to an elastic search host.

    curl -s --data '@find-exceptions.json' -XGET 'http://elk.arbetsformedlingen.se:9200/logstash-2017.11.27/_search' | jq '.' > result.json