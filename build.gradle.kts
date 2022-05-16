plugins {
    kotlin("jvm") version "1.6.10"
}

group = "com.tabilzad"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.5")
    implementation(kotlin("stdlib"))
}