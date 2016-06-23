package telas;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import javafx.scene.control.SplitPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.VBox;

public class PrincipalController implements Initializable {
	@FXML
	private SplitPane spPrincipal;
	@FXML
	private AnchorPane apOpcoes;
	@FXML
	private VBox vbBotoes;
	@FXML
	private Button btnGarcom;
	@FXML
	private Button btnCardapio;
	@FXML
	private Button btnMesas;
	@FXML
	private Button btnPedido;
	@FXML
	private AnchorPane apVisualizar;
	@FXML
	private ImageView imgLogo;

	ArrayList<PrintStream> clientes = new ArrayList<PrintStream>();

	public void iniciarMysql() {
		/**
		 * Caso o mysql esteja no mesmo caminho descomentem a linha 65 assim não
		 * será necessário executar o mysql manualmente antes de testar nossa
		 * aplicação.
		 */
		String caminho = "C:\\xampp\\mysql\\bin\\mysqld.exe";
		ProcessBuilder pb = new ProcessBuilder(caminho);
		try {
			pb.start();
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public void chamarGarcom() {

		try {
			apVisualizar.getChildren().removeAll(apVisualizar.getChildren());
			apVisualizar.getChildren().add(FXMLLoader.load(getClass().getResource("/telas/CRUDgarcom.fxml")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void chamarMenu() {
		apVisualizar.getChildren().removeAll(apVisualizar.getChildren());
		try {
			/**
			 * // Carrega a person overview. FXMLLoader loader = new
			 * FXMLLoader();
			 * loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
			 * AnchorPane personOverview = (AnchorPane) loader.load();
			 * 
			 * // Define a person overview no centro do root layout.
			 * rootLayout.setCenter(personOverview);
			 * 
			 * // Dá ao controlador acesso à the main app.
			 * PersonOverviewController controller = loader.getController();
			 * controller.setMainApp(this);
			 * 
			 */
			// usado paracarregar a interface gráfica e o seu
			// respectivo controle
			FXMLLoader load = new FXMLLoader();
			// recuperando a interface
			load.setLocation(getClass().getResource("/telas/Restaurante.fxml"));
			Parent janela = load.load();
			// recuperando o controle.
			RestauranteController ctrl = load.getController();
			/// ctrl.pesquisar();
			// Parent janela =
			/// FXMLLoader.load(getClass().getResource("/telas/Restaurante.fxml"));

			// ctrl.initialize(janela,
			// getClass().getResource("/telas/Restaurante.fxml"));
			apVisualizar.getChildren().add(janela);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void trataCliente(InputStream is) {
		Scanner t = new Scanner(is);
		while (t.hasNextLine()) {
			System.out.println(t.nextLine());
			
		}
		t.close();
	}
	public void iniciarServidor() {
		try {
			Socket cliente;
			ServerSocket servidor = new ServerSocket(12345);
			System.out.println("Porta 12345 aberta!");
			while (true) {
				cliente = servidor.accept();
				System.out.println("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());
				// adiciona saida do cliente à lista
				PrintStream ps = new PrintStream(cliente.getOutputStream());				
				this.clientes.add(ps);
				InputStream is = cliente.getInputStream();
				Thread t2 = new Thread(() ->trataCliente(is));
				t2.start();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Thread t = new Thread(() -> iniciarServidor());
		t.start();
		
		iniciarMysql();
		// Inserindo ações no butão ao clicar
		btnGarcom.setOnMouseClicked(event -> chamarGarcom());
		btnCardapio.setOnMouseClicked(event -> chamarMenu());
		// Inserindo imagens nos botões.
		btnGarcom.setGraphic(new ImageView(new Image("/imagens/garcom.png")));
		btnMesas.setGraphic(new ImageView(new Image("/imagens/mesa.jpg")));
		btnCardapio.setGraphic(new ImageView(new Image("imagens/menu.jpg")));
		btnPedido.setGraphic(new ImageView(new Image("imagens/pedido.jpg")));

		// Inserindo texto que aparece ao deixar o mouse parado sobre
		// o botão
		btnGarcom.setTooltip(new Tooltip("Cadastro Garçom"));
		btnCardapio.setTooltip(new Tooltip("Cadastro de Cardápio"));
		btnMesas.setTooltip(new Tooltip("Cadastro de Mesas"));
		btnPedido.setTooltip(new Tooltip("Cadastro de Pedido"));
	}

}
