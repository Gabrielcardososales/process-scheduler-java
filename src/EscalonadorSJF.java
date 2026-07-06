
import java.util.ArrayList;
import java.util.Comparator;

public class EscalonadorSJF {

    public static void executar(ArrayList<Processo> processos) {
        if (processos.isEmpty()) return;

        ArrayList<Processo> aExecutar = new ArrayList<>(processos);
        int tempoAtual = 0;

        while (!aExecutar.isEmpty()) {
            // Filtra os processos que já chegaram no tempo atual
            ArrayList<Processo> prontos = new ArrayList<>();
            for (Processo p : aExecutar) {
                if (p.getTempoChegada() <= tempoAtual) {
                    prontos.add(p);
                }
            }

            // Se nenhum processo chegou ainda, avança o tempo para o momento de chegada do próximo
            if (prontos.isEmpty()) {
                Processo proximoAJuntar = aExecutar.stream()
                        .min(Comparator.comparingInt(Processo::getTempoChegada))
                        .orElseThrow();
                tempoAtual = proximoAJuntar.getTempoChegada();
                continue;
            }

            // Escolhe o processo com MENOR tempo de execução (Burst Time)
            // Empate? O Comparator resolve pegando o que chegou primeiro (pelo id ou tempoChegada)
            Processo escolhido = prontos.stream()
                    .min(Comparator.comparingInt(Processo::getTempoExecucao)
                            .thenComparingInt(Processo::getTempoChegada))
                    .orElseThrow();

            // Executa o processo escolhido até o fim (Não-preemptivo)
            escolhido.setTempoPrimeiraExecucao(tempoAtual);
            escolhido.setTempoInicio(tempoAtual);
            
            tempoAtual += escolhido.getTempoExecucao();
            
            escolhido.setTempoFim(tempoAtual);
            escolhido.setTempoRestante(0);
            escolhido.calcularResultados();

            // Remove o processo concluído da lista de pendentes
            aExecutar.remove(escolhido);
        }
    }
}