package magic_book.core.file.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import magic_book.core.graph.node.BookNodeStatus;

/**
 * Désérializer pour GSON afin qu'il puisse correctement lire les status des noeuds
 */
public class BookNodeStatusDeserializer implements JsonDeserializer<BookNodeStatus> {

	@Override
	public BookNodeStatus deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
		for(BookNodeStatus nodeType : BookNodeStatus.values()) {					
			if(nodeType.name().equals(je.getAsString())) {
				return nodeType;
			}
		}

		return null;

	}
}
