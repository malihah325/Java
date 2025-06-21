public class LibraryCard {
    private static int instanceCount = 0;
    private final String cardId;

    public LibraryCard(String userId) {
        this.cardId = "LIBRARYCARD-" + userId;
        instanceCount++;
    }

    public String getCardId() {
        return cardId;
    }

    public static int getIssuedCardCount() {
        return instanceCount;
    }
}
