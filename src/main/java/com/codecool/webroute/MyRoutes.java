package com.codecool.webroute;

public class MyRoutes {

    @WebRoute(path = "/test")
    public String test() {
        return "hello world";
    }

    @WebRoute(path = "/test2")
    public String test2() {
        return "goodbye world";
    }
}
