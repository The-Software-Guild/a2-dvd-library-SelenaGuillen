package com.sg.dvdlibrary.dao;

import com.sg.dvdlibrary.dto.DVD;

import java.io.*;
import java.util.*;

public class DVDLibraryDao {
    public static final String DVD_FILE = "DVDLibrary.txt";
    public static final String DELIMITER = "::";

    private Map<String, DVD> dvdList = new HashMap<>();

    public DVD addDVD(String title, DVD dvd) throws DVDLibraryDaoException{
        loadDVDLibrary();
        DVD newDVD = dvdList.put(title, dvd);
        writeDVDLibrary();
        return newDVD;
    }

    public DVD removeDVD(String title) throws DVDLibraryDaoException {
        loadDVDLibrary();
        DVD removedDVD = dvdList.remove(title);
        writeDVDLibrary();
        return removedDVD;
    }

    public DVD getDVD(String title) throws DVDLibraryDaoException {
        loadDVDLibrary();
        return dvdList.get(title);
    }
    public List<DVD> getAllDVDs() throws DVDLibraryDaoException {
        loadDVDLibrary();
        return new ArrayList<>(dvdList.values());
    }

    public DVD editTitle(String oldTitle, String newTitle) throws DVDLibraryDaoException {
        loadDVDLibrary();
        DVD currentDVD = getDVD(oldTitle);
        currentDVD.setTitle(newTitle);
        writeDVDLibrary();
        return currentDVD;
    }
    //Data Persistence
    private DVD unmarshallDVD(String dvdAsText) {
        String[] dvdTokens = dvdAsText.split(DELIMITER);
        String title = dvdTokens[0];

        DVD dvdFromFile = new DVD(title);

        dvdFromFile.setYear(Integer.parseInt(dvdTokens[1]));
        dvdFromFile.setMPAARating(dvdTokens[2]);
        dvdFromFile.setDirectorName(dvdTokens[3]);
        dvdFromFile.setStudio(dvdTokens[4]);
        dvdFromFile.setDescription(dvdTokens[5]);

        return dvdFromFile;
    }

    private void loadDVDLibrary() throws DVDLibraryDaoException {
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(DVD_FILE)));
        } catch (FileNotFoundException e) {
            throw new DVDLibraryDaoException(
                    "Could not load DVD Library into memory.", e);
        }

        String currentLine;
        DVD currentDVD;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentDVD = unmarshallDVD(currentLine);

            dvdList.put(currentDVD.getTitle(), currentDVD);
        }
        scanner.close();
    }

    private String marshallDVD(DVD dvd) {
        String dvdAsText = dvd.getTitle() + DELIMITER;
        dvdAsText += dvd.getYear() + DELIMITER;
        dvdAsText += dvd.getMPAARating() + DELIMITER;
        dvdAsText += dvd.getDirectorName() + DELIMITER;
        dvdAsText += dvd.getStudio() + DELIMITER;
        dvdAsText += dvd.getDescription();

        return dvdAsText;
    }

    private void writeDVDLibrary() throws DVDLibraryDaoException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(DVD_FILE));
        } catch (IOException e) {
            throw new DVDLibraryDaoException(
                    "Could not save DVD data.", e);
        }

        String dvdAsText;
        List<DVD> dvdsList = this.getAllDVDs();
        for (DVD currentDVD: dvdsList) {
            dvdAsText = marshallDVD(currentDVD);
            out.println(dvdAsText);
            out.flush();
        }
        out.close();
    }
}
