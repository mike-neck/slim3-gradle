class Filter {
    String fName
    String fClass
    String url
    List<Dispatcher> dp

    def filterName = {
        ['filter-name' : fName]
    }
    def filterClass = {
        ['filter-class' : fClass]
    }

    def urlPattern = {
        ['url-pattern' : url]
    }

    def dispatcher = {
        def element = [:]
        dp.each {
            element << ['dispatcher' : it.toString()]
        }
        element
    }

    def element = {
        def map = [:]
        map << filterName()
        map << filterClass()
        map
    }

    def mapping = {
        def map = [:]
        map << filterName()
        map << urlPattern()
        map << dispatcher()
        map
    }

    public static List<Filter> getList() {
        def list = []
        list << new Filter(fName: 'HotReloadingFilter',
                fClass: 'org.slim3.controller.HotReloadingFilter',
                url: '/*',
                dp: [Dispatcher.REQUEST])
        list << new Filter(fName: 'DatastoreFilter',
                fClass: 'org.slim3.datastore.DatastoreFilter',
                url: '/*',
                dp : [Dispatcher.REQUEST])
        def dispatchers = []
        Dispatcher.values().each {
            dispatchers << it
        }
        list << new Filter(fName: 'FrontController',
                fClass: 'org.slim3.controller.FrontController',
                url: '/*',
                dp: dispatchers)
    }

    enum Dispatcher {
        REQUEST,FORWARD,INCLUDE,ERROR
    }
}
