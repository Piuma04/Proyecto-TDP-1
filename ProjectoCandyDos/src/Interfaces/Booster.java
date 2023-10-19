package Interfaces;

import Entities.Candy;
import Entities.Empty;
import Entities.Entity;
import Entities.Glazed;
import Entities.Stripped;
import Entities.Wrapped;

public interface Booster {
    public boolean bothBooster(Entity e);
    public boolean isBooster(Candy c);
    public boolean isBooster(Stripped c);
    public boolean isBooster(Wrapped c);
    public boolean isBooster(Glazed c);
    public boolean isBooster(Empty e);
}
