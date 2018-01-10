package digitalbedrock.software.pbcore.core;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Observer;

public final class Settings implements Serializable {

    private static final long serialVersionUID = 7426471155622776147L;
    private static final int MAX_SAVED_SEARCHES = 10;

    private transient HashSet<Observer> observers = null;

    private final HashSet<File> directories = new HashSet<>();

    private final ArrayList<String> savedSearches = new ArrayList<>();

    public Settings() {
    }

    public void addObserver(Observer oberserver) {
        if (observers == null) {
            observers = new HashSet<>();
        }
        observers.add(oberserver);
    }

    public void deleteObserver(Observer o) {
        observers.remove(o);
    }

    private void notifyObservers() {
        observers.forEach((obs) -> {
            obs.update(null, this);
        });
    }

    public HashSet<File> getDirectories() {
        return directories;
    }

    public void addPath(File p) {
        directories.add(p);
        notifyObservers();
    }

    public void addSearch(String s) {
        savedSearches.remove(s);
        savedSearches.add(0, s);
        while (savedSearches.size() > MAX_SAVED_SEARCHES) {
            savedSearches.remove(savedSearches.size() - 1);
        }
    }

}
