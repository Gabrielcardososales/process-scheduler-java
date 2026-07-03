import java.util.ArrayList;

public class EscalonadorFCFS {
    public static void executar(ArrayList<Processo> processos) {
        int tempAtual = 0;
        for(Processo processo : processos) {
            //Evita o tempo de ociosidade
            if (tempAtual < processo.getTempoChegada()) {
                tempAtual = processo.getTempoChegada();
            }
            processo.setTempoInicio(tempAtual);
            processo.setTempoPrimeiraExecucao(tempAtual);
            tempAtual += processo.getTempoExecucao();
            processo.setTempoFim(tempAtual);
            processo.calcularResultados();
        }
    }
}
