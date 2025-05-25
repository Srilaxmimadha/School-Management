package SchoolMangement;

import java.io.*;
import java.util.*;

class Student implements Serializable {
    String name;
    int rollNumber;
    String className;
    String section;
    Map<String, Integer> academicScores = new HashMap<>();
    Map<String, Integer> activityScores = new HashMap<>();
    String activity;
    String status; // ongoing, graduated, or left

    public Student(String name, int rollNumber, String className, String section, String status) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.className = className;
        this.section = section;
        this.status = status;
    }

    public void addActivity(String activity) {
        this.activity = activity;
    }

    public void addAcademicScore(String subject, int score) {
        academicScores.put(subject, score);
    }

    public void addActivityScore(int score) {
        if (activity != null) {
            activityScores.put(activity, score);
        } else {
            System.out.println("No activity assigned to add score.");
        }
    }

    public void printMarksSheet() {
        System.out.println("Marks Sheet for " + name);
        System.out.println("Academic Scores:");
        for (String subject : academicScores.keySet()) {
            System.out.println(subject + ": " + academicScores.get(subject));
        }
        if (activity != null && activityScores.containsKey(activity)) {
            System.out.println("Activity: " + activity + " Score: " + activityScores.get(activity));
        } else {
            System.out.println("No activity scores available.");
        }
        System.out.println("Status: " + status);
    }

    public void updateAcademicScore(String subject, int newScore) {
        if (academicScores.containsKey(subject)) {
            academicScores.put(subject, newScore);
        } else {
            System.out.println("Subject not found.");
        }
    }

    public void updateActivityScore(int newScore) {
        if (activity != null && activityScores.containsKey(activity)) {
            activityScores.put(activity, newScore);
        } else {
            System.out.println("Activity not found.");
        }
    }

    public void updateStudentDetails(String name, String className, String section, String status) {
        this.name = name;
        this.className = className;
        this.section = section;
        this.status = status;
    }
}

