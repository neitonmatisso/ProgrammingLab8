package ru.nik.businessLogic.rootCommands;

public class Message {
    private String author;
    private String receiver;
    private String text;

    public Message(String author, String text) {
        this.author = author;
        this.text = text;
    }
    public Message(){

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
