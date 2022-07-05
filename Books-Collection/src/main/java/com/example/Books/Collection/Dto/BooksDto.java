package com.example.Books.Collection.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BooksDto {
    private String title;
    private String author;
    private String kind;

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getKind() {
        return kind;
    }

    @Override
    public String toString() {
        return "BooksDto{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", kind='" + kind + '\'' +
                '}';
    }
}
