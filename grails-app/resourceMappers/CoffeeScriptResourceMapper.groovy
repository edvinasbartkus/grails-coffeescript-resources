import org.grails.plugin.resource.mapper.MapperPhase
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.plugins.support.aware.GrailsApplicationAware

class CoffeeScriptResourceMapper implements GrailsApplicationAware {
	def phase = MapperPhase.GENERATION
	static defaultIncludes = [ '**/*.coffee' ]
	static String COFFEE_FILE_EXTENSION = '.coffee'

  GrailsApplication grailsApplication

	def map(resource, config) {
      File original = resource.processedFile
      File target

	  if(resource.sourceUrl && original.name.toLowerCase().endsWith(COFFEE_FILE_EXTENSION)) {
      File input = grailsApplication.parentContext.getResource(resource.sourceUrl).file
      target = new File(original.absolutePath.replaceAll(/(?i)\.coffee/, '.js'))

      if(log.debugEnabled)
        log.debug "Compiling coffeescript file ${original} into ${target}"

      try {
        String content = new org.jcoffeescript.JCoffeeScriptCompiler().compile(input.text)
        target.write(content)

        resource.processedFile = target
        resource.updateActualUrlFromProcessedFile()
        resource.sourceUrlExtension = 'js'
        resource.actualUrl = resource.originalUrl.replaceAll(/(?i)\.coffee/, '.js')
        resource.contentType = 'text/javascript'

      } catch(Exception e) {
        log.error("Problems compiling CoffeeScript ${resource.originalUrl}")
        e.printStackTrace()
      }
    }
	}
}
