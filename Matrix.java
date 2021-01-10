package processor;

public class Matrix {
    private final double[][] internalRep;
    private double[][] matrix1;

    private double determinant = Double.POSITIVE_INFINITY;

    public Matrix(double[][] rep) {
        internalRep = rep;
        matrix1 = new double[internalRep.length][internalRep[0].length];
        copy(matrix1);
    }

    public Matrix add(Matrix other) {
        double[][] matrix2 = other.get();

        if (matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length) {
            System.out.println("ERROR");
            return null;
        }

        double[][] matrix3 = new double[matrix1.length][matrix1[0].length];
        for (int i = 0; i < matrix3.length; i++) {
            for (int j = 0; j < matrix3[0].length; j++) {
                matrix3[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        return new Matrix(matrix3);
    }

    public Matrix multiply(Matrix other) {
        double[][] matrix2 = other.get();

        if (matrix1[0].length != matrix2.length) {
            System.out.println("ERROR");
            return null;
        }

        double[][] matrix3 = new double[matrix1.length][matrix2[0].length];

        for (int i = 0; i < matrix3.length; i++) {
            for (int j = 0; j < matrix3[0].length; j++) {
                double dotProdut = 0;
                for (int k = 0; k < matrix2.length; k++) {
                    dotProdut += matrix1[i][k] * matrix2[k][j];
                }
                matrix3[i][j] = dotProdut;
            }
        }

        return new Matrix(matrix3);
    }

    public Matrix scale(double scalar) {
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[0].length; j++) {
                matrix1[i][j] *= scalar;
            }
        }
        return new Matrix(matrix1);
    }

    public Matrix transpose(int way) {
        switch (way) {
            case 1:
                double[][] matrix2 = new double[matrix1[0].length][matrix1.length];
                for (int i = 0; i < matrix1.length; i++) {
                    for (int j = 0; j < matrix1[0].length; j++) {
                        matrix2[i][j] = matrix1[j][i];
                    }
                }
                return new Matrix(matrix2);
            case 2:
                double[][] matrix3 = new double[matrix1[0].length][matrix1.length];
                for (int i = 1; i <= matrix1.length; i++) {
                    for (int j = 1; j <= matrix1[0].length; j++) {
                        matrix3[matrix1.length-i][matrix1[0].length-j] = matrix1[j-1][i-1];
                    }
                }
                return new Matrix(matrix3);
            case 3:
                for (int i = 0; i < matrix1.length; i++) {
                    for (int j = 0; j < matrix1[0].length/2.0; j++) {
                        swap(i, j, i, matrix1[0].length - 1 - j);
                    }
                }
                return new Matrix(matrix1);
            case 4:
                for (int i = 0; i < matrix1.length/2.0; i++) {
                    for (int j = 0; j < matrix1[0].length; j++) {
                        swap(i, j, matrix1.length - 1 -i, j);
                    }
                }
                return new Matrix(matrix1);
            default:
                return null;
        }
    }

    public double determinant() {
        if (determinant != Double.POSITIVE_INFINITY) return determinant;
        if (matrix1.length != matrix1[0].length) return Double.NaN;
        this.determinant = determinant(matrix1);
        return this.determinant;
    }

    private double determinant(double[][] matrix) {
        if (matrix.length == 2) return matrix[0][0] * matrix[1][1] - matrix[0][1]*matrix[1][0];
        double determinant = 0;
        for (int j = 0; j < matrix.length; j++) {
            determinant += cofactor(matrix, 0, j) * matrix[0][j];
        }
        return determinant;
    }

    private double cofactor(double[][] matrix, int i, int j) {
        return Math.pow(-1, i+j) * determinant(minorMatrix(matrix, i, j));
    }

    private double[][] minorMatrix(double[][] matrix, int i, int j) {
        double[][] minor = new double[matrix.length-1][matrix[0].length-1];

        int kOffset;
        int lOffset;
        for (int k = 0; k < minor.length; k++) {
            if (k >= i) kOffset = 1;
            else kOffset = 0;
            for (int l = 0; l < minor[0].length; l++) {
                if (l >= j) lOffset = 1;
                else lOffset = 0;
                minor[k][l] = matrix[k+kOffset][l+lOffset];
            }
        }

        return minor;
    }

    public Matrix inverse() {
        double scalar = 1/determinant(matrix1);

        double[][] inverse = new double[matrix1.length][matrix1[0].length];

        for (int i = 0; i < inverse.length; i++) {
            for (int j = 0; j < inverse[0].length; j++) {
                inverse[i][j] = cofactor(matrix1, i, j);
            }
        }

       return new Matrix(inverse).transpose(1).scale(scalar);
    }


    private void swap(int i1, int j1, int i2, int j2) {
        double temp = matrix1[i1][j1];
        matrix1[i1][j1] = matrix1[i2][j2];
        matrix1[i2][j2] = temp;
    }

    private void correct() {
        copy(matrix1);
    }

    private void copy(double[][] dest) {
        assert(internalRep.length * internalRep[0].length == dest.length * dest[0].length);
        for(int i = 0; i < internalRep.length; i++) {
            System.arraycopy(internalRep[i], 0, dest[i], 0, internalRep[i].length);
        }
    }

    @Override
    public String toString() {
        var stringified = new StringBuilder();
        for (int i = 0; i < internalRep.length; i++) {
            for (int j = 0; j < internalRep[0].length; j++) {
                stringified.append(internalRep[i][j] + " ");
            }
            if (i + 1 < internalRep.length) stringified.append("\n");
        }
        return stringified.toString();
    }

    public double[][] get() {
        correct();
        return matrix1;
    }
}