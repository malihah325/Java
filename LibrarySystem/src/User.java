import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

public abstract class User implements BorrowPolicy, PenaltyPolicy {
    protected String name;
    protected String id;
    protected String profession;
    protected int days;
    protected int borrowedBooksCount = 0;
    protected LibraryCard libraryCard;
    protected int returnedBooksCount = 0;
    protected Set<String> borrowedTitles = new HashSet<>();
    protected Map<String, Integer> penalties = new HashMap<>();

    // To preserve existing users and cards
    private static final Map<String, User> userRegistry = new HashMap<>();

    public static User getUserById(String id) {
        return userRegistry.get(id);
    }

    public static void registerUser(User user) {
        userRegistry.put(user.id, user);
    }

    public User(String name, String id, String profession) {
        this.name = name;
        this.id = id;
        this.profession = profession;

        if (userRegistry.containsKey(id)) {
            User existingUser = userRegistry.get(id);
            this.libraryCard = existingUser.libraryCard;
            this.borrowedBooksCount = existingUser.borrowedBooksCount;
            this.borrowedTitles = existingUser.borrowedTitles;
            this.penalties = existingUser.penalties;
            System.out.println(name + " reusing existing Library Card: " + libraryCard.getCardId());
        } else {
            this.libraryCard = new LibraryCard(id);
            userRegistry.put(id, this);
            System.out.println(name + " received a new Library Card: " + libraryCard.getCardId());
        }
    }

    public boolean borrowOneBook(String title, int days) {
        if (borrowedTitles.contains(title)) {
            System.out.println("Already borrowed this book: " + title);
            return false;
        }
        if (canBorrowBooks() && canBorrowDays(days)) {
            borrowedBooksCount++;
            borrowedTitles.add(title);
            this.days = days;
            return true;
        }
        return false;
    }

    public void returnOneBook(String title, int actualDays) {
        if (borrowedTitles.contains(title)) {
            borrowedTitles.remove(title);
            borrowedBooksCount--;
            returnedBooksCount++; // 🔹 This line increments the returned book count
            int penalty = calculatePenalty(actualDays);
            if (penalty > 0) {
                penalties.put(title, penalty);
                System.out.println(" Penalty applied for book '" + title + "': Rs." + penalty);
            }
        }
        if (borrowedBooksCount == 0) {
            System.out.println(name + " has returned all books. Card remains on file: " + libraryCard.getCardId());
        }
    }

    public int getBorrowedBooksCount() {
        return borrowedBooksCount;
    }

    public Map<String, Integer> getPenalties() {
        return penalties;
    }

    @Override
    public int calculatePenalty(int days) {
        int allowedDays = getAllowedDays();
        return days > allowedDays ? (days - allowedDays) * 10 : 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name)
                .append(", Profession: ").append(profession)
                .append(", Books Borrowed: ").append(borrowedBooksCount)
                .append(", Books Returned: ").append(returnedBooksCount)
                .append(", Penalty applied for: ");
        if (penalties.isEmpty()) {
            sb.append("0");
        } else {
            penalties.forEach((title, penalty) -> sb.append("\n  → Book: ").append(title).append(", Rs.").append(penalty));
        }
        sb.append(", Card ID: ").append(libraryCard != null ? libraryCard.getCardId() : "None");
        return sb.toString();
    }



    public static class UserBuilder {
        private String name;
        private String id;
        private String profession;

        public UserBuilder(String name, String id) {
            this.name = name;
            this.id = id;
        }

        public UserBuilder setProfession(String profession) {
            this.profession = profession;
            return this;
        }

        public User build() {
            if (User.getUserById(id) != null) {
                return User.getUserById(id);
            }
            if ("Student".equalsIgnoreCase(profession)) {
                User student = new Student(name, id);
                User.registerUser(student);
                return student;
            } else if ("Teacher".equalsIgnoreCase(profession)) {
                User teacher = new Teacher(name, id);
                User.registerUser(teacher);
                return teacher;
            } else {
                throw new IllegalArgumentException("Unsupported profession: " + profession);
            }
        }
    }
}
