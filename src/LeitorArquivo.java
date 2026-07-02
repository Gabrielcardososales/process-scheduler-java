import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LeitorArquivo {

    public static ArrayList<Processo> lerArquivo(String caminhoArquivo) {
        ArrayList<Processo> processos = new ArrayList<>();
        int contadorId = 1; // Para gerar P1, P2, P3...

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty()) continue;

                String[] dados = linha.split("\\s+");

                if (dados.length >= 2) {
                    int tempoChegada = Integer.parseInt(dados[0]);
                    int tempoExecucao = Integer.parseInt(dados[1]);

                    String id = "P" + contadorId;
                    processos.add(new Processo(id, tempoChegada, tempoExecucao));
                    contadorId++;
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Erro na leitura: " + e.getMessage());
        }

        return processos;
    }
}