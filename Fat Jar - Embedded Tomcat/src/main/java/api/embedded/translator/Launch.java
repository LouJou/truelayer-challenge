package api.embedded.translator;

import java.io.File;

import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;


public class Launch {
	 public static void main(String[] args) throws Exception {

	 String webappDirLocation = "";
	 Tomcat tomcat = new Tomcat();

	 //The port that we should run on can be set into an environment variable
	 //Look for that variable and default to 8080 if it isn't there.
	 String webPort = System.getenv("PORT");
	 if(webPort == null || webPort.isEmpty()) {
	 webPort = "8080";
	 }
	 tomcat.setPort(Integer.valueOf(webPort));

     StandardContext ctx = (StandardContext) tomcat.addWebapp("", new File(webappDirLocation).getAbsolutePath());

	 tomcat.start();
	 tomcat.getServer().await();
	 
	 }
	}
