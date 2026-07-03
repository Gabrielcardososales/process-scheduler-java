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
        ArrayList<Processo> listaFCFS = cloneLista(listaOriginal);
        EscalonadorFCFS.executar(listaFCFS);
        printResultados("FCFS", listaFCFS);

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

    // Imprime o resultado dos escalonadores
    private static void printResultados(String sigla, ArrayList<Processo> processos) {
        double tempRetornoMedio = 0, tempRespostaMedio = 0, tempEsperaMedio = 0;
        int cont = processos.size();
        for(Processo processo : processos) {
            tempRetornoMedio += processo.getTempoRetorno();
            tempRespostaMedio += processo.getTempoResposta();
            tempEsperaMedio += processo.getTempoEspera();
        }

        double mediaRetorno = tempRetornoMedio / cont;
        double mediaResposta = tempRespostaMedio / cont;
        double mediaEspera = tempEsperaMedio / cont;

        System.out.printf("%s %.1f %.1f %.1f\n", sigla, mediaRetorno, mediaResposta, mediaEspera);
    }
}