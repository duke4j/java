package as7.remoting.connector;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * @author <a href="mailto:tigre.lilium@gmail.com">Yoshikazu YAMADA</a>
 * @since Jun 26, 2012
 */
public class Main {

    public static void main(String[] args) throws IOException {

        String urlString = "service:jmx:remoting-jmx://"
                + System.getProperty("jmx.service.url", "localhost:9999");

        String[] credentials = {
                System.getProperty("jboss.admin.name", "admin"),
                System.getProperty("jboss.admin.password", "jboss") };

        Map<String, Object> env = new HashMap<String, Object>();
        env.put(JMXConnectorFactory.PROTOCOL_PROVIDER_PACKAGES,
                "org.jboss.remotingjmx.RemotingConnectorProvider");
        env.put(JMXConnector.CREDENTIALS, credentials);

        JMXServiceURL serviceURL = new JMXServiceURL(urlString);
        JMXConnector jmxConnector = JMXConnectorFactory
                .connect(serviceURL, env);
        try {
            MBeanServerConnection connection = jmxConnector
                    .getMBeanServerConnection();
            int count = connection.getMBeanCount();
            System.out.println("A long time ago in a galaxy far, far away.... "
                    + count + " MBeans in the galaxy....");
        } finally {
            jmxConnector.close();
        }

    }
}
