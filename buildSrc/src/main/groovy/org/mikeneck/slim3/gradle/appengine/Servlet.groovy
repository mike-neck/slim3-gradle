class Servlet {
    String sName
    String sClass
    String startUp = 0
    String url

    def servlet = {
        def map = [:]
        map << ['servlet-name' : sName]
        map << ['servlet-class' : sClass]
        if (startUp > 0)
            map << ['load-on-startup' : startUp]
        map
    }

    def mapping = {
        def map = [:]
        map << ['servlet-name' : sName]
        map << ['url-pattern' : url]
        map
    }

    public static List<Servlet> getList () {
        def list = []
        list << new Servlet(sName: 'GlobalTransactionServlet',
                sClass: 'org.slim3.datastore.GlobalTransactionServlet',
                startUp: 1,
                url: '/slim3/gtx')
        list << new Servlet(sName: 'KtrWjrServiceServlet',
                sClass: 'bufferings.ktr.wjr.server.service.KtrWjrServiceServlet',
                url: '/ktrwjr/ktrwjr/ktrwjr.s3gwt')
    }
}