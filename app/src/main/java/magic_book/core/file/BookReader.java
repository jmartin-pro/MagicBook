package magic_book.core.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import magic_book.core.Book;
import magic_book.core.exception.BookFileException;
import magic_book.core.file.deserializer.BookNodeStatusDeserializer;
import magic_book.core.file.json.BookJson;
import magic_book.core.file.json.CharacterCreationJson;
import magic_book.core.file.json.CharacterJson;
import magic_book.core.file.json.ItemJson;
import magic_book.core.file.json.ItemType;
import magic_book.core.file.json.SectionJson;
import magic_book.core.file.json.SkillJson;
import magic_book.core.file.json.TypeJson;
import magic_book.core.game.BookCharacter;
import magic_book.core.game.BookSkill;
import magic_book.core.game.character_creation.AbstractCharacterCreation;
import magic_book.core.game.character_creation.CharacterCreationItem;
import magic_book.core.game.character_creation.CharacterCreationShop;
import magic_book.core.game.character_creation.CharacterCreationSkill;
import magic_book.core.game.character_creation.CharacterCreationText;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.graph.node.BookNodeStatus;
import magic_book.core.graph.node.BookNodeTerminal;
import magic_book.core.graph.node.BookNodeWithChoices;
import magic_book.core.graph.node.BookNodeWithRandomChoices;
import magic_book.core.item.BookItem;
import magic_book.core.item.BookItemDefense;
import magic_book.core.item.BookItemHealing;
import magic_book.core.item.BookItemMoney;
import magic_book.core.item.BookItemWeapon;

/**
 * Permet d'un livre au format JSON, idéalement, cette classe ne devrait plus contenir les méthode "getEvery" car la classe Book devrait implémenter JsonExportable.
 */
public class BookReader {
	
	private HashMap<String, BookItem> items;
	private HashMap<String, BookCharacter> characters;
	private HashMap<Integer, AbstractBookNode> nodes;
	private HashMap<String, BookSkill> skills;
	private List<AbstractCharacterCreation> characterCreations;
	
	/**
	 * Lecture du livre JSON
	 * @param path Le fichier à lire
	 * @return Le livre lu
	 * @throws FileNotFoundException Exception si le fichier n'existe pas
	 * @throws BookFileException Exception si le livre est invalide
	 */
	public Book read(String path) throws FileNotFoundException, BookFileException {		
		BookJson bookJson = readFileWithGson(path);
		
		skills = getEverySkills(bookJson);
		items = getEveryItems(bookJson);
		characters = getEveryCharacters(bookJson);
		characterCreations = getCharacterCreations(bookJson);
		nodes = getEveryNodes(bookJson);
		
		return new Book(bookJson.getPrelude(), nodes, items, characters, skills, characterCreations);
	}

	/**
	 * Lecture du fichier JSON avec GSON
	 * @param path Le fichier à lire
	 * @return Le livre au format JSON
	 * @throws FileNotFoundException Exception si le fichier n'existe pas 
	 */
	private BookJson readFileWithGson(String path) throws FileNotFoundException {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(BookNodeStatus.class, new BookNodeStatusDeserializer());
		
		Gson gson = builder.create(); 
		BufferedReader bufferedReader = new BufferedReader(new FileReader(path)); 
		
		return gson.fromJson(bufferedReader, BookJson.class);
	}

	/**
	 * Lecture des skills
	 * @param bookJson Le livre au format JSON
	 * @return Les différents skills
	 */
	private HashMap<String, BookSkill> getEverySkills(BookJson bookJson) {
		HashMap<String, BookSkill> skills = new HashMap<>();
		
		for(SkillJson s : bookJson.getSetup().getSkills()) {
			BookSkill skill = new BookSkill();
			skill.fromJson(s);
			
			skills.put(skill.getId(), skill);
		}
			
		return skills;
	}

