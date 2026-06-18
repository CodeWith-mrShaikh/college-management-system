package com.college.menu;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import com.college.entity.Book;
import com.college.entity.Department;
import com.college.entity.Staff;
import com.college.entity.Student;
import com.college.exception.ResourceNotFoundException;
import com.college.service.BookService;
import com.college.service.DepartmentService;
import com.college.service.StaffService;
import com.college.service.StudentService;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.DisposableBean;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Component
@ConditionalOnProperty(value = "app.console.enabled", havingValue = "true", matchIfMissing = true)
public class ConsoleMenu implements DisposableBean {
    private Scanner scanner;
    private final StudentService studentService;
    private final StaffService staffService;
    private final DepartmentService departmentService;
    private final BookService bookService;

    public ConsoleMenu(StudentService studentService, StaffService staffService, DepartmentService departmentService, BookService bookService) {
        this.studentService = studentService;
        this.staffService = staffService;
        this.departmentService = departmentService;
        this.bookService = bookService;
    }

    public void start() {
        scanner = new Scanner(System.in);
        while (true) {
            System.out.println("================================");
            System.out.println("COLLEGE MANAGEMENT SYSTEM");
            System.out.println("================================");
            System.out.println("1. Student Management");
            System.out.println("2. Staff Management");
            System.out.println("3. Department Management");
            System.out.println("4. Book Management");
            System.out.println("5. Reports");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
            switch (choice) {
                case 1: studentMenu(); break;
                case 2: staffMenu(); break;
                case 3: departmentMenu(); break;
                case 4: bookMenu(); break;
                case 5: reports(); break;
                case 6: System.out.println("Exiting..."); return;
                default: System.out.println("Invalid choice");
            }
        }
    }

