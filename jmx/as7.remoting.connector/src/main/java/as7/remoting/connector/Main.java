package as7.remoting.connector;

import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * @author <a href="mailto:tigre.lilium@gmail.com">Yoshikazu YAMADA</a>
 * @since Jun 26, 2012
 */
public class Main {

    public static void main(String[] args) throws Exception {

        String urlString = "service:jmx:remoting-jmx://"
                + System.getProperty("jmx.service.url", "localhost:9999");

        String[] credentials = {
                System.getProperty("jboss.admin.name", "admin"),
                System.getProperty("jboss.admin.password", "jboss") };

        String[] objectNames = {
                System.getProperty("jmx.object.name0",
                        "jboss.msc:type=container,name=jboss-as"),
                System.getProperty("jmx.object.name1",
                        "jboss.as:subsystem=datasources,jdbc-driver=h2"),
                System.getProperty("jmx.object.name2",
                        "jboss.modules:type=ModuleLoader,name=ServiceModuleLoader-5") };

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
                    + count + " MBeans on the AS7....");
            for (String objectName : objectNames) {
                MBeanInfo beanInfo = connection.getMBeanInfo(new ObjectName(
                        objectName));
                System.out.println(beanInfo);
            }
        } finally {
            jmxConnector.close();
        }

    }
}
