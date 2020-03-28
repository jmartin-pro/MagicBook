package magic_book.window.component;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import magic_book.core.Book;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.observer.book.BookNodeObserver;

public class RightPane extends ScrollPane implements BookNodeObserver{
	
	private Book book;
	
	private int nodeCount;
	private Label nodeCountLabel;
	
	private float difficulte;
	private Label difficultePourcentageLabel;
	
	public RightPane(Book book){		
		nodeCount = 0;
		difficulte = 0;
		
		this.setMaxWidth(200);
		this.setMinWidth(200);
		this.setPadding(new Insets(5, 5, 5, 5));
		this.setFitToWidth(true);
				
		this.setContent(createRightPanel());
		
		setBook(book);
	}
	
	private Node createRightPanel() {
		HBox nodeCountBox = new HBox();
		Label prefixNodeCountLabel = new Label("Total de noeuds : ");
		nodeCountLabel = new Label(""+nodeCount);
		nodeCountBox.getChildren().addAll(prefixNodeCountLabel, nodeCountLabel);
		
		HBox difficulteBox = new HBox();
		Label difficulteLabel = new Label("Difficulte du livre : ");
		difficultePourcentageLabel = new Label(""+difficulte);
		difficulteBox.getChildren().addAll(difficulteLabel, difficultePourcentageLabel);
		
		VBox statsLayout = new VBox();
		statsLayout.getChildren().addAll(nodeCountBox, difficulteBox);

		return statsLayout;
	}
	
	
	private void updateStats() {
		nodeCountLabel.setText(""+nodeCount);
	}

	@Override
	public void nodeAdded(AbstractBookNode node) {
		nodeCount++;
		
		updateStats();
	}

	@Override
	public void nodeDeleted(AbstractBookNode node) {
		nodeCount--;
	
		updateStats();
	}

	@Override
	public void nodeEdited(AbstractBookNode oldNode, AbstractBookNode newNode) {
	
		updateStats();
	}
	
	public void difficulteAdded(float difficulte) {
		System.out.println("oki");
		this.difficulte = difficulte;
		difficultePourcentageLabel.setText(""+difficulte);
	}
	
	
	public void setBook(Book book) {
		if(this.book != null)
			this.book.removeNodeObserver(this);
		
		this.book = book;
		this.book.addNodeObserver(this);
		
		nodeCount = this.book.getNodes().size();
		updateStats();
	}

	public Book getBook() {
		return book;
	}

}
