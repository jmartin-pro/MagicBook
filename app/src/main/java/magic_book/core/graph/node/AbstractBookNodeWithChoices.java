package magic_book.core.graph.node;

import java.util.ArrayList;
import java.util.List;
import magic_book.core.Book;
import magic_book.core.file.json.ItemLinkJson;
import magic_book.core.file.json.SectionJson;
import magic_book.core.item.BookItemLink;
import magic_book.core.graph.node_link.BookNodeLink;

public abstract class AbstractBookNodeWithChoices <T extends BookNodeLink> extends AbstractBookNode {
	
	private Integer nbItemsAPrendre;
	private List<BookItemLink> itemLinks;
	private List<BookItemLink> shopItemLinks;
	private List<T> choices;
	private boolean mustEat;
	private int hp;
	
	public AbstractBookNodeWithChoices(String text, Integer nbItemsAPrendre, List<BookItemLink> itemLinks, List<BookItemLink> shopItemLinks, List<T> choices){
		this(text, nbItemsAPrendre, itemLinks, shopItemLinks, choices, false, 0);
	}
	
	public AbstractBookNodeWithChoices(String text, Integer nbItemsAPrendre, List<BookItemLink> itemLinks, List<BookItemLink> shopItemLinks, List<T> choices, boolean mustEat, int hp){
		super(text);
		
		this.nbItemsAPrendre = nbItemsAPrendre;
		this.itemLinks = itemLinks;
		this.shopItemLinks = shopItemLinks;
		this.choices = choices;
		this.mustEat = mustEat;
		this.hp = hp;
		
		if(this.choices == null)
			this.choices = new ArrayList<>();
		
		if(this.itemLinks == null)
			this.itemLinks = new ArrayList<>();
		
		if(this.shopItemLinks == null)
			this.shopItemLinks = new ArrayList<>();
	}
	
	public String getItemDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		if(!itemLinks.isEmpty()) {
			buffer.append("\n");
			buffer.append("Les items suivants sont disponibles : \n");

			for(BookItemLink il : itemLinks) {
				buffer.append("\n");
				buffer.append(il.getDescription(book));
			}
			
			if(nbItemsAPrendre == -1) {
				buffer.append("\nVous pouvez prendre autant d'items que vous le voulez.\n");
			} else {
				buffer.append("\nVous pouvez prendre ");
				buffer.append(nbItemsAPrendre);
				buffer.append(" items.\n");
			}
		}
		
		return buffer.toString();
	}
	
	public String getShopDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		if(!shopItemLinks.isEmpty()) {
			buffer.append("\n");
			buffer.append("Les items suivants sont en vente : \n");

			for(BookItemLink il : shopItemLinks) {
				buffer.append("\n");
				buffer.append(il.getDescription(book));
			}
		}
		
		return buffer.toString();
	}
	
	public String getMiscellaneousDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		if(mustEat) {
			buffer.append("\n");
			buffer.append("Vous devez manger pour continuer.\n");
		}
		
		if(hp < 0) {
			buffer.append("\n");
			buffer.append("Vous venez de perdre ");
			buffer.append(Math.abs(hp));
			buffer.append(" HP.\n");
		} else if(hp > 0) {
			buffer.append("\n");
			buffer.append("Vous venez de gagner ");
			buffer.append(hp);
			buffer.append(" HP.\n");
		}	
		
		return buffer.toString();
	}

	@Override
	public String getDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(super.getDescription(book));
		
		buffer.append(getItemDescription(book));
		
		buffer.append(getShopDescription(book));
		
		buffer.append(getMiscellaneousDescription(book));
		
		if(!choices.isEmpty()) {
			buffer.append("\n");
			buffer.append("Que souhaitez vous faire ?\n\n");


			for(int i = 0 ; i < choices.size() ; i++) {
				buffer.append(choices.get(i).getDescription(book));
				
				if(i < choices.size() - 1) {
					buffer.append("\n");
				}
			}
		}
		
		return buffer.toString();
	}

	@Override
	public SectionJson toJson() {
		SectionJson sectionJson = super.toJson();
		
		if(hp != 0)
			sectionJson.setHp(hp);

		if(isMustEat())
			sectionJson.setMustEat(true);

		if(!itemLinks.isEmpty())
			sectionJson.setAmountToPick(nbItemsAPrendre);

		if(!shopItemLinks.isEmpty()) {
			sectionJson.setShop(new ArrayList<>());

			for(BookItemLink itemLink : shopItemLinks) {
				sectionJson.getShop().add(itemLink.toJson());
			}
		}

		if(!itemLinks.isEmpty()) {
			sectionJson.setItems(new ArrayList<>());

			for(BookItemLink itemLink : itemLinks) {
				sectionJson.getItems().add(itemLink.toJson());
			}
		}

		sectionJson.setChoices(new ArrayList<>());
		for(BookNodeLink nodeLink : choices) {
			sectionJson.getChoices().add(nodeLink.toJson());
		}
		
		return sectionJson;
	}

	@Override
	public void fromJson(SectionJson json) {
		super.fromJson(json);
		
		if(json.getAmountToPick() != null)
			nbItemsAPrendre = json.getAmountToPick();
		else
			nbItemsAPrendre = -1;
		
		if(json.getMustEat() == null) 
			mustEat = false;
		else 
			mustEat = json.getMustEat();
		
		if(json.getHp()== null) 
			hp = 0;
		else 
			hp = json.getHp();
		
		if(json.getItems() != null) {
			for(ItemLinkJson itemLinkJson : json.getItems()) {
				BookItemLink bookItemsLink = new BookItemLink();
				bookItemsLink.fromJson(itemLinkJson);

				itemLinks.add(bookItemsLink);
			}
		}
		
		if(json.getShop() != null) {
			for(ItemLinkJson itemLinkJson : json.getShop()) {
				BookItemLink bookItemsLink = new BookItemLink();
				bookItemsLink.fromJson(itemLinkJson);

				shopItemLinks.add(bookItemsLink);
			}
		}
	}
	
	public void addChoice(T nodeLink) {
		this.choices.add(nodeLink);
	}

	public void removeChoice(T nodeLink) {
		if(this.choices.contains(nodeLink))
			choices.remove(nodeLink);
	}
	
	@Override
	public List<T> getChoices() {
		return choices;
	}
	
	public void addChoices(T newChoices){
		this.choices.add(newChoices);
	}
	
	public void addShopItemLink(BookItemLink newShopItemLink){
		this.shopItemLinks.add(newShopItemLink);
	}

	public void addItemLink(BookItemLink newItemLink){
		this.itemLinks.add(newItemLink);
	}

	public Integer getNbItemsAPrendre() {
		return nbItemsAPrendre;
	}

	public void setNbItemsAPrendre(Integer nbItemsAPrendre) {
		this.nbItemsAPrendre = nbItemsAPrendre;
	}

	public List<BookItemLink> getItemLinks() {
		return itemLinks;
	}

	public void setItemLinks(List<BookItemLink> itemLinks) {
		this.itemLinks = itemLinks;
	}

	public List<BookItemLink> getShopItemLinks() {
		return shopItemLinks;
	}

	public void setShopItemLinks(List<BookItemLink> shopItemLinks) {
		this.shopItemLinks = shopItemLinks;
	}

	public boolean isMustEat() {
		return mustEat;
	}

	public void setMustEat(boolean mustEat) {
		this.mustEat = mustEat;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}
	
}
