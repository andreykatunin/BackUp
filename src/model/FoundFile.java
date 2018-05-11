package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FoundFile {

    private StringProperty name;
    private StringProperty path;

    public FoundFile () {
    }

    public FoundFile (String s1, String s2) {

        this.name = new SimpleStringProperty(s1);
        this.path = new SimpleStringProperty(s2);
    }

    public String getName() {

        return name.get();
    }
    public void setName(String s) {
	
        name.set(s);
    }
    
    public StringProperty nameProperty() {
    	return name;
    }

    public String getPath() {

        return path.get();
    }
    public void setPath(String s) {

        path.set(s);
    }
    
    public StringProperty pathProperty() {
    	return path;
    }

    @Override
    public String toString() {

        return (name.get() + ", in " + path.get());
    }
}