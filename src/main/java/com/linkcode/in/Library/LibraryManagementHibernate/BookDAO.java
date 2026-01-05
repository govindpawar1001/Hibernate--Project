package com.linkcode.in.Library.LibraryManagementHibernate;

import java.util.List;
import java.util.Scanner;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class BookDAO {

    static EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("hibernate");

    static EntityManager em = emf.createEntityManager();

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n===== Library Management System =====");
            System.out.println("1. Add Book");
            System.out.println("2. View Book");
            System.out.println("3. ViewAllBooks");
            System.out.println("4. Update Book");
            System.out.println("5. Delete Book");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1: addBook(); break;
                case 2: viewBook();break;
                case 3: viewAllBooks(); break;
                case 4: updateBook(); break;
                case 5: deleteBook(); break;
                case 6:
                    em.close();
                    emf.close();
                    System.out.println("Thank You!");
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

 

	// ---------- INSERT ----------
    static void addBook() {

        sc.nextLine(); // clear buffer (VERY IMPORTANT)

        System.out.print("Enter Title: ");
        String title = sc.nextLine();

        System.out.print("Enter Author: ");
        String author = sc.nextLine();

        System.out.print("Enter Price: ");
        double price = sc.nextDouble();

        BookDTO book = new BookDTO();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPrice(price);

        em.getTransaction().begin();
        em.persist(book);
        em.getTransaction().commit();

        System.out.println("Book Added Successfully with ID: " + book.getId());
    }


    static void viewBook() {

        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();

        BookDTO book = em.find(BookDTO.class, id);

        if (book != null) {
            System.out.println("\nID: " + book.getId());
            System.out.println("Title: " + book.getTitle());
            System.out.println("Author: " + book.getAuthor());
            System.out.println("Price: " + book.getPrice());
        } else {
            System.out.println("Book Not Found");
        }
    }
    
    // ---------- SELECT ----------
    static void viewAllBooks() {

        List<BookDTO> books =
                em.createQuery("FROM BookDTO", BookDTO.class)
                  .getResultList();

        if (books.isEmpty()) {
            System.out.println("No Books Found");
            return;
        }

        System.out.println("\n===== Book List =====");
        System.out.printf("%-5s %-25s %-20s %-10s%n",
                "ID", "Title", "Author", "Price");
        System.out.println("--------------------------------------------------------");

        for (BookDTO book : books) {
            System.out.printf("%-5d %-25s %-20s %-10.2f%n",
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPrice());
        }
    }

    // ---------- UPDATE ----------
    static void updateBook() {

        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();
        sc.nextLine(); // consume newline

        BookDTO book = em.find(BookDTO.class, id);

        if (book != null) {

            System.out.print("Enter New Title: ");
            String title = sc.nextLine();

            System.out.print("Enter New Author: ");
            String author = sc.nextLine();

            System.out.print("Enter New Price: ");
            double price = sc.nextDouble();

            em.getTransaction().begin();

            book.setTitle(title);
            book.setAuthor(author);
            book.setPrice(price);

            em.getTransaction().commit();

            System.out.println("Book Updated Successfully ");
        } else {
            System.out.println("Book Not Found ");
        }
    }


    // ---------- DELETE ----------
    static void deleteBook() {

        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();

        BookDTO book = em.find(BookDTO.class, id);

        if (book != null) {
            em.getTransaction().begin();
            em.remove(book);
            em.getTransaction().commit();

            System.out.println("Book Deleted Successfully");
        } else {
            System.out.println("Book Not Found");
        }
    }
}
