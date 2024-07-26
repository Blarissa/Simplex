public class App {

    private static final double EPSILON = 1.0e-8;

    private static void printTableau(double[][] tableau) {
        for (double[] row : tableau) {
            for (double value : row) {
                System.out.printf("%10.4f ", value);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static int findPivotColumn(double[][] tableau) {
        int pivotColumn = -1;
        double minValue = 0.0;
        int lastRow = tableau.length - 1;

        for (int j = 0; j < tableau[lastRow].length - 1; ++j) {
            if (tableau[lastRow][j] < minValue) {
                minValue = tableau[lastRow][j];
                pivotColumn = j;
            }
        }

        return pivotColumn;
    }

    private static int findPivotRow(double[][] tableau, int pivotColumn) {
        int pivotRow = -1;
        double minRatio = Double.MAX_VALUE;

        for (int i = 0; i < tableau.length - 1; ++i) {
            if (tableau[i][pivotColumn] > EPSILON) {
                double ratio = tableau[i][tableau[i].length - 1] / tableau[i][pivotColumn];
                if (ratio < minRatio) {
                    minRatio = ratio;
                    pivotRow = i;
                }
            }
        }

        return pivotRow;
    }

    private static void performPivot(double[][] tableau, int pivotRow, int pivotColumn) {
        int m = tableau.length;
        int n = tableau[0].length;
        double pivotValue = tableau[pivotRow][pivotColumn];

        // Divide the pivot row by the pivot value
        for (int j = 0; j < n; ++j) {
            tableau[pivotRow][j] /= pivotValue;
        }

        // Perform row operations to make other entries in the pivot column zero
        for (int i = 0; i < m; ++i) {
            if (i != pivotRow) {
                double factor = tableau[i][pivotColumn];
                for (int j = 0; j < n; ++j) {
                    tableau[i][j] -= factor * tableau[pivotRow][j];
                }
            }
        }
    }

    private static boolean isOptimal(double[][] tableau) {
        int lastRow = tableau.length - 1;

        for (int j = 0; j < tableau[lastRow].length - 1; ++j) {
            if (tableau[lastRow][j] < -EPSILON) {
                return false;
            }
        }

        return true;
    }

    private static void simplex(double[][] tableau) {
        while (!isOptimal(tableau)) {
            int pivotColumn = findPivotColumn(tableau);
            int pivotRow = findPivotRow(tableau, pivotColumn);

            if (pivotRow == -1) {
                System.out.println("Unbounded solution");
                return;
            }

            performPivot(tableau, pivotRow, pivotColumn);

            System.out.println("Tableau after pivoting:");
            printTableau(tableau);
        }

        System.out.println("Optimal solution found:");
        printTableau(tableau);
    }

    public static void main(String[] args) {
        // Example problem
        // Maximize Z = 3x1 + 2x2
        // Subject to:
        // x1 + x2 <= 4
        // x1 <= 2
        // x2 <= 3
        // x1, x2 >= 0

        double[][] tableau = {
            { 1, 1, 1, 0, 0, 4 },
            { 1, 0, 0, 1, 0, 2 },
            { 0, 1, 0, 0, 1, 3 },
            { -3, -2, 0, 0, 0, 0 }
        };

        System.out.println("Initial tableau:");
        printTableau(tableau);

        simplex(tableau);
    }
}
