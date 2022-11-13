package dea;

public class State {
    public String id;
    public StateType type;

    public State(String id, StateType type) {
        this.id = id;
        this.type = type;
    }

    public enum StateType {
        STANDARD, STARTING, FINAL, TRAP
    }
}
