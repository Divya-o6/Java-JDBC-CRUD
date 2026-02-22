import java.sql.*;
import java.util.Scanner;

public class StudentCRUD {

    static final String URL = "jdbc:mysql://localhost:3306/studentdb";
    static final String USER = "root";
    static final String PASSWORD = "root";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            int choice;

            do {
                System.out.println("\n===== STUDENT CRUD MENU =====");
                System.out.println("1. Insert Student");
                System.out.println("2. View Students");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        insertStudent(con, sc);
                        break;
                    case 2:
                        viewStudents(con);
                        break;
                    case 3:
                        updateStudent(con, sc);
                        break;
                    case 4:
                        deleteStudent(con, sc);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }

            } while (choice != 5);

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // INSERT
    public static void insertStudent(Connection con, Scanner sc) throws SQLException {
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Marks: ");
        int marks = sc.nextInt();

        String query = "INSERT INTO students(name, email, marks) VALUES (?, ?, ?)";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, name);
        pst.setString(2, email);
        pst.setInt(3, marks);

        pst.executeUpdate();
        System.out.println("Student Inserted Successfully!");
    }

    // SELECT
    public static void viewStudents(Connection con) throws SQLException {
        String query = "SELECT * FROM students";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        System.out.println("\nID\tName\tEmail\tMarks");
        while (rs.next()) {
            System.out.println(
                rs.getInt("id") + "\t" +
                rs.getString("name") + "\t" +
                rs.getString("email") + "\t" +
                rs.getInt("marks")
            );
        }
    }

    // UPDATE
    public static void updateStudent(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter Student ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter New Name: ");
        String name = sc.nextLine();

        System.out.print("Enter New Email: ");
        String email = sc.nextLine();

        System.out.print("Enter New Marks: ");
        int marks = sc.nextInt();

        String query = "UPDATE students SET name=?, email=?, marks=? WHERE id=?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, name);
        pst.setString(2, email);
        pst.setInt(3, marks);
        pst.setInt(4, id);

        int rows = pst.executeUpdate();
        if (rows > 0)
            System.out.println("Student Updated Successfully!");
        else
            System.out.println("Student Not Found!");
    }

    // DELETE
    public static void deleteStudent(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter Student ID to delete: ");
        int id = sc.nextInt();

        String query = "DELETE FROM students WHERE id=?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, id);

        int rows = pst.executeUpdate();
        if (rows > 0)
            System.out.println("Student Deleted Successfully!");
        else
            System.out.println("Student Not Found!");
    }
}