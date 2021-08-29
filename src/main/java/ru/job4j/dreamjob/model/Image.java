package ru.job4j.dreamjob.model;

public class Image {

    private int id;
    private byte[] content;

    public Image(int aId) {
        id = aId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public boolean isEmpty() {
        return content == null || content.length == 0;
    }
}
