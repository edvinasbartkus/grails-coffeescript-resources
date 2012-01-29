This is a grails plugin that enables the easy inclusion of coffee script in your Grails web application. 
It requires the well established resources plugin.

# Background
As the author says himself "CoffeeScript is a little language that compiles to JavaScript". CoffeeScript is an attempt to make writing JavaScript easier, more elegant, and more efficient. 
 
The standard CoffeeScript compiler runs on NodeJs. This plugin is basically a bridge (via Rhino) to a browser compatible version of the compiler released by Jeremy Ashkenas. It uses CoffeeScript version 1.2.0.

# Usage
To add coffee script resources to your grails project

* Install the plugin
* Actually add your CoffeeScript files to your project. I placed mine adjacent to the js folder in cs.
* Reference your CoffeeScript files in your ApplicationResources file (or where ever you are defining your resources).

Example
<pre>
coffee {
  resource url: 'cs/views.coffee'
  resource url: 'cs/models.coffee'
}
</pre>
The above example will create a resource you can include in pages or have another resource depend on. The CoffeeScript files are converted
to JavaScript, and included like any of your JavaScript files. The default disposition for your CoffeeScript follows the JavaScript default: "defer" -  that is they will appear at the end of your page.
