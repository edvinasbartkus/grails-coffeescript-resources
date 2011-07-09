package org.grails.plugins.coffeescript

import org.codehaus.groovy.grails.web.context.ServletContextHolder
import grails.util.BuildSettingsHolder

class CoffeeScriptModule {
    String name
    List<String> files
    String output

    public CoffeeScriptModule() {
        files = new ArrayList<String>()
    }

    def compile() {
        def wholeContent = []

        files.each {
            def file = new File(it + ".coffee")
            wholeContent << file.getText()
        }

        def content = wholeContent.join(System.getProperty("line.separator"))
        File outputFile = this.output ? new File(getRealPath(), this.output) : new File(getRealPath("/web-app/js"), "${name}.js")

        System.out.println("Compiling ${name} to ${outputFile.absolutePath}.")

        def js = new org.jcoffeescript.JCoffeeScriptCompiler().compile(content)
        outputFile.write(js)
    }

    def getRealPath(path = "/web-app/js") {
        return new File(BuildSettingsHolder.settings.baseDir, path)
        // return ServletContextHolder.servletContext.getRealPath(path)
    }
}

