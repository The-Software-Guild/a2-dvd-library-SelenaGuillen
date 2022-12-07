package com.sg.dvdlibrary.dao;

import com.sg.dvdlibrary.dto.DVD;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Component
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

    public DVD editTitle(String title, String newTitle) throws DVDLibraryDaoException {
        loadDVDLibrary();
        DVD currentDVD = getDVD(title);
        currentDVD.setTitle(newTitle);
        writeDVDLibrary();
        return currentDVD;
    }

    public DVD editReleaseDate(String title, LocalDate newDate) throws DVDLibraryDaoException{
        loadDVDLibrary();
        DVD currentDVD = getDVD(title);
        currentDVD.setReleaseDate(newDate);
        writeDVDLibrary();
        return currentDVD;
    }

    public DVD editMPAARating(String title, String newMPAARating) throws DVDLibraryDaoException {
        loadDVDLibrary();
        DVD currentDVD = getDVD(title);
        currentDVD.setMPAARating(newMPAARating);
        writeDVDLibrary();
        return currentDVD;
    }

    public DVD editDirectorName(String title, String newDirectorName) throws DVDLibraryDaoException {
        loadDVDLibrary();
        DVD currentDVD = getDVD(title);
        currentDVD.setDirectorName(newDirectorName);
        writeDVDLibrary();
        return currentDVD;
    }

    public DVD editStudio(String title, String newStudio) throws DVDLibraryDaoException {
        loadDVDLibrary();
        DVD currentDVD = getDVD(title);
        currentDVD.setStudio(newStudio);
        writeDVDLibrary();
        return currentDVD;
    }

    public DVD editDescription(String title, String newDescription) throws DVDLibraryDaoException {
        DVD currentDVD = getDVD(title);
        currentDVD.setDescription(newDescription);
        writeDVDLibrary();
        return currentDVD;
    }

    public List<DVD> findMoviesReleasedNYears(long years) throws DVDLibraryDaoException{
        loadDVDLibrary();
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusYears(years);
        List<DVD> dvds = new ArrayList<>(dvdList.values());
        List<DVD> searchResults = dvds.stream()
                .filter((dvd -> dvd.getReleaseDate().isAfter(startDate)))
                .collect(Collectors.toList());
        return searchResults;
    }

    public List<DVD> findByMPAARating(String mpaaRating) throws DVDLibraryDaoException {
        loadDVDLibrary();
        List<DVD> dvds = new ArrayList<>(dvdList.values());
        List<DVD> searchResults = dvds.stream()
                .filter((dvd -> dvd.getMPAARating().equalsIgnoreCase(mpaaRating)))
                .collect(Collectors.toList());
        return searchResults;
    }

    public List<DVD> findByDirector(String directorName) throws DVDLibraryDaoException {
        loadDVDLibrary();
        List<DVD> dvds = new ArrayList<>(dvdList.values());
        List<DVD> searchResults = dvds.stream()
                .filter((dvd -> dvd.getDirectorName().equalsIgnoreCase(directorName)))
                .collect(Collectors.toList());
        return searchResults;
    }

    public List<DVD> findByStudio(String studio) throws DVDLibraryDaoException {
        loadDVDLibrary();
        List<DVD> dvds = new ArrayList<>(dvdList.values());
        List<DVD> searchResults = dvds.stream()
                .filter((dvd -> dvd.getStudio().equalsIgnoreCase(studio)))
                .collect(Collectors.toList());
        return searchResults;
    }

    public List<DVD> findOldest() throws DVDLibraryDaoException{
        loadDVDLibrary();
        List<DVD> dvds = new ArrayList<>(dvdList.values());
        List<LocalDate> dates = new ArrayList<>();

        for (DVD dvd: dvds) {
            dates.add(dvd.getReleaseDate());
        }

        LocalDate oldest = dates.stream()
                .min(Comparator.comparing(LocalDate::toEpochDay)).get();

        List<DVD> oldestDVDs = dvds.stream()
                .filter((dvd -> dvd.getReleaseDate().isEqual(oldest)))
                .collect(Collectors.toList());

        return oldestDVDs;

    }

    public List<DVD> findNewest() throws DVDLibraryDaoException{
        loadDVDLibrary();
        List<DVD> dvds = new ArrayList<>(dvdList.values());
        List<LocalDate> dates = new ArrayList<>();

        for (DVD dvd: dvds) {
            dates.add(dvd.getReleaseDate());
        }

        LocalDate newest = dates.stream()
                .max(Comparator.comparing(LocalDate::toEpochDay)).get();

        List<DVD> newestDVDs = dvds.stream()
                .filter((dvd -> dvd.getReleaseDate().isEqual(newest)))
                .collect(Collectors.toList());

        return newestDVDs;

    }


    //Data Persistence
    private DVD unmarshallDVD(String dvdAsText) {
        String[] dvdTokens = dvdAsText.split(DELIMITER);
        String title = dvdTokens[0];

        DVD dvdFromFile = new DVD(title);

        LocalDate releaseDate = LocalDate.parse(dvdTokens[1]);

        dvdFromFile.setReleaseDate(releaseDate);
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
        dvdAsText += dvd.getReleaseDate() + DELIMITER;
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
