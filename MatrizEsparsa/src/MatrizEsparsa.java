import java.util.Arrays;

public class MatrizEsparsa {
  public ElementoMatriz[] mat;
  public int totalLinhas;
  public int totalColunas;
  public int naoZeros = 0;

  public MatrizEsparsa(int maxLin, int maxCol) {
    /*
     * recebe as dimensões da matriz (maxLin e maxCol); todos elementos da matriz
     * são
     * inicialmente considerados “zerados”; o dimensionamento (alocação) do array
     * unidimensional irá
     * corresponder a 1% do valor do produto de maxLin e maxCol; o tamanho mínimo do
     * array será de 10
     * posições
     */
    this.totalLinhas = maxLin;
    this.totalColunas = maxCol;
    double tamanho = maxLin * maxCol * 0.01;
    int tamanhoFinal = tamanho < 10 ? 10 : (int) tamanho;
    this.mat = new ElementoMatriz[tamanhoFinal];
  }

  public double get(int numLinha, int numColuna) {
    /* retorna o conteúdo desta célula da matriz, se nao existir retorna 0 */
    double valor = 0;

    for (int i = 0; i < this.naoZeros; i++) {
      ElementoMatriz elem = this.mat[i];
      if (elem.lin == numLinha && elem.col == numColuna) {
        valor = elem.valor;
      }
    }

    return valor;
  }

  public void set(double valor, int numLinha, int numColuna) {
    /*
     * seta o conteúdo de uma célula; se a
     * célula for zerada por esta operação, ela deve ser removida do array
     * unidimensional que representa
     * a matriz
     */
    int pos = getPosicao(numLinha, numColuna);

    if (pos != -1) { // linha x coluna ocupada
      if (valor != 0) { // valor nao zero
        this.mat[pos] = new ElementoMatriz(valor, numLinha, numColuna); // insere no ocupado
      } else { // valor zero
        this.mat[pos] = this.mat[this.naoZeros - 1]; // troca o ultimo elemento com o atual
        this.mat[naoZeros - 1] = null; // anula o ultimo elemento
        this.naoZeros -= 1; // reduz a qtde de elementos nao zero
      }
    } else { // linha x coluna vazia
      this.mat[this.naoZeros] = new ElementoMatriz(valor, numLinha, numColuna); // insere
      this.naoZeros += 1; // soma a qtde de nao zero
    }
  }

  public double soma() {
    /* retorna o somatório de todos elementos da matriz */
    double soma = 0;

    for (int i = 0; i < this.naoZeros; i++) {
      ElementoMatriz elem = this.mat[i];
      soma += elem.valor;
    }

    return soma;
  }

  public double min() {
    /* retorna o menor dos elementos da matriz */
    double menor = 0;

    for (int i = 0; i < this.naoZeros; i++) {
      ElementoMatriz elem = this.mat[i];
      if (elem.valor < menor) {
        menor = elem.valor;
      }
    }

    return menor;
  }

  public double max() {
    /* retorna o maior dos elementos da matriz */
    double maior = 0;

    for (int i = 0; i < this.naoZeros; i++) {
      ElementoMatriz elem = this.mat[i];
      if (elem.valor > maior) {
        maior = elem.valor;
      }
    }

    return maior;
  }

  public boolean exist(double valor) {
    /*  informa se valor está presente na matriz */
    boolean existe = false;

    for (int i = 0; i < this.naoZeros; i++) {
      ElementoMatriz elem = this.mat[i];
      if (elem.valor == valor) {
        existe = true;
      }
    }

    return existe;
  }

  public double coluna(int numCol) {
    /*
     * retorna o valor médio dos elementos não-zero da coluna; se não
     * houver elementos diferentes de zero nesta coluna, retorna zero;
     */
    double soma = 0;
    double media = 0;
    int quantidadeElementos = 0;

    for (int i = 0; i < this.naoZeros; i++) {
      ElementoMatriz elem = this.mat[i];
      if (elem.col == numCol) {
        soma += elem.valor;
        quantidadeElementos++;
      }
    }

    if (quantidadeElementos != 0) {
      media = soma / quantidadeElementos;
    }

    return media;
  }

  public double linha(int numLin) {
    /*
     * retorna o valor médio dos elementos não-zero da linha; se não
     * houver elementos diferentes de zero nesta linha, retorna zero;
     */
    double soma = 0;
    double media = 0;
    int quantidadeElementos = 0;

    for (int i = 0; i < this.naoZeros; i++) {
      ElementoMatriz elem = this.mat[i];
      if (elem.lin == numLin) {
        soma += elem.valor;
        quantidadeElementos++;
      }
    }

    if (quantidadeElementos != 0) {
      media = soma / quantidadeElementos;
    }

    return media;
  }

  public double[][] copia(int primLin, int primCol, int quantasLinhas, int quantasColunas) {
    /*
     *  retorna uma
     * matriz de doubles com os elementos da matriz esparsa que estão na região
     * indicada pelos primeiros
     * quatro parametros do método
     */
    double[][] matrizCopiada = new double[quantasLinhas][quantasColunas];

    for (int i = 0; i < this.naoZeros; i++) {
      ElementoMatriz elem = this.mat[i];
      if (elem.lin >= primLin && elem.lin < (primLin + quantasLinhas)
          &&
          elem.col >= primCol && elem.col < (primCol + quantasColunas)) {
        matrizCopiada[elem.lin - primLin][elem.col - primCol] = elem.valor;
      }
    }
    return matrizCopiada;
  }

  public static MatrizEsparsa transposta(MatrizEsparsa m) {
    /*
     *  retorna um objeto do tipo MatrizEsparsa que
     * corresponde a matriz transposta daquela passada pelo parametro
     */

    MatrizEsparsa t = new MatrizEsparsa(m.totalColunas, m.totalLinhas);

    for (int i = 0; i < m.naoZeros; i++) {
      ElementoMatriz elem = m.mat[i];
      t.set(elem.valor, elem.col, elem.lin);
    }

    return t;
  }

  public int getPosicao(int lin, int col) {
    int pos = -1;
    for (int i = 0; i < this.naoZeros; i++) {
      ElementoMatriz elem = this.mat[i];
      if (elem.lin == lin && elem.col == col) {
        pos = i;
      }
    }
    return pos;
  }

  public void printaMatriz() {
    double[][] matrizParaPrintar = new double[this.totalLinhas][this.totalColunas];

    matrizParaPrintar = copia(0, 0, this.totalLinhas, this.totalColunas);

    System.out.println(Arrays.deepToString(matrizParaPrintar));
  }

  public void printaMatriz(double[][] matriz) {
    System.out.println(Arrays.deepToString(matriz));

  }
}
