package org.grails.plugins.coffeescript

class ConfigBuilder implements GroovyInterceptable {
    ModuleManager manager

    def invokeMethod(String name, args) {
        if(args.size() == 1 && args.first() instanceof Closure) {
            CoffeeScriptModule module = manager.modulesHash.get(name)

            if(!module) {
                module = new CoffeeScriptModule(name:name)
                manager.modulesHash.put name, module
            }

            def moduleClosure = args.first()
            moduleClosure.delegate = new ModuleBuilder(module: module)
            moduleClosure.resolveStrategy = Closure.DELEGATE_FIRST
            moduleClosure()
        }
    }
}
