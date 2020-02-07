package magic_book.window;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainWindow extends Stage {

	private String mode;

	public MainWindow() {
		BorderPane border = new BorderPane();
		//Button button = new Button("Bouton");
		//Label l= new Label("0");

		//button.setOnAction(value ->  {
		//   l.setText("1");
		//});
		//root.getChildren().add(button);
		//root.getChildren().add(l);
		mode = "0";
		Scene scene = new Scene(border, 1000, 800);

		Button add = new Button("Ajouter");
		Button del = new Button("Supprimer");
		Button mod = new Button("Modifier");

		add.setOnAction(value -> {
			mode = "add";
			System.out.println("clic bouton");
		});
		del.setOnAction(value -> {
			mode = "del";
			System.out.println("clic bouton");
		});
		mod.setOnAction((ActionEvent value) -> {
			mode = "mod";
			System.out.println("clic bouton");
		});

		add.setMinSize(100, 100);
		add.setMaxSize(100, 100);
		del.setMinSize(100, 100);
		del.setMaxSize(100, 100);
		mod.setMinSize(100, 100);
		mod.setMaxSize(100, 100);

		FlowPane flow = new FlowPane();
		flow.getChildren().addAll(add, del, mod);
		flow.setMaxWidth(210);
		flow.setPadding(new Insets(5, 5, 5, 5));
		border.setLeft(flow);
		System.out.println(border.getCenter());
		Pane appContent = new Pane();
		appContent.setCursor(Cursor.CLOSED_HAND);
		border.setCenter(appContent);

		appContent.addEventFilter(MouseEvent.MOUSE_PRESSED, (new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				System.out.println("clic fenêtre avec mode " + mode);
				if (mode == "add") {

				}
				if (mode == "del") {

				}
			}
		}));

		this.setScene(scene);
		this.show();
	}

}
