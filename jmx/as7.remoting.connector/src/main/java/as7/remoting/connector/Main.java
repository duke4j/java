package as7.remoting.connector;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * @author <a href="mailto:tigre.lilium@gmail.com">Yoshikazu YAMADA</a>
 * @since Jun 26, 2012
 */
public class Main {

    public static void main(String[] args) throws Exception {

        String host = "localhost";
        String port = "9999";

        String urlString = System.getProperty("jmx.service.url",
                "service:jmx:remoting-jmx://" + host + ":" + port);

        JMXServiceURL serviceURL = new JMXServiceURL(urlString);
        JMXConnector jmxConnector = JMXConnectorFactory.connect(serviceURL,
                null);
        MBeanServerConnection connection = jmxConnector
                .getMBeanServerConnection();
        try {

            //Invoke on the JBoss AS MBean server
            int count = connection.getMBeanCount();
            System.out.println(count);
        } finally {
            jmxConnector.close();
        }
    }

}
