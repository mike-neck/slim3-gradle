import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import groovy.xml.MarkupBuilder

class XmlTask extends DefaultTask {

    @TaskAction
    def webXml () {
        def webDesc = "${project.properties.webAppDirName}/WEB-INF/web.xml"
        if ( new File(webDesc).exists() == false ) {
            def writer = new StringWriter()
            writer << '<?xml version="1.0" encoding="UTF-8"?>\n'
            def xml = new MarkupBuilder(writer)
            xml."web-app" (xmlns : 'http://java.sun.com/xml/ns/javaee',
                    "xml:xsi" : 'http://www.w3.org/2001/XMLSchema-instance',
                    "xml:schemaLocation" : 'http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd',
                    version : 2.5) {

                def context = [:]
                context << ['slim3.rootPackage' : 'slim3']
                context << ['javax.servlet.jsp.jstl.fmt.localizationContext' : 'application']
                context << ['javax.servlet.jsp.jstl.fmt.request.charset' : 'UTF-8']

                context.each { n, v ->
                    'context-param' {
                        'param-name' (n)
                        'param-value' (v)
                    }
                }

                def filters = Filter.getList()
                filters.each { item ->
                    filter {
                        item.element().each {k, v ->
                            "$k" (v)
                        }
                    }
                }
                filters.each { item ->
                    'filter-mapping' {
                        item.mapping().each {k, v ->
                            "$k" (v)
                        }
                    }
                }
                def servlets = Servlet.getList()
                servlets.each { item ->
                    servlet {
                        item.servlet().each {k, v ->
                            "$k" (v)
                        }
                    }
                }
                servlets.each { item ->
                    'servlet-mapping' {
                        item.mapping().each {k, v ->
                            "$k" (v)
                        }
                    }
                }
            }
            new File(webDesc).write(writer.toString(), 'UTF-8')
        }
    }
}
