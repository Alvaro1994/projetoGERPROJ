package telas;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dominio.Garcom;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.GarcomDAO;

public class CRUDgarcomControler implements Initializable {
	private GarcomDAO garcomDAO = new GarcomDAO();
	private Garcom garcomSelecionado = new Garcom();
	private ArrayList<Garcom> garcoms = new ArrayList<>();
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private SplitPane spGarcom;

	@FXML
	private AnchorPane anpTabGarcom;

	@FXML
	private TableView<Garcom> tvGarcom;

	@FXML
	private TableColumn<Garcom, String> tcNomeGarcom;

	@FXML
	private TableColumn<Garcom, Double> tcMediaGarcom;

	@FXML
	private Label lbGarcon;

	@FXML
	private AnchorPane anpCRUDgarcom;

	@FXML
	private Label lbCabecalho;

	@FXML
	private VBox vbNomeDispo;

	@FXML
	private Label lbNomeGarcon;

	@FXML
	private TextField tfNome;

	@FXML
	private TextField tfPesquisar;

	@FXML
	private HBox hbBotoesCRUDgarcom;

	@FXML
	private Button btnSalvar;

	@FXML
	private Button btnExcluir;

	@FXML
	private Button btnAlterar;

	public void clicouTabela() {
		System.out.println("clicou tabela");
	}

	public void carregarTabela() {
		tcNomeGarcom.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tcMediaGarcom.setCellValueFactory(new PropertyValueFactory<>("avaliacaoMedia"));
	}

	public void limparTabela() {
		garcoms.removeAll(garcoms);
		tvGarcom.setItems(FXCollections.observableArrayList(garcoms));
	}

	public void adicionarTabela() {
		garcoms.removeAll(garcoms);
		garcoms.addAll(garcomDAO.pesquisarGarcom(tfPesquisar.getText()));
		tvGarcom.setItems(FXCollections.observableArrayList(garcoms));
	}

	public void cancelar() {

	}

	public void desabilitarExcluirAlterar() {
		btnExcluir.setDisable(true);
		btnAlterar.setDisable(true);
	}

	public void habilitarExcluirAlterar() {
		btnExcluir.setDisable(false);
		btnAlterar.setDisable(false);
	}

	public void alterarGarcom() {
		tfNome.setPromptText("Informe o nome para pesquisar");
		lbNomeGarcon.setText("Pesquisar:");
		System.out.println(garcomSelecionado.getNome());
		Garcom novoGarcom = new Garcom();
		novoGarcom.setNome(tfNome.getText());
		garcomDAO.alterarGarcom(garcomSelecionado, novoGarcom);
		tfNome.clear();
		limparTabela();
		desabilitarExcluirAlterar();
	}

	public void salvarGarcom() {
		// Salva garçom
		if (!tfNome.getText().equals("")) {
			garcomDAO.inserirGarcom(tfNome.getText(), "Disponivél");
			tfNome.clear();
			// Habilita a operação salvar
		} else {
			Alert dialogoErro = new Alert(Alert.AlertType.ERROR);
			dialogoErro.setTitle("Erro!!!");
			dialogoErro.setHeaderText("Nome inválido");
			dialogoErro.setContentText("O campo está vazio!!!");
			dialogoErro.showAndWait();
		}
	}

	public void excluirGarcom() {
		garcomDAO.excluirGarcom(tfNome.getText());
		limparTabela();
		tvGarcom.getSelectionModel().clearSelection();
		// limpa campo
		tfNome.clear();
		desabilitarExcluirAlterar();
	}

	public void pesquisarGarcom() {
		adicionarTabela();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		desabilitarExcluirAlterar();
		carregarTabela();
		tvGarcom.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Garcom>() {

			@Override
			public void changed(ObservableValue<? extends Garcom> observable, Garcom oldValue, Garcom newValue) {
				if (newValue != null) {
					tfNome.setText(newValue.getNome());

					System.out.println("Evento na tabela " + newValue.toString());
				}

				garcomSelecionado = newValue;

				habilitarExcluirAlterar();
			}
		});

		btnAlterar.setOnMouseClicked(event -> alterarGarcom());
		btnSalvar.setOnMouseClicked(event -> salvarGarcom());
		btnExcluir.setOnMouseClicked(event -> excluirGarcom());
		tfPesquisar.setOnKeyPressed(event -> pesquisarGarcom());
	}

}
