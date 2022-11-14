package com.sg.dvdlibrary.controller;

import com.sg.dvdlibrary.dao.DVDLibraryDao;
import com.sg.dvdlibrary.dao.DVDLibraryDaoException;
import com.sg.dvdlibrary.dto.DVD;
import com.sg.dvdlibrary.ui.DVDLibraryView;
import com.sun.security.jgss.GSSUtil;

import java.util.List;

public class DVDLibraryController {
    private DVDLibraryDao dao;
    private DVDLibraryView view;

    public DVDLibraryController(DVDLibraryDao dao, DVDLibraryView view) {
        this.dao = dao;
        this.view = view;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        try {
            while (keepGoing) {
                menuSelection = getMenuSelection();

                switch(menuSelection) {
                    case 1:
                        addDVD();
                        break;
                    case 2:
                        removeDVD();
                        break;
                    case 3:
                        edit();
                        break;
                    case 4:
                        listDVDs();
                        break;
                    case 5:
                        displayDVDInfo();
                        break;
                    case 6:
                        search();
                        break;
                    case 7:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
            }
            exitMessage();
        } catch (DVDLibraryDaoException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }
    private void addDVD() throws DVDLibraryDaoException {
        view.displayAddDVDBanner();
        DVD newDVD = view.getNewDVDInfo();
        dao.addDVD(newDVD.getTitle(), newDVD);
        view.displayAddSuccessBanner();
    }

    private void removeDVD() throws DVDLibraryDaoException {
        view.displayRemoveDVDBanner();
        String title = view.getDVDTitleChoice();
        DVD removedDVD = dao.removeDVD(title);
        view.displayRemoveResult(removedDVD);
    }
    private void listDVDs() throws DVDLibraryDaoException {
        view.displayDisplayAllBanner();
        List<DVD> dvdList = dao.getAllDVDs();
        view.displayDVDList(dvdList);
    }

    private void displayDVDInfo() throws DVDLibraryDaoException {
        view.displayDVDInfoBanner();
        String title = view.getDVDTitleChoice();
        DVD dvd = dao.getDVD(title);
        view.displayDVDInformation(dvd);
    }

    private void search() throws DVDLibraryDaoException {
        view.displaySearchResultBanner();
        List<DVD> dvdList = dao.getAllDVDs();
        String title = view.getDVDTitleChoice();
        view.displaySearchResults(dvdList, title);
    }

    private void edit() throws DVDLibraryDaoException {
        view.displayEditMenuBanner();
        String title = view.getDVDTitleChoice();
        DVD currentDVD = dao.getDVD(title);

        //Menu Options
        boolean keepGoing = true;
        int menuSelection = 0;
        try {
            while (keepGoing) {
                menuSelection = view.printEditMenuAndGetSelection();

                switch(menuSelection) {
                    case 1:
                        editTitle(currentDVD);
                        break;
                    case 2:
                        editYear(currentDVD);
                        break;
                    case 3:
                        editMPAARating(currentDVD);
                        break;
                    case 4:
                        editDirectorName(currentDVD);
                        break;
                    case 5:
                        editStudio(currentDVD);
                        break;
                    case 6:
                        editDescription(currentDVD);
                        break;
                    case 7:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
            }
        } catch (DVDLibraryDaoException e) {
            view.displayErrorMessage(e.getMessage());
        }

    }

    //TODO: fix issue where edited dvd gets printed twice
    private void editTitle(DVD dvd) throws DVDLibraryDaoException {
        view.displayEditTitleBanner();
        String newTitle = view.printEditAndGetNewTitle();
        dao.editTitle(dvd.getTitle(), newTitle);
        view.displayTitleChangedSuccess(newTitle);
    }

    private void editYear(DVD dvd) throws DVDLibraryDaoException {
        view.displayEditYearBanner();
        int newYear = view.printEditAndGetNewYear();
        dao.editYear(dvd.getTitle(), newYear);
        view.displayYearChangedSuccess(newYear);
    }

    private void editMPAARating(DVD dvd) throws DVDLibraryDaoException {
        view.displayEditMPAARatingBanner();
        String newMPAARating = view.printEditAndGetNewMPAARating();
        dao.editMPAARating(dvd.getTitle(), newMPAARating);
        view.displayMPAARatingChangedSuccess(newMPAARating);
    }

    private void editDirectorName(DVD dvd) throws DVDLibraryDaoException {
        view.displayEditDirectorNameBanner();
        String newDirectorName = view.printEditAndGetNewDirectorName();
        dao.editDirectorName(dvd.getTitle(), newDirectorName);
        view.displayDirectorNameChangedSuccess(newDirectorName);
    }

    private void editStudio(DVD dvd) throws DVDLibraryDaoException {
        view.displayEditStudioBanner();
        String newStudio = view.printEditAndGetNewStudio();
        dao.editStudio(dvd.getTitle(), newStudio);
        view.displayMPAARatingChangedSuccess(newStudio);
    }

    private void editDescription(DVD dvd) throws DVDLibraryDaoException {
        view.displayEditDescriptionBanner();
        String newDescription = view.printEditAndGetNewDescription();
        dao.editDescription(dvd.getTitle(), newDescription);
        view.displayDescriptionChangedSuccess(newDescription);
    }
    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }
}
