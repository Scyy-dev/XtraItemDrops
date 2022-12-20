package me.scyphers.xtraitemdrops.drop;

public abstract class DropModifier {

    private final String name;

    private final int priority;

    private final boolean terminal;

    public DropModifier(String name, int priority) {
        this(name, priority, false);
    }

    public DropModifier(String name, int priority, boolean terminal) {
        this.name = name;
        this.priority = priority;
        this.terminal = terminal;
    }

    public abstract double modify(double current, DropContext context);

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isTerminal() {
        return terminal;
    }
}
