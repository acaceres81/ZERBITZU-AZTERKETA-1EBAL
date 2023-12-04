package azterketa;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant();

        Scanner scanner = new Scanner(System.in);
        int numberOfDiners = 0;

        while (numberOfDiners < 10) {
            System.out.println("Enter the number of diners (must be at least 10): ");
            numberOfDiners = scanner.nextInt();
            if (numberOfDiners < 10) {
                System.out.println("Number of diners must be at least 10. Please try again.");
            }
        }

        scanner.close();

        Diner[] diners = new Diner[numberOfDiners];
        for (int i = 0; i < diners.length; i++) {
            diners[i] = new Diner(restaurant, i + 1);
        }

        for (Diner diner : diners) {
            try {
                Thread.sleep((int) (Math.random() * 3000)); // Wait between 0 to 3 seconds
                diner.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}