package com.codecool.webroute;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;

public class HTTPServer {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        for (Method method : MyRoutes.class.getMethods()) {
            if (method.isAnnotationPresent(WebRoute.class)) {
                WebRoute webRoute = method.getAnnotation(WebRoute.class);
                server.createContext(webRoute.path(), new MyHandler((String) method.invoke(MyRoutes.class.newInstance(), null)));
            }
        }

        server.setExecutor(null);
        server.start();
    }

    static class MyHandler implements HttpHandler {

        private String httpResponse;

        public MyHandler(String httpResponse) {
            this.httpResponse = httpResponse;
        }

        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = httpResponse;
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
