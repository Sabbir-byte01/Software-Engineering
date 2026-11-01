
import java.sql.*;
import java.util.Scanner;

public class IntegerDatabase {

    public static boolean isPrime(int num) {

        if (num < 2) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the prime number position: ");
        int n = sc.nextInt();

        if (n <= 0) {
            System.out.println("Please enter a positive number.");
            sc.close();
            return;
        }

        String url = "jdbc:mysql://localhost:3306/integer_db";
        String user = "root";
        String password = "12345";

        String sql = "INSERT INTO integers (number) VALUES (?)";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(sql)) {

            int count = 0;
            int num = 2;
            int primeNumber = 0;

            while (count < n) {

                if (isPrime(num)) {
                    count++;
                    primeNumber = num;
                }

                num++;
            }

            pst.setInt(1, primeNumber);
            pst.executeUpdate();

            System.out.println("The " + n + "th prime number is: " + primeNumber);
            System.out.println("Data saved successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        sc.close();
    }
}
