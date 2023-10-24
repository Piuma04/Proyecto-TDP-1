package Interfaces;
import java.util.Set;

import Entities.Candy;
import Entities.Empty;
import Entities.Glazed;
import Entities.Stripped;
import Entities.Wrapped;
import Logic.Block;
import Logic.Board;

public interface SpecialDestroy {
    public Set<Block> getSpecialDestroy(SpecialDestroy e, Board b);
    public Set<Block> getSpecialDestroyables(Candy c, Board b);
    public Set<Block> getSpecialDestroyables(Stripped c, Board b);
    public Set<Block> getSpecialDestroyables(Wrapped c, Board b);
    public Set<Block> getSpecialDestroyables(Glazed g, Board b);
    public Set<Block> getSpecialDestroyables(Empty e, Board b);
}
