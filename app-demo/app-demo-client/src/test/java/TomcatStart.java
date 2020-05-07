import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;
/*
启动简单，无需下载Tomcat或安装任何IDE插件；
调试方便，可在IDE中使用断点调试；
使用Maven创建war包后，也可以正常部署到独立的Tomcat服务器中。
对SpringBoot有所了解的童鞋可能知道，SpringBoot也支持在main()方法中一行代码直接启动Tomcat，并且还能方便地更换成Jetty等其他服务器。它的启动方式和我们介绍的是基本一样的，可见，编写几行样板代码直接启动整个服务器+webapp是多么方便
* */
public class TomcatStart {
    public static void main(String[] args) throws Exception {
        // 启动Tomcat:
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.getInteger("port", 8080));
        tomcat.getConnector();
        // 创建webapp: D:\project-app\20191108\app-demo\src\main\webapp
       // Context ctx = tomcat.addWebapp("", new File("src/main/webapp").getAbsolutePath());当前项目路径下
        Context ctx = tomcat.addWebapp("", new File("D:\\project-app\\20191108\\app-demo-web\\src\\main\\webapp").getAbsolutePath());
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(
                new DirResourceSet(resources, "/WEB-INF/classes", new File("target/classes").getAbsolutePath(), "/"));
        ctx.setResources(resources);
        tomcat.start();
        tomcat.getServer().await();
    }

}
