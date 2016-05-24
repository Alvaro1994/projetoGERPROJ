package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;



public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//Carrega a tela em root, root é do tipo Parent porque todos os
			//containers herdam desta classe, assim não importa o container 
			//que usamos para definir nossa interface. 
			Parent root = FXMLLoader.load(getClass().getResource("/telas/CRUDgarcom.fxml"));
			Scene scene = new Scene(root);
			//Adiciona o icnone que está na pasta imagens
			primaryStage.getIcons().add(new Image("/imagens/icone.jpg"));
			//aplica a folha de estilo que deixam os botões com
			//cor azul
			scene.getStylesheets().add(getClass()
					.getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Cadastro de Garçom");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
