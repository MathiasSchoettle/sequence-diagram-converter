package sequenceDiagram;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class SequenceDiagramImport {
    public List<Lifeline> lifelines;
    public List<Message> messages;

    public Set<Gate> getGates() {
        Set<Gate> gates = new HashSet<>();

        lifelines.stream().filter(line -> !line.gates.isEmpty()).forEach(line -> {

            Gate previous = new Gate(line.gates.get(0));
            gates.add(previous);

            for (int i = 1; i < line.gates.size(); ++i) {
                Gate gate = new Gate(line.gates.get(i));
                gate.before = previous;
                gates.add(gate);

                previous = gate;
            }
        });

        for (var message : messages) {
            Gate source = gates.stream().filter(gate -> gate.name.equals(message.source)).findFirst().orElseThrow();
            Gate target = gates.stream().filter(gate -> gate.name.equals(message.target)).findFirst().orElseThrow();
            target.messagesFrom.add(source);
        }

        gates.forEach(Gate::setLesserGates);

        return gates;
    }

    public static SequenceDiagramImport fromFile(String fileName) {
        try {
            String json = Files.readString(Paths.get(Objects.requireNonNull(SequenceDiagramImport.class.getClassLoader().getResource(fileName)).toURI()));
            return new ObjectMapper().readValue(json, SequenceDiagramImport.class);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
