package dea;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

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

    public State getLast() {
        return states.get(states.size() - 1);
    }

    @SuppressWarnings("UnusedReturnValue")
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

    @SuppressWarnings("unused")
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

    @SuppressWarnings("unused")
    public void printRun() {
        log.forEach(System.out::println);
    }

    public void exportToGraphviz(String fileName) {
        var file = new File(System.getProperty("user.home"), fileName + ".gv.txt ");

        try(var os = new FileOutputStream(file)) {
            var writer = new BufferedWriter(new OutputStreamWriter(os));

            writer.write("digraph " + fileName + "{\n");
            writer.write("\tfontname=\"Helvetica,Arial,sans-serif\"\n");
            writer.write("\tnode [fontname=\"Helvetica,Arial,sans-serif\"]\n");
            writer.write("\tedge [fontname=\"Helvetica,Arial,sans-serif\"]\n");
            writer.write("\trankdir=LR;\n");

            String finiteStateIds = states.stream()
                    .filter(s -> s.type.equals(State.StateType.FINAL))
                    .map(s -> s.id)
                    .collect(Collectors.joining(" "));

            writer.write("\tnode [shape = doublecircle]; " + finiteStateIds + ";\n");
            writer.write("\tnode [shape = circle];\n");

            for (var t : transitions) {
                writer.write("\t" + t.from.id + " -> " + t.to.id + " [label = \"" + t.label + "\"];\n");
            }

            writer.write("}");
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
