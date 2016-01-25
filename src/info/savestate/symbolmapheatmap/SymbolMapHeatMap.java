/*
 * Written and Designed by Joseph El-Khouri
 * ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
 *      email: joseph(dot)elkhouri(at)gmail(dot)com
 *     github: https://github.com/Always8bit/
 * soundcloud: https://soundcloud.com/savestate
 *                ^--- shameless plug ---^
 */

package info.savestate.symbolmapheatmap;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Joseph El-Khouri
 */
public class SymbolMapHeatMap {
    
    private final BufferedImage memoryMap;
    private final BufferedImage symbolMap;
    private final SymbolMap sm;

    public SymbolMapHeatMap(int width, SymbolMap sm) {
        memoryMap = new BufferedImage(width, 256, BufferedImage.TYPE_INT_RGB);
        symbolMap = new BufferedImage(width, 256, BufferedImage.TYPE_INT_RGB);
        this.sm = sm;
    }
    
    public void drawSymbolMap() {
        int range = sm.getSymbols().size();
        float sectionSize = range / symbolMap.getWidth();
        int intSectionSize = range / symbolMap.getWidth();
        System.out.println("Total Symbols: " + range);
        System.out.println("Effective pixel range: " + intSectionSize + " symbols");
        ArrayList<Integer> symbolCounts = new ArrayList<>();
        for (int i=0; i<symbolMap.getWidth(); i++) {
            int intSectionBegin = (int)(sectionSize * ((float)i));
            int intSectionEnd   = (int)(sectionSize * ((float)(i+1)));
            symbolCounts.add(sm.getCountOfNamedSymbolsInSymbolRange(intSectionBegin, intSectionEnd));
        }
        int max = maxValue(symbolCounts);
        double scalar = 255.0/((double)max);
        ArrayList<Double> doubleSymbolCounts = doubleArrayBuilder(symbolCounts);
        for (int i=0; i<symbolMap.getWidth(); i++) {
            int brightness = (int)(doubleSymbolCounts.get(i) * scalar);
            for (int j=0; j<symbolMap.getHeight(); j++) {
                int r = brightness;
                int g = brightness;
                int b = brightness;
                int rgb = r;
                rgb = (rgb << 8) + g;
                rgb = (rgb << 8) + b;
                symbolMap.setRGB(i, j, rgb);
            }
        }
    }
    
    public void drawMemoryMap() {
        int offset = Integer.parseUnsignedInt("80000000", 16);
        int range = sm.getMemoryEnd() - offset;
        float sectionSize = range / memoryMap.getWidth();
        int intSectionSize = range / memoryMap.getWidth();
        System.out.println("Total Memory Size: 0x" + Integer.toUnsignedString(range, 16));
        System.out.println("Effective pixel range: 0x" + Integer.toString(intSectionSize, 16) + " (" + (intSectionSize/4) + " instructions)");
        ArrayList<Integer> symbolCounts = new ArrayList<>();
        for (int i=0; i<memoryMap.getWidth(); i++) {
            float sectionBegin = sectionSize * ((float)(i));
            float sectionEnd   = sectionSize * ((float)(i+1));
            int intSectionBegin = (int)sectionBegin + offset;
            int intSectionEnd   = (int)sectionEnd + offset;
            symbolCounts.add(sm.getCountOfSymbolsInRange(intSectionBegin, intSectionEnd));
        }
        int max = maxValue(symbolCounts);
        double scalar = 255.0/((double)max);
        ArrayList<Double> doubleSymbolCounts = doubleArrayBuilder(symbolCounts);
        for (int i=0; i<memoryMap.getWidth(); i++) {
            int brightness = (int)(doubleSymbolCounts.get(i) * scalar);
            for (int j=0; j<memoryMap.getHeight(); j++) {
                int r = brightness;
                int g = brightness;
                int b = brightness;
                int rgb = r;
                rgb = (rgb << 8) + g;
                rgb = (rgb << 8) + b;
                memoryMap.setRGB(i, j, rgb);
            }
        }
    }
    
    public BufferedImage getMemoryMapImage() {
        return memoryMap;
    }
    
    public BufferedImage getSymbolMapImage() {
        return symbolMap;
    }
    
    
    
    private ArrayList<Double> doubleArrayBuilder (ArrayList<Integer> array) {
        ArrayList<Double> doubleArray = new ArrayList<>();
        for (int value : array) {
            doubleArray.add((double)value);
        }
        return doubleArray;
    }
    
    private int maxValue(ArrayList<Integer> array) {
        int max = 0;
        for (int value : array) {
            if (value > max) max = value;
        }
        return max;
    }
}
