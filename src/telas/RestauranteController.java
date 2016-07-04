package telas;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dominio.Produto;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.SplitPane;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;

import javafx.scene.control.ScrollPane;

import javafx.scene.control.ComboBox;

import javafx.scene.control.TextArea;

import javafx.scene.layout.AnchorPane;
import model.ProdutoDAO;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class RestauranteController implements Initializable {
	ArrayList<Produto> produtos = new ArrayList<Produto>();
	ProdutoDAO produtoDAO = new ProdutoDAO();
	Produto produtoSelecionado = new Produto();
	@FXML
	private SplitPane spMenu;
	@FXML
	private AnchorPane apCadMenu;
	@FXML
	private Label lbTipo;
	@FXML
	private ComboBox<String> cbTipo;
	@FXML
	private Label lbNome;
	@FXML
	private TextField tfNome;
	@FXML
	private TextField tfPesquisar;
	@FXML
	private Label lbValor;
	@FXML
	private TextField tfValor;
	@FXML
	private Label lbDescricao;
	@FXML
	private TextArea tfDescricao;
	@FXML
	private Button btnSalvar;
	@FXML
	private Button btnCancelar;
	@FXML
	private Button btnExcluir;
	@FXML
	private Label lbTitulo;
	@FXML
	private AnchorPane apTabela;
	@FXML
	private ScrollPane spTabela;
	@FXML
	private TableView<Produto> tvMenu;
	@FXML
	private TableColumn<Produto, String> tcTipo;
	@FXML
	private TableColumn<Produto, String> tcNome;
	@FXML
	private TableColumn<Produto, Double> tcValor;

	public void carregarTabela() {
		tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tcTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
		tcValor.setCellValueFactory(new PropertyValueFactory<>("preco"));
	}

	public void limparTabela() {
		produtos.removeAll(produtos);
		tvMenu.setItems(FXCollections.observableArrayList(produtos));
	}

	public void adicionarTabela() {
		produtos.removeAll(produtos);
		produtos.addAll(produtoDAO.pesquisarProduto(tfPesquisar.getText().trim()));
		tvMenu.setItems(FXCollections.observableArrayList(produtos));
	}

	public void pesquisar() {
		adicionarTabela();
	}

	public void salvar() {
		if (btnSalvar.getText().equals("Salvar")) {
			try {
				produtoDAO.inserirProduto(tfNome.getText(), cbTipo.getSelectionModel().getSelectedItem(), tfDescricao.getText(),
						Double.parseDouble(tfValor.getText().replace(",",".")));
			} catch (NumberFormatException e) {
				Alert dialogoErro = new Alert(Alert.AlertType.ERROR);
				dialogoErro.setTitle("Erro!!!");
				dialogoErro.setHeaderText("Preço inválido");
				dialogoErro.setContentText("Preencha o campo com número positivos!!!");
				dialogoErro.showAndWait();
			}
			tfDescricao.clear();
			tfNome.clear();
			tfPesquisar.clear();
			tfValor.clear();
		}else {
			//Alterar o produto
			Produto p = new Produto();
			p.setNome(tfNome.getText());
			p.setDescricao(tfDescricao.getText());
			p.setPreco(Double.parseDouble(tfValor.getText().replace(",",".")));
			p.setTipo(cbTipo.getSelectionModel().getSelectedItem());
			produtoDAO.alterarProduto(produtoSelecionado, p);
			cancelar();
			
		}
	}
	public void excluir() {

		produtoDAO.excluirProduto(tfNome.getText().trim());
		tfDescricao.clear();
		tfNome.clear();
		tfPesquisar.clear();
		cancelar();
		limparTabela();
	}
	public void cancelar() {
		tfDescricao.clear();
		tfNome.clear();
		tfPesquisar.clear();
		tfValor.clear();
		btnSalvar.setText("Salvar");
		btnExcluir.setDisable(true);
		
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		carregarTabela();
		cbTipo.setItems(FXCollections.observableArrayList("Bebida", "Pratos", "Tira Gosto"));
		cbTipo.getSelectionModel().selectFirst();
		tvMenu.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Produto>() {

			@Override
			public void changed(ObservableValue<? extends Produto> observable, Produto oldValue, Produto newValue) {
				produtoSelecionado = newValue;
				if (produtoSelecionado != null) {
					cbTipo.getSelectionModel().select(produtoSelecionado.getTipo());
					tfNome.setText(produtoSelecionado.getNome());
					tfDescricao.setText(produtoSelecionado.getDescricao());
					tfValor.setText(String.valueOf(produtoSelecionado.getPreco()).replace(".", ","));
					btnSalvar.setText("Alterar");
					btnExcluir.setDisable(false);
				}
				
				
			}
		});
		/**
		 * 
		 */
		tfPesquisar.setOnKeyPressed(event -> pesquisar());
		btnSalvar.setOnMouseClicked(event -> salvar());
		btnExcluir.setOnMouseClicked(event -> excluir());
		btnExcluir.setDisable(true);
		btnCancelar.setOnMouseClicked(event -> cancelar());
	}

}
