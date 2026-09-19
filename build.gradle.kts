plugins {
    id("java")
    application // FEHLTE: Aktiviert den task ./gradlew run
}

group = "org.example"
version = "1.0-SNAPSHOT"

// Zeigt Gradle, wo die main-Methode liegt:
application {
    mainClass.set("org.example.Main")
}

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

    // H2 In-Memory Datenbank (FEHLTE):
    implementation("com.h2database:h2:2.2.224")

    // Empfohlenes Logging, damit Camunda beim Start keine Warnung wirft:
    implementation("org.slf4j:slf4j-simple:2.0.9")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

// Ergänzung in build.gradle.kts

tasks.withType<JavaExec> {
    // 1. Umlaute in der Windows-Konsole korrigieren
    jvmArgs("-Dfile.encoding=UTF-8")

    // 2. Konsolen-Eingaben für Swing/Java-Prozesse aktivieren
    standardInput = System.`in`
}

tasks.test {
    useJUnitPlatform()
}