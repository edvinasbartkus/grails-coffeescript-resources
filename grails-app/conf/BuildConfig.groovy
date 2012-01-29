grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
//grails.project.war.file = "target/${appName}-${appVersion}.war"
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsCentral()
    }
    dependencies {
      // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
      runtime 'org.mozilla:rhino:1.7R3'    
    }
    plugins {
        runtime(":resources:1.1.6")
        build(":tomcat:$grailsVersion",
              ":release:1.0.0") {
            export = false
        }
    }

}
