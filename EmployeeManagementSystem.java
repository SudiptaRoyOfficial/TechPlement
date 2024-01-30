package techplement;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeManagementSystem {
    private List<Employee> employees;
    private static final String FILE_NAME = "E:\\Techplement\\Techplement\\src\\techplement\\employees.txt";

    public EmployeeManagementSystem() {
        employees = readEmployeesFromFile();
    }

    // Read employees from file
    private List<Employee> readEmployeesFromFile() {
        List<Employee> employees = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                int age = Integer.parseInt(parts[2]);
                String position = parts[3];
                Employee employee = new Employee(id, name, age, position);
                employees.add(employee);
            }
        } catch (IOException | NumberFormatException e) {
            // Handle file IO errors or data format errors
            e.printStackTrace();
        }
        return employees;
    }

    // Write employees to file
    private void writeEmployeesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Employee employee : employees) {
                writer.write(employee.getId() + "," + employee.getName() + "," + employee.getAge() + "," + employee.getPosition());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Add new employee
    public void addEmployee(Employee employee) {
        employees.add(employee);
        writeEmployeesToFile();
    }

    // View all employees
    public void viewEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            System.out.println("Employee List:");
            for (Employee employee : employees) {
                System.out.println(employee);
            }
        }
    }

    // Update employee details
    public void updateEmployee(int id, String name, int age, String position) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                employee.setName(name);
                employee.setAge(age);
                employee.setPosition(position);
                writeEmployeesToFile();
                return;
            }
        }
        System.out.println("Employee not found with ID: " + id);
    }

    // Search for an employee by ID
    public Employee searchEmployee(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        System.out.println("Employee not found with ID: " + id);
        return null;
    }

    // Employee class
    private static class Employee {
        private int id;
        private String name;
        private int age;
        private String position;

        public Employee(int id, String name, int age, String position) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.position = position;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        @Override
        public String toString() {
            return "Employee{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    ", position='" + position + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        EmployeeManagementSystem system = new EmployeeManagementSystem();
        Scanner scanner = new Scanner(System.in);

        // Main menu loop
        boolean exit = false;
        while (!exit) {
            System.out.println("\nEmployee Management System");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Search Employee");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter employee name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter employee age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character
                    System.out.print("Enter employee position: ");
                    String position = scanner.nextLine();
                    system.addEmployee(new Employee(system.employees.size() + 1, name, age, position));
                    System.out.println("Employee added successfully.");
                    break;
                case 2:
                    system.viewEmployees();
                    break;
                case 3:
                    System.out.print("Enter employee ID to update: ");
                    int idToUpdate = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character
                    Employee employeeToUpdate = system.searchEmployee(idToUpdate);
                    if (employeeToUpdate != null) {
                        System.out.print("Enter new name: ");
                        String newName = scanner.nextLine();
                        System.out.print("Enter new age: ");
                        int newAge = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                        System.out.print("Enter new position: ");
                        String newPosition = scanner.nextLine();
                        system.updateEmployee(idToUpdate, newName, newAge, newPosition);
                        System.out.println("Employee updated successfully.");
                    }
                    break;
                case 4:
                    System.out.print("Enter employee ID to search: ");
                    int idToSearch = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character
                    Employee foundEmployee = system.searchEmployee(idToSearch);
                    if (foundEmployee != null) {
                        System.out.println("Employee found: " + foundEmployee);
                    }
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }

        scanner.close();
    }
}
