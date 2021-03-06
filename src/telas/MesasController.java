package telas;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;



public class MesasController implements Initializable{
	private AnchorPane apVisualizar;
	@FXML
	private AnchorPane apPrincipal;
	@FXML
	private ScrollPane srpMesas;
	@FXML
	private GridPane grpMessas;
	@FXML
	private Text txtMesas;
	private ArrayList<VBox> messas = new ArrayList<>();
	
	public void setPrincipal(AnchorPane apVisualizar) {
		this.apVisualizar = apVisualizar;
	}
	public void mudarStatus(Button btn) {
		apVisualizar.getChildren().removeAll(apVisualizar.getChildren());
		try {
			// usado paracarregar a interface gr�fica e o seu
			// respectivo controle
			FXMLLoader load = new FXMLLoader();
			// recuperando a interface
			load.setLocation(getClass().getResource("/telas/AcompanharPedido.fxml"));
			Parent janela = load.load();
			// recuperando o controle.
			AcompanharPedidoController ctrl = load.getController();
			
			int idMesa = Integer.parseInt(btn.getId());
			System.out.println("Mesa atual"+idMesa);
			ctrl.setMesaAtual(idMesa);
			//ctrl.setPrincipal(apVisualizar);
			
			apVisualizar.getChildren().add(janela);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void carregarMessas() {
		//grpMessas.setGridLinesVisible(true);
		int cont = 1;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				VBox vb = new VBox();
				vb.setPrefWidth(100);
				vb.setMaxWidth(100);
				vb.setMaxHeight(120);
				vb.setPrefHeight(120);
				Text txtStatus = new Text(Integer.toString(cont)+" Vazia");
				txtStatus.setTextAlignment(TextAlignment.CENTER);
				Image img = new Image("/imagens/mesa_vazia.png");
				ImageView viewimg = new ImageView(img);
				Button btnMesa = new Button();
				btnMesa.setId(cont+"");
				btnMesa.setPrefHeight(100.0);
				btnMesa.setPrefHeight(100.0);
				btnMesa.setMinHeight(100.0);
				btnMesa.setMinWidth(100.0);
				btnMesa.setGraphic(viewimg);
				btnMesa.setId(Integer.toString(cont));
				btnMesa.setOnMouseClicked((event)->mudarStatus(btnMesa));
				vb.getChildren().addAll(btnMesa,txtStatus);
				this.messas.add(vb);
				grpMessas.add(vb, j, i);
				cont++;
			}
		}
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		carregarMessas();
		
	}
	public ArrayList<VBox> getMessas() {
		return messas;
	}
	public void setMessas(ArrayList<VBox> messas) {
		this.messas = messas;
	}
}
