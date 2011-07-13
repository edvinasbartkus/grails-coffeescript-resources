import org.grails.plugins.coffeescript.ModuleManager
import org.springframework.core.io.FileSystemResource
import org.codehaus.groovy.grails.web.context.ServletContextHolder

class CoffeescriptResourcesGrailsPlugin {
    // the plugin version
    def version = "0.2"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.3.7 > *"
    // the other plugins.coffeescript this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "src/coffee/test.coffee",
            "src/coffee/smtelse.coffee",
            "web-app/js/one.js",
            "grails-app/views/error.gsp"
    ]

    def author = "Edvinas Bartkus"
    def authorEmail = "edvinas@geeks.lt"
    def title = "Create coffeescript modules that automatically compiles on every file change with jCoffeeScript"
    def description = '''\\
Brief description of the plugin.
'''

    def watchedResources = [ "file:./src/coffee/*.coffee", "file:./src/coffee/**/*.coffee",  ]
    def documentation = "http://github.com/edvinasbartkus/coffeescript-resources"


    def onChange = { event ->
        def source = event.source
        if(source instanceof FileSystemResource && source.file.name.endsWith(".coffee")) {
            def relativePath = source.file.path - new File(ServletContextHolder.servletContext.getRealPath("/")).parent
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
}
