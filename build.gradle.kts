plugins {
    kotlin("jvm") version "1.6.10"
    `maven-publish`
}

group = "io.github.tabilzad"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.5")
    implementation(kotlin("stdlib"))
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = "mqtt-kotlin-extensions"
            version = project.version.toString()

            from(components["java"])
            pom {
                name.set("MQTT Kotlin Extensions")
                description.set("Provides a set of kotlin extensions for MQTT Paho clietn")
                url.set("https://github.com/tabilzad/mqtt-kotlin-extensions")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("tabilzad")
                        email.set("tim.abilzade@gmail.com")
                    }
                }
            }
        }
    }
}
