package me.scyphers.xtraitemdrops.drop;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public abstract class SingleDrop<T> implements Drop<T> {

    private final double chance;

    private final DropModifierList modifiers;

    public SingleDrop(double chance) {
        this.chance = chance;
        this.modifiers = new DropModifierList();
    }

    public SingleDrop(double chance, T drop, Set<DropModifier> modifiers) {
        this.chance = chance;
        this.modifiers = new DropModifierList(modifiers);
    }

    public double getChance() {
        return chance;
    }

    @Override
    public @NotNull List<T> drop(DropContext context) {
        double dropChance = modifiers.evaluateModifiers(chance, context);
        double roll = context.random().nextDouble();
        return roll <= chance ? Collections.singletonList(getDrop()) : Collections.emptyList();
    }

    public abstract T getDrop();

    public @NotNull DropModifierList getModifiers() {
        return modifiers;
    }

}
