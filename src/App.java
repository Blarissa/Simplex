import io.github.mrrefactoring.Fraction;

public class App {

    private static final double EPSILON = 1.0e-8;

    private static String[] printHeader( double[][] tableau) {
        int l = tableau[0].length;
        int c = tableau.length;
        
        String linha[] = new String[l];
        String[] coluna = new String[c];
        
        linha[0] = "Z";
        linha[l - 1] = "b";
        coluna[0] = "Z";
        
        for (int i = 1; i < l - 1; i++) 
            linha[i] = "x" + (i + 1);        
        
        for (int i = 1, j = c; i < c; i++) 
            coluna[i] = "x" + (i + j);

        String cabecalho = String.format("%-10s|", "");

        for (String item : linha) 
            cabecalho += String.format("%-10s|", item);
             
        cabecalho += "\n" + "-".repeat(c * 25) + "\n"; 
        System.out.print(cabecalho);

        return coluna;
    }

    private static void printTableau(double[][] tableau) {
        String[] coluna = printHeader(tableau);              

        int i = 0;
        for (double[] row : tableau) {            
            System.out.printf("%-10s|", coluna[i]);
            i++;
            
            for (double value : row) {
                Fraction f = new Fraction(value);       
                System.out.printf("%-10s|", f.toFraction());
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

        // Divide o pivô pela entrada para torná-lo 1 
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
                System.out.println("A solução é ilimitada.");
                return;
            }

            performPivot(tableau, pivotRow, pivotColumn);

            System.out.println("Tableau depois de pivoteamento:");
            printTableau(tableau);
        }

        System.out.println("Solução ótima encontrada:");
        printTableau(tableau);
    }

    public static void main(String[] args) {

        double[][] tableau = {
            {  1,  1,  1,   1, 1, 0, 0, 15 },
            {  7,  5,  3,   2, 0, 1, 0, 120 },
            {  3,  5, 10,  15, 0, 0, 1, 100 },
            { -4, -5, -9, -11, 0, 0, 0, 0 }
        };

        System.out.println("Tableau inicial:");
        printTableau(tableau);
        simplex(tableau);
    }
}
