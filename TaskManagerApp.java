import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Task implements Comparable<Task> {
    private String description;
    private int priority; // 1 (high) to 5 (low)

    public Task(String description, int priority) {
        this.description = description;
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    // Sorting tasks by priority (ascending)
    @Override
    public int compareTo(Task other) {
        return Integer.compare(this.priority, other.priority);
    }

    @Override
    public String toString() {
        return "[" + priority + "] " + description;
    }
}

public class TaskManagerApp {
    private ArrayList<Task> tasks = new ArrayList<>();

    public void addTask(String description, int priority) {
        tasks.add(new Task(description, priority));
        System.out.println("Task added!");
    }

    public void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }
        // Sort tasks by priority before displaying
        Collections.sort(tasks);
        System.out.println("\nYour Tasks:");
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Task Manager!");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter task description: ");
                    String desc = scanner.nextLine();
                    System.out.print("Enter priority (1=high, 5=low): ");
                    int prio = scanner.nextInt();
                    scanner.nextLine();
                    addTask(desc, prio);
                    break;
                case 2:
                    viewTasks();
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public static void main(String[] args) {
        TaskManagerApp app = new TaskManagerApp();
        app.run();
    }
}
