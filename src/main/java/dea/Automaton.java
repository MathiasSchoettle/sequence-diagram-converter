package dea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Automaton {
    public List<State> states = new ArrayList<>();
    public List<Transition> transitions = new ArrayList<>();
    public State current;
    private final State TRAP = new State("", State.StateType.TRAP);

    private final List<String> log = new ArrayList<>();

    public Automaton addState(String id) {
        return addState(id, State.StateType.STANDARD);
    }
    public Automaton addState(String id, State.StateType type) {
        states.add(new State(id, type));

        if (states.size() == 1) {
            current = states.get(0);
            current.type = State.StateType.STARTING;
        }

        return this;
    }

    public Automaton addTransition(String fromId, String toId, String label) {
        Optional<State> fromOptional = states.stream().filter(s -> s.id.equals(fromId)).findFirst();
        Optional<State> toOptional = states.stream().filter(s -> s.id.equals(toId)).findFirst();

        if (fromOptional.isEmpty() || toOptional.isEmpty() || label.isBlank()) {
            System.out.println("Invalid transition: " + fromOptional + ", " + toOptional + ", " + label);
            return this;
        }

        State from = fromOptional.get();
        State to = toOptional.get();

        Transition transition = new Transition(from, to, label);
        transitions.add(transition);

        return this;
    }

    public void process(String[] labels) {
        Arrays.stream(labels).forEach(this::next);
    }

    public void next(String label) {
        if (current.type.equals(State.StateType.TRAP)) {
            return;
        }

        var transitionOptional = transitions.stream()
                .filter(t -> t.from.id.equals(current.id))
                .filter(t -> t.label.equals(label))
                .findFirst();

        String logString = current.id + " -> ";

        if (transitionOptional.isEmpty()) {
            current = TRAP;
            logString += "Trap";
        }
        else {
            current = transitionOptional.get().to;
            logString += current.id;
        }

        log.add(logString);
    }

    public void printRun() {
        log.forEach(System.out::println);
    }

    public void exportToGraphviz() {
        // TODO implement pls
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
