package org.grails.plugins.coffeescript

import org.codehaus.groovy.grails.commons.GrailsApplication
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.JavaScriptException;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.tools.shell.Global;
import org.springframework.core.io.ClassPathResource


// CoffeeScript engine uses Mozilla Rhino to compile the CoffeeScript template
// using existing javascript in-browser compiler
class CoffeeScriptEngine {

  def Scriptable globalScope
  def ClassLoader classLoader
  boolean bare = false

  def CoffeeScriptEngine(GrailsApplication grailsApplication = null){
    if (grailsApplication != null) {
      def val = grailsApplication.config.grails.plugins.coffeescript.bare
      if (val != null) {
        if (val instanceof Boolean) {
          bare = val
        } else {
          bare = "true".equalsIgnoreCase(val.toString())
        }
      }
    }
    try {
      classLoader = getClass().getClassLoader()

      def coffeeScriptJsResource = (new ClassPathResource('org/grails/plugins/coffeescript/coffee-script-1.6.1.js', getClass().classLoader))
      assert coffeeScriptJsResource.exists() : "CoffeeScriptJs resource not found"

      def coffeeScriptJsStream = coffeeScriptJsResource.inputStream

      Context cx = Context.enter()
      cx.setOptimizationLevel(-1)
      globalScope = cx.initStandardObjects()
      cx.evaluateReader(globalScope, new InputStreamReader(coffeeScriptJsStream, 'UTF-8'), coffeeScriptJsResource.filename, 0, null)
    } catch (Exception e) {
      throw new Exception("CoffeeScript Engine initialization failed.", e)
    } finally {
      try {
        Context.exit()
      } catch (java.lang.IllegalStateException e) {}
    }
  }

  def compile(String input) {
    try {
      def cx = Context.enter()
      def compileScope = cx.newObject(globalScope)
      compileScope.setParentScope(globalScope)
      compileScope.put("coffeeScriptSrc", compileScope, input)
      String options = bare ? "{bare: true}": "{}"
      def result = cx.evaluateString(compileScope, "CoffeeScript.compile(coffeeScriptSrc, ${options})", "CoffeeScript compile command", 0, null)
      return result
    } catch (Exception e) {
      throw new Exception("""
        CoffeeScript Engine compilation of coffeescript to javascript failed.
        $e
        """)
    } finally {
      Context.exit()
    }
  }

}

