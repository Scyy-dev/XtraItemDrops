package me.scyphers.xtraitemdrops.drop;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Drop<T> {

    @NotNull List<T> drop(DropContext context);

    @NotNull DropModifierList getModifiers();

}
