package main;

public class Main {
    public static void main(String[] args) {
        User u1 = new User("User1");
        User u2 = new User("User2");

        u1.buyPackage(4);
        u2.buyPackage(4);

        u1.openPackages();
        u2.openPackages();

        for (var card: u1.getCards()) {
            System.out.println("card: "+ card.getClass().getSimpleName());
        }

    }
}
