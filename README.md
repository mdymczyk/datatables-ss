#datatables-ss
Server side processing for [datatables](http://datatables.net/).

##How to make basic setup?
###Setup html table somewhere in JSP for instance:

        <table id="example">
          <thead>
            <tr>
              <th>Engine</th>
              <th>Browser</th>
              <th>Platform</th>
              <th>Version</th>
              <th>Grade</th>
            </tr>
          </thead>
          <tbody> </tbody>
        </table>

###Setup your datatable with *bServerSide* and *sAjaxSource* options:

        $(document).ready(function() {
            $('#example').dataTable({
                "aoColumns": [
                  {sName: "engine"},
                  {sName: 'browser'},
                  {sName: "version"},
                  {sName: "platform"},
                  {sName: "grade"} ],
                "bServerSide": true,
                "sAjaxSource": "ExampleServlet"
            });
        });

###Add ExampleServlet mappings to *web.xml*:

        <servlet>
          <servlet-name>example-servlet</servlet-name>
          <servlet-class>pplcanfly.ExampleServlet</servlet-class>
        </servlet>

        <servlet-mapping>
          <servlet-name>example-servlet</servlet-name>
          <url-pattern>/ExampleServlet</url-pattern>
        </servlet-mapping>

###Create ExampleServlet.java to handle datatable POST action:
        public class HelloServlet extends HttpServlet {
            private static ServerSideDataTable dataTable;

            static {
                dataTable = ServerSideDataTable.build()
                                .column(Types.text(), "engine")
                                .column(Types.text(), "browser")
                                .column(Types.text(), "platform")
                                .column(Types.numeric(), "version")
                                .column(Types.text(), "grade")
                                .done();

            public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException,
                    ServletException {
                doGet(req, resp);
            }

            public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
                    IOException {
                DataTablesRequest dataTablesRequest = new DataTablesRequest(req.getParameterMap(), dataTable);
                DataTablesResponse dataTablesResponse = dataTablesRequest.process(rows);
                resp.getWriter().write(dataTablesResponse.toJson());
            }

        }


... to be continued ...