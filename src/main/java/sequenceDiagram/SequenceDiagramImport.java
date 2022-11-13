package sequenceDiagram;

import java.util.List;

/**
 * TODO
 * String json = Files.readString(Paths.get(Objects.requireNonNull(Main.class.getResource("lifelines.json")).toURI()));
 * SequenceDiagramImport imported = new ObjectMapper().readValue(json, SequenceDiagramImport.class);
 */
public class SequenceDiagramImport {
    public List<Lifeline> lifelines;
    public List<Message> messages;
}
