public class Book {
    protected String title;
    protected String author;
    protected User borrower;
    protected int borrowDays;

    public Book(String title, String author, User borrower) {
        this.title = title;
        this.author = author;

        if (borrower.borrowOneBook(title, borrowDays)) {
            this.borrower = borrower;
            this.borrowDays = borrowDays;
        } else {
            System.out.println("Borrowing not allowed for: " + borrower.name + " (Limit Exceeded or Already Borrowed)");
        }
    }

    public void returnBook(int actualDays) {
        borrower.returnOneBook(title, actualDays);
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author;
    }
}