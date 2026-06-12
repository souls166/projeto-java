import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private GestorFinancas gestor = new GestorFinancas();
    private TextArea areaTransacoes = new TextArea();
    private Label saldoLabel = new Label("Saldo atual: R$ 0.00");
    private PieChart grafico = new PieChart();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gestor de Finanças Pessoais");

        TextField descricaoField = new TextField();
        descricaoField.setPromptText("Descrição");

        TextField valorField = new TextField();
        valorField.setPromptText("Valor");

        ComboBox<String> tipoBox = new ComboBox<>();
        tipoBox.getItems().addAll("Receita", "Despesa");

        Button adicionarBtn = new Button("Adicionar");
        adicionarBtn.setOnAction(e -> {
            try {
                String descricao = descricaoField.getText();
                double valor = Double.parseDouble(valorField.getText());
                String tipo = tipoBox.getValue();

                if (tipo == null) {
                    throw new IllegalArgumentException("Selecione Receita ou Despesa!");
                }

                if (tipo.equals("Receita")) {
                    gestor.adicionarTransacao(new Receita(descricao, valor));
                } else {
                    gestor.adicionarTransacao(new Despesa(descricao, valor));
                }

                atualizarInterface();
                descricaoField.clear();
                valorField.clear();
                tipoBox.setValue(null);

            } catch (NumberFormatException ex) {
                mostrarErro("Valor inválido! Digite um número.");
            } catch (Exception ex) {
                mostrarErro(ex.getMessage());
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(descricaoField, valorField, tipoBox, adicionarBtn, saldoLabel, areaTransacoes, grafico);

        Scene scene = new Scene(layout, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void atualizarInterface() {
        areaTransacoes.clear();
        for (Transacao t : gestor.getTransacoes()) {
            areaTransacoes.appendText(t.toString() + "\n");
        }
        saldoLabel.setText("Saldo atual: R$ " + gestor.calcularSaldo());

        // Atualizar gráfico
        double totalReceitas = gestor.getTransacoes().stream()
                .filter(t -> t instanceof Receita)
                .mapToDouble(Transacao::getValor).sum();

        double totalDespesas = gestor.getTransacoes().stream()
                .filter(t -> t instanceof Despesa)
                .mapToDouble(Transacao::getValor).sum();

        grafico.getData().clear();
        grafico.getData().add(new PieChart.Data("Receitas", totalReceitas));
        grafico.getData().add(new PieChart.Data("Despesas", totalDespesas));
    }

    private void mostrarErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
