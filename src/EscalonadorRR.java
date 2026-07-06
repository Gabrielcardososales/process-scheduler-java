import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class EscalonadorRR {

    public static void executar(ArrayList<Processo> processos) {
        if (processos.isEmpty()) return;

        //Organiza os processos por ordem de chegada para saber quem entra primeiro
        ArrayList<Processo> clonados = new ArrayList<>(processos);
        clonados.sort(Comparator.comparingInt(Processo::getTempoChegada));

        Queue<Processo> filaProntos = new LinkedList<>();
        int tempoAtual = 0;
        int indexProximoProcesso = 0;
        int n = clonados.size();
        int processosConcluidos = 0;
        
        //Quantum como 2
        final int QUANTUM = 2; 

        // loop roda enquanto houver processos para chegar ou na fila de prontos
        while (processosConcluidos < n) {

            // A: Coloca na fila todos os processos que CHEGARAM até o tempo atual
            while (indexProximoProcesso < n && clonados.get(indexProximoProcesso).getTempoChegada() <= tempoAtual) {
                filaProntos.add(clonados.get(indexProximoProcesso));
                indexProximoProcesso++;
            }

            // B: Se a fila estiver vazia, significa que a CPU ficou ociosa esperando alguém chegar
            if (filaProntos.isEmpty()) {
                tempoAtual = clonados.get(indexProximoProcesso).getTempoChegada();
                continue;
            }

            // C: Pega o próximo da fila para rodar
            Processo atual = filaProntos.poll();

            // D: Se for a primeira vez que ele encosta na CPU, grava o tempo de resposta
            if (atual.getTempoPrimeiraExecucao() == -1) {
                atual.setTempoPrimeiraExecucao(tempoAtual);
                atual.setTempoInicio(tempoAtual); // Apenas para fins de histórico local
            }

            // E: Calcula quanto tempo ele vai rodar (o menor entre o quantum e o que falta para ele)
            int tempoExecutadoNesteTurno = Math.min(QUANTUM, atual.getTempoRestante());
            
            // Avança o relógio do sistema
            tempoAtual += tempoExecutadoNesteTurno;
            atual.setTempoRestante(atual.getTempoRestante() - tempoExecutadoNesteTurno);

            // F: Checa se CHEGARAM NOVOS PROCESSOS enquanto este estava rodando
            // (Regra de ouro do RR: novos processos entram na fila ANTES do que acabou de rodar)
            while (indexProximoProcesso < n && clonados.get(indexProximoProcesso).getTempoChegada() <= tempoAtual) {
                filaProntos.add(clonados.get(indexProximoProcesso));
                indexProximoProcesso++;
            }

            // G: Verifica se o processo atual terminou ou precisa voltar para o fim da fila
            if (atual.getTempoRestante() == 0) {
                atual.setTempoFim(tempoAtual);
                atual.calcularResultados();
                processosConcluidos++;
            } else {
                // Não terminou? Volta para o final da fila de prontos
                filaProntos.add(atual);
            }
        }
    }
}