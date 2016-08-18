package telas;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dominio.Pedido;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import javafx.scene.text.Text;
import model.PedidoDAO;
import javafx.scene.layout.HBox;

import javafx.scene.control.SplitPane;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;

import javafx.scene.control.ScrollPane;

import javafx.scene.layout.FlowPane;

import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.VBox;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class AcompanharPedidoController implements Initializable{
	private AnchorPane apVisualizar;
	@FXML
	private SplitPane spPrincipal;
	@FXML
	private AnchorPane anpBotoes;
	@FXML
	private FlowPane flpBotoes;
	@FXML
	private VBox vbBotoes;
	@FXML
	private Button btnSalvar;
	@FXML
	private Button btnFechar;
	@FXML
	private Button btnExcluir;
	@FXML
	private Button btnVoltar;
	@FXML
	private AnchorPane anpPedido;
	@FXML
	private VBox vbPedido;
	@FXML
	private Text txtPedido;
	private ScrollPane srpPedido;
	@FXML
	private TableView<Pedido> tvPedido;
	@FXML
	private TableColumn<Pedido,String> tcNome;
	@FXML
	private TableColumn<Pedido,Integer> tcQuantidade;
	@FXML
	private TableColumn<Pedido,Double> tcValor;
	@FXML
	private TableColumn<Pedido,String> tcStatus;
	@FXML
	private TableColumn<Pedido,Double> tcTotal;
	PedidoDAO pdao = new PedidoDAO();
	private int idPedidoAtual;
	private int mesaAtual;
	ArrayList<Pedido> pedidosItens = new ArrayList<Pedido>();
	Pedido itemPedidoSelecionado = new Pedido();
	public void setPrincipal(AnchorPane apVisualizar) {
		this.apVisualizar = apVisualizar;
	}
	public void carregarTabela() {
		tcNome.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
		tcValor.setCellValueFactory(new PropertyValueFactory<>("valorProduto"));
		tcTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
		tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		tcQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
		
	}
	
	public void voltar() {
		apVisualizar.getChildren().removeAll(apVisualizar.getChildren());
		try {
			// usado paracarregar a interface gráfica e o seu
			// respectivo controle
			FXMLLoader load = new FXMLLoader();
			// recuperando a interface
			load.setLocation(getClass().getResource("/telas/Mesas.fxml"));
			Parent janela = load.load();
			// recuperando o controle.
			MesasController ctrl = load.getController();
			ctrl.setPrincipal(apVisualizar);
			apVisualizar.getChildren().add(janela);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void adicionarTabela() {
		tvPedido.getItems().removeAll(pedidosItens);
		pedidosItens.removeAll(pedidosItens);
		pedidosItens.addAll(pdao.selecionarItens(mesaAtual));
		System.out.println("pesquisando itens");
		tvPedido.setItems(FXCollections.observableArrayList(pedidosItens));
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		carregarTabela();
		tvPedido.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pedido>() {

			@Override
			public void changed(ObservableValue<? extends Pedido> arg0, Pedido antigo, Pedido novo) {
				if (itemPedidoSelecionado != null) {
					itemPedidoSelecionado = novo;
				}
				
			}
		});
		
		btnExcluir.setOnMouseClicked(event ->adicionarTabela());
		btnSalvar.setOnMouseClicked(event -> mudarStatus());
	}
	public void mudarStatus() {
		pdao.alterarStatus(itemPedidoSelecionado.getIdPedido());
		
	}
	public int getMesaAtual() {
		return mesaAtual;
	}
	public void setMesaAtual(int mesaAtual) {
		this.mesaAtual = mesaAtual;
	}

}
