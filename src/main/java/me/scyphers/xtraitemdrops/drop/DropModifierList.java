package me.scyphers.xtraitemdrops.drop;

import java.util.*;
import java.util.function.Predicate;

public class DropModifierList {

    // Tree map so priority can be evaluated in order
    private final Map<Integer, List<DropModifier>> modifiers = new TreeMap<>();

    public DropModifierList() {

    }

    public DropModifierList(Collection<DropModifier> modifiers) {
        this.addModifiers(modifiers);
    }

    public double evaluateModifiers(double initial, DropContext context) {

        double value = initial;

        // Lowest -> highest due to tree map
        for (int priority : modifiers.keySet()) {
            List<DropModifier> modifierList = modifiers.get(priority);
            for (DropModifier modifier : modifierList) {
                value = modifier.modify(value, context);
                if (value == 0 && modifier.isTerminal()) return value;
            }
        }

        return value;
    }

    public void addModifier(DropModifier modifier) {
        List<DropModifier> priorityModifiers = modifiers.getOrDefault(modifier.getPriority(), new LinkedList<>());
        priorityModifiers.add(modifier);
        modifiers.put(modifier.getPriority(), priorityModifiers);

        // Sort
    }

    public void addModifiers(Collection<DropModifier> modifiers) {
        modifiers.forEach(this::addModifier);
    }

    public void removeModifiers(int priority) {
        modifiers.remove(priority);
    }

    public void removeModifiers(DropModifier modifier) {
        this.removeModifiers(modifier::equals);
    }

    public void removeModifiers(String modifierName) {
        this.removeModifiers(modifier -> modifier.getName().equals(modifierName));
    }

    public void removeModifiers(Predicate<DropModifier> removePredicate) {
        Map<Integer, List<DropModifier>> changedModifiers = new HashMap<>();
        for (int priority : modifiers.keySet()) {
            List<DropModifier> modifierList = modifiers.get(priority);
            if (modifierList.removeIf(removePredicate)) {
                changedModifiers.put(priority, modifierList);
            }
        }

        if (changedModifiers.isEmpty()) return;
        for (int priority : modifiers.keySet()) {
            List<DropModifier> modifierList = changedModifiers.get(priority);
            if (modifierList.size() == 0) modifiers.remove(priority);
            else modifiers.put(priority, modifierList);
        }

    }

}
