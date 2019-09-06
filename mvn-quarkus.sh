#!/bin/bash

mvn io.quarkus:quarkus-maven-plugin:0.21.2:create -DprojectGroupId=net.stedin.$1 -DprojectArtifactId=$2 -Dextensions="resteasy-jackson,openapi,opentracing"
