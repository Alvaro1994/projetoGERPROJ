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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    private Label lbInfDados;

    @FXML
    private VBox vbNomeDispo;

    @FXML
    private Label lbNomeGarcon;

    @FXML
    private TextField tfNome;

    @FXML
    private Label lblDisponibilidadeGarcom;

    @FXML
    private ComboBox<String> cbDisponibilidade;

    @FXML
    private HBox hbBotoesCRUDgarcom;
    
    @FXML
    private Button btnNovo;

    @FXML
    private Button btnPesquisar;

    @FXML
    private Button btnCancelar;
    
    public void clicouTabela() {
		System.out.println("clicou tabela");
	}
    public void configurarCombobox() {
    	String[] disponibilidade = { "Disponível", "Indisponível" };
    	cbDisponibilidade.getItems().addAll(FXCollections.observableArrayList(disponibilidade));
    	//Seleciona o primeiro objeto do combobox
    	cbDisponibilidade.getSelectionModel().selectFirst();
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
    	garcoms.addAll(garcomDAO.pesquisarGarcom(tfNome.getText()));
		tvGarcom.setItems(FXCollections.observableArrayList(garcoms));
	}
    public void cancelar() {
		tfNome.clear();
		limparTabela();
		btnNovo.setDisable(false);
    	btnPesquisar.setDisable(false);
		btnNovo.setText("Novo");
		btnPesquisar.setText("Pesquisar");
		lbNomeGarcon.setText("Pesquisar:");
	}
    public void salvar() {
    	//Salva garçom
    	if (btnNovo.getText().equals("Salvar")) {
    		tfNome.setPromptText("Informe o nome para salvar");
    		btnNovo.setText("Novo");
    		lbNomeGarcon.setText("Pesquisar:");
    		btnPesquisar.setDisable(false);
    		garcomDAO.inserirGarcom(tfNome.getText(), cbDisponibilidade.getSelectionModel().getSelectedItem());
    		tfNome.clear();
    	//Habilita a operação salvar
		}else if(btnNovo.getText().equals("Novo")){
			tfNome.setPromptText("Informe o nome para pesquisar");
			lbNomeGarcon.setText("Nome:");
			btnPesquisar.setDisable(true);
			btnNovo.setText("Salvar");
		//Altera garçom
		}else{
			tfNome.setPromptText("Informe o nome para pesquisar");
			lbNomeGarcon.setText("Pesquisar:");
			System.out.println(garcomSelecionado.getNome());
			Garcom novoGarcom = new Garcom();
			novoGarcom.setNome(tfNome.getText());
			garcomDAO.alterarGarcom(garcomSelecionado,novoGarcom);
			btnNovo.setText("Novo");
			btnNovo.setDisable(false);
			btnPesquisar.setText("Pesquisar");
			btnPesquisar.setDisable(false);
			tfNome.clear();
			limparTabela();
			
		}
	}
   
    public void pesquisarGarcom() {
    	if (btnPesquisar.getText().equals("Pesquisar")) {
    		btnPesquisar.setText("Excluir");
    		btnPesquisar.setDisable(true);
    		btnNovo.setDisable(true);
    		btnNovo.setText("Alterar");
    		adicionarTabela();
    		
		}else{
			//Exclui garçom
			garcomDAO.excluirGarcom(tfNome.getText());
			limparTabela();
			tvGarcom.getSelectionModel().clearSelection();
			//limpa campo
			tfNome.clear();
			btnNovo.setText("Novo");
			btnPesquisar.setText("Pesquisar");
		}
    	
	}
    
    public int indexComboBox(String disponibilidade) {
		if (disponibilidade.equals("Disponível")) {
			return 0;
		}
		return 1;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tfNome.setPromptText("Informe o nome para pesquisar");
		configurarCombobox();
		carregarTabela();
		tvGarcom.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Garcom>() {

			@Override
			public void changed(ObservableValue<? extends Garcom> observable, Garcom oldValue, Garcom newValue) {
				//tfNome.textProperty().bindBidirectional(newValue.nomeProperty());
				if (newValue != null) {
					tfNome.setText(newValue.getNome());
					int index = indexComboBox(newValue.getDisponibilidade());
					cbDisponibilidade.getSelectionModel().select(index);
					System.out.println("Evento na tabela "+newValue.toString());
				}
				
				garcomSelecionado = newValue;
				
				btnNovo.setDisable(false);
	        	btnPesquisar.setDisable(false);
			}
		});
		
		lbNomeGarcon.setText("Pesquisar:");
		//Desabilita o botão selecionar se nenhum item estiver selecionado
		//nomebtn.disableProperty().bind(tvGarcom.getSelectionModel().selectedItemProperty().isNull());
		btnCancelar.setOnMouseClicked(event -> cancelar());
		btnNovo.setOnMouseClicked(event -> salvar());
		btnPesquisar.setOnMouseClicked(event -> pesquisarGarcom());
		
		
	}

}
