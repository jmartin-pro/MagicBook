package magic_book.core.game.player;

import java.util.List;
import magic_book.core.Book;
import magic_book.core.game.BookCharacter;
import magic_book.core.game.BookState;
import magic_book.core.game.player.Jeu.ChoixCombat;
import magic_book.core.graph.node.AbstractBookNodeWithChoices;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.item.BookItemLink;

public interface InterfacePlayerFourmis {
	
	public BookCharacter execPlayerCreation(Book book);
	
	public ChoixCombat combatChoice(BookNodeCombat bookNodeCombat, int remainingRoundBeforeEvasion, BookState state);
	
	public BookCharacter chooseEnnemi(List<BookCharacter> listEnnemis);
	
	public void prendreItems(BookState state, List<BookItemLink> bookItemLinks, int nbItemMax);	
	
	public int makeAChoice(AbstractBookNodeWithChoices node);
	
}
