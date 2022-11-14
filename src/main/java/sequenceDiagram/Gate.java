package sequenceDiagram;

import java.util.HashSet;
import java.util.Set;

public class Gate {
    public String name;
    /**
     * The gate above regarding the lifeline. Null if this is the first gate on the line.
     */
    public Gate before;
    /**
     * Gates which sends a message to this gate. Empty if no such gate exists.
     */
    public Set<Gate> messagesFrom = new HashSet<>();
    public Set<Gate> lesser = new HashSet<>();

    public Gate(String name) {
        this.name = name;
    }

    public void setLesserGates() {
        this.lesser = getLesserGates();
    }

    private Set<Gate> getLesserGates() {
        Set<Gate> gates = new HashSet<>();

        if (before != null) {
            gates.add(before);
            gates.addAll(before.getLesserGates());
        }

        if (!messagesFrom.isEmpty()) {
            for (Gate gate : messagesFrom) {
                gates.add(gate);
                gates.addAll(gate.getLesserGates());
            }
        }

        return gates;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !this.getClass().equals(obj.getClass())) {
            return false;
        }

        Gate other = (Gate) obj;
        return this.name.equals(other.name);
    }
}
