package Entities;

import Interfaces.Equivalent;
import Logic.VisualEntityDummy;

public abstract class Modifier extends VisualEntityDummy implements Equivalent {

    public abstract void destroy();
    @Override public boolean isEquivalent(Equivalent e) { return false; }
    @Override public boolean isEqual(Candy c)        { return false; }
    @Override public boolean isEqual(Glazed g)       { return false; }
    @Override public boolean isEqual(Wrapped w)      { return false; }
    @Override public boolean isEqual(Stripped s)     { return false; }
    @Override public boolean isEqual(Empty e)        { return false; }
    @Override public boolean isEqual(Jelly j)        { return false; }
    @Override public boolean isEqual(MegaStripped m) { return false; }
    @Override public boolean isEqual(Bomb b)         { return false; }
}
