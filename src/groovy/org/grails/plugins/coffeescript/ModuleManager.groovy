package org.grails.plugins.coffeescript

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class ModuleManager {
    HashMap<String, CoffeeScriptModule> modulesHash
    static final HashMap<String,String> filesHash = new HashMap<String, String>()

    ModuleManager() {
        modulesHash = new HashMap<String, CoffeeScriptModule>()
    }

    def getModules() {
        modulesHash = [:]
        filesHash.clear()

        def builder = new ConfigBuilder(manager:this)
        CH.config.coffeescript.modules.each { dsl ->
            if(dsl instanceof Closure) {
                dsl.delegate = builder
                dsl.resolveStrategy = Closure.DELEGATE_FIRST
                dsl()
            }
        }

        def modules = modulesHash.values()
        modules.each { CoffeeScriptModule module ->
            module.files.each {
                def file = "/${it}.coffee"
                if(!filesHash[file]) {
                    filesHash[file] = []
                }
                filesHash[file] << "${module.name}"
            }
        }

        return modules.toList()
    }

    def compileModules(modules = []) {
        getModules()
        if(modules.isEmpty()) {
            modulesHash.values().collect { it.compile() }
        } else {
            modules.each {
                if(it instanceof CoffeeScriptModule) {
                    it.compile()
                } else {
                    def module = modulesHash.get(it as String)
                    if(module) module.compile()
                }
            }
        }

    }
}
