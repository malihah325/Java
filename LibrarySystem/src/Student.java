public class Student extends User {
    public Student(String name, String id) {
        super(name, id, "Student");
    }

    @Override
    public boolean canBorrowBooks() {
        return borrowedBooksCount < 3;
    }

    @Override
    public boolean canBorrowDays(int days) {
        return days <= 90;
    }

    @Override
    public int getAllowedDays() {
        return 90;
    }
}