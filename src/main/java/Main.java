import sequenceDiagram.SequenceDiagramConverter;
import sequenceDiagram.SequenceDiagramImport;

public class Main {

    public static final String FILE_NAME = "bsp_3";
    public static void main(String[] args) {
        var imported = SequenceDiagramImport.fromFile(FILE_NAME + ".json");
        var automaton = new SequenceDiagramConverter(imported).toAutomaton();
        automaton.exportToGraphviz(FILE_NAME);
    }
}
