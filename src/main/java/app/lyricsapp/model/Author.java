package app.lyricsapp.model;

import java.io.Serializable;
import java.util.Objects;

public class Author implements Serializable {
    private  String name;

    public Author(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        return Objects.equals(name, author.name);
    }
}
