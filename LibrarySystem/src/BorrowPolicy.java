public interface BorrowPolicy {
    boolean canBorrowBooks();
    boolean canBorrowDays(int days);
    int getAllowedDays();
}