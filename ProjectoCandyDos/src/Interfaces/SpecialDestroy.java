package Interfaces;

import Entities.Candy;
import Entities.Empty;
import Entities.Entity;
import Entities.Glazed;
import Entities.Stripped;
import Entities.Wrapped;

public interface SpecialDestroy {
    public boolean isSpecialSwap(SpecialDestroy e);
    public boolean hasSpecialExplosion(Candy c);
    public boolean hasSpecialExplosion(Stripped s);
    public boolean hasSpecialExplosion(Wrapped w);
    public boolean hasSpecialExplosion(Glazed g);
    public boolean hasSpecialExplosion(Empty e);
}
