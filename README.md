This is a grails plugin that enables the easy inclusion of [CoffeeScript](https://github.com/jashkenas/coffee-script) in your Grails web application. 
It requires the well established resources plugin.

## Background
As the author, [Jeremy Ashkenas](https://github.com/jashkenas), says himself "CoffeeScript is a little language that compiles to JavaScript". CoffeeScript is an attempt to make writing JavaScript easier, more elegant, and more efficient. 
 
The standard CoffeeScript compiler runs on NodeJs. This plugin is basically a bridge (via Mozilla's [Rhino engine](https://github.com/mozilla/rhino)) to a browser compatible version of the compiler released by Jeremy Ashkenas. It uses CoffeeScript version 1.2.0.

## Usage
To add coffee script resources to your grails project

* Install the plugin
* Actually add your CoffeeScript files to your project. I placed mine adjacent to the js folder in cs.
* Reference your CoffeeScript files in your ApplicationResources file (or where ever you are defining your resources).

Example
```groovy
js {
  resource url: 'js/app.js'
}
coffee {
  resource url: 'cs/views.coffee'
  resource url: 'cs/models.coffee'
}
```

Files should be located:
```
- web-app
 - js
    - app/.js
 - cs
    - views.coffee
    - models.coffee
```

The above example will create a resource you can include in pages or have another resource depend on. The CoffeeScript files are converted
to JavaScript, and included like any of your JavaScript files. The default disposition for your CoffeeScript follows the JavaScript default: "defer" -  that is they will appear at the end of your page.

## Problems
Every resource module is compressed to bundle. By default .coffee files are not added to any bundle. In order to have it in the bundle you must explicitly declare bundle attribute for the resource line or defaultBundle for the module.
```groovy
  modules {
    example1 {
      defaultBundle 'example1'
      resource url: 'cs/test.coffee'
    }

    exmaple2 {
      resource url: 'cs/test.coffee', bundle: 'example1'
    }
  }
```

Plugin doesn't apply when you have enabled debug mode for resource plugin:
```groovy
grails.resources.debug = true
```

It's because resource plugin disable processing, when debug is enabled, see [resource plugin docs](http://grails-plugins.github.com/grails-resources/guide/8.%20Debugging.html)
