package es.ucm.fdi.googlebooksclient;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookInfo {
    private String title;
    //private String authors;
    private ArrayList<String> authorsList;
    private URL infoLink;
    BookInfo(String title){
        this.title = title;
    }

    public BookInfo(String title, List<String> authors, URL infoLink) {
        this.title = title;
        this.authorsList = (ArrayList<String>) authors;
        this.infoLink = infoLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

   /* public String getAuthors() {
        return authors;
    }*/

    public List<String> getAuthorsList(){return authorsList;}

    /*public void setAuthors(String authors) {
        this.authors = authors;
    }*/

    public URL getInfoLink() {
        return infoLink;
    }

    public void setInfoLink(URL infoLink) {
        this.infoLink = infoLink;
    }

    private String getAutListAsString()
    {
        String aux = "";
        for (String a: authorsList ) {
            aux += a + " ";
        }
        return aux;
    }

    @Override
    public String toString() {
        return "BookInfo{" +
                "title='" + title + '\'' +
                ", authors='" + getAutListAsString() + '\'' +
                ", infoLink=" + infoLink +
                '}';
    }
}
