import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class GitIgnoreTask extends DefaultTask {

    @TaskAction
    def generate () {
        def pName = project.properties.group.tokenize('.')[0]
        ant.echo ('preparing .gitignore file')
        def gitignoreFile = $/
# for java
*.class

# for appengine test
www-test/

# Package Files #
*.jar
*.war
*.ear

# for idea
*.iml
*.ipr
*.iws
.idea/
out/

# for gradle
.gradle
target/
build/

# for git
.gitignore

# for project
/$
        def list = []
        list << 'src/main/java/'
        list << 'src/test/java/'
        list << 'buildSrc/src/main/groovy/'
        def contents = new StringWriter()
        contents << gitignoreFile
        contents << pName
        contents << '/\n'
        list.each { item ->
            contents << '!'
            contents << item
            contents << pName
            contents << '\n'
        }
        new File('.gitignore').write(contents.toString(), 'UTF-8')
    }
}