    private void studentMenu() {
        while (true) {
            System.out.println("\nStudent Menu");
            System.out.println("1 Add Student");
            System.out.println("2 View Student");
            System.out.println("3 View All Students");
            System.out.println("4 Update Student");
            System.out.println("5 Delete Student");
            System.out.println("6 Back");
            System.out.print("Enter choice: ");
            int c;
            try {
                c = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
            try {
                switch (c) {
                    case 1: addStudent(); break;
                    case 2: viewStudent(); break;
                    case 3: viewAllStudents(); break;
                    case 4: updateStudent(); break;
                    case 5: deleteStudent(); break;
                    case 6: return;
                    default: System.out.println("Invalid");
                }
            } catch (ResourceNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void addStudent() {
        System.out.print("Enter Student ID: "); String id = scanner.nextLine();
        System.out.print("Enter Name: "); String name = scanner.nextLine();
        System.out.print("Enter Email: "); String email = scanner.nextLine();
        System.out.print("Enter Phone: "); String phone = scanner.nextLine();
        System.out.print("Enter Department ID: "); String deptId = scanner.nextLine();
        Student s = new Student(id, name, email, phone, deptId);
        studentService.addStudent(s);
        System.out.println("Student added.");
    }

    private void viewStudent() {
        System.out.print("Enter Student ID: "); String id = scanner.nextLine();
        Student s = studentService.getStudent(id);
        System.out.println("ID: " + s.getId() + " Name: " + s.getName() + " Email: " + s.getEmail() + " Dept: " + s.getDepartmentId());
    }

    private void viewAllStudents() {
        List<Student> list = studentService.getAll();
        list.forEach(s -> System.out.println(s.getId() + " - " + s.getName() + " - " + s.getDepartmentId()));
    }

    private void updateStudent() {
        System.out.print("Enter Student ID to update: "); String id = scanner.nextLine();
        Student s = studentService.getStudent(id);
        System.out.print("Enter new Email (leave blank to keep): "); String email = scanner.nextLine();
        if (!email.isBlank()) s.setEmail(email);
        System.out.print("Enter new Phone (leave blank to keep): "); String phone = scanner.nextLine();
        if (!phone.isBlank()) s.setPhone(phone);
        studentService.updateStudent(s);
        System.out.println("Updated.");
    }

    private void deleteStudent() {
        System.out.print("Enter Student ID to delete: "); String id = scanner.nextLine();
        studentService.deleteStudent(id);
        System.out.println("Deleted.");
    }

    private void staffMenu() {
        while (true) {
            System.out.println("\nStaff Menu");
            System.out.println("1 Add Staff");
            System.out.println("2 View Staff");
            System.out.println("3 View All Staff");
            System.out.println("4 Update Staff");
            System.out.println("5 Delete Staff");
            System.out.println("6 Back");
            System.out.print("Enter choice: ");
            int c;
            try {
                c = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
            try {
                switch (c) {
                    case 1: addStaff(); break;
                    case 2: viewStaff(); break;
                    case 3: viewAllStaff(); break;
                    case 4: updateStaff(); break;
                    case 5: deleteStaff(); break;
                    case 6: return;
                    default: System.out.println("Invalid");
                }
            } catch (ResourceNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void addStaff() {
        System.out.print("Enter Staff ID: "); String id = scanner.nextLine();
        System.out.print("Enter Name: "); String name = scanner.nextLine();
        System.out.print("Enter Designation: "); String des = scanner.nextLine();
        System.out.print("Enter Email: "); String email = scanner.nextLine();
        System.out.print("Enter Department ID: "); String deptId = scanner.nextLine();
        Staff s = new Staff(id, name, des, email, deptId);
        staffService.addStaff(s);
        System.out.println("Staff added.");
    }

    private void viewStaff() {
        System.out.print("Enter Staff ID: "); String id = scanner.nextLine();
        Staff s = staffService.getStaff(id);
        System.out.println(s.getId() + " - " + s.getName() + " - " + s.getDesignation());
    }

    private void viewAllStaff() {
        List<Staff> list = staffService.getAll();
        list.forEach(s -> System.out.println(s.getId() + " - " + s.getName() + " - " + s.getDesignation()));
    }

    private void updateStaff() {
        System.out.print("Enter Staff ID to update: "); String id = scanner.nextLine();
        Staff s = staffService.getStaff(id);
        System.out.print("Enter new Email (leave blank to keep): "); String email = scanner.nextLine();
        if (!email.isBlank()) s.setEmail(email);
        System.out.print("Enter new Designation (leave blank to keep): "); String des = scanner.nextLine();
        if (!des.isBlank()) s.setDesignation(des);
        staffService.updateStaff(s);
        System.out.println("Updated.");
    }

    private void deleteStaff() {
        System.out.print("Enter Staff ID to delete: "); String id = scanner.nextLine();
        staffService.deleteStaff(id);
        System.out.println("Deleted.");
    }

    private void departmentMenu() {
        while (true) {
            System.out.println("\nDepartment Menu");
            System.out.println("1 Add Department");
            System.out.println("2 View Department");
            System.out.println("3 View All Departments");
            System.out.println("4 Update Department");
            System.out.println("5 Delete Department");
            System.out.println("6 Back");
            System.out.print("Enter choice: ");
            int c;
            try {
                c = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
            try {
                switch (c) {
                    case 1: addDepartment(); break;
                    case 2: viewDepartment(); break;
                    case 3: viewAllDepartments(); break;
                    case 4: updateDepartment(); break;
                    case 5: deleteDepartment(); break;
                    case 6: return;
                    default: System.out.println("Invalid");
                }
            } catch (ResourceNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void addDepartment() {
        System.out.print("Enter Department ID: "); String id = scanner.nextLine();
        System.out.print("Enter Name: "); String name = scanner.nextLine();
        System.out.print("Enter HOD Name: "); String hod = scanner.nextLine();
        System.out.print("Enter Location: "); String loc = scanner.nextLine();
        Department d = new Department(id, name, hod, loc);
        departmentService.addDepartment(d);
        System.out.println("Department added.");
    }

    private void viewDepartment() {
        System.out.print("Enter Department ID: "); String id = scanner.nextLine();
        Department d = departmentService.getDepartment(id);
        System.out.println(d.getId() + " - " + d.getDepartmentName() + " - HOD: " + d.getHodName());
    }

    private void viewAllDepartments() {
        List<Department> list = departmentService.getAll();
        list.forEach(d -> System.out.println(d.getId() + " - " + d.getDepartmentName()));
    }

    private void updateDepartment() {
        System.out.print("Enter Department ID to update: "); String id = scanner.nextLine();
        Department d = departmentService.getDepartment(id);
        System.out.print("Enter new HOD Name (leave blank to keep): "); String hod = scanner.nextLine();
        if (!hod.isBlank()) d.setHodName(hod);
        System.out.print("Enter new Location (leave blank to keep): "); String loc = scanner.nextLine();
        if (!loc.isBlank()) d.setLocation(loc);
        departmentService.updateDepartment(d);
        System.out.println("Updated.");
    }

    private void deleteDepartment() {
        System.out.print("Enter Department ID to delete: "); String id = scanner.nextLine();
        departmentService.deleteDepartment(id);
        System.out.println("Deleted.");
    }

    private void bookMenu() {
        while (true) {
            System.out.println("\nBook Menu");
            System.out.println("1 Add Book");
            System.out.println("2 View Book");
            System.out.println("3 View All Books");
            System.out.println("4 Update Book");
            System.out.println("5 Delete Book");
            System.out.println("6 Issue Book to Student");
            System.out.println("7 Back");
            System.out.print("Enter choice: ");
            int c;
            try {
                c = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
            try {
                switch (c) {
                    case 1: addBook(); break;
                    case 2: viewBook(); break;
                    case 3: viewAllBooks(); break;
                    case 4: updateBook(); break;
                    case 5: deleteBook(); break;
                    case 6: issueBook(); break;
                    case 7: return;
                    default: System.out.println("Invalid");
                }
            } catch (ResourceNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void addBook() {
        System.out.print("Enter Book ID: "); String id = scanner.nextLine();
        System.out.print("Enter Title: "); String title = scanner.nextLine();
        System.out.print("Enter Author: "); String author = scanner.nextLine();
        System.out.print("Enter ISBN: "); String isbn = scanner.nextLine();
        Book b = new Book(id, title, author, isbn);
        bookService.addBook(b);
        System.out.println("Book added.");
    }

    private void viewBook() {
        System.out.print("Enter Book ID: "); String id = scanner.nextLine();
        Book b = bookService.getBook(id);
        System.out.println(b.getId() + " - " + b.getTitle() + " - " + b.getAuthor());
    }

    private void viewAllBooks() {
        List<Book> list = bookService.getAll();
        list.forEach(b -> System.out.println(b.getId() + " - " + b.getTitle()));
    }

    private void updateBook() {
        System.out.print("Enter Book ID to update: "); String id = scanner.nextLine();
        Book b = bookService.getBook(id);
        System.out.print("Enter new Title (leave blank to keep): "); String title = scanner.nextLine();
        if (!title.isBlank()) b.setTitle(title);
        System.out.print("Enter new Author (leave blank to keep): "); String author = scanner.nextLine();
        if (!author.isBlank()) b.setAuthor(author);
        bookService.updateBook(b);
        System.out.println("Updated.");
    }

    private void deleteBook() {
        System.out.print("Enter Book ID to delete: "); String id = scanner.nextLine();
        bookService.deleteBook(id);
        System.out.println("Deleted.");
    }

    private void issueBook() {
        System.out.print("Enter Student ID: "); String sid = scanner.nextLine();
        Student s = studentService.getStudent(sid);
        System.out.print("Enter Book ID to issue: "); String bid = scanner.nextLine();
        // validate book exists
        bookService.getBook(bid);
        if (s.getBorrowedBookIds() == null) s.setBorrowedBookIds(new java.util.ArrayList<>());
        s.getBorrowedBookIds().add(bid);
        studentService.updateStudent(s);
        System.out.println("Book issued.");
    }

    private void reports() {
        System.out.println("Reports:\n1 Students per Department\n2 Books Issued\n3 Staff per Department\n4 Back");
        System.out.print("Enter choice: ");
        int c;
        try {
            c = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException ex) {
            System.out.println("Invalid input. Please enter a number.");
            return;
        }
        switch (c) {
            case 1: studentsPerDepartment(); break;
            case 2: booksIssued(); break;
            case 3: staffPerDepartment(); break;
            case 4: return;
            default: System.out.println("Invalid");
        }
    }

    private void studentsPerDepartment() {
        List<Student> students = studentService.getAll();
        List<Department> depts = departmentService.getAll();
        for (Department d : depts) {
            long count = students.stream().filter(s -> Objects.equals(d.getId(), s.getDepartmentId())).count();
            System.out.println((d.getDepartmentName() == null ? "(unknown)" : d.getDepartmentName()) + " : " + count);
        }
    }

    private void booksIssued() {
        List<Student> students = studentService.getAll();
        for (Student s : students) {
            if (s.getBorrowedBookIds() != null && !s.getBorrowedBookIds().isEmpty()) {
                System.out.println("Student ID : " + s.getId());
                for (String bid : s.getBorrowedBookIds()) {
                    try { System.out.println(" - " + bookService.getBook(bid).getTitle()); } catch (ResourceNotFoundException ex) { System.out.println(" - Book ID " + bid + " (not found)"); }
                }
            }
        }
    }

    private void staffPerDepartment() {
        List<Staff> staff = staffService.getAll();
        List<Department> depts = departmentService.getAll();
        for (Department d : depts) {
            long count = staff.stream().filter(s -> Objects.equals(d.getId(), s.getDepartmentId())).count();
            System.out.println((d.getDepartmentName() == null ? "(unknown)" : d.getDepartmentName()) + " : " + count + " Staff");
        }
    }

    @Override
    public void destroy() throws Exception {
        try {
            if (scanner != null) scanner.close();
        } catch (Exception ignored) {
        }
    }
}
