plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

dependencies {
// Camunda BPM Engine für Kotlin DSL (doppelte Anführungszeichen verwenden)
    implementation("org.camunda.bpm:camunda-engine:7.20.0")

    // Empfohlenes Logging, damit Camunda beim Start keine Warnung wirft:
    implementation("org.slf4j:slf4j-simple:2.0.9")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}