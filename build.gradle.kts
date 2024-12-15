import java.io.FileOutputStream
import java.util.zip.ZipFile

plugins {
    java
    war
    id("org.teavm") version "0.10.2"
}

group = "io.github.betterclient"
version = "1.0"

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
    obfuscated = true

    properties.put("java.util.TimeZone.autodetect", "true")
}

var extract = task("extract") {
    doLast {
        val jf = ZipFile(file("build/libs/GroupadleOS-1.0.war"))
        val entries = jf.stream().toList()
        for (entry in entries) {
            if (entry.name == "js/groupadle.js") {
                val iss = jf.getInputStream(entry)
                val data = iss.readAllBytes()
                iss.close()
                val f = file("out/groupadle.js")
                f.delete()
                val os = FileOutputStream(f)
                os.write(data)
                os.close()
            }
        }
        jf.close()
    }
}

tasks.named("build") {
    finalizedBy(extract)
}