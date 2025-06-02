package project;

import java.io.*;
import java.util.*;

public class StudentManagementSystem {
    public static class Student implements Serializable {
        private String name;
        private String rollNumber;
        private String grade;
        private int age;

        public Student(String name, String rollNumber, String grade, int age) {
            this.name = name;
            this.rollNumber = rollNumber;
            this.grade = grade;
            this.age = age;
        }

        public String getName() { return name; }
        public String getRollNumber() { return rollNumber; }
        public String getGrade() { return grade; }
        public int getAge() { return age; }

        @Override
        public String toString() {
            return String.format("Roll No: %s | Name: %s | Age: %d | Grade: %s",
                    rollNumber, name, age, grade);
        }
    }

    private Map<String, Student> students = new HashMap<>();
    private static final String DATA_FILE = "students.dat";

    public StudentManagementSystem() {
        loadFromFile();
    }

    public boolean addStudent(Student student) {
        if (students.containsKey(student.getRollNumber())) {
            return false;
        }
        students.put(student.getRollNumber(), student);
        saveToFile();
        return true;
    }

    public boolean removeStudent(String rollNumber) {
        if (students.remove(rollNumber) != null) {
            saveToFile();
            return true;
        }
        return false;
    }

    public Student searchStudent(String rollNumber) {
        return students.get(rollNumber);
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        System.out.println("----- Student List -----");
        for (Student s : students.values()) {
            System.out.println(s);
        }
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.err.println("Failed to save students data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof HashMap) {
                students = (HashMap<String, Student>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to load students data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("=== Student Management System ===");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Exit");

            int choice = getIntInput(scanner, "Enter your choice: ");

            switch (choice) {
                case 1 -> addStudentUI(sms, scanner);
                case 2 -> removeStudentUI(sms, scanner);
                case 3 -> searchStudentUI(sms, scanner);
                case 4 -> sms.displayAllStudents();
                case 5 -> {
                    System.out.println("Exiting... Goodbye!");
                    exit = true;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void addStudentUI(StudentManagementSystem sms, Scanner scanner) {
        System.out.println("--- Add New Student ---");

        String rollNumber;
        while (true) {
            rollNumber = getStringInput(scanner, "Enter Roll Number: ");
            if (rollNumber.isEmpty()) {
                System.out.println("Roll number cannot be empty.");
            } else if (sms.searchStudent(rollNumber) != null) {
                System.out.println("Roll number already exists. Try another.");
            } else {
                break;
            }
        }

        String name = getStringInput(scanner, "Enter Name: ");
        int age = getIntInput(scanner, "Enter Age: ");
        String grade = getStringInput(scanner, "Enter Grade (e.g., A, B+): ");

        Student student = new Student(name, rollNumber, grade, age);
        if (sms.addStudent(student)) {
            System.out.println("Student added successfully.");
        } else {
            System.out.println("Failed to add student.");
        }
    }

    private static void removeStudentUI(StudentManagementSystem sms, Scanner scanner) {
        System.out.println("--- Remove Student ---");
        String rollNumber = getStringInput(scanner, "Enter Roll Number to remove: ");
        if (sms.removeStudent(rollNumber)) {
            System.out.println("Student removed successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void searchStudentUI(StudentManagementSystem sms, Scanner scanner) {
        System.out.println("--- Search Student ---");
        String rollNumber = getStringInput(scanner, "Enter Roll Number to search: ");
        Student student = sms.searchStudent(rollNumber);
        if (student != null) {
            System.out.println("Student found:");
            System.out.println(student);
        } else {
            System.out.println("Student not found.");
        }
    }

    private static String getStringInput(Scanner scanner, String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty.");
            }
        } while (input.isEmpty());
        return input;
    }

    private static int getIntInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value <= 0) {
                    System.out.println("Please enter a positive integer.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }
}
