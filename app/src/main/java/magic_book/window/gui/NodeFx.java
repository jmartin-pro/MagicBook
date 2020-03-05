package magic_book.window.gui;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import magic_book.core.node.BookNode;
import magic_book.observer.NodeFxObservable;
import magic_book.observer.NodeFxObserver;

public class NodeFx extends Rectangle {
	
	private BookNode node;
	
	private NodeFxObservable nodeFxObservable;

	public NodeFx(BookNode node) {
		this.node = node;
		nodeFxObservable = new NodeFxObservable();
		
		this.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
			nodeFxObservable.notifyOnNodeFXClicked(NodeFx.this, event);
		});				
	}
	
	public void addNodeFxObserver(NodeFxObserver observer) {
		nodeFxObservable.addObserver(observer);
	}

	public BookNode getNode() {
		return node;
	}

	public void setNode(BookNode node) {
		this.node = node;
	}
	
}