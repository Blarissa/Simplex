import io.github.mrrefactoring.Fraction;

public class App {

    private static final double EPSILON = 1.0e-8;

    // Imprime o tableau atual
    private static void printTableau(double[][] tableau) {
        for (double[] row : tableau) {           
            for (double value : row) {
                Fraction f = new Fraction(value);       
                System.out.printf("%-10s|", f.toFraction());
            }
            System.out.println();
        }
        System.out.println();
    }

    // Encontra a coluna de pivô usando a regra do menor coeficiente
    private static int findPivotColumn(double[][] tableau) {
        int pivotColumn = -1;
        double minValue = 0.0;
        int lastRow = tableau.length - 1;

        for (int j = 0; j < tableau[lastRow].length - 1; ++j)           
            if (tableau[lastRow][j] < minValue) {
                minValue = tableau[lastRow][j];
                pivotColumn = j;
            }
        
        return pivotColumn;
    }

    // Encontra a linha de pivô usando a regra da razão mínima
    private static int findPivotRow(double[][] tableau, int pivotColumn) {
        int pivotRow = -1;
        double minRatio = Double.MAX_VALUE;

        for (int i = 0; i < tableau.length - 1; ++i) 
            if (tableau[i][pivotColumn] > EPSILON) {
                double ratio = tableau[i][tableau[i].length - 1] / tableau[i][pivotColumn];
                if (ratio < minRatio) {
                    minRatio = ratio;
                    pivotRow = i;
                }
            }

        return pivotRow;
    }

    // Realiza o pivoteamento
    private static void performPivot(double[][] tableau, int pivotRow, int pivotColumn) {
        int m = tableau.length;
        int n = tableau[0].length;
        double pivotValue = tableau[pivotRow][pivotColumn];

        // Divide o pivô pela entrada para torná-lo 1 
        for (int j = 0; j < n; ++j) 
            tableau[pivotRow][j] /= pivotValue;
        
        // Executa a eliminação de Gauss para tornar todas as outras entradas na coluna do pivô zero
        for (int i = 0; i < m; ++i) 
            if (i != pivotRow) {
                double factor = tableau[i][pivotColumn];                
                for (int j = 0; j < n; ++j) {
                    tableau[i][j] -= factor * tableau[pivotRow][j];
                }
            }
    }

    // Verifica se a solução é ótima
    private static boolean isOptimal(double[][] tableau) {
        int lastRow = tableau.length - 1;

        for (int j = 0; j < tableau[lastRow].length - 1; ++j) 
            if (tableau[lastRow][j] < -EPSILON) 
                return false;
            
        return true;
    }

    private static void simplex(double[][] tableau) {                
        while (!isOptimal(tableau)) {            
            int pivotColumn = findPivotColumn(tableau);            
            int pivotRow = findPivotRow(tableau, pivotColumn);

            // Se não houver linha de pivô, a solução é ilimitada
            if (pivotRow == -1) {
                System.out.println("A solução é ilimitada.");
                return;
            }

            // Realiza o pivoteamento
            performPivot(tableau, pivotRow, pivotColumn);

            System.out.println("Tableau depois de pivoteamento:");
            printTableau(tableau);
        }

        System.out.println("Solução ótima encontrada:");
        printTableau(tableau);
    }

    public static void main(String[] args) {
        /*  
        Problema 1
            Max Z = 4x1 + 5x2 + 9x3 + 11x4 
            sujeito a:
             x1 +  x2 +   x3 + x4 <= 15
            7x1 + 5x2 +  3x3      <= 120
            3x1 + 5x2 + 10x3      <= 100
            x1, x2, x3, x4 >= 0
        */

        double[][] p1 = {
            {  1,  1,  1,   1, 1, 0, 0, 15 },
            {  7,  5,  3,   2, 0, 1, 0, 120 },
            {  3,  5, 10,  15, 0, 0, 1, 100 },
            { -4, -5, -9, -11, 0, 0, 0, 0 }
        };

        
            

        System.out.println("Tableau inicial:");
        printTableau(p1);
        simplex(p1);
    }
}
