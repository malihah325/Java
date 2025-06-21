public class Teacher extends User {
    public Teacher(String name, String id) {
        super(name, id, "Teacher");
    }

    @Override
    public boolean canBorrowBooks() {
        return borrowedBooksCount < 5;
    }

    @Override
    public boolean canBorrowDays(int days) {
        return days <= 180;
    }

    @Override
    public int getAllowedDays() {
        return 180;
    }
}