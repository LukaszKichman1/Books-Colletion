package com.example.Books.Collection.Entity;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id_book;

    private String title;

    private String kind;

    private String author;

    private int rate;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "Id_user")
    private User user;

    public Book() {

    }

    public int getId_book() {
        return Id_book;
    }

    public void setId_book(int id_book) {
        Id_book = id_book;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static class Builder {
        private String title;

        private String kind;

        private String author;

        private int rate;
        private User user;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder kind(String kind) {
            this.kind = kind;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder rate(int rate) {
            this.rate = rate;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Book build() {
            Book book = new Book();
            book.title = this.title;
            book.kind = this.kind;
            book.author = this.author;
            book.rate = this.rate;
            book.user = this.user;
            return book;
        }

    }
}
