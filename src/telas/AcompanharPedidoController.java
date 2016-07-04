package telas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import javafx.scene.text.Text;

import javafx.scene.layout.HBox;

import javafx.scene.control.SplitPane;

import javafx.scene.control.TextField;

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
	@FXML
	private HBox hbDados;
	@FXML
	private Label lbMesa;
	@FXML
	private TextField tfMesa;
	@FXML
	private Label lbGarcom;
	@FXML
	private TextField tfGarcom;
	@FXML
	private Label lbDesconto;
	@FXML
	private TextField tfDesconto;
	@FXML
	private Label lbTotal;
	@FXML
	private TextField tfTotal;
	@FXML
	private ScrollPane srpPedido;
	@FXML
	private TableView tvPedido;
	@FXML
	private TableColumn tcNome;
	@FXML
	private TableColumn tcQuantidade;
	@FXML
	private TableColumn tcValor;
	@FXML
	private TableColumn Status;
	public void setPrincipal(AnchorPane apVisualizar) {
		this.apVisualizar = apVisualizar;
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
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnVoltar.setOnMouseClicked(event -> voltar());
		
	}

}
