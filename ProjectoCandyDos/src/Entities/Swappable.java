package Entities;

public interface Swappable {
    public boolean isSwappable(Entity e);

    public boolean canReceive(Candy c);

    public boolean canReceive(Glazed g);

    public boolean canReceive(Stripped s);
    
    public boolean canReceive(Wrapped w);
}