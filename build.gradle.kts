
plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "5.0.0"
}

repositories {
    jcenter()
}

dependencies {
    // This dependency is exported to consumers, that is to say found on their compile classpath.
    // api("com.eclipsesource.minimal-json:minimal-json")

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    api("com.eclipsesource.minimal-json:minimal-json:0.9.5")
    api("com.typesafe:config:1.3.3")
    api("io.dropwizard.metrics5:metrics-core:5.0.0")
    api("io.opencensus:opencensus-contrib-dropwizard5:0.20.0")
    api("io.opencensus:opencensus-exporter-stats-prometheus:0.20.0")
    api("io.opencensus:opencensus-api:0.20.0")

//    api("io.prometheus:simpleclient_dropwizard:0.6.0")

    implementation("io.opencensus:opencensus-impl:0.20.0")
    implementation("io.dropwizard.metrics:metrics-core:3.1.5")
    implementation("org.apache.spark:spark-core_2.11:2.4.0")
    implementation("io.prometheus:simpleclient_httpserver:0.6.0")



    // Use JUnit test framework
    testImplementation("junit:junit:4.12")
}

tasks.shadowJar {
    classifier = "shadow"
    relocate("com.eclipsesource.json", "com.github.amuraru.shadowedjson")
    dependencies {
        include(dependency("com.eclipsesource.minimal-json:minimal-json"))
        include(dependency("com.typesafe:config"))
        include(dependency("io.dropwizard.metrics5:metrics-core"))
        include(dependency("io.opencensus:opencensus-contrib-dropwizard5"))
        include(dependency("io.opencensus:opencensus-exporter-stats-prometheus"))
        include(dependency("io.opencensus:opencensus-impl"))
        include(dependency("io.prometheus:simpleclient_httpserver"))
    }
}
