```java
import java.util.Scanner;

class Student {
    int age = 20;

    void display() {
        int marks = 85;
        System.out.println("Instance Variable (age): " + age);
        System.out.println("Local Variable (marks): " + marks);
    }
}

class Employee {
    int salary;
    int bonus;

    Employee(int salary, int bonus) {
        this.salary = salary;
        this.bonus = bonus;
    }

    void display() {
        System.out.println("\nInside display() Method:");
        System.out.println("Salary (Instance Variable): " + salary);
        System.out.println("Bonus (Local Variable): " + bonus);

        class Report {
            void show() {
                System.out.println("Report: Employee salary is " + salary);
            }
        }

        Report r = new Report();

        System.out.println("\nReport Object Created");
        System.out.println("Report Identity Hash Code: "
                + System.identityHashCode(r));
        r.show();
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Student s = new Student();

        System.out.println("Student Object Created");
        System.out.println("Object Identity Hash Code: "
                + System.identityHashCode(s));
        s.display();

        System.out.print("\nEnter salary: ");
        int salary = sc.nextInt();

        System.out.print("Enter bonus: ");
        int bonus = sc.nextInt();

        Employee e = new Employee(salary, bonus);

        System.out.println("\nEmployee Object Created");
        System.out.println("Employee Identity Hash Code: "
                + System.identityHashCode(e));

        e.display();

        System.out.println("\nMemory Concept:");
        System.out.println("Student object -> Heap");
        System.out.println("Employee object -> Heap");
        System.out.println("Report object -> Heap");
        System.out.println("age and salary -> Instance fields");
        System.out.println("marks and bonus -> Local variables");
        System.out.println("Methods -> Execute using stack frames");

        sc.close();
    }
}
```
