package magic_book;

import javafx.scene.layout.Pane;
import javafx.application.Application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Main extends Application {

	@Override
	public void start(Stage stage) {
		VBox root = new VBox();
		root.setPrefSize(800, 600);

		
		Label texte = new Label("Texte :");
		
	
		TextArea histoire = new TextArea();
		
		
		Label typeNoeud = new Label("Type de noeud :");
		
		
		Button boutonAnnuler = new Button("Annuler");
		boutonAnnuler.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				stage.close();
			}
		});
		
		boutonAnnuler.setContentDisplay(ContentDisplay.TOP);
		
		Button boutonValider = new Button("Valider");
		
		boutonValider.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				//Node node = new Node()
				stage.close();
			}
		});
		
		root.getChildren().addAll(boutonAnnuler, boutonValider, texte, histoire, typeNoeud);

		Scene scene = new Scene(root);

		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}

}
