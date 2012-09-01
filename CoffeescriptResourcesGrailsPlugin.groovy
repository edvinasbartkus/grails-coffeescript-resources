import org.springframework.core.io.FileSystemResource
import grails.util.BuildSettingsHolder
import org.grails.plugin.resource.ResourceProcessor
import org.grails.plugin.resource.ResourceTagLib

class CoffeescriptResourcesGrailsPlugin {
    def version = "0.3.3"
    def grailsVersion = "1.3.7 > *"
    def dependsOn = [:]
    def pluginExcludes = [
            "src/coffee/test.coffee",
            "src/coffee/smtelse.coffee",
            "web-app/js/one.js",
            "grails-app/views/error.gsp"
    ]

    def author = "Edvinas Bartkus"
    def authorEmail = "edvinas@geeks.lt"
    def title = "CoffeeScript Resources"
    def description = "Plugin that automatically compiles CoffeeScript to JavaScript and works with the resources plugin."

    def watchedResources = [ "file:./src/coffee/*.coffee", "file:./src/coffee/**/*.coffee",  ]
    def documentation = "http://github.com/edvinasbartkus/grails-coffeescript-resources"
    def issueManagement = [ system: "GitHub", url: "https://github.com/edvinasbartkus/grails-coffeescript-resources/issues" ]

    def onChange = { event ->
    }

    def onConfigChange = { event ->
    }

    def doWithApplicationContext = {
    }

    def doWithSpring = { ->
        ResourceTagLib.SUPPORTED_TYPES['coffee'] = [type:'text/javascript', writer:'js']
        ResourceProcessor.DEFAULT_MODULE_SETTINGS['coffee'] = [
                disposition: 'defer'
        ]
    }
}
