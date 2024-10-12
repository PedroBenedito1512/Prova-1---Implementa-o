package puzle2;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class EstadoPuzzle {
    private int[][] tabuleiro;
    private int linhaVazia, colunaVazia;
    private int g; // Custo acumulado
    private int f; // f(x) = g(x) + h(x)

    public EstadoPuzzle(int[][] tabuleiro, int g, int h) {
        this.tabuleiro = tabuleiro;
        this.g = g;
        this.f = g + h; // Atualiza o valor de f
        encontrarVazia();
    }

    private void encontrarVazia() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[i][j] == 0) {
                    linhaVazia = i;
                    colunaVazia = j;
                    return;
                }
            }
        }
    }

    public Set<EstadoPuzzle> obterVizinhosDiretamente() {
        Set<EstadoPuzzle> vizinhos = new HashSet<>();
        int[][] movimentos = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // baixo, cima, direita, esquerda

        for (int[] movimento : movimentos) {
            int novaLinha = linhaVazia + movimento[0];
            int novaColuna = colunaVazia + movimento[1];

            if (novaLinha >= 0 && novaLinha < 3 && novaColuna >= 0 && novaColuna < 3) {
                // Modificar diretamente
                int temp = tabuleiro[novaLinha][novaColuna];
                tabuleiro[novaLinha][novaColuna] = 0; // Mover a peça para a posição vazia
                tabuleiro[linhaVazia][colunaVazia] = temp; // Atualizar a posição da peça que estava vazia

                // Adicionar o estado atual ao conjunto de vizinhos
                vizinhos.add(new EstadoPuzzle(tabuleiro, g + 1, calcularHeuristica(tabuleiro)));

                // Desfazer a modificação
                tabuleiro[linhaVazia][colunaVazia] = temp; // Reverter para o estado original
                tabuleiro[novaLinha][novaColuna] = 0; // Colocar a peça de volta na posição vazia
            }
        }
        return vizinhos;
    }

    private int calcularHeuristica(int[][] tabuleiro) {
        int foraDoLugar = 0;
        int[][] objetivo = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[i][j] != objetivo[i][j] && tabuleiro[i][j] != 0) {
                    foraDoLugar++;
                }
            }
        }
        return foraDoLugar;
    }

    public int getF() {
        return f; // Retorna o valor de f
    }

    public boolean isObjetivo() {
        int[][] objetivo = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        return Arrays.deepEquals(tabuleiro, objetivo);
    }

    // Método para imprimir o tabuleiro
    public void imprimirTabuleiro() {
        for (int[] linha : tabuleiro) {
            for (int val : linha) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof EstadoPuzzle)) return false;
        EstadoPuzzle outro = (EstadoPuzzle) obj;
        return Arrays.deepEquals(this.tabuleiro, outro.tabuleiro);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(tabuleiro);
    }
}
