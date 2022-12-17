package library.app.com;

import androidx.annotation.NonNull;

public class WhislistPinjamBuku {
    private int book_id;
    private String book_title, book_type, book_author, book_isbn, book_borrowed, book_pages, book_status;

    public WhislistPinjamBuku(){

    }

    public String getBook_status() {
        return book_status;
    }

    public void setBook_status(String book_status) {
        this.book_status = book_status;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public String getBook_type() {
        return book_type;
    }

    public void setBook_type(String book_type) {
        this.book_type = book_type;
    }

    public String getBook_author() {
        return book_author;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public String getBook_isbn() {
        return book_isbn;
    }

    public void setBook_isbn(String book_isbn) {
        this.book_isbn = book_isbn;
    }

    public String getBook_borrowed() {
        return book_borrowed;
    }

    public void setBook_borrowed(String book_borrowed) {
        this.book_borrowed = book_borrowed;
    }

    public String getBook_pages() {
        return book_pages;
    }

    public void setBook_pages(String book_pages) {
        this.book_pages = book_pages;
    }

    @NonNull
    @Override
    public String toString() {
        return "Book Title: "+book_title+ "\n" +book_author+ "\nCategory: " +book_type;
    }
}