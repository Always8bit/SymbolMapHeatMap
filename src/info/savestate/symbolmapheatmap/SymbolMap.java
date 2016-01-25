/*
 * Written and Designed by Joseph El-Khouri
 * ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
 *      email: joseph(dot)elkhouri(at)gmail(dot)com
 *     github: https://github.com/Always8bit/
 * soundcloud: https://soundcloud.com/savestate
 *                ^--- shameless plug ---^
 */

package info.savestate.symbolmapheatmap;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Joseph El-Khouri
 */
public class SymbolMap {
    private final ArrayList<Symbol> symbols;
    
    private SymbolMap() {
        symbols = new ArrayList<>();
    }
    
    public ArrayList<Symbol> getSymbols() {
        return symbols;
    }
    
    public static SymbolMap symbolMapBuilder(ArrayList<String> lines) {
        SymbolMap sm = new SymbolMap();
        for (String line : lines) {
            if (line.equals(".text") || line.isEmpty())
                continue;
            sm.symbols.add(Symbol.symbolBuilder(line));
        }
        Collections.sort(sm.symbols);
        return sm;
    }
    
    public int getCountOfSymbolsInRange(int start, int end) {
        int count = 0;
        for (Symbol symbol : symbols) {
            if (symbol.name.startsWith("zz") && (!symbol.name.startsWith("zz_blr")))
                continue;
            if (unsignedBetween(symbol.address, start, end) ||
                unsignedBetween(symbol.address+symbol.size, start, end) ||
                ((Integer.compareUnsigned(symbol.address, start) <= 0) && 
                 (Integer.compareUnsigned(symbol.address+symbol.size, end) > 0))) {
                count++;
            }
        }
        return count;
    }
    
    public int getCountOfNamedSymbolsInSymbolRange(int start, int end) {
        if (start < 0) start = 0;
        if (end > symbols.size()) end = symbols.size()-1;
        int count = 0;
        for (int i=start; i<end; i++) {
            Symbol s = symbols.get(i);
            if (s.name.startsWith("zz") && (!s.name.startsWith("zz_blr")))
                continue;
            count++;
        }
        return count;
    }
    
    public int getMemoryEnd() {
        return symbols.get(symbols.size()-1).address+symbols.get(symbols.size()-1).size;
    }
    
    private boolean unsignedBetween(int value, int start, int end) {
        return (Integer.compareUnsigned(value, start) >= 0) && 
               (Integer.compareUnsigned(value, end) < 0);
    }
}
