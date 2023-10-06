package Entities;

import GUI.GraphicalEntity;
import Interfaces.Equivalent;
import Interfaces.LogicEntity;
import Interfaces.VisualEntity;
import Logic.VisualEntityDummy;

/**
 * Abstract class representing modifiers
 */
public abstract class Modifier extends VisualEntityDummy implements Equivalent, VisualEntity {

    protected GraphicalEntity gEntity;

    public abstract void destroy();
    @Override public boolean isEquivalent(Entity e) { return false; }
    @Override public boolean equals(Candy c) { return false; }
    @Override public boolean equals(Glazed g) { return false; }
    @Override public boolean equals(Wrapped w) { return false; }
    @Override public boolean equals(Stripped s) { return false; }
    @Override public boolean equals(Empty e) { return false; }

}
