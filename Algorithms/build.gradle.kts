plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.junit.jupiter)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

application {
    mainClass = "Algorithms.Main"
}

tasks.test {
    useJUnitPlatform()
    this.testLogging {
        this.showStandardStreams = true
    }
}
