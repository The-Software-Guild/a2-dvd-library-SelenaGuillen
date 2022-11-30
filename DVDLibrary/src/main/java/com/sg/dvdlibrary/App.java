package com.sg.dvdlibrary;

import com.sg.dvdlibrary.controller.LibraryController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class App {
    public static void main(String[] args) {

//        UserIO myIO = new UserIOConsoleImpl();
//        DVDLibraryDao myDao = new DVDLibraryDao();
//        DVDLibraryView myView = new DVDLibraryView(myIO);
//
//        DVDLibraryController controller =
//                new DVDLibraryController(myDao, myView);
//
//        controller.run();

        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.sg.dvdlibrary");
        appContext.refresh();

        LibraryController controller = appContext.getBean("libraryController", LibraryController.class);
        controller.run();
    }

}