public class StudentManagementSystem {
    static Map<String, Student> students = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);
    static final String DATA_FILE = "students.dat";

    public static void main(String[] args) {
        loadData();

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add Student Details");
            System.out.println("2. Add Activity");
            System.out.println("3. Add Scores");
            System.out.println("4. Update Scores");
            System.out.println("5. Print Mark Sheet");
            System.out.println("6. Display Summary");
            System.out.println("7. Update Record");
            System.out.println("8. Display Students");
            System.out.println("9. Display Course Average");
            System.out.println("10. Display Top Scorer");
            System.out.println("0. Exit");

            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input. Enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    addStudentDetails();
                    break;
                case 2:
                    addActivity();
                    break;
                case 3:
                    addScores();
                    break;
                case 4:
                    updateScores();
                    break;
                case 5:
                    printMarkSheet();
                    break;
                case 6:
                    displayStudentSummary();
                    break;
                case 7:
                    updateRecord();
                    break;
                case 8:
                    displayStudents();
                    break;
                case 9:
                    displayCourseAverage();
                    break;
                case 10:
                    displayTopScorer();
                    break;
                case 0:
                    System.out.println("Do you want to save the data before exiting? (yes/no):");
                    String saveChoice = scanner.nextLine();
                    if (saveChoice.equalsIgnoreCase("yes")) {
                        saveData();
                        System.out.println("Data saved. Exiting...");
                    } else {
                        System.out.println("Data not saved. Exiting...");
                    }
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static String generateKey(int rollNumber, String className, String section) {
        return rollNumber + "" + className + "" + section;
    }

    static void addStudentDetails() {
        System.out.println("Enter student name:");
        String name = scanner.nextLine();
        System.out.println("Enter roll number:");
        int rollNumber = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter class:");
        String className = scanner.nextLine();
        System.out.println("Enter section:");
        String section = scanner.nextLine();
        System.out.println("Enter status (ongoing, graduated, left):");
        String status = scanner.nextLine();

        String key = generateKey(rollNumber, className, section);

        if (students.containsKey(key)) {
            System.out.println("Student already exists.");
            return;
        }
        students.put(key, new Student(name, rollNumber, className, section, status));
        System.out.println("Student details added.");
    }

    static void addActivity() {
        System.out.println("Enter roll number:");
        int rollNumber = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter class:");
        String className = scanner.nextLine();
        System.out.println("Enter section:");
        String section = scanner.nextLine();

        String key = generateKey(rollNumber, className, section);

        if (students.containsKey(key)) {
            System.out.println("Choose activity (1: Sports, 2: Arts, 3: Dancing, 4: Singing):");
            int choice = Integer.parseInt(scanner.nextLine());
            String activity = null;
            switch (choice) {
                case 1:
                    activity = "Sports";
                    break;
                case 2:
                    activity = "Arts";
                    break;
                case 3:
                    activity = "Dancing";
                    break;
                case 4:
                    activity = "Singing";
                    break;
                default:
                    activity = null;
            }
            if (activity != null) {
                students.get(key).addActivity(activity);
                System.out.println("Activity added.");
            } else {
                System.out.println("Invalid activity choice.");
            }
        } else {
            System.out.println("Student not found.");
        }
    }

    static void addScores() {
        System.out.println("Enter roll number:");
        int rollNumber = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter class:");
        String className = scanner.nextLine();
        System.out.println("Enter section:");
        String section = scanner.nextLine();

        String key = generateKey(rollNumber, className, section);

        if (students.containsKey(key)) {
            Student student = students.get(key);
            System.out.println("Add score for (1: Academic, 2: Activity):");
            int type = Integer.parseInt(scanner.nextLine());
            if (type == 1) {
                System.out.println("Enter subject:");
                String subject = scanner.nextLine();
                System.out.println("Enter score:");
                int score = Integer.parseInt(scanner.nextLine());
                student.addAcademicScore(subject, score);
                System.out.println("Academic score added.");
            } else if (type == 2) {
                if (student.activity == null) {
                    System.out.println("No activity assigned to this student.");
                } else {
                    System.out.println("Enter activity score for " + student.activity + ":");
                    int score = Integer.parseInt(scanner.nextLine());
                    student.addActivityScore(score);
                    System.out.println("Activity score added.");
                }
            } else {
                System.out.println("Invalid choice.");
            }
        } else {
            System.out.println("Student not found.");
        }
    }

    static void updateScores() {
        System.out.println("Enter roll number:");
        int rollNumber = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter class:");
        String className = scanner.nextLine();
        System.out.println("Enter section:");
        String section = scanner.nextLine();

        String key = generateKey(rollNumber, className, section);

        if (students.containsKey(key)) {
            Student student = students.get(key);
            System.out.println("Update score for (1: Academic, 2: Activity):");
            int type = Integer.parseInt(scanner.nextLine());
            if (type == 1) {
                System.out.println("Enter subject:");
                String subject = scanner.nextLine();
                System.out.println("Enter new score:");
                int score = Integer.parseInt(scanner.nextLine());
                student.updateAcademicScore(subject, score);
                System.out.println("Academic score updated.");
            } else if (type == 2) {
                if (student.activity == null) {
                    System.out.println("No activity assigned to this student.");
                } else {
                    System.out.println("Enter new activity score for " + student.activity + ":");
                    int score = Integer.parseInt(scanner.nextLine());
                    student.updateActivityScore(score);
                    System.out.println("Activity score updated.");
                }
            } else {
                System.out.println("Invalid choice.");
            }
        } else {
            System.out.println("Student not found.");
        }
    }

    static void printMarkSheet() {
        System.out.println("Enter roll number:");
        int rollNumber = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter class:");
        String className = scanner.nextLine();
        System.out.println("Enter section:");
        String section = scanner.nextLine();

        String key = generateKey(rollNumber, className, section);

        if (students.containsKey(key)) {
            students.get(key).printMarksSheet();
        } else {
            System.out.println("Student not found.");
        }
    }

    static void displayStudentSummary() {
        System.out.println("Enter roll number:");
        int rollNumber = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter class:");
        String className = scanner.nextLine();
        System.out.println("Enter section:");
        String section = scanner.nextLine();

        String key = generateKey(rollNumber, className, section);

        if (students.containsKey(key)) {
            Student selectedStudent = students.get(key);

            // Get all students in the same class and section
            List<Student> classStudents = new ArrayList<>();
            List<Student> sectionStudents = new ArrayList<>();

            for (Student student : students.values()) {
                if (student.className.equals(className)) {
                    classStudents.add(student);
                }
                if (student.section.equals(section)) {
                    sectionStudents.add(student);
                }
            }

            // Sort by total score (academic + activity)
            classStudents.sort((s1, s2) -> Integer.compare(
                    s2.academicScores.values().stream().mapToInt(Integer::intValue).sum() +
                            s2.activityScores.values().stream().mapToInt(Integer::intValue).sum(),
                    s1.academicScores.values().stream().mapToInt(Integer::intValue).sum() +
                            s1.activityScores.values().stream().mapToInt(Integer::intValue).sum()));

            sectionStudents.sort((s1, s2) -> Integer.compare(
                    s2.academicScores.values().stream().mapToInt(Integer::intValue).sum() +
                            s2.activityScores.values().stream().mapToInt(Integer::intValue).sum(),
                    s1.academicScores.values().stream().mapToInt(Integer::intValue).sum() +
                            s1.activityScores.values().stream().mapToInt(Integer::intValue).sum()));

            // Find positions in class and section
            int classPosition = 1;
            int sectionPosition = 1;

            for (Student student : classStudents) {
                if (student.rollNumber == selectedStudent.rollNumber) {
                    break;
                }
                classPosition++;
            }

            for (Student student : sectionStudents) {
                if (student.rollNumber == selectedStudent.rollNumber) {
                    break;
                }
                sectionPosition++;
            }

            // Print student details with position
            System.out.println("Student Summary for " + selectedStudent.name);
            System.out.println("Class: " + selectedStudent.className + ", Section: " + selectedStudent.section);
            System.out.println("Position in Class: " + classPosition);
            System.out.println("Position in Section: " + sectionPosition);

            System.out.println("Academic Scores:");
            selectedStudent.academicScores.forEach((subject, score) ->
                    System.out.println(subject + ": " + score));

            if (selectedStudent.activity != null && selectedStudent.activityScores.containsKey(selectedStudent.activity)) {
                System.out.println("Activity: " + selectedStudent.activity + " Score: " + selectedStudent.activityScores.get(selectedStudent.activity));
            } else {
                System.out.println("No activity scores available.");
            }
            System.out.println("Status: " + selectedStudent.status);
        } else {
            System.out.println("Student not found.");
        }
    }

    static void updateRecord() {
        System.out.println("Enter roll number:");
        int rollNumber = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter class:");
        String className = scanner.nextLine();
        System.out.println("Enter section:");
        String section = scanner.nextLine();

        String key = generateKey(rollNumber, className, section);

        if (students.containsKey(key)) {
            Student student = students.get(key);
            System.out.println("Enter new name (current: " + student.name + "):");
            String newName = scanner.nextLine();
            System.out.println("Enter new class (current: " + student.className + "):");
            String newClass = scanner.nextLine();
            System.out.println("Enter new section (current: " + student.section + "):");
            String newSection = scanner.nextLine();
            System.out.println("Enter new status (ongoing, graduated, left) (current: " + student.status + "):");
            String newStatus = scanner.nextLine();

            // Remove old key and update
            students.remove(key);
            student.updateStudentDetails(newName.isEmpty() ? student.name : newName,
                    newClass.isEmpty() ? student.className : newClass,
                    newSection.isEmpty() ? student.section : newSection,
                    newStatus.isEmpty() ? student.status : newStatus);

            // Generate new key after update
            String newKey = generateKey(student.rollNumber, student.className, student.section);
            students.put(newKey, student);

            System.out.println("Student record updated.");
        } else {
            System.out.println("Student not found.");
        }
    }

    static void displayStudents() {
        System.out.println("Enter class to display (or 'all' for all classes):");
        String className = scanner.nextLine();

        if (className.equalsIgnoreCase("all")) {
            for (Student student : students.values()) {
                System.out.println(student.rollNumber + " - " + student.name + " - Class: " + student.className + " Section: " + student.section);
            }
        } else {
            for (Student student : students.values()) {
                if (student.className.equalsIgnoreCase(className)) {
                    System.out.println(student.rollNumber + " - " + student.name + " - Section: " + student.section);
                }
            }
        }
    }

    static void displayCourseAverage() {
        System.out.println("Enter class:");
        String className = scanner.nextLine();
        System.out.println("Enter section:");
        String section = scanner.nextLine();

        int studentCount = 0;
        Map<String, Integer> totalScores = new HashMap<>();
        int totalActivityScore = 0;

        for (Student student : students.values()) {
            if (student.className.equalsIgnoreCase(className) && student.section.equalsIgnoreCase(section)) {
                studentCount++;
                for (Map.Entry<String, Integer> entry : student.academicScores.entrySet()) {
                    totalScores.put(entry.getKey(), totalScores.getOrDefault(entry.getKey(), 0) + entry.getValue());
                }
                totalActivityScore += student.activityScores.values().stream().mapToInt(Integer::intValue).sum();
            }
        }

        if (studentCount == 0) {
            System.out.println("No students found in the given class and section.");
            return;
        }

        System.out.println("Average Scores for Class " + className + " Section " + section + ":");
        for (String subject : totalScores.keySet()) {
            System.out.println(subject + ": " + (totalScores.get(subject) / studentCount));
        }
        System.out.println("Activity Average Score: " + (totalActivityScore / studentCount));
    }

    static void displayTopScorer() {
        System.out.println("Enter class:");
        String className = scanner.nextLine();
        System.out.println("Enter section:");
        String section = scanner.nextLine();

        Student topScorer = null;
        int highestScore = -1;

        for (Student student : students.values()) {
            if (student.className.equalsIgnoreCase(className) && student.section.equalsIgnoreCase(section)) {
                int totalScore = student.academicScores.values().stream().mapToInt(Integer::intValue).sum() +
                        student.activityScores.values().stream().mapToInt(Integer::intValue).sum();
                if (totalScore > highestScore) {
                    highestScore = totalScore;
                    topScorer = student;
                }
            }
        }

        if (topScorer != null) {
            System.out.println("Top Scorer in Class " + className + " Section " + section + ":");
            System.out.println(topScorer.name + " with total score: " + highestScore);
        } else {
            System.out.println("No students found in the given class and section.");
        }
    }

    static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(students);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    static void loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            students = (HashMap<String, Student>) ois.readObject();
            System.out.println("Data loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}
