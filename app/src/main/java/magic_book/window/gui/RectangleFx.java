package magic_book.window.gui;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import magic_book.core.node.BookNode;
import magic_book.observer.RectangleFxObservable;
import magic_book.observer.RectangleFxObserver;

public class RectangleFx extends Rectangle {

	private RectangleFxObservable nodeFxObservable;

	public RectangleFx() {
	
		nodeFxObservable = new RectangleFxObservable();
		
		this.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
			nodeFxObservable.notifyOnNodeFXClicked(RectangleFx.this, event);
		});
		
		this.setOnMouseDragged(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent select) {
					RectangleFx.this.setX(select.getX()-RectangleFx.this.getWidth()/2);
					RectangleFx.this.setY(select.getY()-RectangleFx.this.getHeight()/2);
				}
			});
	}
	
	public void addNodeFxObserver(RectangleFxObserver observer) {
		nodeFxObservable.addObserver(observer);
	}
}