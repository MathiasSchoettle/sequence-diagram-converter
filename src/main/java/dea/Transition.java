package dea;

public class Transition {
    public State from;
    public State to;
    public String label;

    public Transition(State from, State to, String label) {
        this.from = from;
        this.to = to;
        this.label = label;
    }
}
