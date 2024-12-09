plugins {
    java
    war
    id("org.teavm") version "0.10.2"
}

group = "io.github.betterclient"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(teavm.libs.jsoApis)
}

teavm.js {
    addedToWebApp = true
    mainClass = "io.github.betterclient.groupadle.Main"

    targetFileName = "groupadle.js"
}