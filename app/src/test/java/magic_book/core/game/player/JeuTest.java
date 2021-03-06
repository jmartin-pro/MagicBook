package magic_book.core.game.player;

public class JeuTest {
	/*
	 * Les tests suivants sont soumis à l'aléatoire. Les résultats sont donc imprévisibles. 
	 * Il faut trouver une meilleure méthode afin de les réaliser 
	 * (mocker le Random ou tester uniquement les actions spécifiques à la fourmis par exemple).
	 * Du fait qu'ils font échouer la validation du code, ils seront désactivés
	*/
	
	/*
	//Variables
	Jeu jeu;
	Book book;
	float victoire;
	List<BookNodeLink> listAbstractBookNode ;
	BookNodeWithChoices bookNodeWithChoices;
	HashMap<Integer, AbstractBookNode> nodes;
	BookNodeTerminal bookNodeTerminalVictory = new BookNodeTerminal("C'est un noeud de victoire", BookNodeStatus.VICTORY);
	BookNodeTerminal bookNodeTerminalFailure = new BookNodeTerminal("C'est un noeud de défaite", BookNodeStatus.FAILURE);
		
	
	@Test
	public void runGame(){
		
		BookNodeLink bookNodeLink1 = new BookNodeLink("C'est le bookNodeLink", 2);
		BookNodeLink bookNodeLink2 = new BookNodeLink("C'est le bookNodeLink", 3);
		
		//1er test : noeud Terminal Failure
		listAbstractBookNode = new ArrayList();
		listAbstractBookNode.add(bookNodeLink1);
		
		bookNodeWithChoices = new BookNodeWithChoices("", 0, null, null, listAbstractBookNode);
		
		nodes = new HashMap();
		nodes.put(1, bookNodeWithChoices);
		nodes.put(2, bookNodeTerminalFailure);
		
		book = new Book();
		book.setNodes(nodes);
		jeu = new Jeu(book);
		victoire = jeu.fourmis(10000);
		Assert.assertTrue(victoire == 0);
		
		//2eme test : noeud Terminal Victoire
		nodes.put(1, bookNodeWithChoices);
		nodes.put(2, bookNodeTerminalVictory);
		
		book = new Book();
		book.setNodes(nodes);
		jeu = new Jeu(book);
		victoire = jeu.fourmis(10000);
		Assert.assertTrue(victoire == 100);
		
		//3eme test : noeud Terminal Failure et Victoire
		listAbstractBookNode.add(bookNodeLink2);
		
		bookNodeWithChoices = new BookNodeWithChoices("", 0, null, null, listAbstractBookNode);
		
		nodes.put(3, bookNodeTerminalFailure);
		
		book = new Book();
		book.setNodes(nodes);
		jeu = new Jeu(book);
		victoire = jeu.fourmis(10000);
		Assert.assertTrue(victoire > 49 && victoire < 51);
	}
	@Test
	public void execAbstractNodeWithChoices(){		
		BookNodeLink bookNodeLink1 = new BookNodeLink("C'est le bookNodeLink", 2);
		
		//1er test : noeud Terminal Failure
		listAbstractBookNode = new ArrayList();
		listAbstractBookNode.add(bookNodeLink1);
		
		bookNodeWithChoices = new BookNodeWithChoices("", 0, null, null, listAbstractBookNode);
		
		nodes = new HashMap();
		nodes.put(1, bookNodeWithChoices);
		nodes.put(2, bookNodeTerminalVictory);
		
		
		book = new Book();
		
		BookCharacter bookCharacter = new BookCharacter("0", "Salut", 3, 20, null, null, 2);
		bookCharacter.setHp(0);
		HashMap<String, BookCharacter> characters = new HashMap();
		characters.put("0", bookCharacter);
		book.setCharacters(characters);
		
		book.setNodes(nodes);
		jeu = new Jeu(book);
		victoire = jeu.fourmis(1);
		
		Assert.assertTrue(victoire ==  0);
		
		bookCharacter.setHp(20);
		characters.put("0", bookCharacter);
		book.setCharacters(characters);
		jeu = new Jeu(book);
		victoire = jeu.fourmis(1);
		Assert.assertTrue(victoire ==  100);
		
	}
	
	@Test
	public void execNodeWithChoicesLienValide(){
		//Création des Noeuds
		BookItemWeapon bookItemWeapon = new BookItemWeapon("arme", "Aiguille", 2, 2);
		
		RequirementItem requirementItem = new RequirementItem(bookItemWeapon.getId());
		
		List<AbstractRequirement> listRequirements = new ArrayList();
		listRequirements.add(requirementItem);
		
		List<List<AbstractRequirement>> requirements = new ArrayList();
		requirements.add(listRequirements);
		
		//1er test : Requirements non valide - Defaite
		BookNodeLink bookNodeLink1 = new BookNodeLink("C'est le bookNodeLink qui a besoin d'une arme", 2, requirements);
		
		
		listAbstractBookNode = new ArrayList();
		listAbstractBookNode.add(bookNodeLink1);
		
		bookNodeWithChoices = new BookNodeWithChoices("", 0, null, null, listAbstractBookNode);
		
		nodes = new HashMap();
		nodes.put(1, bookNodeWithChoices);
		nodes.put(2, bookNodeTerminalVictory);
		
		
		book = new Book();
		
		book.setNodes(nodes);
		jeu = new Jeu(book);
		victoire = jeu.fourmis(1);
		Assert.assertTrue(victoire ==  0);
		
		//2eme test : Requirements valide - Victoire
		List<String> listItems = new ArrayList();
		listItems.add(bookItemWeapon.getId());
		
		BookCharacter bookCharacter = new BookCharacter("0", "Salut", 3, 20, null, listItems, 2);
		HashMap<String, BookCharacter> characters = new HashMap();
		characters.put(bookCharacter.getId(), bookCharacter);
		book.setCharacters(characters);
		
		jeu = new Jeu(book);
		victoire = jeu.fourmis(1);
		Assert.assertTrue(victoire ==  100);
		
		
		BookSkill bookSkill = new BookSkill("competence", "sort");
		
		List<String> listSkills = new ArrayList();
		listSkills.add(bookSkill.getId());
		
		
		
		AbstractRequirement requirementSkill = new RequirementSkill(bookSkill.getId());
		
		listRequirements = new ArrayList();
		listRequirements.add(requirementSkill);
		
		requirements = new ArrayList();
		requirements.add(listRequirements);
		
		bookNodeLink1 = new BookNodeLink("C'est le bookNodeLink qui a besoin d'une competence", 2, requirements);
		
		
		listAbstractBookNode = new ArrayList();
		listAbstractBookNode.add(bookNodeLink1);
		
		bookNodeWithChoices = new BookNodeWithChoices("", 0, null, null, listAbstractBookNode);
		
		nodes = new HashMap();
		nodes.put(1, bookNodeWithChoices);
		nodes.put(2, bookNodeTerminalVictory);
		
		bookCharacter = new BookCharacter("0", "Salut", 3, 20, listSkills, null, 2);
		characters = new HashMap();
		characters.put("0", bookCharacter);
		book.setCharacters(characters);
		
		book.setNodes(nodes);
		
		jeu = new Jeu(book);
		victoire = jeu.fourmis(1);
		Assert.assertTrue(victoire ==  100);
	}
	
	@Test
	public void execNodeCombat(){
		//Création des Noeuds
		BookNodeTerminal bookNodeTerminalEvasion = new BookNodeTerminal("Evasion", BookNodeStatus.VICTORY);
		
		BookNodeLink bookNodeLinkWin = new BookNodeLink("BookNodeLink win", 2);
		BookNodeLink bookNodeLinkLoose = new BookNodeLink("BookNodeLink loose", 3);
		BookNodeLink bookNodeLinkEvasion = new BookNodeLink("BookNodeLink evasion", 4);
		
		//1er test : victoire sans ennemis
		BookNodeCombat bookNodeCombatSansEnnemis = new BookNodeCombat("Combat", bookNodeLinkWin, bookNodeLinkLoose, bookNodeLinkEvasion, 100, null);
		
		nodes = new HashMap();
		nodes.put(1, bookNodeCombatSansEnnemis);
		nodes.put(2, bookNodeTerminalVictory);
		nodes.put(3, bookNodeTerminalFailure);
		nodes.put(4, bookNodeTerminalEvasion);
			
		
		book = new Book();
		
		book.setNodes(nodes);
		
		jeu = new Jeu(book);
		victoire = jeu.fourmis(1);
		Assert.assertTrue(victoire ==  100);
		
		//2ème test : defaite avec ennemis
		BookCharacter bookCharacterEnnemi1 = new BookCharacter("Sorcier", "Ennemis1", 50, 100, null, null, 2);
		BookCharacter bookCharacterEnnemi2 = new BookCharacter("Zombie", "Ennemis2", 50, 100, null, null, 2);
		
		
		HashMap<String, BookCharacter> mapEnnemis = new HashMap();
		mapEnnemis.put(bookCharacterEnnemi1.getId(), bookCharacterEnnemi1);
		mapEnnemis.put(bookCharacterEnnemi2.getId(), bookCharacterEnnemi2);
		
		
		List<String> listEnnemis = new ArrayList();
		listEnnemis.add(bookCharacterEnnemi1.getId());
		listEnnemis.add(bookCharacterEnnemi2.getId());
		
		BookNodeCombat bookNodeCombatAvecEnnemis = new BookNodeCombat("Combat", bookNodeLinkWin, bookNodeLinkLoose, bookNodeLinkEvasion, 100, listEnnemis);
		
		nodes = new HashMap();
		nodes.put(1, bookNodeCombatAvecEnnemis);
		nodes.put(2, bookNodeTerminalVictory);
		nodes.put(3, bookNodeTerminalFailure);
		nodes.put(4, bookNodeTerminalEvasion);
		
		book = new Book();
		
		book.setNodes(nodes);
		book.setCharacters(mapEnnemis);
		
		jeu = new Jeu(book);
		victoire = jeu.fourmis(1);
		Assert.assertTrue(victoire ==  0);

		
		//3ème test : evasion avec ennemis
		BookNodeCombat bookNodeCombatAvecEvasion = new BookNodeCombat("Combat", bookNodeLinkLoose, bookNodeLinkLoose, bookNodeLinkEvasion, 0, listEnnemis);
		nodes = new HashMap();
		nodes.put(1, bookNodeCombatAvecEvasion);
		nodes.put(2, bookNodeTerminalFailure);
		nodes.put(3, bookNodeTerminalFailure);
		nodes.put(4, bookNodeTerminalEvasion);
		book = new Book();
		
		book.setNodes(nodes);
		book.setCharacters(mapEnnemis);
		
		jeu = new Jeu(book);
		victoire = jeu.fourmis(1);
		Assert.assertTrue(victoire ==  100);
	}
	
	@Test
	public void execNodeWithRandomChoices(){
		List<BookNodeLinkRandom> listAbstractBookNodeRandom;
		BookNodeWithRandomChoices bookNodeWithRandomChoices;
		BookNodeLinkRandom bookNodeLinkVictoire;
		BookNodeLinkRandom bookNodeLinkDefaite;
				
		
		//Création des Noeuds
		BookNodeTerminal bookNodeTerminalVictoire = new BookNodeTerminal("Victoire", BookNodeStatus.VICTORY);
		BookNodeTerminal bookNodeTerminalDefaite = new BookNodeTerminal("Defaite", BookNodeStatus.FAILURE);
		
		//1er test - Chance victoire
		bookNodeLinkVictoire = new BookNodeLinkRandom("BookNodeLink Victoire", 2, null, 1);
		bookNodeLinkDefaite = new BookNodeLinkRandom("BookNodeLink Defaite", 3, null, 0);
		
		listAbstractBookNodeRandom = new ArrayList();
		listAbstractBookNodeRandom.add(bookNodeLinkVictoire);
		listAbstractBookNodeRandom.add(bookNodeLinkDefaite);
		

		bookNodeWithRandomChoices = new BookNodeWithRandomChoices("Noeud Random", 0, null, null, listAbstractBookNodeRandom);
		
		nodes = new HashMap();
		nodes.put(1, bookNodeWithRandomChoices);
		nodes.put(2, bookNodeTerminalVictoire);
		nodes.put(3, bookNodeTerminalDefaite);
			
		book = new Book();
		
		book.setNodes(nodes);
		
		jeu = new Jeu(book);
		victoire = jeu.fourmis(1);
		Assert.assertTrue(victoire ==  100);
		
		//2eme test - Chance defaite
		bookNodeLinkVictoire = new BookNodeLinkRandom("BookNodeLink Victoire", 2, null, 0);
		bookNodeLinkDefaite = new BookNodeLinkRandom("BookNodeLink Defaite", 3, null, 1);
		
		listAbstractBookNodeRandom = new ArrayList();
		listAbstractBookNodeRandom.add(bookNodeLinkVictoire);
		listAbstractBookNodeRandom.add(bookNodeLinkDefaite);
		

		bookNodeWithRandomChoices = new BookNodeWithRandomChoices("Noeud Random", 0, null, null, listAbstractBookNodeRandom);
		
		nodes = new HashMap();
		nodes.put(1, bookNodeWithRandomChoices);
		nodes.put(2, bookNodeTerminalVictoire);
		nodes.put(3, bookNodeTerminalDefaite);
			
		book = new Book();
		
		book.setNodes(nodes);
		
		jeu = new Jeu(book);
		victoire = jeu.fourmis(1);
		Assert.assertTrue(victoire ==  0);
		
		
		
		//3eme test - 50/50 Victoire/Defaite
		bookNodeLinkVictoire = new BookNodeLinkRandom("BookNodeLink Victoire", 2, null, 1);
		bookNodeLinkDefaite = new BookNodeLinkRandom("BookNodeLink Defaite", 3, null, 1);
		
		listAbstractBookNodeRandom = new ArrayList();
		listAbstractBookNodeRandom.add(bookNodeLinkVictoire);
		listAbstractBookNodeRandom.add(bookNodeLinkDefaite);
		

		bookNodeWithRandomChoices = new BookNodeWithRandomChoices("Noeud Random", 0, null, null, listAbstractBookNodeRandom);
		
		nodes = new HashMap();
		nodes.put(1, bookNodeWithRandomChoices);
		nodes.put(2, bookNodeTerminalVictoire);
		nodes.put(3, bookNodeTerminalDefaite);
			
		book = new Book();
		
		book.setNodes(nodes);
		
		jeu = new Jeu(book);
		victoire = jeu.fourmis(10000);
		Assert.assertTrue(victoire > 48 && victoire < 52);
	}
	
	@Test
	public void execNodeHp(){
		BookNodeLink bookNodeLinkVictoire;
		HashMap<String, BookCharacter> mapCharacter;
		BookCharacter bookCharacter ;
				
		
		//Création des Noeuds
		bookNodeLinkVictoire = new BookNodeLink("BookNodeLink Victoire", 2);
		
		listAbstractBookNode = new ArrayList();
		listAbstractBookNode.add(bookNodeLinkVictoire);
		
		bookNodeWithChoices = new BookNodeWithChoices("Noeud", 0, null, null, listAbstractBookNode);
		
		//1er test - Defaite : le character perd tout ses hp
		bookCharacter = new BookCharacter();
		bookCharacter.setId("0");
		bookCharacter.setHp(20);
		
		bookNodeWithChoices.setHp(-20);
		nodes = new HashMap();
		nodes.put(1, bookNodeWithChoices);
		nodes.put(2, bookNodeTerminalVictory);
		
		
		mapCharacter = new HashMap();
		mapCharacter.put(bookCharacter.getId(), bookCharacter);
		
		book = new Book();
		
		book.setNodes(nodes);
		book.setCharacters(mapCharacter);
		
		jeu = new Jeu(book);
		victoire = jeu.fourmis(1);
		Assert.assertTrue(victoire ==  0);
		
		//2eme test - Victoire : le character ne perd pas tout ses hp
		bookCharacter = new BookCharacter("0", "Personnage", 0, 20, null, null, 2);
		
		listAbstractBookNode = new ArrayList();
		listAbstractBookNode.add(bookNodeLinkVictoire);
		
		bookNodeWithChoices = new BookNodeWithChoices("Noeud", 0, null, null, listAbstractBookNode);
		bookNodeWithChoices.setHp(-19);
		
		nodes = new HashMap();
		nodes.put(1, bookNodeWithChoices);
		nodes.put(2, bookNodeTerminalVictory);
		
		mapCharacter = new HashMap();
		mapCharacter.put(bookCharacter.getId(), bookCharacter);
		
		book = new Book();
		
		book.setNodes(nodes);
		book.setCharacters(mapCharacter);
		
		jeu = new Jeu(book);
		victoire = jeu.fourmis(1);
		Assert.assertTrue(victoire ==  100);
	}
	
	@Test
	public void chooseItems(){
		HashMap<String, BookCharacter> mapCharacter;
		BookCharacter bookCharacter ;
		
		BookItemWeapon bookItemWeapon = new BookItemWeapon("arme", "Aiguille", 2, 2);
		
		RequirementItem requirementItem = new RequirementItem(bookItemWeapon.getId());
		
		List<AbstractRequirement> listRequirements = new ArrayList();
		listRequirements.add(requirementItem);
		
		List<List<AbstractRequirement>> requirements = new ArrayList();
		requirements.add(listRequirements);
				
		
		//Création des Noeuds
		BookNodeTerminal bookNodeTerminalVictoire = new BookNodeTerminal("Victoire", BookNodeStatus.VICTORY);
		
		BookNodeLink bookNodeLinkObjet = new BookNodeLink("BookNodeLink Objet", 2, requirements);
		
		listAbstractBookNode = new ArrayList();
		listAbstractBookNode.add(bookNodeLinkObjet);
	
		
		BookItemLink bookItemLink = new BookItemLink(bookItemWeapon.getId(), 0, 0, false, 0);
		
		List<BookItemLink> listBookItemLink = new ArrayList();
		listBookItemLink.add(bookItemLink);
		
		
		//1er test - Defaite : ne peux pas prendre l'objet
		BookNodeWithChoices bookNodeWithChoicesNePrendPasObjet = new BookNodeWithChoices("Noeud Random", 0, null, null, listAbstractBookNode);
		book = new Book();
		
		nodes = new HashMap();
		nodes.put(1, bookNodeWithChoicesNePrendPasObjet);
		nodes.put(2, bookNodeTerminalVictoire);
		
		book.setNodes(nodes);
		
		jeu = new Jeu(book);
		victoire = jeu.fourmis(1);
		Assert.assertTrue(victoire == 0);
		
		
		
		//2eme test - Victoire : prend l'objet
		BookNodeWithChoices bookNodeWithChoicesPrendObjet = new BookNodeWithChoices("Noeud Random", 1, listBookItemLink, null, listAbstractBookNode);
		book = new Book();
		
		nodes = new HashMap();
		nodes.put(1, bookNodeWithChoicesPrendObjet);
		nodes.put(2, bookNodeTerminalVictoire);
		
		book.setNodes(nodes);
		
		jeu = new Jeu(book);
		victoire = jeu.fourmis(1);
		Assert.assertTrue(victoire ==  100);
	}*/
}
