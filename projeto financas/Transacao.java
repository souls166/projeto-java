public abstract class Transacao {
    private String descricao;
    private double valor;

    public Transacao(String descricao, double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor deve ser positivo!");
        }
        this.descricao = descricao;
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    // Polimorfismo: cada subclasse define seu tipo
    public abstract String getTipo();

    @Override
    public String toString() {
        return getTipo() + " - " + descricao + ": R$ " + valor;
    }
}
