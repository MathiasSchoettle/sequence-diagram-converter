import dea.Automaton;
import dea.State;

public class Main {

    public static void main(String[] args) {
        String[] labels = {"a", "b", "a", "b", "b"};
        var dea = new Automaton();

        dea.addState("1")
                .addState("2")
                .addState("3", State.StateType.FINAL)
                .addTransition("1", "2", "a")
                .addTransition("2", "2", "b")
                .addTransition("2", "3", "a")
                .addTransition("3", "1", "b");

        dea.process(labels);
        dea.printRun();
    }
}
