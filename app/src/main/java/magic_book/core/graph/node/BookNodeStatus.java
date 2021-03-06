package magic_book.core.graph.node;

/**
 * Statut du noeud 
 */
public enum BookNodeStatus {
	/**
	 * Victoire ou Défaite
	 */
	VICTORY("Victoire"), FAILURE("Défaite");

	/**
	 * Permet d'écrire leurs nom
	 */
	private String name;

	/**
	 * Constructeur, prend le nom du statut en paramètre
	 * @param name Nom du BookNodeState
	 */
	BookNodeStatus(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
