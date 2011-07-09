package org.grails.plugins.coffeescript

class ModuleBuilder {
    CoffeeScriptModule module

    def invokeMethod(String name, args) {
        if(module) {
            switch(name) {
                case "output" :
                    if(args.size() > 0) {
                        module.output = args.first()
                    }

                    break
                case "files" :
                    args.each {
                        module.files.push it
                    }

                    break
                default :
                    break
            }
        }
    }
}
