/*
 * Written and Designed by Joseph El-Khouri
 * ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
 *      email: joseph(dot)elkhouri(at)gmail(dot)com
 *     github: https://github.com/Always8bit/
 * soundcloud: https://soundcloud.com/savestate
 *                ^--- shameless plug ---^
 */

package info.savestate.symbolmapheatmap;

/**
 *
 * @author Joseph El-Khouri
 */
public class Symbol implements Comparable<Symbol>{
    public int address;
    public int size;
    public String name;
    
    private Symbol() {
        address = -1;
        size = -1;
        name = "";
    }
    
    public static Symbol symbolBuilder(String raw) {
        //80003130 000000c4 80003130 0 ac_memset
        Symbol symbol = new Symbol();
        String[] rawArray = raw.split("\\s+");
        symbol.address = Integer.parseUnsignedInt(rawArray[0], 16);
        symbol.size = Integer.parseUnsignedInt(rawArray[1], 16);
        symbol.name = rawArray[4];
        return symbol;
    }

    @Override
    public int compareTo(Symbol o) {
        return (Integer.compareUnsigned(this.address, o.address));
    }
    
}
