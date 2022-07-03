package com.example.Books.Collection.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id_user;

    private String nickName;

    private String login;

    private String password;

    private String email;

    private String roles;

    private boolean isEnabled;

    @JsonIgnoreProperties("user")
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Book> books;

    @OneToOne
    @JoinColumn(name = "id_token", referencedColumnName = "Id_token")
    private Token token;

    public User(){

    }

    public void addBook(Book book){
        this.books.add(book);
    }

    public int getId_user() {
        return Id_user;
    }

    public void setId_user(int id_user) {
        Id_user = id_user;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    
}