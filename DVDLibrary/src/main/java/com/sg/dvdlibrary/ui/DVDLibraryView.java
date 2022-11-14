package com.sg.dvdlibrary.ui;


import com.sg.dvdlibrary.dto.DVD;

import java.util.List;

public class DVDLibraryView {
    private UserIO io;

    public DVDLibraryView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        io.print("Main Menu");
        io.print("1. Add a DVD");
        io.print("2. Remove a DVD");
        io.print("3. Edit a DVD");
        io.print("4. List all DVDs");
        io.print("5. Display Information for a DVD");
        io.print("6. Search for a DVD by Title");
        io.print("7. Exit");

        return io.readInt("Please select from the above choices: ", 1, 7);

    }

    public String getDVDTitleChoice() {
        //TODO: ignore case
        return io.readString("Please enter the title");
    }
    public void displayAddDVDBanner() {
        io.print("=== Add a DVD ===");
    }
    public void displayAddSuccessBanner() {
        io.readString("DVD added successfully. Please hit enter to continue.");
    }
    public DVD getNewDVDInfo() {
        String title = io.readString("Please enter the title");
        String year = io.readString("Please enter the year released");
        String mpaaRating = io.readString("Please enter the MPAA rating");
        String directorName = io.readString("Please enter the director's name");
        String studio = io.readString("Please enter the studio");
        String description = io.readString("Please enter a short description");

        DVD currentDVD = new DVD(title);
        currentDVD.setYear(Integer.parseInt(year));
        currentDVD.setMPAARating(mpaaRating);
        currentDVD.setDirectorName(directorName);
        currentDVD.setStudio(studio);
        currentDVD.setDescription(description);
        return currentDVD;
    }

    public void displayRemoveDVDBanner() {
        io.print("=== Remove a DVD ===");
    }

    public void displayRemoveSuccessBanner() {
        io.readString("DVD removed successfully. Please hit Enter to continue.");
    }

    public void displayRemoveResult(DVD dvd) {
        if (dvd != null) {
            io.print(dvd.getTitle() + " removed successfully.");
        } else {
            io.print("No such DVD.");
        }
        io.readString("Please hit Enter to continue.");
    }

    public void displayDVDInfoBanner() {
        io.print("=== DVD Information ===");
    }
    public void displayDVDInformation(DVD dvd) {
        String dvdInfo = String.format("%s \n------------------\nYear Released: %s " +
                        "\nMPAA Rating: %s\nDirector Name: %s\n" +
                        "Studio: %s\nDescription: %s\n",
                dvd.getTitle(),
                dvd.getYear(),
                dvd.getMPAARating(),
                dvd.getDirectorName(),
                dvd.getStudio(),
                dvd.getDescription());
        io.print(dvdInfo);
        io.readString("Please hit Enter to continue.");
    }

    public void displayDisplayAllBanner() {
        io.print("=== Display All DVDs ===");
    }
    public void displayDVDList(List<DVD> dvdList) {
        for (DVD currentDVD : dvdList) {
            io.print(currentDVD.getTitle() + " (" + currentDVD.getYear() + ")");
        }
        io.readString("Please hit Enter to continue");
    }

    public void displaySearchResultBanner() {
        io.print("=== Search Results ===");
    }

    public void displaySearchResults(List<DVD> dvdList, String title) {
        int count = 1;
        for (DVD currentDVD: dvdList) {
            if (currentDVD.getTitle().equalsIgnoreCase(title)) {
                io.print(count + ". " + currentDVD.getTitle() + " (" + currentDVD.getYear() + ")");
                count++;
            }
        }
        io.readString("Please hit Enter to continue.");
    }

    public void displayEditMenuBanner() {
        io.print("=== Edit Menu ===");
    }

    public int printEditMenuAndGetSelection() {
        io.print("1. Edit Title");
        io.print("2. Edit Year");
        io.print("3. Edit MPAA Rating");
        io.print("4. Edit Director Name");
        io.print("5. Edit Studio");
        io.print("6. Edit Description");
        io.print("7. Cancel and Go Back");
        return io.readInt("Please select from the above choices.", 1, 7);
    }

    public void displayEditTitleBanner() {
        io.print("=== Edit Title ===");
    }

    public String printEditAndGetNewTitle() {
        return io.readString("Please enter the new title.");
    }

    public void displayTitleChangedSuccess(String newTitle) {
        io.print("Title successfully changed to " + newTitle + "\n");
        io.readString("Please hit Enter to continue");
    }

    public void displayEditYearBanner() {
        io.print("=== Edit Year ===");
    }

    public int printEditAndGetNewYear() {
        return io.readInt("Please enter the new year");
    }

    public void displayYearChangedSuccess(int newYear) {
        io.print("Year successfully changed to " + newYear + "\n");
        io.readString("Please hit Enter to continue.");
    }

    public void displayEditMPAARatingBanner() {
        io.print("=== Edit MPAA Rating ===");
    }

    public String printEditAndGetNewMPAARating() {
        return io.readString("Please enter the new MPAA Rating.");
    }

    public void displayMPAARatingChangedSuccess(String newMPAARating) {
        io.print("MPAA Rating successfully changed to " + newMPAARating + "\n");
        io.readString("Please hit Enter to continue.");
    }

    public void displayEditDirectorNameBanner() {
        io.print("=== Edit Director Name ===");
    }

    public String printEditAndGetNewDirectorName() {
        return io.readString("Please enter the new Director Name.");
    }

    public void displayDirectorNameChangedSuccess(String newDirectorName) {
        io.print("Director Name successfully changed to " + newDirectorName + "\n");
        io.readString("Please hit Enter to continue.");
    }

    public void displayEditStudioBanner() {
        io.print("=== Edit Studio ===");
    }

    public String printEditAndGetNewStudio() {
        return io.readString("Please enter the new Studio.");
    }

    public void displayStudioChangedSuccess(String newStudio) {
        io.print("Studio successfully changed to " + newStudio + "\n");
        io.readString("Please hit Enter to continue.");
    }

    public void displayEditDescriptionBanner() {
        io.print("=== Edit Description ===");
    }

    public String printEditAndGetNewDescription() {
        return io.readString("Please enter the new Description.");
    }

    public void displayDescriptionChangedSuccess(String newDescription) {
        io.print("Description successfully changed to " + newDescription + "\n");
        io.readString("Please hit Enter to continue.");
    }
    public void displayExitBanner() {
        io.print("Good Bye!");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }
}
