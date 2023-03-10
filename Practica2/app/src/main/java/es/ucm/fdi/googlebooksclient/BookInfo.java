package es.ucm.fdi.googlebooksclient;

import java.net.URL;

public class BookInfo {
    private String title;
    private String authors;
    private URL infoLink;
    BookInfo(String title){
        this.title = title;
    }

    public BookInfo(String title, String authors, URL infoLink) {
        this.title = title;
        this.authors = authors;
        this.infoLink = infoLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public URL getInfoLink() {
        return infoLink;
    }

    public void setInfoLink(URL infoLink) {
        this.infoLink = infoLink;
    }

    @Override
    public String toString() {
        return "BookInfo{" +
                "title='" + title + '\'' +
                ", authors='" + authors + '\'' +
                ", infoLink=" + infoLink +
                '}';
    }
}
