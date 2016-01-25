/**
 *
 * @author Joseph
 */

import info.savestate.symbolmapheatmap.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        Scanner s = new Scanner(new File(args[0]));
        ArrayList<String> lines = new ArrayList<>();
        while (s.hasNextLine()){
            lines.add(s.nextLine());
        }
        SymbolMap sm = SymbolMap.symbolMapBuilder(lines);
        SymbolMapHeatMap smhm = new SymbolMapHeatMap(Integer.parseInt(args[1]), sm);
        smhm.drawMemoryMap();
        smhm.drawSymbolMap();
        ImageIO.write(smhm.getMemoryMapImage(), "png", new File("memorymap.png"));
        ImageIO.write(smhm.getSymbolMapImage(), "png", new File("symbolmap.png"));
    }
    
}
