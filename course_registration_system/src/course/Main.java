package course;

import coursedao.*;
import course.model.*;
import coursedao.AdminDAO;
import coursedao.CourseDAO;
import coursedao.EnrollmentDAO;
import coursedao.StudentDAO;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        StudentDAO studentDAO = new StudentDAO();
        AdminDAO adminDAO = new AdminDAO();
        CourseDAO courseDAO = new CourseDAO();
        EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

        System.out.println("\uD83C\uDF93 Welcome to Course Registration System \uD83C\uDF93");

        while (true) {
            System.out.println("\nSelect User Type:");
            System.out.println("1. Student");
            System.out.println("2. Admin");
            System.out.println("3. Exit");
            System.out.print("Choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // flush

            switch (choice) {
                case 1 -> studentMenu(sc, studentDAO, courseDAO, enrollmentDAO);
                case 2 -> adminMenu(sc, adminDAO, courseDAO, studentDAO, enrollmentDAO);
                case 3 -> {
                    System.out.println("\uD83D\uDC4B Thank you for using the system!");
                    System.exit(0);
                }
                default -> System.out.println("\u274C Invalid choice. Try again.");
            }
        }
    }

    private static void studentMenu(Scanner sc, StudentDAO studentDAO, CourseDAO courseDAO, EnrollmentDAO enrollmentDAO) {
        System.out.println("\n--- Student Section ---");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.print("Choice: ");
        int option = sc.nextInt();
        sc.nextLine();

        int studentId = -1;

        if (option == 1) {
            System.out.print("Email: ");
            String email = sc.nextLine();
            System.out.print("Password: ");
            String password = sc.nextLine();
            studentId = studentDAO.login(email, password);

            if (studentId == -1) {
                System.out.println("\u274C Invalid login.");
                return;
            }
            System.out.println("\u2705 Login successful!");
        } else if (option == 2) {
            System.out.print("Name: ");
            String name = sc.nextLine();
            System.out.print("Email: ");
            String email = sc.nextLine();
            System.out.print("Password: ");
            String password = sc.nextLine();

            Student s = new Student(0, name, email, password);
            if (studentDAO.register(s)) {
                System.out.println("\u2705 Registration successful! Please login.");
                return;
            } else {
                System.out.println("\u274C Registration failed.");
                return;
            }
        } else {
            System.out.println("\u274C Invalid option.");
            return;
        }

        while (true) {
            System.out.println("\n--- Student Dashboard ---");
            System.out.println("1. View All Courses");
            System.out.println("2. Enroll in Course");
            System.out.println("3. Drop a Course");
            System.out.println("4. My Courses");
            System.out.println("5. Logout");
            System.out.print("Choice: ");
            int studentChoice = sc.nextInt();

            switch (studentChoice) {
                case 1 -> {
                    List<Course> courses = courseDAO.getAllCourses();
                    System.out.println("\n\uD83D\uDCDA Available Courses:");
                    for (Course c : courses) {
                        System.out.println(c.getCourseId() + " - " + c.getCourseName() + " [Capacity: " + c.getCapacity() + "]");
                    }
                }
                case 2 -> {
                    System.out.print("Enter Course ID to Enroll: ");
                    int cid = sc.nextInt();
                    if (enrollmentDAO.enroll(studentId, cid)) {
                        System.out.println("\u2705 Enrolled successfully!");
                    }
                }
                case 3 -> {
                    System.out.print("Enter Course ID to Drop: ");
                    int cid = sc.nextInt();
                    if (enrollmentDAO.drop(studentId, cid)) {
                        System.out.println("\u2705 Dropped successfully!");
                    }
                }
                case 4 -> enrollmentDAO.viewEnrolledCourses(studentId);
                case 5 -> {
                    System.out.println("\uD83D\uDC4B Logged out.");
                    return;
                }
                default -> System.out.println("\u274C Invalid choice.");
            }
        }
    }

    private static void adminMenu(Scanner sc, AdminDAO adminDAO, CourseDAO courseDAO, StudentDAO studentDAO, EnrollmentDAO enrollmentDAO) throws Exception {
        System.out.print("\nUsername: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        int adminId = adminDAO.login(username, password);
        if (adminId == -1) {
            System.out.println("\u274C Admin login failed.");
            return;
        }

        System.out.println("\u2705 Admin login successful!");

        while (true) {
            System.out.println("\n--- Admin Dashboard ---");
            System.out.println("1. Add Course");
            System.out.println("2. View Courses");
            System.out.println("3. Delete Course");
            System.out.println("4. Logout");
            System.out.println("5. View All Students");
            System.out.println("6. View Student's Enrollments");
            System.out.print("Choice: ");
            int adminChoice = sc.nextInt();
            sc.nextLine();

            switch (adminChoice) {
                case 1 -> {
                    System.out.print("Course Name: ");
                    String name = sc.nextLine();
                    System.out.print("Capacity: ");
                    int capacity = sc.nextInt();
                    Course course = new Course(0, name, capacity);
                    if (courseDAO.addCourse(course)) {
                        System.out.println("\u2705 Course added.");
                    }
                }
                case 2 -> {
                    List<Course> courses = courseDAO.getAllCourses();
                    System.out.println("\n\uD83D\uDCDA All Courses:");
                    for (Course c : courses) {
                        System.out.println(c.getCourseId() + " - " + c.getCourseName() + " [Capacity: " + c.getCapacity() + "]");
                    }
                }
                case 3 -> {
                    System.out.print("Enter Course ID to Delete: ");
                    int id = sc.nextInt();
                    if (courseDAO.deleteCourse(id)) {
                        System.out.println("\u2705 Course deleted.");
                    }
                }
                case 4 -> {
                    System.out.println("\uD83D\uDC4B Admin logged out.");
                    return;
                }
                case 5 -> {
                    List<Student> students = studentDAO.getAllStudents();
                    System.out.println("\n\uD83D\uDC65 Registered Students:");
                    for (Student s : students) {
                        System.out.println(s.getStudentId() + " - " + s.getName() + " | " + s.getEmail());
                    }
                }
                case 6 -> {
                    System.out.print("Enter Student ID to view their enrollments: ");
                    int sid = sc.nextInt();
                    enrollmentDAO.viewStudentEnrollments(sid);
                }
                default -> System.out.println("\u274C Invalid choice.");
            }
        }
    }
}
