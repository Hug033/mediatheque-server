package server;

import java.io.Serializable;

public class Media implements Serializable {

    private byte[] photo;
    private String ref;
    private String title;
    private String author;
    private String description;
    private int nbRate;
    private int totalRate;
    private int rate;
    private int state;
    private int borrow_id;

    public Media(byte[] photo, String ref, String title, String author, String description, int nbRate, int totalRate, int state, int borrow_id) {
        this.photo = photo;
        this.ref = ref;
        this.title = title;
        this.author = author;
        this.description = description;
        this.nbRate = nbRate;
        this.totalRate = totalRate;
        this.rate = (int)(totalRate / nbRate);
        this.state = state;
        this.borrow_id = borrow_id;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public String getRef() {
        return ref;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public int getNbRate() {
        return nbRate;
    }

    public int getRate() {
        return rate;
    }

    public int getState() { return state;}

    public int getBorrowId() { return borrow_id;}
}
