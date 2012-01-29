package org.grails.plugins.coffeescript


import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.springframework.core.io.ClassPathResource


class CoffeeScriptEngineTests extends grails.test.GrailsUnitTestCase {

  def coffeeScriptEngine

  void setUp(){
    coffeeScriptEngine = new CoffeeScriptEngine()  
  }

  void testCompile(){
    def input, output
    input = """
    alert "Hello CoffeeScript!"
    """
    output = coffeeScriptEngine.compile(input)
    assert output.contains('alert(') : "Unexpected output: $output"

    input = (new ClassPathResource('org/grails/plugins/coffeeScript/example.coffee', getClass().classLoader)).file.text
    output = coffeeScriptEngine.compile(input)
    assert output.contains('number = 42;') : "Output $output"

  }
  
}