	/**
	 * Lecture des items
	 * @param bookJson Le livre au format JSON
	 * @return La liste des items
	 * @throws BookFileException Exception si un item est invalide
	 */
	private HashMap<String, BookItem> getEveryItems(BookJson bookJson) throws BookFileException {
		HashMap<String, BookItem> items = new HashMap<>();
		
		for(ItemJson itemJson : bookJson.getSetup().getItems()) {
			BookItem item = null;
			
			if(itemJson.getItemType() == ItemType.DEFENSE) {
				item = new BookItemDefense();
			} else if(itemJson.getItemType() == ItemType.WEAPON) {
				item = new BookItemWeapon();
			} else if(itemJson.getItemType() == ItemType.MONEY) {
				item = new BookItemMoney();
			} else if(itemJson.getItemType() == ItemType.HEALING) {
				item = new BookItemHealing();				
			} else if(itemJson.getItemType() == ItemType.KEY_ITEM) {
				item = new BookItem();
			} else {
				throw new BookFileException("L'item avec le type " + itemJson.getItemType() + " est invalide");
			}
			
			item.fromJson(itemJson);
			
			items.put(item.getId(), item);
		}
			
		return items;
	}
	
	/**
	 * Lecture des personnages du livre
	 * @param bookJson Le livre au format JSON
	 * @return Les personnages du livre
	 */
	private HashMap<String, BookCharacter> getEveryCharacters(BookJson bookJson) {
		HashMap<String, BookCharacter> characters = new HashMap<>();
		
		for(CharacterJson c : bookJson.getSetup().getCharacters()) {
			BookCharacter character = new BookCharacter();
			character.fromJson(c);
			
			characters.put(character.getId(), character);
		}
			
		return characters;
	}
	
	/**
	 * Lecture des étapes de la création du personnage
	 * @param bookJson Le livre au format JSON
	 * @return La liste des étapes de la création du personnage
	 * @throws BookFileException Exception si une étape est invalide
	 */
	private List<AbstractCharacterCreation> getCharacterCreations(BookJson bookJson) throws BookFileException {
		List<AbstractCharacterCreation> characterCreations = new ArrayList<>();
		
		for(CharacterCreationJson characterCreationJson : bookJson.getSetup().getCharacterCreation()) {
			AbstractCharacterCreation characterCreation = null;
			
			if(characterCreationJson.getType() == null) {
				characterCreation = new CharacterCreationText();
			} else if(characterCreationJson.getType() == TypeJson.ITEM) {
				characterCreation = new CharacterCreationItem();
			} else if(characterCreationJson.getType() == TypeJson.SHOP) {
				characterCreation = new CharacterCreationShop();
			} else if(characterCreationJson.getType() == TypeJson.SKILL) {
				characterCreation = new CharacterCreationSkill();
			} else {
				throw new BookFileException("Le type " + characterCreationJson.getType() + " est invalide pour la conception d'un personnage");
			}
			
			characterCreation.fromJson(characterCreationJson);
			
			characterCreations.add(characterCreation);
		}
		
		return characterCreations;
	}
	
	/**
	 * Lecture des noeuds
	 * @param bookJson Le livre au format JSON
	 * @return La liste des noeuds
	 * @throws BookFileException Exception si un noeud du livre est invalide
	 */
	private HashMap<Integer, AbstractBookNode> getEveryNodes(BookJson bookJson) throws BookFileException {
		HashMap<Integer, AbstractBookNode> nodes = new HashMap<>();
		for(Map.Entry<Integer, SectionJson> entry : bookJson.getSections().entrySet()) {
			SectionJson sectionJson = entry.getValue();
			AbstractBookNode node = null;
			
			if(sectionJson.getCombat() != null) {
				node = new BookNodeCombat();
			} else if(sectionJson.isRandomPick() != null && sectionJson.isRandomPick()) {
				node = new BookNodeWithRandomChoices();
			} else if(sectionJson.getEndType() != null) {
				node = new BookNodeTerminal();
			} else if(sectionJson.getChoices() != null && !sectionJson.getChoices().isEmpty()){
				node = new BookNodeWithChoices();
			} else {
				throw new BookFileException("Le noeud numéro " + entry.getKey() + " est invalide");
			}
			
			node.fromJson(sectionJson);
			
			nodes.put(entry.getKey(), node);
		}
		
		return nodes;
	}

}
