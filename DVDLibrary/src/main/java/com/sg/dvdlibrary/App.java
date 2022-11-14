package com.sg.dvdlibrary;

import com.sg.dvdlibrary.controller.DVDLibraryController;
import com.sg.dvdlibrary.dao.DVDLibraryDao;
import com.sg.dvdlibrary.ui.DVDLibraryView;
import com.sg.dvdlibrary.ui.UserIO;
import com.sg.dvdlibrary.ui.UserIOConsoleImpl;

import java.util.List;

public class App {
    public static void main(String[] args) {

        UserIO myIO = new UserIOConsoleImpl();
        DVDLibraryDao myDao = new DVDLibraryDao();
        DVDLibraryView myView = new DVDLibraryView(myIO);

        DVDLibraryController controller =
                new DVDLibraryController(myDao, myView);

        controller.run();
    }

}
