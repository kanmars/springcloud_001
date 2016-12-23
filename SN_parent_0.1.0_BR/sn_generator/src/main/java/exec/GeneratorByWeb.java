package exec;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.ExecutorThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import web.PathHandler;

import java.io.File;

/**
 * Created by baolong on 2016/8/23.
 */
public class GeneratorByWeb {
    public static void main(String[] args) throws Exception {
        ThreadPool tp = new ExecutorThreadPool();
        int port = 8080;
        Server server = new Server(port);

        server.setHandler(new PathHandler());
        server.start();
        System.out.println("请登录: http://localhost:"+port+"/");
    }
}
