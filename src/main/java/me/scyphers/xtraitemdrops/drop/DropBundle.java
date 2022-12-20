package me.scyphers.xtraitemdrops.drop;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DropBundle<T> implements Drop<T> {

    private final double chance;

    private final DropModifierList modifierList;

    private final List<Drop<T>> dropList;

    public DropBundle(double chance, DropModifierList modifierList) {
        this(chance, modifierList, new ArrayList<>());
    }

    public DropBundle(double chance, DropModifierList modifierList, List<Drop<T>> dropList) {
        this.chance = chance;
        this.modifierList = modifierList;
        this.dropList = dropList;
    }

    public List<Drop<T>> getDrops() {
        return dropList;
    }

    @Override
    public @NotNull List<T> drop(DropContext context) {
        double dropChance = modifierList.evaluateModifiers(chance, context);
        double roll = context.random().nextDouble();
        if (roll > dropChance) return Collections.emptyList();
        List<T> resultDrops = new LinkedList<>();
        for (Drop<T> drop : dropList) {
            List<T> drops = drop.drop(context);
            if (drops.size() != 0) resultDrops.addAll(drops);
        }
        return resultDrops;
    }

    @Override
    public @NotNull DropModifierList getModifiers() {
        return modifierList;
    }
}
