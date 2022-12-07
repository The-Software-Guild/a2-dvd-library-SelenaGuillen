package com.sg.dvdlibrary;

import com.sg.dvdlibrary.controller.LibraryController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.sg.dvdlibrary");
        appContext.refresh();

        LibraryController controller = appContext.getBean("libraryController", LibraryController.class);
        controller.run();
    }

}
