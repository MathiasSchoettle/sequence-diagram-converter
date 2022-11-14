import sequenceDiagram.SequenceDiagramConverter;
import sequenceDiagram.SequenceDiagramImport;

public class Main {
    public static void main(String[] args) {
        var imported = SequenceDiagramImport.fromFile("lifelines.json");
        var automaton = new SequenceDiagramConverter(imported).toAutomaton();
        automaton.exportToGraphviz("aufgabe_vorlesung");
    }
}
