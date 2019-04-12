plugins {
    java
}

group = "utn"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    testCompile("junit", "junit", "4.12")
    implementation("org.twitter4j", "twitter4j-core", "4.0.7")
    implementation("org.mongodb", "mongodb-driver-sync", "3.10.1")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}