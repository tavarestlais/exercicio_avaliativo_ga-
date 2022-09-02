public class App {
    //funciona para qualquer valor em que o tamanho final é maior do que 10
    static final int LINHA = 100;
    static final int COLUNA = 100;
    static final int TAMANHO_FINAL = (int) (LINHA * COLUNA * 0.01);

    public static void main(String[] args) {
        MatrizEsparsa me = new MatrizEsparsa(LINHA, COLUNA);
        int sLin, sCol;
        double sVal;

        for (int x = 0; x < TAMANHO_FINAL; x++) {
            sLin = RNG.randomNumberGenerator(0, LINHA);
            sCol = RNG.randomNumberGenerator(0, COLUNA);
            sVal = RNG.randomNumberGenerator(-50, 50);
            me.set(sVal, sLin, sCol);
        }

        // TEST GET
        for (int i = 0; i < 5; i++) {
            int lin = RNG.randomNumberGenerator(0, LINHA);
            int col = RNG.randomNumberGenerator(0, COLUNA);
            System.out.println("\n get linha: " + lin + " coluna " + col + " " + me.get(lin, col));
        }

        // TEST SOMA
        System.out.println("Soma: " + me.soma());

        // TEST MIN
        System.out.println("Minimo: " + me.min());

        // TEST MAX
        System.out.println("Maximo: " + me.max());

        // TEST EXIST
        int x = RNG.randomNumberGenerator(-50, 50);
        String auxText = me.exist(x) ? "existe" : "nao existe";
        System.out.println("Exist: " + x + " " + auxText + "?");

        // TEST COLUNA
        System.out.println("Media Coluna 2: " + me.coluna(2));

        // TEST LINHA
        System.out.println("Media Linha 4: " + me.linha(4));

        // TEST COPIA
        double[][] copia = me.copia(3, 3, 2, 2);
        System.out.println("Copia:");
        me.printaMatriz(copia);

        // TEST TRANSPOSTA
        MatrizEsparsa mt = MatrizEsparsa.transposta(me);
        System.out.println("Transposta: ");
        mt.printaMatriz();
    }

}
