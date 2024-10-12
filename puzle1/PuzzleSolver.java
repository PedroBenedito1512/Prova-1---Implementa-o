import java.util.*;

class ResolutorPuzzle {
    private static int totalPassos = 0; // Contador de passos
    private static int profundidadeMaxima = 0; // Profundidade máxima atingida

    public static void buscaAprofundamentoIterativo(EstadoPuzzle estadoInicial) {
        for (int profundidade = 0; profundidade < Integer.MAX_VALUE; profundidade++) {
            Set<EstadoPuzzle> visitados = new HashSet<>();
            System.out.println("Profundidade: " + profundidade);
            if (buscaLimitadaPorProfundidade(estadoInicial, profundidade, visitados)) {
                return;
            }
            profundidadeMaxima = profundidade; // Atualiza a profundidade máxima
        }
    }

    private static boolean buscaLimitadaPorProfundidade(EstadoPuzzle estado, int limite, Set<EstadoPuzzle> visitados) {
        totalPassos++; // Incrementa o contador de passos
        estado.imprimirTabuleiro(); // Imprime o estado atual do tabuleiro

        if (estado.isObjetivo()) {
            System.out.println("Objetivo alcançado em " + totalPassos + " passos na profundidade " + profundidadeMaxima);
            return true;
        }
        if (limite == 0) {
            return false;
        }

        visitados.add(estado);
        for (EstadoPuzzle vizinho : estado.obterVizinhos()) {
            if (!visitados.contains(vizinho)) {
                if (buscaLimitadaPorProfundidade(vizinho, limite - 1, visitados)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[][] estadoInicial = {{1, 2, 3}, {4, 0, 6}, {7, 5, 8}};
        EstadoPuzzle estadoInicialObj = new EstadoPuzzle(estadoInicial, 0, 0); // h inicial é 0

        long tempoInicial = System.currentTimeMillis(); // Captura o tempo inicial

        buscaAprofundamentoIterativo(estadoInicialObj);

        long tempoFinal = System.currentTimeMillis(); // Captura o tempo final
        long duracao = tempoFinal - tempoInicial; // Calcula a duração

        System.out.println("Tempo de execução: " + duracao + " milissegundos");
    }
}
