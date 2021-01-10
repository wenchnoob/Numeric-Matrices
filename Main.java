package processor;

import static processor.Utilities.*;

public class Main {
    public static void main(String[] args) {
        System.out.println(
                "1. Add matrices\n" +
                        "2. Multiply matrix by a constant\n" +
                        "3. Multiply matrices\n" +
                        "4. Transpose\n" +
                        "5. Determinant\n"+
                        "0. Exit\n");

        int choice;
        do {
            switch (choice = getChoice()) {
                case 1:
                    add();
                    break;
                case 2:
                    scale();
                    break;
                case 3:
                    multiply();
                    break;
                case 4:
                    transpose();
                    break;
                case 5:
                    determinant();
                    break;
                case 6:
                    inverse();
            }
        } while (choice != 0);
    }

    public static void add() {
        Matrix sum = readMatrix().add(readMatrix());
        System.out.print(sum == null? "" : sum + "\n");
    }

    public static void scale() {
        System.out.println(readMatrix().scale(scan.nextDouble()));
    }

    public static void multiply() {
        Matrix prod = readMatrix().multiply(readMatrix());
        System.out.print(prod == null? "" : prod + "\n");
    }

    public static void transpose() {
        System.out.println(
                "1. Main Diagonal\n" +
                        "2. Side Diagonal\n" +
                        "3. Vertical Line\n" +
                        "4. Horizontal Line\n");

        int choice = getChoice();
        Matrix matrix = readMatrix();
        System.out.println(matrix.transpose(choice));
    }

    public static void determinant() {
        double determinant = readMatrix().determinant();
        System.out.println(determinant == Double.NaN ? "Error" : determinant);
    }

    public static void inverse() {
        System.out.println(readMatrix().inverse());
    }
}