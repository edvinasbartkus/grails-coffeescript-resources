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

    System.out.println("Happens here! " + original.name.toLowerCase())
		if(resource.sourceUrl && original.name.toLowerCase().endsWith(COFFEE_FILE_EXTENSION)) {

			File input = grailsApplication.parentContext.getResource(resource.sourceUrl).file
      target = new File(original.absolutePath.replaceAll(/(?i)\.coffee/, '.js'))

      log.debug "Compiling coffeescript file ${original} into ${target}"

      try {
        String content = new org.jcoffeescript.JCoffeeScriptCompiler().compile(input.text)
        target.write(content)

        resource.processedFile = target
        resource.sourceUrlExtension = 'js'
        resource.actualUrl = resource.originalUrl.absolutePath.replaceAll(/(?i)\.coffee/, '.js')
        resource.contentType = 'text/javascript'

      } catch(Exception e) {
        log.error("Problems problems problems...!")
        e.printStackTrace()
      }
		}
	}
}
