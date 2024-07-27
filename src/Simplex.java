public class Simplex {

    public Simplex() {
    }

    private static void imprimirTableau(double[][] tableau) {
        for(int i = 0; i < tableau.length; ++i) {            
            for(int j = 0; j < tableau[i].length; ++j) {
                System.out.printf("|%-8.2f", tableau[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static int encontrarColunaPivote(double[][] tableau) {
        int colunaPivote = -1;
        double min = 0.0;

        for(int i = 0; i < tableau[tableau.length - 1].length - 1; ++i) {
            if (tableau[tableau.length - 1][i] < min) {
                min = tableau[tableau.length - 1][i];
                colunaPivote = i;
            }
        }

        return colunaPivote;
    }

    private static int encontrarLinhaPivote(double[][] tableau, int colunaPivote) {
        int linhaPivote = -1;
        double min = Double.MAX_VALUE;

        for(int i = 0; i < tableau.length - 1; ++i) {
            if (tableau[i][colunaPivote] > 0.0) {
                double razao = tableau[i][tableau[i].length - 1] / tableau[i][colunaPivote];
                if (razao < min) {
                    min = razao;
                    linhaPivote = i;
                }
            }
        }

        return linhaPivote;
    }

    private static void executarPivote(double[][] tableau, int linhaPivote, int colunaPivote) {
        double elementoPivote = tableau[linhaPivote][colunaPivote];

        for(int j = 0; j < tableau[linhaPivote].length; ++j) {
            tableau[linhaPivote][j] /= elementoPivote;
        }

        for(int i = 0; i < tableau.length; ++i) {
            if (i != linhaPivote) {
                double razao = tableau[i][colunaPivote];

                for(int j = 0; j < tableau[i].length; ++j) {
                    tableau[i][j] -= razao * tableau[linhaPivote][j];
                }
            }
        }
    }

    private static boolean ehOtimizado(double[][] tableau) {
        int ultimaLinha = tableau.length - 1;

        for(int j = 0; j < tableau[ultimaLinha].length - 1; ++j) {
            if (tableau[ultimaLinha][j] < -1.0E-8) {
                return false;
            }
        }

        return true;
    }

    private static void simplex(double[][] tableau) {
        while(!ehOtimizado(tableau)) {
            int colunaPivote = encontrarColunaPivote(tableau);
            int linhaPivote = encontrarLinhaPivote(tableau, colunaPivote);
            if (linhaPivote == -1) {
                System.out.println("A solução é ilimitada.");
                return;
            }

            executarPivote(tableau, linhaPivote, colunaPivote);
            System.out.println("Tableau depois de pivoteamento:");
            imprimirTableau(tableau);
        }

        System.out.println("Solução ótima encontrada:");
        imprimirTableau(tableau);
    }

    public static int menu(){
        int num;
        System.out.println(("-------------------------------- Menu Inicial --------------------------------"));
        System.out.println("Escolha uma opção:");
        System.out.println("1. Resolver o problema do exemplo");
        System.out.println("2. Resolver um problema personalizado");
        System.out.println("3. Sair");
        System.out.println("--------------------------------------------------------------------------------");
        num = Integer.parseInt(System.console().readLine());
        return num;
    }

    public static void main(String[] args) {
        boolean continuar = true;
        while(continuar){
            int num = menu();
            switch (num){
                case 1 -> {
                    double[][] exemplo = new double[][]{
                        {1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 15.0},
                        {7.0, 5.0, 3.0, 2.0, 0.0, 1.0, 0.0, 120.0},
                        {3.0, 5.0, 10.0, 15.0, 0.0, 0.0, 1.0, 100.0},
                        {-4.0, -5.0, -9.0, -11.0, 0.0, 0.0, 0.0, 0.0}
                    };
                    System.out.println("\nTableau inicial:");
                    imprimirTableau(exemplo);
                    simplex(exemplo);
                }
                case 2 -> {
                    System.out.println("Digite o número de linhas da matriz:");
                    int linhas = Integer.parseInt(System.console().readLine());
                    System.out.println("Digite o número de colunas da matriz:");
                    int colunas = Integer.parseInt(System.console().readLine());
                    double[][] personalizado = new double[linhas][colunas];
                    for (int i = 0; i < linhas; i++){
                        for (int j = 0; j < colunas; j++){
                            System.out.println("Digite o valor da posição [" + i + "][" + j + "]:");
                            personalizado[i][j] = Double.parseDouble(System.console().readLine());
                        }
                    }
                    System.out.println("Tableau inicial:");
                    imprimirTableau(personalizado);
                    simplex(personalizado);
                }
                case 3 -> continuar = false;
            }
        }
    }
}