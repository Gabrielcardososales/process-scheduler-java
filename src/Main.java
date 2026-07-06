import java.util.ArrayList;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        // Certifique-se de que o caminho do arquivo está correto para o ambiente de teste
        String arquivoTeste = "src/data/entrada2.txt";

        ArrayList<Processo> listaOriginal = LeitorArquivo.lerArquivo(arquivoTeste);

        if(listaOriginal.isEmpty()) {
            // Só printa erro se o arquivo realmente estiver vazio ou não encontrado
            System.err.println("Nenhum processo carregado. Verifique o arquivo.");
            return;
        }

        // FCFS
        ArrayList<Processo> listaFCFS = cloneLista(listaOriginal);
        EscalonadorFCFS.executar(listaFCFS);
        printResultados("FCFS:", listaFCFS);

        // SJF
        ArrayList<Processo> listaSJF = cloneLista(listaOriginal);
        EscalonadorSJF.executar(listaSJF);
        printResultados("SJF:", listaSJF);

        // RR (Corrigido o nome da sigla aqui)
        ArrayList<Processo> listaRR = cloneLista(listaOriginal);
        EscalonadorRR.executar(listaRR);
        printResultados("RR:", listaRR);
    }

    // Método auxiliar para não misturar os dados entre execuções
    private static ArrayList<Processo> cloneLista(ArrayList<Processo> original) {
        ArrayList<Processo> clone = new ArrayList<>();
        for (Processo p : original) {
            clone.add(p.clone());
        }
        return clone;
    }

    // Imprime o resultado dos escalonadores EXATAMENTE como o corretor quer
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

        // Locale.of("pt", "BR") garante que os decimais saiam com VÍRGULA (ex: 10,0)
        // removendo possíveis quebras por configuração do sistema do professor.
        System.out.printf(Locale.of("pt", "BR"), "%s %.1f %.1f %.1f\n",
                sigla, mediaRetorno, mediaResposta, mediaEspera);
    }
}