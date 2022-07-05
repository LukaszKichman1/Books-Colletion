package com.example.Books.Collection.Dto;

public class BookAuthorsDto {
    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "BookAuthorsDto{" +
                "name='" + name + '\'' +
                '}';
    }
}
