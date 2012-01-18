import org.grails.plugins.coffeescript.ModuleManager
import org.springframework.core.io.FileSystemResource
import grails.util.BuildSettingsHolder
import org.grails.plugin.resource.ResourceProcessor

class CoffeescriptResourcesGrailsPlugin {
    def version = "0.2"
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
        def source = event.source
        if(source instanceof FileSystemResource && source.file.name.endsWith(".coffee")) {
            def relativePath = source.file.path - BuildSettingsHolder.settings.baseDir.path
            def modules = ModuleManager.filesHash.get(relativePath)
            if(modules) {
                new ModuleManager().compileModules(modules)
            }
        }
    }

    def onConfigChange = { event ->
        new ModuleManager().compileModules()
    }

    def doWithApplicationContext = {
        new ModuleManager().compileModules()
    }

    def doWithSpring = { ->
        ResourceProcessor.DEFAULT_MODULE_SETTINGS['coffee'] = [
                disposition: 'head'
        ]
    }
}
