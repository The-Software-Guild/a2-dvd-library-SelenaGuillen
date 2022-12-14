package com.sg.dvdlibrary.controller;

import com.sg.dvdlibrary.dao.DVDLibraryDao;
import com.sg.dvdlibrary.dao.DVDLibraryDaoException;
import com.sg.dvdlibrary.dto.DVD;
import com.sg.dvdlibrary.ui.DVDLibraryView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class LibraryController {
    private DVDLibraryDao dao;
    private DVDLibraryView view;

    @Autowired
    public LibraryController(DVDLibraryDao dao, DVDLibraryView view) {
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
        view.displaySearchBanner();
        //Menu Options
        boolean keepGoing = true;
        int menuSelection = 0;
        try {
            while (keepGoing) {
                menuSelection = view.printSearchMenuAndGetSelection();

                switch(menuSelection) {
                    case 1:
                        searchByNYears();
                        break;
                    case 2:
                        searchByMPAARating();
                        break;
                    case 3:
                        searchByDirectorName();
                        break;
                    case 4:
                        searchByStudio();
                        break;
                    case 5:
                        searchForNewest();
                        break;
                    case 6:
                        searchForOldest();
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
        LocalDate newReleaseDate = view.printEditAndGetNewDate();
        dao.editReleaseDate(dvd.getTitle(), newReleaseDate);
        view.displayDateChangedSuccess(newReleaseDate);
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

    private void searchByNYears() throws DVDLibraryDaoException {
        view.displaySearchResultBanner();
        int years = view.printAndCollectNYears();
        List<DVD> searchResults = dao.findMoviesReleasedNYears(years);
        view.displaySearchResults(searchResults);
    }
    private void searchByMPAARating() throws DVDLibraryDaoException {
        view.displaySearchResultBanner();
        String mpaaRating = view.printAndCollectRating();
        List<DVD> searchResults = dao.findByMPAARating(mpaaRating);
        view.displaySearchResults(searchResults);
    }
    private void searchByDirectorName() throws DVDLibraryDaoException {
        view.displaySearchResultBanner();
        String directorName = view.printAndCollectDN();
        List<DVD> searchResults = dao.findByDirector(directorName);
        view.displaySearchResults(searchResults);
    }

    private void searchByStudio() throws DVDLibraryDaoException {
        view.displaySearchResultBanner();
        String studio = view.printAndCollectStudio();
        List<DVD> searchResults = dao.findByStudio(studio);
        view.displaySearchResults(searchResults);
    }

    private void searchForOldest() throws DVDLibraryDaoException {
        view.displaySearchResultBanner();
        List<DVD> searchResults = dao.findOldest();
        view.displaySearchResults(searchResults);
    }

    private void searchForNewest() throws DVDLibraryDaoException {
        view.displaySearchResultBanner();
        List<DVD> searchResults = dao.findNewest();
        view.displaySearchResults(searchResults);
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }
}
