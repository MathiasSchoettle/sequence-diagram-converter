package sequenceDiagram;

import dea.Automaton;
import dea.State;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class SequenceDiagramConverter {
    private final SequenceDiagramImport sequenceDiagram;
    private final Automaton automaton;
    private int currentState = 1;
    private Set<Gate> gates;
    private final String STATE_PREFIX = "s";

    public SequenceDiagramConverter(SequenceDiagramImport sequenceDiagram) {
        this.sequenceDiagram = sequenceDiagram;
        this.automaton = new Automaton();
    }

    public Automaton toAutomaton() {
        gates = sequenceDiagram.getGates();
        automaton.addState(STATE_PREFIX + "0");

        createState(automaton.getLast(), new HashSet<>());

        return automaton;
    }

    private void createState(State previous, Set<Gate> finishedGates) {
        Set<Gate> minimalGates = gates.stream()
                .filter(gate -> !finishedGates.contains(gate) && finishedGates.containsAll(gate.lesser))
                .collect(Collectors.toSet());

        for (Gate gate : minimalGates) {
            String stateId = STATE_PREFIX + currentState++;
            automaton.addState(stateId)
                    .addTransition(previous.id, stateId, gate.name);

            State last = automaton.getLast();

            if (finishedGates.size() == gates.size() - 1) {
                last.type = State.StateType.FINAL;
            }
            else {
                Set<Gate> finished = new HashSet<>(finishedGates);
                finished.add(gate);
                createState(last, finished);
            }
        }
    }
}
