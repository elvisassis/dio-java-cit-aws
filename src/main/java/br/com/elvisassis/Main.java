package br.com.elvisassis;

import br.com.elvisassis.generics.dao.UserDao;
import br.com.elvisassis.generics.domain.UserDomain;
import br.com.elvisassis.generics.repository.Repository;

import java.util.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Repository<Integer, UserDomain> dao = new UserDao();

    public static void main(String[] args) {

        boolean running = true;

        while (running) {
            printMenu();
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> save();
                case 2 -> saveBatch();
                case 3 -> saveAll();
                case 4 -> findAll();
                case 5 -> findById();
                case 6 -> update();
                case 7 -> delete();
                case 8 -> printIds();
                case 9 -> addIntegersDemo();
                case 10 -> count();
                case 0 -> running = false;
                default -> System.out.println("Invalid option");
            }
        }

        System.out.println("Finished.");
    }

    /* ================= MENU ================= */

    private static void printMenu() {
        System.out.println("""
                
                ===== GENERIC DAO DEMO =====
                1  - Save (T)
                2  - Save batch (T...)
                3  - Save all (? extends T)
                4  - Find all
                5  - Find by id
                6  - Update
                7  - Delete
                8  - Print IDs (static generic)
                9  - Add integers (? super Integer)
                10 - Count
                0  - Exit
                ===========================
                Choose:
                """);
    }

    /* ================= ACTIONS ================= */

    private static void save() {
        UserDomain user = readUser();
        dao.save(user);
        System.out.println("Saved: " + user);
    }

    private static void saveBatch() {
        UserDomain u1 = new UserDomain(1, "Alice", 22);
        UserDomain u2 = new UserDomain(2, "Bob",25);

        dao.saveBatch(2, u1, u2);
        System.out.println("Batch saved.");
    }

    private static void saveAll() {
        List<UserDomain> users = List.of(
                new UserDomain(3, "Charlie",26),
                new UserDomain(4, "Diana",27)
        );

        dao.saveAll(users); // ? extends T
        System.out.println("saveAll executed.");
    }

    private static void findAll() {
        dao.findAll().forEach(System.out::println);
    }

    private static void findById() {
        System.out.print("ID: ");
        Integer id = scanner.nextInt();

        dao.find(u -> u.getId().equals(id))
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("UserDao not found")
                );
    }

    private static void update() {
        System.out.print("ID to update: ");
        Integer id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("New name: ");
        String name = scanner.nextLine();

        System.out.println("Age: ");
        int age = scanner.nextInt();

        UserDomain updated = new UserDomain(id, name, age);
        dao.update(id, updated);
        System.out.println("Updated: " + updated);
    }

    private static void delete() {
        UserDomain user = readUser();
        boolean removed = dao.delete(user);
        System.out.println(removed ? "Deleted." : "UserDao not found.");
    }

    private static void printIds() {
        Repository.printIds(dao.findAll());
    }

    private static void addIntegersDemo() {
        List<Number> numbers = new ArrayList<>();

        List<? super Integer> result = Repository.addIntegers(numbers);

        System.out.println("Numbers after addIntegers:");
        result.forEach(System.out::println);
    }

    private static void count() {
        System.out.println("Total records: " + dao.count());
    }

    /* ================= HELPERS ================= */

    private static UserDomain readUser() {
        System.out.print("ID: ");
        Integer id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.println("Age: ");
        int age = scanner.nextInt();

        return new UserDomain(id, name, age);
    }
}
