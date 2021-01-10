package processor;

import java.util.Scanner;

public class Utilities {

    public static Scanner scan = new Scanner(System.in);

    public static Matrix readMatrix() {
        int n = scan.nextInt();
        int m = scan.nextInt();
        double[][] matrix = new double[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = scan.nextDouble();
            }
        }

        return new Matrix(matrix);
    }

    public static int getChoice() {
        return scan.nextInt();
    }
}
