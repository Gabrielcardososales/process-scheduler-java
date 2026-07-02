import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String arquivoTeste = "entrada.txt";

        System.out.println("--- Carregando processos do arquivo ---");
        ArrayList<Processo> listaOriginal = LeitorArquivo.lerArquivo(arquivoTeste);

        if(listaOriginal.isEmpty()) {
            System.out.println("Nenhum processo carregado. Verifique o arquivo.");
            return;
        }

        // Exemplo futuro de como podemos chamar o algoritmo do escalonador
        // ArrayList<Processo> listaSJF = cloneLista(listaOriginal);
        // EscalonadorSJF.executar(listaSJF);
        // System.out.println("\n=== RESULTADO SJF ===");
        // printResultados(listaSJF);
    }

    // Método auxiliar para não misturar os dados entre execuções
    private static ArrayList<Processo> cloneLista(ArrayList<Processo> original) {
        ArrayList<Processo> clone = new ArrayList<>();
        for (Processo p : original) {
            clone.add(p.clone());
        }
        return clone;
    }

    // Print simples temporário (na função B voce melhora, mas ate então é isso)
    private static void printResultados(ArrayList<Processo> processos) {
        System.out.println("ID | Chegada | Burst | Início | Fim | Retorno | Espera");
        for (Processo p : processos) {
            System.out.printf("%s  | %7d | %5d | %6d | %3d | %7d | %6d\n",
                    p.getId(), p.getTempoChegada(), p.getTempoExecucao(),
                    p.getTempoInicio(), p.getTempoFim(), p.getTempoRetorno(), p.getTempoEspera());
        }
    }
}