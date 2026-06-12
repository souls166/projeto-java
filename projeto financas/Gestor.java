import java.util.ArrayList;

public class Gestor {
    private ArrayList<Transacao> transacoes = new ArrayList<>();

    public void adicionarTransacao(Transacao t) {
        if (t == null) {
            throw new IllegalArgumentException("Transação inválida!");
        }
        transacoes.add(t);
    }

    public double calcularSaldo() {
        double saldo = 0;
        for (Transacao t : transacoes) {
            if (t instanceof Receita) {
                saldo += t.getValor();
            } else if (t instanceof Despesa) {
                saldo -= t.getValor();
            }
        }
        return saldo;
    }

    public ArrayList<Transacao> getTransacoes() {
        return transacoes;
    }
}