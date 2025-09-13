import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
// Book class to represent a book
class Book {
    int id;
    String title;
    String author;
    boolean isAvailable;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isAvailable = true;  // By default, a new book is available
    }

    @Override
    public String toString() {
        return id + ". " + title + " by " + author + " - " + (isAvailable ? "Available" : "Borrowed");
    }
}

// User class to represent a library user
class User {
    int id;
    String name;
    ArrayList<Integer> borrowedBooks;  // List of book IDs

    public User(int id, String name) {
        this.id = id;
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    @Override
    public String toString() {
        return id + ". " + name + " - Borrowed Books: "
                + (borrowedBooks.isEmpty() ? "None" : borrowedBooks);
    }
}

// Main Library Management System class (contains the main() method)
public class LibraryManagementSystem {
    private static final Scanner scanner = new Scanner(System.in);

    // Using HashMaps to store Book and User objects by their ID
    private static final HashMap<Integer, Book> books = new HashMap<>();
    private static final HashMap<Integer, User> users = new HashMap<>();

    // Counters to generate unique IDs for books and users
    private static int bookIdCounter = 1;
    private static int userIdCounter = 1;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Library Management System ===");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Register User");
            System.out.println("4. View Users");
            System.out.println("5. Borrow Book");
            System.out.println("6. Return Book");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        addBook();
                        break;
                    case 2:
                        viewBooks();
                        break;
                    case 3:
                        registerUser();
                        break;
                    case 4:
                        viewUsers();
                        break;
                    case 5:
                        borrowBook();
                        break;
                    case 6:
                        returnBook();
                        break;
                    case 7:
                        System.out.println("Goodbye! Exiting the system.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    // 1. Add a new book
    private static void addBook() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine().trim();

        if (title.isEmpty() || author.isEmpty()) {
            System.out.println("Book title and author cannot be empty.");
            return;
        }

        Book book = new Book(bookIdCounter++, title, author);
        books.put(book.id, book);
        System.out.println("Book added successfully: " + book);
    }

    // 2. View all books
    private static void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
        } else {
            System.out.println("\nAvailable Books:");
            books.values().forEach(System.out::println);
        }
    }

    // 3. Register a new user
    private static void registerUser() {
        System.out.print("Enter user name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("User name cannot be empty.");
            return;
        }

        User user = new User(userIdCounter++, name);
        users.put(user.id, user);
        System.out.println("User registered successfully: " + user);
    }

    // 4. View all users
    private static void viewUsers() {
        if (users.isEmpty()) {
            System.out.println("No users registered in the system.");
        } else {
            System.out.println("\nRegistered Users:");
            users.values().forEach(System.out::println);
        }
    }

    // 5. Borrow a book
    private static void borrowBook() {
        try {
            System.out.print("Enter user ID: ");
            int userId = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter book ID: ");
            int bookId = Integer.parseInt(scanner.nextLine());

            if (!users.containsKey(userId) || !books.containsKey(bookId)) {
                System.out.println("Invalid user ID or book ID.");
                return;
            }

            Book book = books.get(bookId);
            User user = users.get(userId);

            if (book.isAvailable) {
                book.isAvailable = false;
                user.borrowedBooks.add(bookId);
                System.out.println(user.name + " borrowed \"" + book.title + "\" successfully.");
            } else {
                System.out.println("Sorry, this book is already borrowed.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid numerical IDs.");
        }
    }

    // 6. Return a borrowed book
    private static void returnBook() {
        try {
            System.out.print("Enter user ID: ");
            int userId = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter book ID: ");
            int bookId = Integer.parseInt(scanner.nextLine());

            if (!users.containsKey(userId) || !books.containsKey(bookId)) {
                System.out.println("Invalid user ID or book ID.");
                return;
            }

            User user = users.get(userId);
            Book book = books.get(bookId);

            if (user.borrowedBooks.contains(bookId)) {
                user.borrowedBooks.remove(Integer.valueOf(bookId));
                book.isAvailable = true;
                System.out.println(user.name + " returned \"" + book.title + "\" successfully.");
            } else {
                System.out.println("This user did not borrow that book.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid numerical IDs.");
        }
    }
}