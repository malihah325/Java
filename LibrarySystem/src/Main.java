public class Main {
        public static void main(String[] args) {
            User teacher1 = new User.UserBuilder("Ali", "S001").setProfession("Teacher").build();
            Book history = new Book("World War 2", "John",teacher1);
            Book literature = new Book("Advanced English", "Peterson", teacher1);
            Book comic = new Book("Harry Potter", "Berlin", teacher1);
/*
        System.out.println("Borrowed Books Count: " + teacher1.getBorrowedBooksCount());*/
        history.returnBook(200); // late return should trigger penalty
            //literature.returnBook(89); // no penalty

        User student2 = new User.UserBuilder("Ali", "S001").setProfession("Student").build();// reuse

        System.out.println("Student1: " + teacher1);
        System.out.println("Student2: " + teacher1);
        System.out.println("Books:");
        System.out.println(history);
        System.out.println(literature);
    }
}