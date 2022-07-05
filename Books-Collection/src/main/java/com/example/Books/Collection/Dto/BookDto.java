package com.example.Books.Collection.Dto;

import lombok.Getter;

import java.util.ArrayList;


public class BookDto {

    public ArrayList<BookAuthorsDto> authors;
    public ArrayList<BookKindsDto> kinds;

    public ArrayList<BookAuthorsDto> getAuthors() {
        return authors;
    }

    public ArrayList<BookKindsDto> getKinds() {
        return kinds;
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "authors=" + authors +
                ", kinds=" + kinds +
                '}';
    }
}
