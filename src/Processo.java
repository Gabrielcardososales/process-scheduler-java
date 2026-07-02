public class Processo implements Cloneable {

    private final String id;
    private final int tempoChegada;
    private final int tempoExecucao;

    private int tempoRestante;
    private int tempoPrimeiraExecucao; // -1 se ainda não começou, serve para o Tempo de Resposta

    // ---- Dados de Saída (Calculados no fim de cada algoritmo) ----
    private int tempoInicio;
    private int tempoFim;
    private int tempoRetorno;
    private int tempoEspera;
    private int tempoResposta;

    // Construtor padrão usado pelo Parser
    public Processo(String id, int tempoChegada, int tempoExecucao) {
        this.id = id;
        this.tempoChegada = tempoChegada;
        this.tempoExecucao = tempoExecucao;

        this.tempoRestante = tempoExecucao;
        this.tempoPrimeiraExecucao = -1;
    }

    // ---- Getters e Setters ----
    public String getId() { return id; }
    public int getTempoChegada() { return tempoChegada; }
    public int getTempoExecucao() { return tempoExecucao; }

    public int getTempoRestante() { return tempoRestante; }
    public void setTempoRestante(int tempoRestante) { this.tempoRestante = tempoRestante; }

    public int getTempoPrimeiraExecucao() { return tempoPrimeiraExecucao; }
    public void setTempoPrimeiraExecucao(int tempoPrimeiraExecucao) { this.tempoPrimeiraExecucao = tempoPrimeiraExecucao; }

    public int getTempoInicio() { return tempoInicio; }
    public void setTempoInicio(int tempoInicio) { this.tempoInicio = tempoInicio; }

    public int getTempoFim() { return tempoFim; }
    public void setTempoFim(int tempoFim) { this.tempoFim = tempoFim; }

    public void calcularResultados() {
        this.tempoRetorno = this.tempoFim - this.tempoChegada;
        this.tempoEspera = this.tempoRetorno - this.tempoExecucao;
        this.tempoResposta = this.tempoPrimeiraExecucao - this.tempoChegada;
    }

    public int getTempoRetorno() { return tempoRetorno; }
    public int getTempoEspera() { return tempoEspera; }
    public int getTempoResposta() { return tempoResposta; }

    // ---- MÉTODO CRUCIAL PARA CÓPIA PROFUNDA ----
    @Override
    public Processo clone() {
        try {
            Processo clonado = (Processo) super.clone();
            // Garante que o clone resete as variáveis de controle/saída
            clonado.tempoRestante = this.tempoExecucao;
            clonado.tempoPrimeiraExecucao = -1;
            clonado.tempoInicio = 0;
            clonado.tempoFim = 0;
            clonado.tempoRetorno = 0;
            clonado.tempoEspera = 0;
            clonado.tempoResposta = 0;
            return clonado;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}