import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Gui extends JFrame
{
    private JPanel contentPanel, tileMap, validMap, hbStartMap, hbEndMap, optionsPanel, hammerBroOptionsPanel, mapPanel, checkBoxPanel, directionPanel, rightPanel, leftPanel, downPanel, upPanel, maps;
    private JButton increaseX, decreaseX, increaseY, decreaseY;
    private JTextField xPosition, yPosition;
    private JLabel hb1Start, hb2Start, hb3Start, hb1End, hb2End, hb3End;
    private JCheckBox[] completedTiles;
    
    private JComboBox hammerBroMenu, worldMenu;
    
    private Bro[] bros = {
        new Bro("HB1 Start", 6, 1), new Bro("HB2 Start", 9, 2), new Bro("HB3 Start", 11, 2),
        new Bro("HB1 End", 6, 1), new Bro("HB2 End", 9, 2), new Bro("HB3 End", 11, 2)
    };
    
    private class Bro
    {
        private int xPos, yPos;
        private String label;
        
        Bro(String label)
        {
            this.label = label;
            this.xPos = 0;
            this.yPos = 0;
        }
        
        Bro(String label, int xPos, int yPos)
        {
            this.label = label;
            this.xPos = xPos;
            this.yPos = yPos;
        }
    
        public void setLabel(String label)
        {
            this.label = label;
        }
        
        public void setXPos(int xPos)
        {
            this.xPos = xPos;
        }
    
        public void addXPos(int add)
        {
            this.xPos += add;
        }
    
        public void setYPos(int yPos)
        {
            this.yPos = yPos;
        }
    
        public void addYPos(int add)
        {
            this.yPos += add;
        }
        
        public String getLabel()
        {
            return label;
        }
    
        public int getXPos()
        {
            return xPos;
        }
    
        public int getRealXPos()
        {
            return (xPos * 2) + 1;
        }
    
        public int getYPos()
        {
            return yPos;
        }
    
        public int getRealYPos()
        {
            return yPos * 2;
        }
    }
    
    private class ValidTile
    {
        private byte[] dir;
        private boolean isLevel;
        
        ValidTile(byte[] dir, boolean isLevel)
        {
            this.dir = dir;
            this.isLevel = isLevel;
        }
    
        ValidTile(int right, int left, int down, int up, boolean isLevel)
        {
            this.dir = new byte[]{(byte)right, (byte)left, (byte)down, (byte)up};
            this.isLevel = isLevel;
        }
    
        ValidTile(int right, int left, int down, int up, int isLevel)
        {
            this.dir = new byte[]{(byte)right, (byte)left, (byte)down, (byte)up};
            this.isLevel = isLevel == 1;
        }
        
        public void setDir(byte[] dir)
        {
            this.dir = dir;
        }
        
        public byte[] getDir()
        {
            return dir;
        }
        
        public void setDirIndex(int dir, int index)
        {
            this.dir[index] = (byte)dir;
        }
        
        public byte getDirIndex(int index)
        {
            return dir[index];
        }
    
        public int getDirIndexInt(int index)
        {
            return Byte.toUnsignedInt(dir[index]);
        }
    
        public void setIsLevelB(boolean isLevel)
        {
            this.isLevel = isLevel;
        }
    
        public boolean isLevelB()
        {
            return isLevel;
        }
    
        public void setIsLevel(int isLevel)
        {
            this.isLevel = isLevel == 1;
        }
        
        public int isLevel()
        {
            return isLevel ? 1 : 0;
        }
    }
    
    int[][] world2tiles = {
        {33, 33, 33, 33, 33, 33, 33, 33, 8, 9, 9, 5, 15, 18, 15, 1, 29, 17, 15, 18, 7, 21, 15, 18, 15, 3, 15, 18, 11, 9, 15, 21, 7, 27, 31, 31, 31, 26, 33, 33, 33, 33, 33, 33, 33, 33},
        {33, 33, 33, 33, 33, 33, 33, 33, 9, 25, 9, 9, 8, 16, 25, 16, 8, 16, 8, 16, 9, 7, 25, 16, 11, 11, 11, 16, 11, 11, 11, 16, 9, 8, 28, 28, 28, 8, 33, 33, 33, 33, 33, 33, 33, 33},
        {33, 33, 33, 33, 33, 33, 33, 33, 7, 9, 9, 17, 15, 20, 8, 19, 8, 19, 7, 2, 15, 18, 15, 14, 8, 8, 11, 19, 9, 11, 8, 19, 9, 8, 28, 28, 28, 8, 33, 33, 33, 33, 33, 33, 33, 33},
        {33, 33, 33, 33, 33, 33, 33, 33, 8, 8, 8, 16, 10, 8, 10, 16, 10, 16, 8, 16, 8, 8, 8, 16, 8, 23, 8, 16, 9, 11, 11, 16, 9, 27, 30, 30, 30, 26, 33, 33, 33, 33, 33, 33, 33, 33},
        {33, 33, 33, 33, 33, 33, 33, 33, 8, 8, 11, 0, 8, 10, 8, 22, 15, 20, 15, 20, 15, 13, 8, 19, 8, 24, 8, 19, 8, 11, 11, 19, 15, 18, 11, 9, 8, 8, 33, 33, 33, 33, 33, 33, 33, 33},
        {33, 33, 33, 33, 33, 33, 33, 33, 11, 8, 8, 16, 8, 8, 10, 8, 8, 9, 9, 16, 8, 8, 8, 16, 10, 16, 10, 16, 8, 8, 11, 11, 9, 16, 9, 11, 9, 8, 33, 33, 33, 33, 33, 33, 33, 33},
        {33, 33, 33, 33, 33, 33, 33, 33, 8, 8, 11, 19, 8, 21, 15, 13, 8, 11, 9, 19, 11, 18, 7, 4, 8, 19, 15, 10, 8, 8, 8, 11, 9, 19, 25, 11, 9, 8, 33, 33, 33, 33, 33, 33, 33, 33},
        {33, 33, 33, 33, 33, 33, 33, 33, 8, 8, 9, 16, 9, 25, 7, 12, 11, 11, 9, 9, 9, 16, 8, 16, 9, 8, 8, 16, 8, 8, 8, 11, 8, 9, 9, 8, 11, 8, 33, 33, 33, 33, 33, 33, 33, 33},
        {33, 33, 33, 33, 33, 33, 33, 33, 15, 6, 15, 20, 15, 18, 15, 20, 8, 11, 11, 8, 9, 5, 15, 20, 15, 18, 15, 20, 8, 8, 8, 8, 11, 11, 11, 11, 8, 8, 33, 33, 33, 33, 33, 33, 33, 33}
    };
    
    /*int[][] world2valid = {
        {0, 0, 0, 0, 0, 1, 2, 3, 0, 1, 2, 1, 0, 3, 2, 1, 2, 3, 2, 1, 0, 0, 2, 3, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 2, 0, 2, 0, 2, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 1, 2, 1, 0, 1, 0, 1, 0, 3, 2, 1, 2, 3, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 2, 0, 0, 0, 2, 0, 2, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 3, 0, 0, 0, 3, 2, 1, 2, 1, 0, 0, 0, 1, 0, 3, 0, 1, 0, 0, 0, 1, 2, 1, 0, 0, 0, 0},
        {0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 2, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0},
        {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 3, 0, 1, 2, 3, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
        {0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 1, 2, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 1, 2, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };*/
    //0: Invalid
    //1: Node
    //2: Path
    //3: Level
    
    int[][] world3tiles = {
        {57, 34, 19, 28, 21, 31, 22, 2, 60, 34, 40, 28, 40, 28, 19, 5, 38, 28, 19, 6, 19, 28, 53, 46, 46, 80, 65, 71, 12, 53, 46, 15, 19, 34, 14, 14, 53, 46, 46, 46, 46, 76, 61, 61, 73, 46},
        {80, 65, 71, 20, 53, 46, 80, 26, 79, 65, 59, 20, 52, 20, 50, 41, 72, 47, 47, 47, 47, 27, 74, 46, 46, 46, 46, 80, 65, 78, 46, 57, 14, 12, 69, 65, 78, 46, 46, 46, 46, 57, 13, 13, 53, 46},
        {46, 46, 80, 1, 78, 76, 73, 32, 46, 46, 57, 3, 23, 30, 52, 7, 38, 28, 19, 9, 19, 35, 53, 46, 76, 61, 61, 61, 73, 46, 46, 80, 65, 65, 78, 46, 46, 46, 46, 46, 46, 57, 12, 13, 53, 46},
        {46, 46, 46, 24, 76, 70, 54, 25, 61, 61, 75, 26, 59, 20, 56, 27, 72, 47, 66, 65, 71, 17, 53, 76, 70, 9, 19, 34, 68, 73, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 77, 47, 66, 78, 46},
        {61, 73, 46, 32, 57, 9, 23, 35, 19, 28, 21, 33, 57, 29, 52, 29, 38, 8, 21, 18, 57, 12, 53, 57, 45, 20, 12, 45, 12, 53, 46, 46, 46, 46, 46, 46, 76, 61, 61, 61, 61, 70, 13, 53, 46, 46},
        {13, 53, 46, 24, 80, 65, 59, 20, 49, 47, 74, 24, 57, 20, 51, 47, 64, 47, 62, 73, 80, 65, 78, 15, 19, 34, 19, 9, 45, 53, 46, 46, 46, 46, 76, 61, 58, 12, 13, 13, 36, 13, 13, 53, 46, 46},
        {13, 68, 73, 0, 46, 76, 70, 29, 17, 18, 53, 4, 22, 30, 16, 28, 19, 28, 19, 15, 43, 46, 46, 80, 67, 47, 47, 47, 66, 78, 46, 46, 46, 46, 57, 13, 56, 47, 41, 48, 37, 19, 28, 21, 18, 46},
        {13, 12, 68, 25, 61, 70, 13, 20, 49, 47, 74, 46, 80, 65, 65, 65, 65, 65, 65, 78, 44, 46, 46, 46, 57, 12, 13, 12, 53, 46, 46, 46, 46, 46, 57, 13, 52, 13, 12, 55, 65, 65, 65, 78, 46, 46},
        {19, 10, 19, 18, 16, 9, 16, 30, 19, 18, 68, 61, 61, 61, 61, 61, 61, 61, 61, 61, 61, 61, 61, 61, 63, 47, 47, 47, 62, 61, 61, 61, 61, 61, 63, 47, 64, 47, 47, 62, 61, 61, 61, 61, 61, 61}
    };
    
    int[][] world3valid = {
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };
    //0: Invalid
    //1: Node
    //2: Path
    //3: Level
        
    int[][] world4tiles = {
        {9, 9, 9, 9, 9, 9, 9, 9, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 59, 45, 45, 45, 45, 45, 45, 56, 33, 33, 59, 45, 45, 45, 45, 45, 45, 56, 33, 33, 9, 9, 9, 9, 9, 9, 9, 9},
        {9, 9, 9, 9, 9, 9, 9, 9, 45, 45, 57, 45, 56, 33, 33, 33, 59, 45, 54, 11, 10, 11, 10, 11, 10, 52, 45, 45, 54, 11, 13, 13, 13, 13, 11, 43, 33, 33, 9, 9, 9, 9, 9, 9, 9, 9},
        {9, 9, 9, 9, 9, 9, 9, 9, 16, 7, 31, 15, 43, 33, 59, 45, 54, 27, 16, 5, 16, 22, 16, 22, 10, 15, 14, 28, 16, 22, 16, 2, 16, 22, 16, 1, 45, 56, 9, 9, 9, 9, 9, 9, 9, 9},
        {9, 9, 9, 9, 9, 9, 9, 9, 49, 20, 63, 49, 62, 59, 54, 29, 10, 11, 40, 21, 51, 36, 37, 17, 11, 10, 10, 17, 11, 13, 13, 13, 32, 17, 32, 17, 10, 43, 9, 9, 9, 9, 9, 9, 9, 9},
        {9, 9, 9, 9, 9, 9, 9, 9, 33, 25, 33, 34, 33, 44, 10, 30, 16, 22, 42, 24, 31, 28, 42, 23, 16, 22, 11, 23, 16, 6, 11, 13, 10, 26, 10, 23, 11, 43, 9, 9, 9, 9, 9, 9, 9, 9},
        {9, 9, 9, 9, 9, 9, 9, 9, 59, 19, 56, 33, 33, 64, 55, 10, 11, 11, 41, 21, 48, 36, 38, 17, 11, 17, 53, 20, 55, 10, 11, 13, 10, 10, 10, 17, 10, 43, 9, 9, 9, 9, 9, 9, 9, 9},
        {9, 9, 9, 9, 9, 9, 9, 9, 44, 15, 43, 33, 34, 33, 61, 36, 35, 6, 16, 4, 16, 22, 16, 24, 10, 23, 18, 3, 44, 11, 10, 27, 13, 15, 16, 0, 10, 43, 9, 9, 9, 9, 9, 9, 9, 9},
        {9, 9, 9, 9, 9, 9, 9, 9, 54, 12, 52, 56, 33, 33, 44, 11, 10, 11, 53, 49, 49, 49, 55, 10, 10, 53, 60, 19, 58, 49, 55, 11, 11, 39, 36, 36, 50, 62, 9, 9, 9, 9, 9, 9, 9, 9},
        {9, 9, 9, 9, 9, 9, 9, 9, 10, 12, 12, 52, 45, 45, 47, 36, 36, 36, 46, 45, 45, 45, 47, 36, 36, 46, 54, 26, 52, 45, 47, 36, 36, 36, 36, 36, 46, 45, 9, 9, 9, 9, 9, 9, 9, 9}
    };
    
    int[][] world5tiles = {
        {10, 10, 10, 10, 10, 10, 10, 10, 10, 16, 20, 18, 1, 18, 24, 33, 2, 18, 21, 17, 16, 34, 27, 59, 60, 61, 62, 36, 37, 27, 27, 27, 27, 51, 39, 39, 48, 10, 10, 10, 10, 10, 10, 10, 10, 10},
        {10, 10, 10, 10, 10, 10, 10, 10, 10, 17, 19, 17, 19, 32, 29, 30, 19, 17, 19, 17, 17, 34, 27, 63, 64, 65, 66, 67, 68, 27, 27, 27, 27, 50, 39, 40, 43, 10, 10, 10, 10, 10, 10, 10, 10, 10},
        {10, 10, 10, 10, 10, 10, 10, 10, 10, 16, 0, 17, 13, 33, 13, 18, 23, 17, 22, 18, 26, 34, 27, 35, 35, 35, 35, 37, 27, 57, 13, 53, 53, 55, 44, 42, 4, 10, 10, 10, 10, 10, 10, 10, 10, 10},
        {10, 10, 10, 10, 10, 10, 10, 10, 10, 16, 19, 32, 29, 30, 19, 17, 16, 17, 19, 17, 36, 37, 27, 53, 56, 27, 27, 57, 52, 55, 43, 39, 39, 40, 43, 39, 43, 10, 10, 10, 10, 10, 10, 10, 10, 10},
        {10, 10, 10, 10, 10, 10, 10, 10, 10, 17, 22, 33, 25, 18, 23, 16, 20, 18, 23, 17, 34, 27, 27, 54, 58, 57, 52, 55, 44, 41, 47, 42, 3, 42, 47, 39, 11, 10, 10, 10, 10, 10, 10, 10, 10, 10},
        {10, 10, 10, 10, 10, 10, 10, 10, 10, 16, 19, 33, 19, 17, 31, 28, 19, 17, 17, 17, 34, 57, 52, 52, 53, 55, 39, 39, 43, 39, 39, 39, 39, 39, 39, 40, 43, 10, 10, 10, 10, 10, 10, 10, 10, 10},
        {10, 10, 10, 10, 10, 10, 10, 10, 10, 18, 12, 33, 22, 18, 11, 18, 23, 17, 17, 17, 34, 50, 39, 39, 40, 39, 11, 42, 47, 42, 49, 42, 45, 42, 6, 42, 47, 10, 10, 10, 10, 10, 10, 10, 10, 10},
        {10, 10, 10, 10, 10, 10, 10, 10, 10, 35, 35, 38, 35, 35, 35, 35, 35, 35, 35, 35, 37, 51, 39, 39, 14, 39, 39, 40, 39, 39, 43, 39, 43, 40, 39, 39, 43, 10, 10, 10, 10, 10, 10, 10, 10, 10},
        {10, 10, 10, 10, 10, 10, 10, 10, 10, 52, 53, 52, 53, 52, 53, 52, 53, 52, 53, 52, 53, 55, 39, 39, 15, 42, 45, 42, 8, 42, 7, 39, 46, 42, 48, 42, 5, 10, 10, 10, 10, 10, 10, 10, 10, 10}
    };
    
    int[][] world6tiles = {
        {55, 34, 34, 60, 58, 57, 47, 47, 47, 45, 19, 30, 19, 51, 55, 34, 34, 34, 34, 34, 44, 14, 22, 27, 41, 27, 19, 19, 19, 19, 19, 19, 17, 51, 47, 47, 55, 34, 57, 47, 55, 34, 60, 50, 50, 54},
        {51, 55, 35, 34, 34, 44, 19, 19, 19, 41, 17, 23, 19, 19, 51, 55, 34, 34, 57, 47, 48, 36, 36, 36, 38, 23, 17, 19, 19, 19, 52, 54, 17, 17, 17, 17, 51, 55, 44, 19, 42, 35, 34, 34, 34, 44},
        {17, 51, 47, 47, 47, 53, 19, 26, 22, 1, 22, 29, 20, 14, 19, 51, 47, 47, 53, 17, 18, 17, 17, 26, 22, 29, 22, 27, 19, 19, 51, 8, 22, 27, 22, 27, 17, 42, 60, 50, 58, 57, 47, 47, 55, 44},
        {19, 17, 39, 36, 37, 18, 19, 23, 39, 49, 37, 23, 19, 19, 39, 36, 37, 19, 18, 19, 17, 17, 39, 25, 37, 19, 19, 23, 18, 19, 19, 23, 39, 36, 37, 23, 17, 51, 56, 47, 56, 53, 18, 15, 42, 44},
        {22, 13, 24, 27, 24, 0, 22, 10, 41, 31, 32, 29, 21, 27, 24, 14, 41, 26, 22, 5, 22, 27, 41, 31, 32, 27, 19, 7, 22, 27, 22, 29, 41, 31, 32, 29, 21, 27, 32, 27, 32, 27, 22, 16, 42, 44},
        {19, 17, 40, 25, 46, 18, 19, 23, 43, 36, 38, 23, 19, 23, 40, 36, 38, 23, 17, 17, 17, 23, 40, 36, 38, 23, 39, 36, 37, 19, 19, 23, 40, 36, 38, 23, 33, 52, 59, 50, 59, 54, 18, 17, 42, 44},
        {17, 17, 18, 14, 41, 18, 19, 28, 32, 27, 22, 2, 19, 10, 22, 3, 22, 29, 17, 30, 17, 28, 22, 27, 20, 29, 24, 6, 41, 18, 19, 28, 22, 27, 22, 9, 19, 51, 55, 34, 34, 60, 50, 50, 58, 44},
        {17, 17, 17, 18, 40, 37, 19, 19, 41, 17, 17, 17, 19, 19, 19, 19, 17, 23, 17, 23, 17, 23, 17, 23, 17, 17, 40, 25, 38, 18, 18, 19, 19, 19, 19, 19, 19, 19, 51, 55, 34, 34, 34, 34, 34, 44},
        {17, 17, 17, 17, 18, 40, 36, 36, 38, 19, 19, 19, 19, 17, 17, 18, 18, 28, 22, 4, 22, 29, 22, 29, 22, 10, 22, 29, 17, 17, 18, 18, 19, 19, 19, 19, 19, 19, 19, 51, 47, 47, 47, 47, 47, 53}
    };
    
    final byte DIR_FAIL = 0x0;
    final byte DIR_INVALID = 1;
    final byte DIR_NEEDED = 2;
    
    private byte[] bro_facing = {0x0, 0x0, 0x0};
    
    public class ValidMaps
    {
        public ValidTile[][] world2Valid = {
            {new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(2,1,2,1,0), new ValidTile(1,2,2,1,1), new ValidTile(2,1,2,1,0), new ValidTile(1,2,2,1,0), new ValidTile(2,1,1,1,1), new ValidTile(2,2,2,1,0), new ValidTile(2,2,1,1,1), new ValidTile(1,2,2,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,2,1,1), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0)},
            {new ValidTile(1,1,1,1,0), new ValidTile(2,1,2,1,0), new ValidTile(1,2,1,2,0), new ValidTile(1,1,2,2,0), new ValidTile(1,1,2,2,0), new ValidTile(2,1,2,2,1), new ValidTile(2,2,1,1,0), new ValidTile(1,2,2,2,1), new ValidTile(1,1,1,1,0), new ValidTile(1,1,2,2,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,2,2,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0)},
            {new ValidTile(1,1,1,1,0), new ValidTile(1,1,2,2,1), new ValidTile(1,1,1,1,0), new ValidTile(2,1,1,2,1), new ValidTile(2,2,1,2,0), new ValidTile(1,2,2,2,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,2,2,0), new ValidTile(1,1,2,1,1), new ValidTile(1,1,2,2,0), new ValidTile(1,1,1,1,0), new ValidTile(2,1,1,2,0), new ValidTile(1,2,2,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0)},
            {new ValidTile(1,1,1,1,0), new ValidTile(1,1,2,2,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,2,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,2,2,1), new ValidTile(2,1,1,2,0), new ValidTile(1,2,2,2,1), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,2,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0)},
            {new ValidTile(1,1,1,1,0), new ValidTile(2,1,1,2,0), new ValidTile(2,2,1,1,0), new ValidTile(1,2,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(2,1,1,2,0), new ValidTile(2,2,1,1,0), new ValidTile(1,2,1,2,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0)}
        };
        
        public ValidTile[][] world3Valid = {
            {new ValidTile(2,1,1,1,1), new ValidTile(2,2,2,1,0), new ValidTile(2,2,1,1,0), new ValidTile(1,2,2,1,1), new ValidTile(2,1,1,1,1), new ValidTile(2,2,2,1,0), new ValidTile(2,2,2,1,0), new ValidTile(2,2,1,1,1), new ValidTile(2,2,1,1,0), new ValidTile(2,2,1,1,1), new ValidTile(1,2,2,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0)},
            {new ValidTile(1,1,1,1,0), new ValidTile(1,1,2,2,1), new ValidTile(1,1,1,1,0), new ValidTile(1,1,2,2,0), new ValidTile(1,1,1,1,0), new ValidTile(2,1,2,2,1), new ValidTile(1,2,2,2,0), new ValidTile(2,1,2,1,1), new ValidTile(1,2,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,2,1), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0)},
            {new ValidTile(1,1,1,1,0), new ValidTile(1,1,2,2,0), new ValidTile(1,1,1,1,0), new ValidTile(2,1,2,2,1), new ValidTile(2,2,1,1,0), new ValidTile(1,2,2,2,0), new ValidTile(1,1,2,2,0), new ValidTile(2,1,1,2,0), new ValidTile(1,2,1,1,1), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0)},
            {new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,2,1), new ValidTile(1,1,1,1,0), new ValidTile(1,1,2,2,0), new ValidTile(1,1,1,1,0), new ValidTile(2,1,1,2,1), new ValidTile(1,2,2,2,0), new ValidTile(2,1,1,1,0), new ValidTile(1,2,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0)},
            {new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,2,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0)}
        };
        
        public ValidTile[][] world4Valid = {
            {new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0)},
            {new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(2,1,1,1,1), new ValidTile(2,2,2,1,1), new ValidTile(2,2,1,1,0), new ValidTile(1,2,2,1,0), new ValidTile(1,1,1,1,0), new ValidTile(2,1,2,1,1), new ValidTile(2,2,1,1,0), new ValidTile(2,2,1,1,1), new ValidTile(2,2,2,1,0), new ValidTile(1,2,2,1,1), new ValidTile(1,1,1,1,0)},
            {new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(2,1,1,1,1), new ValidTile(1,2,1,1,0), new ValidTile(2,1,2,2,0), new ValidTile(1,2,1,1,1), new ValidTile(2,1,2,2,0), new ValidTile(1,2,2,1,0), new ValidTile(1,1,2,2,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,2,1), new ValidTile(1,1,2,2,0), new ValidTile(1,1,1,1,0)},
            {new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(2,1,1,2,1), new ValidTile(2,2,1,1,0), new ValidTile(2,1,2,1,0), new ValidTile(2,1,1,2,0), new ValidTile(1,2,2,2,1), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,2,1), new ValidTile(1,1,1,1,0)},
            {new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,2,1), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0)}
        };
        
        public ValidTile[][] world5Valid = {
            {new ValidTile(2,1,2,1,0), new ValidTile(2,2,1,1,1), new ValidTile(1,2,1,1,1), new ValidTile(2,1,2,1,1), new ValidTile(1,2,2,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,2,1,1)},
            {new ValidTile(1,1,2,2,1), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,2,0), new ValidTile(1,1,2,2,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(2,1,2,1,0), new ValidTile(1,2,1,2,1)},
            {new ValidTile(1,1,1,2,0), new ValidTile(2,1,2,1,1), new ValidTile(1,2,1,1,0), new ValidTile(2,1,2,1,0), new ValidTile(1,2,1,2,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,2,1,0), new ValidTile(2,1,1,1,0), new ValidTile(2,2,1,1,1), new ValidTile(1,2,1,2,0), new ValidTile(1,1,1,1,0)},
            {new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,2,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,2,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(2,1,1,2,0), new ValidTile(2,2,2,1,1), new ValidTile(2,2,2,1,0), new ValidTile(2,2,1,1,1), new ValidTile(1,2,2,1,0)},
            {new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(0,0,0,0,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(2,1,1,1,1), new ValidTile(2,2,1,1,0), new ValidTile(2,2,1,1,1), new ValidTile(1,2,1,2,1), new ValidTile(2,1,1,2,0), new ValidTile(2,2,1,1,1), new ValidTile(1,2,1,2,1)}
        };
        
        public ValidTile[][] world6Valid = {
            {new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,2,1,1), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,2,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0)},
            {new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(2,1,1,1,0), new ValidTile(2,2,1,1,1), new ValidTile(1,2,2,2,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(2,1,2,1,0), new ValidTile(2,2,1,2,0), new ValidTile(1,2,2,1,0), new ValidTile(1,1,1,1,0), new ValidTile(2,1,2,1,1), new ValidTile(2,2,1,1,0), new ValidTile(1,2,2,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0)},
            {new ValidTile(1,1,1,1,0), new ValidTile(2,1,1,1,0), new ValidTile(1,2,1,1,1), new ValidTile(1,1,1,1,0), new ValidTile(2,1,1,1,1), new ValidTile(1,2,2,2,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(2,1,2,1,0), new ValidTile(2,2,1,1,1), new ValidTile(1,2,2,1,0), new ValidTile(2,1,1,2,1), new ValidTile(1,2,2,1,0), new ValidTile(2,1,1,2,1), new ValidTile(2,2,1,1,0), new ValidTile(1,2,2,2,0), new ValidTile(2,1,1,1,1), new ValidTile(1,2,2,2,0), new ValidTile(2,1,1,1,0), new ValidTile(2,2,1,1,0), new ValidTile(2,2,1,1,0), new ValidTile(1,2,1,1,1), new ValidTile(1,1,1,1,0)},
            {new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(2,1,1,1,0), new ValidTile(2,2,1,1,0), new ValidTile(1,2,1,2,1), new ValidTile(1,1,1,1,0), new ValidTile(2,1,1,1,1), new ValidTile(1,2,2,2,0), new ValidTile(1,1,2,1,1), new ValidTile(2,1,2,2,0), new ValidTile(1,2,2,1,0), new ValidTile(2,1,1,2,0), new ValidTile(1,2,2,1,1), new ValidTile(1,1,1,1,0), new ValidTile(2,1,1,2,0), new ValidTile(2,2,1,1,0), new ValidTile(1,2,1,2,1), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0)},
            {new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(2,1,1,2,0), new ValidTile(2,2,1,2,1), new ValidTile(2,2,1,2,0), new ValidTile(1,2,1,2,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,2,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0), new ValidTile(1,1,1,1,0)}
        };
        
        public void level1Done(boolean done, int world)
        {
            int valid = done ? 1 : 2;
            switch(world)
            {
                case 4:
                    world4Valid[2][13].setDirIndex(valid, 2);
                    break;
                case 5:
                    world5Valid[0][0].setDirIndex(valid, 2);
                    world5Valid[2][0].setDirIndex(valid, 3);
                    break;
                case 6:
                    world6Valid[2][1].setDirIndex(valid, 0);
                    break;
                default:
                    world2Valid[1][1].setDirIndex(valid, 2);
                    world2Valid[3][1].setDirIndex(valid, 3);
                    break;
            }
        }
    
        public void level2Done(boolean done, int world)
        {
            int valid = done ? 1 : 2;
            switch(world)
            {
                case 4:
                    world4Valid[2][13].setDirIndex(valid, 3);
                    world4Valid[1][12].setDirIndex(valid, 0);
                    break;
                case 5:
                    world5Valid[0][0].setDirIndex(valid, 0);
                    world5Valid[0][2].setDirIndex(valid, 1);
                    break;
                case 6:
                    world6Valid[1][3].setDirIndex(valid, 0);
                    world6Valid[1][5].setDirIndex(valid, 1);
                    break;
                default:
                    world2Valid[0][2].setDirIndex(valid, 0);
                    world2Valid[1][3].setDirIndex(valid, 3);
                    break;
            }
        }
    
        public void level3Done(boolean done, int world)
        {
            int valid = done ? 1 : 2;
            switch(world)
            {
                case 4:
                    world4Valid[1][10].setDirIndex(valid, 0);
                    world4Valid[1][12].setDirIndex(valid, 1);
                    break;
                case 5:
                    world5Valid[0][4].setDirIndex(valid, 1);
                    world5Valid[1][3].setDirIndex(valid, 3);
                    break;
                case 6:
                    world6Valid[3][4].setDirIndex(valid, 0);
                    world6Valid[2][5].setDirIndex(valid, 2);
                    break;
                default:
                    world2Valid[0][5].setDirIndex(valid, 2);
                    world2Valid[2][5].setDirIndex(valid, 3);
                    world2Valid[1][6].setDirIndex(valid, 1);
                    break;
            }
        }
    
        public void level4Done(boolean done, int world)
        {
            int valid = done ? 1 : 2;
            switch(world)
            {
                case 4:
                    world4Valid[3][8].setDirIndex(valid, 0);
                    world4Valid[2][9].setDirIndex(valid, 2);
                    world4Valid[4][9].setDirIndex(valid, 3);
                    break;
                case 5:
                    world5Valid[2][10].setDirIndex(valid, 0);
                    world5Valid[2][12].setDirIndex(valid, 1);
                    break;
                case 6:
                    world6Valid[3][8].setDirIndex(valid, 1);
                    break;
                default:
                    world2Valid[0][7].setDirIndex(valid, 0);
                    world2Valid[0][9].setDirIndex(valid, 1);
                    break;
            }
        }
    
        public void level5Done(boolean done, int world)
        {
            int valid = done ? 1 : 2;
            switch(world)
            {
                case 4:
                    world4Valid[3][6].setDirIndex(valid, 1);
                    world4Valid[2][5].setDirIndex(valid, 2);
                    break;
                case 5:
                    world5Valid[0][13].setDirIndex(valid, 2);
                    world5Valid[1][12].setDirIndex(valid, 0);
                    break;
                case 6:
                    world6Valid[4][8].setDirIndex(valid, 0);
                    world6Valid[4][10].setDirIndex(valid, 1);
                    break;
                default:
                    world2Valid[2][7].setDirIndex(valid, 2);
                    world2Valid[4][7].setDirIndex(valid, 3);
                    break;
            }
        }
    
        public void level6Done(boolean done, int world)
        {
            int valid = done ? 1 : 2;
            switch(world)
            {
                case 4:
                    world4Valid[1][6].setDirIndex(valid, 1);
                    world4Valid[2][5].setDirIndex(valid, 3);
                    break;
                case 5:
                    world5Valid[3][13].setDirIndex(valid, 2);
                    world5Valid[4][12].setDirIndex(valid, 0);
                    break;
                case 6:
                    world6Valid[2][8].setDirIndex(valid, 0);
                    world6Valid[2][10].setDirIndex(valid, 1);
                    break;
                default:
                    System.out.println("INVALID WORLD: " + world);
                    break;
            }
        }
        
        public void level7Done(boolean done, int world)
        {
            int valid = done ? 1 : 2;
            switch(world)
            {
                case 5:
                    world5Valid[3][13].setDirIndex(valid, 1);
                    world5Valid[3][11].setDirIndex(valid, 0);
                    break;
                case 6:
                    world6Valid[3][12].setDirIndex(valid, 0);
                    world6Valid[4][13].setDirIndex(valid, 3);
                    break;
                default:
                    System.out.println("INVALID WORLD: " + world);
                    break;
            }
        }
    
        public void level8Done(boolean done, int world)
        {
            int valid = done ? 1 : 2;
            switch(world)
            {
                case 5:
                    world5Valid[3][10].setDirIndex(valid, 2);
                    world5Valid[4][9].setDirIndex(valid, 0);
                    break;
                case 6:
                    world6Valid[1][13].setDirIndex(valid, 2);
                    world6Valid[2][14].setDirIndex(valid, 1);
                    break;
                default:
                    System.out.println("INVALID WORLD: " + world);
                    break;
            }
        }
    
        public void level9Done(boolean done, int world)
        {
            int valid = done ? 1 : 2;
            switch(world)
            {
                case 5:
                    world5Valid[4][8].setDirIndex(valid, 0);
                    world5Valid[4][10].setDirIndex(valid, 1);
                    break;
                case 6:
                    world6Valid[2][15].setDirIndex(valid, 3);
                    world6Valid[1][16].setDirIndex(valid, 1);
                    break;
                default:
                    System.out.println("INVALID WORLD: " + world);
                    break;
            }
        }
    
        public void level10Done(boolean done, int world)
        {
            int valid = done ? 1 : 2;
            if(world == 6)
            {
                world6Valid[3][16].setDirIndex(valid, 0);
                world6Valid[2][17].setDirIndex(valid, 2);
            }
            else
            {
                System.out.println("INVALID WORLD: " + world);
            }
        }
    
        public void fort1Done(boolean done, int world)
        {
            int valid = done ? 1 : 2;
            int notValid = done ? 2 : 1;
            switch(world)
            {
                case 4:
                    world4Valid[1][10].setDirIndex(valid, 1);
                    world4Valid[2][9].setDirIndex(valid, 3);
                    break;
                case 5:
                    world5Valid[2][2].setDirIndex(valid, 1);
                    world5Valid[3][1].setDirIndex(valid, 3);
                    break;
                case 6:
                    world6Valid[2][5].setDirIndex(valid, 1);
                    
                    world6Valid[2][5].setDirIndex(notValid, 0);
                    world6Valid[2][6].setDirIndex(notValid, 1);
                    break;
                default:
                    world2Valid[1][3].setDirIndex(valid, 2);
                    world2Valid[2][4].setDirIndex(valid, 1);
                    break;
            }
        }
    
        public void fort2Done(boolean done, int world)
        {
            int valid = done ? 1 : 2;
            int notValid = done ? 2 : 1;
            switch(world)
            {
                case 4:
                    world4Valid[2][5].setDirIndex(valid, 0);
                    world4Valid[2][5].setDirIndex(notValid, 1);
                    world4Valid[2][4].setDirIndex(notValid, 0);
                    break;
                case 5:
                    world5Valid[3][9].setDirIndex(valid, 0);
                    world5Valid[3][11].setDirIndex(valid, 1);
                    world5Valid[4][10].setDirIndex(valid, 3);
    
                    world5Valid[2][9].setDirIndex(notValid, 0);
                    world5Valid[2][10].setDirIndex(notValid, 1);
                    break;
                case 6:
                    world6Valid[1][11].setDirIndex(valid, 2);
                    world6Valid[2][12].setDirIndex(valid, 1);
    
                    world6Valid[0][11].setDirIndex(notValid, 0);
                    world6Valid[0][12].setDirIndex(notValid, 1);
                    break;
                default:
                    System.out.println("INVALID WORLD: " + world);
                    break;
            }
        }
    
        public void fort3Done(boolean done, int world)
        {
            int valid = done ? 1 : 2;
            int notValid = done ? 2 : 1;
            if(world == 6)
            {
                world6Valid[2][17].setDirIndex(valid, 1);
    
                world6Valid[2][17].setDirIndex(notValid, 0);
                world6Valid[2][18].setDirIndex(notValid, 1);
            }
            else
            {
                System.out.println("INVALID WORLD: " + world);
            }
        }
    
        public void pyramidDone(boolean done, int world)
        {
            int valid = done ? 1 : 2;
            if(world == 2)
            {
                world2Valid[2][9].setDirIndex(valid, 2);
                world2Valid[4][9].setDirIndex(valid, 3);
                world2Valid[3][8].setDirIndex(valid, 0);
            }
            else
            {
                System.out.println("INVALID WORLD: " + world);
            }
        }
    
        public void quicksandDone(boolean done, int world)
        {
            int valid = done ? 1 : 2;
            if(world == 2)
            {
                world2Valid[1][6].setDirIndex(valid, 0);
                world2Valid[0][7].setDirIndex(valid, 2);
                world2Valid[2][7].setDirIndex(valid, 3);
            }
            else
            {
                System.out.println("INVALID WORLD: " + world);
            }
        }
    
        public void mushroom1Done(boolean done, int world)
        {
            int valid = done ? 1 : 2;
            switch(world)
            {
                case 2:
                    break;
                case 4:
                    world4Valid[1][12].setDirIndex(valid, 2);
                    break;
                case 5:
                    world5Valid[0][1].setDirIndex(valid, 0);
                    break;
                case 6:
                    world6Valid[1][5].setDirIndex(valid, 3);
                    break;
                default:
                    System.out.println("INVALID WORLD: " + world);
                    break;
            }
        }
    
        public void mushroom2Done(boolean done, int world)
        {
            int valid = done ? 1 : 2;
            switch(world)
            {
                case 2:
                    world2Valid[0][7].setDirIndex(valid, 1);
                    break;
                case 4:
                    world4Valid[3][9].setDirIndex(valid, 2);
                    break;
                case 5:
                    world5Valid[1][13].setDirIndex(valid, 3);
                    break;
                case 6:
                    world6Valid[4][9].setDirIndex(valid, 3);
                    break;
                default:
                    System.out.println("INVALID WORLD: " + world);
                    break;
            }
        }
    
        public void mushroom3Done(boolean done, int world)
        {
            int valid = done ? 1 : 2;
            switch(world)
            {
                case 2:
                    if(!completedTiles[19].isSelected())
                    {
                        world2Valid[0][10].setDirIndex(valid, 0);
                    }
                    world2Valid[1][11].setDirIndex(valid, 3);
                    break;
                case 4:
                    world4Valid[1][5].setDirIndex(valid, 1);
                    break;
                case 5:
                    world5Valid[4][13].setDirIndex(valid, 1);
                    world5Valid[4][11].setDirIndex(valid, 0);
                    break;
                default:
                    System.out.println("INVALID WORLD: " + world);
                    break;
            }
        }
    
        public void rock1Broken(boolean done, int world)
        {
            int valid = done ? 1 : 2;
            switch(world)
            {
                case 2:
                    world2Valid[3][5].setDirIndex(valid, 0);
                    world2Valid[3][6].setDirIndex(valid, 1);
                    break;
                case 6:
                    break;
                default:
                    System.out.println("INVALID WORLD: " + world);
                    break;
            }
        }
        
        public void rock2Broken(boolean done, int world)
        {
            int valid = done ? 1 : 2;
            switch(world)
            {
                case 2:
                    world2Valid[0][9].setDirIndex(valid, 0);
                    if(!completedTiles[17].isSelected())
                    {
                        world2Valid[0][10].setDirIndex(valid, 0);
                    }
                    world2Valid[0][10].setDirIndex(valid, 1);
                    world2Valid[0][11].setDirIndex(valid, 1);
                    break;
                case 6:
                    world6Valid[3][11].setDirIndex(valid, 0);
                    world6Valid[3][12].setDirIndex(valid, 1);
                    break;
                default:
                    System.out.println("INVALID WORLD: " + world);
                    break;
            }
        }
    }
    
    ValidMaps validMaps = new ValidMaps();
    
    ArrayList<String> world1sprites = new ArrayList<>();
    ArrayList<String> world2sprites = new ArrayList<>();
    ArrayList<String> world3sprites = new ArrayList<>();
    ArrayList<String> world4sprites = new ArrayList<>();
    ArrayList<String> world5sprites = new ArrayList<>();
    ArrayList<String> world6sprites = new ArrayList<>();
    
    ArrayList<String> validSprites = new ArrayList<>();
    
    final int LEVEL_WAIT = 39;
    final int FORT_WAIT = 102;
    final int DEATH_WAIT = 32;
    
    public Gui()
    {
        super("RNGFinder");
        
        RNG rng = new RNG();
        
        for(int frame = 0; frame < 32767; frame++)
        {
            boolean success = true;
            
            bro_facing[0] = (byte)((Byte.toUnsignedInt(rng.getRandom_Pool(2))) & 3);
            bro_facing[1] = (byte)((Byte.toUnsignedInt(rng.getRandom_Pool(3))) & 3);
            bro_facing[2] = (byte)((Byte.toUnsignedInt(rng.getRandom_Pool(4))) & 3);
            
            /*if(frame > 16100 && frame < 16500)
            {
                System.out.print("RNG" + frame + ": ");
                for(int i = 0; i < 9; i++)
                {
                    System.out.print(Byte.toUnsignedInt(rng.getRandom_Pool_Real(i)) + ", ");
                }
                System.out.print("\n");
            }*/
            
            /*if(
                Byte.toUnsignedInt(rng.getRandom_Pool_Real(0)) == 88 &&
                Byte.toUnsignedInt(rng.getRandom_Pool_Real(1)) == 160 &&
                Byte.toUnsignedInt(rng.getRandom_Pool_Real(2)) == 17 &&
                Byte.toUnsignedInt(rng.getRandom_Pool_Real(3)) == 81 &&
                Byte.toUnsignedInt(rng.getRandom_Pool_Real(4)) == 115 &&
                Byte.toUnsignedInt(rng.getRandom_Pool_Real(5)) == 209 &&
                Byte.toUnsignedInt(rng.getRandom_Pool_Real(6)) == 54 &&
                Byte.toUnsignedInt(rng.getRandom_Pool_Real(7)) == 148 &&
                Byte.toUnsignedInt(rng.getRandom_Pool_Real(8)) == 249)
            {
                hm = true;
                System.out.println(Integer.toString(frame) + " aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            }*/
            
            for(int i = 0; i < LEVEL_WAIT; i++)
            {
                rng.Randomize_Temp();
            }
            
            byte[][] w2_mb_directions_1 = {
                {DIR_FAIL, DIR_NEEDED, DIR_FAIL, DIR_INVALID},
                {DIR_NEEDED, DIR_INVALID, DIR_INVALID, DIR_INVALID},
                {DIR_FAIL, DIR_FAIL, DIR_NEEDED, DIR_INVALID},
                {DIR_INVALID, DIR_NEEDED, DIR_FAIL, DIR_FAIL},
                {DIR_FAIL, DIR_NEEDED, DIR_INVALID, DIR_INVALID},
                {DIR_FAIL, DIR_INVALID, DIR_FAIL, DIR_NEEDED}
            };
    
            byte[][] w2_h_directions_1 = {
                {DIR_INVALID, DIR_NEEDED, DIR_FAIL, DIR_INVALID},
                {DIR_FAIL, DIR_NEEDED, DIR_INVALID, DIR_INVALID},
                {DIR_FAIL, DIR_FAIL, DIR_NEEDED, DIR_INVALID},
                {DIR_INVALID, DIR_NEEDED, DIR_FAIL, DIR_FAIL},
                {DIR_FAIL, DIR_NEEDED, DIR_INVALID, DIR_INVALID},
                {DIR_FAIL, DIR_INVALID, DIR_NEEDED, DIR_FAIL}
            };
    
            byte[][] w2_mb_directions_2 = {
                {DIR_FAIL, DIR_NEEDED, DIR_FAIL, DIR_INVALID},
                {DIR_NEEDED, DIR_INVALID, DIR_INVALID, DIR_INVALID},
                {DIR_FAIL, DIR_FAIL, DIR_NEEDED, DIR_INVALID},
                {DIR_INVALID, DIR_NEEDED, DIR_FAIL, DIR_FAIL},
                {DIR_FAIL, DIR_NEEDED, DIR_INVALID, DIR_INVALID},
                {DIR_FAIL, DIR_INVALID, DIR_NEEDED, DIR_FAIL}
            };
    
            byte[][] w2_h_directions_2 = {
                {DIR_INVALID, DIR_NEEDED, DIR_FAIL, DIR_INVALID},
                {DIR_FAIL, DIR_NEEDED, DIR_INVALID, DIR_INVALID},
                {DIR_FAIL, DIR_FAIL, DIR_NEEDED, DIR_INVALID},
                {DIR_INVALID, DIR_NEEDED, DIR_FAIL, DIR_FAIL},
                {DIR_FAIL, DIR_NEEDED, DIR_INVALID, DIR_INVALID},
                {DIR_FAIL, DIR_INVALID, DIR_FAIL, DIR_NEEDED}
            };
    
            byte[][] w2_mb_directions_3 = {
                {DIR_INVALID, DIR_FAIL, DIR_INVALID, DIR_NEEDED},
                {DIR_INVALID, DIR_FAIL, DIR_FAIL, DIR_NEEDED}
            };
    
            byte[][] w2_h_directions_3 = {
                {DIR_NEEDED, DIR_INVALID, DIR_INVALID, DIR_FAIL},
                {DIR_INVALID, DIR_NEEDED, DIR_FAIL, DIR_FAIL}
            };
    
            byte[][] w2_marathon_directions = {
                {DIR_INVALID, DIR_FAIL, DIR_FAIL, DIR_NEEDED},
                {DIR_INVALID, DIR_INVALID, DIR_FAIL, DIR_NEEDED},
                {DIR_INVALID, DIR_INVALID, DIR_FAIL, DIR_NEEDED},
                {DIR_INVALID, DIR_NEEDED, DIR_FAIL, DIR_INVALID},
                {DIR_INVALID, DIR_NEEDED, DIR_INVALID, DIR_INVALID},
                {DIR_INVALID, DIR_NEEDED, DIR_INVALID, DIR_INVALID},
                {DIR_NEEDED, DIR_INVALID, DIR_INVALID, DIR_INVALID},
                {DIR_NEEDED, DIR_FAIL, DIR_INVALID, DIR_INVALID},
                {DIR_NEEDED, DIR_FAIL, DIR_INVALID, DIR_INVALID},
                {DIR_INVALID, DIR_FAIL, DIR_NEEDED, DIR_INVALID},
                {DIR_INVALID, DIR_INVALID, DIR_NEEDED, DIR_FAIL},
                {DIR_INVALID, DIR_INVALID, DIR_NEEDED, DIR_FAIL},
                {DIR_INVALID, DIR_FAIL, DIR_NEEDED, DIR_FAIL},
                {DIR_INVALID, DIR_NEEDED, DIR_INVALID, DIR_FAIL},
                {DIR_FAIL, DIR_NEEDED, DIR_INVALID, DIR_INVALID},
                {DIR_NEEDED, DIR_INVALID, DIR_INVALID, DIR_INVALID},
                {DIR_NEEDED, DIR_FAIL, DIR_INVALID, DIR_INVALID},
                {DIR_INVALID, DIR_FAIL, DIR_INVALID, DIR_NEEDED}
            };
    
            byte[][] w2_mb_directions_4 = {
                {DIR_INVALID, DIR_FAIL, DIR_INVALID, DIR_NEEDED},
                //{DIR_NEEDED, DIR_INVALID, DIR_INVALID, DIR_FAIL},
                
                {DIR_INVALID, DIR_FAIL, DIR_FAIL, DIR_NEEDED},
                {DIR_INVALID, DIR_INVALID, DIR_FAIL, DIR_NEEDED},
                {DIR_INVALID, DIR_INVALID, DIR_FAIL, DIR_NEEDED},
                {DIR_INVALID, DIR_NEEDED, DIR_FAIL, DIR_INVALID},
                {DIR_INVALID, DIR_NEEDED, DIR_INVALID, DIR_INVALID},
                {DIR_INVALID, DIR_NEEDED, DIR_INVALID, DIR_INVALID},
                {DIR_NEEDED, DIR_INVALID, DIR_INVALID, DIR_INVALID},
                {DIR_NEEDED, DIR_FAIL, DIR_INVALID, DIR_INVALID},
                {DIR_NEEDED, DIR_FAIL, DIR_INVALID, DIR_INVALID},
                {DIR_INVALID, DIR_FAIL, DIR_NEEDED, DIR_INVALID},
                {DIR_INVALID, DIR_INVALID, DIR_NEEDED, DIR_FAIL},
                {DIR_INVALID, DIR_INVALID, DIR_NEEDED, DIR_FAIL},
                {DIR_INVALID, DIR_FAIL, DIR_NEEDED, DIR_FAIL},
                {DIR_INVALID, DIR_NEEDED, DIR_INVALID, DIR_FAIL},
                {DIR_FAIL, DIR_NEEDED, DIR_INVALID, DIR_INVALID},
                {DIR_NEEDED, DIR_INVALID, DIR_INVALID, DIR_INVALID},
                {DIR_NEEDED, DIR_FAIL, DIR_INVALID, DIR_INVALID},
                {DIR_INVALID, DIR_FAIL, DIR_INVALID, DIR_NEEDED},
                
                {DIR_INVALID, DIR_FAIL, DIR_FAIL, DIR_NEEDED},
                {DIR_INVALID, DIR_INVALID, DIR_FAIL, DIR_NEEDED},
                {DIR_INVALID, DIR_INVALID, DIR_FAIL, DIR_NEEDED},
                {DIR_INVALID, DIR_NEEDED, DIR_FAIL, DIR_INVALID},
                {DIR_INVALID, DIR_NEEDED, DIR_INVALID, DIR_INVALID},
                {DIR_INVALID, DIR_NEEDED, DIR_INVALID, DIR_INVALID},
                {DIR_NEEDED, DIR_INVALID, DIR_INVALID, DIR_INVALID},
                {DIR_NEEDED, DIR_FAIL, DIR_INVALID, DIR_INVALID},
                {DIR_NEEDED, DIR_FAIL, DIR_INVALID, DIR_INVALID},
                {DIR_INVALID, DIR_FAIL, DIR_NEEDED, DIR_INVALID},
                {DIR_INVALID, DIR_INVALID, DIR_NEEDED, DIR_FAIL},
                {DIR_INVALID, DIR_INVALID, DIR_NEEDED, DIR_FAIL},
                {DIR_INVALID, DIR_FAIL, DIR_NEEDED, DIR_FAIL},
                {DIR_INVALID, DIR_NEEDED, DIR_INVALID, DIR_FAIL},
                {DIR_FAIL, DIR_NEEDED, DIR_INVALID, DIR_INVALID},
                {DIR_NEEDED, DIR_INVALID, DIR_INVALID, DIR_INVALID},
                {DIR_NEEDED, DIR_FAIL, DIR_INVALID, DIR_INVALID},
                {DIR_INVALID, DIR_FAIL, DIR_INVALID, DIR_NEEDED},
                
                {DIR_INVALID, DIR_NEEDED, DIR_FAIL, DIR_FAIL},
                {DIR_FAIL, DIR_INVALID, DIR_INVALID, DIR_NEEDED},
                {DIR_INVALID, DIR_INVALID, DIR_NEEDED, DIR_INVALID},
                {DIR_NEEDED, DIR_INVALID, DIR_INVALID, DIR_FAIL},
                {DIR_INVALID, DIR_FAIL, DIR_NEEDED, DIR_FAIL},
                {DIR_INVALID, DIR_NEEDED, DIR_INVALID, DIR_FAIL},
                {DIR_FAIL, DIR_NEEDED, DIR_INVALID, DIR_INVALID},
                {DIR_NEEDED, DIR_INVALID, DIR_INVALID, DIR_INVALID},
                {DIR_NEEDED, DIR_FAIL, DIR_INVALID, DIR_INVALID},
                {DIR_INVALID, DIR_FAIL, DIR_INVALID, DIR_NEEDED},
    
                {DIR_INVALID, DIR_FAIL, DIR_FAIL, DIR_NEEDED}
                //{DIR_INVALID, DIR_NEEDED, DIR_FAIL, DIR_FAIL}
            };
    
            byte[][] w2_h_directions_4 = {
                //{DIR_INVALID, DIR_FAIL, DIR_INVALID, DIR_NEEDED},
                {DIR_NEEDED, DIR_INVALID, DIR_INVALID, DIR_FAIL},
                
                {DIR_INVALID, DIR_FAIL, DIR_FAIL, DIR_NEEDED},
                {DIR_INVALID, DIR_INVALID, DIR_FAIL, DIR_NEEDED},
                {DIR_INVALID, DIR_INVALID, DIR_FAIL, DIR_NEEDED},
                {DIR_INVALID, DIR_NEEDED, DIR_FAIL, DIR_INVALID},
                {DIR_INVALID, DIR_NEEDED, DIR_INVALID, DIR_INVALID},
                {DIR_INVALID, DIR_NEEDED, DIR_INVALID, DIR_INVALID},
                {DIR_NEEDED, DIR_INVALID, DIR_INVALID, DIR_INVALID},
                {DIR_NEEDED, DIR_FAIL, DIR_INVALID, DIR_INVALID},
                {DIR_NEEDED, DIR_FAIL, DIR_INVALID, DIR_INVALID},
                {DIR_INVALID, DIR_FAIL, DIR_NEEDED, DIR_INVALID},
                {DIR_INVALID, DIR_INVALID, DIR_NEEDED, DIR_FAIL},
                {DIR_INVALID, DIR_INVALID, DIR_NEEDED, DIR_FAIL},
                {DIR_INVALID, DIR_FAIL, DIR_NEEDED, DIR_FAIL},
                {DIR_INVALID, DIR_NEEDED, DIR_INVALID, DIR_FAIL},
                {DIR_FAIL, DIR_NEEDED, DIR_INVALID, DIR_INVALID},
                {DIR_NEEDED, DIR_INVALID, DIR_INVALID, DIR_INVALID},
                {DIR_NEEDED, DIR_FAIL, DIR_INVALID, DIR_INVALID},
                {DIR_INVALID, DIR_FAIL, DIR_INVALID, DIR_NEEDED},
                
                {DIR_INVALID, DIR_FAIL, DIR_FAIL, DIR_NEEDED},
                {DIR_INVALID, DIR_INVALID, DIR_FAIL, DIR_NEEDED},
                {DIR_INVALID, DIR_INVALID, DIR_FAIL, DIR_NEEDED},
                {DIR_INVALID, DIR_NEEDED, DIR_FAIL, DIR_INVALID},
                {DIR_INVALID, DIR_NEEDED, DIR_INVALID, DIR_INVALID},
                {DIR_INVALID, DIR_NEEDED, DIR_INVALID, DIR_INVALID},
                {DIR_NEEDED, DIR_INVALID, DIR_INVALID, DIR_INVALID},
                {DIR_NEEDED, DIR_FAIL, DIR_INVALID, DIR_INVALID},
                {DIR_NEEDED, DIR_FAIL, DIR_INVALID, DIR_INVALID},
                {DIR_INVALID, DIR_FAIL, DIR_NEEDED, DIR_INVALID},
                {DIR_INVALID, DIR_INVALID, DIR_NEEDED, DIR_FAIL},
                {DIR_INVALID, DIR_INVALID, DIR_NEEDED, DIR_FAIL},
                {DIR_INVALID, DIR_FAIL, DIR_NEEDED, DIR_FAIL},
                {DIR_INVALID, DIR_NEEDED, DIR_INVALID, DIR_FAIL},
                {DIR_FAIL, DIR_NEEDED, DIR_INVALID, DIR_INVALID},
                {DIR_NEEDED, DIR_INVALID, DIR_INVALID, DIR_INVALID},
                {DIR_NEEDED, DIR_FAIL, DIR_INVALID, DIR_INVALID},
                {DIR_INVALID, DIR_FAIL, DIR_INVALID, DIR_NEEDED},
                
                {DIR_INVALID, DIR_NEEDED, DIR_FAIL, DIR_FAIL},
                {DIR_FAIL, DIR_INVALID, DIR_INVALID, DIR_NEEDED},
                {DIR_INVALID, DIR_INVALID, DIR_NEEDED, DIR_INVALID},
                {DIR_NEEDED, DIR_INVALID, DIR_INVALID, DIR_FAIL},
                {DIR_INVALID, DIR_FAIL, DIR_NEEDED, DIR_FAIL},
                {DIR_INVALID, DIR_NEEDED, DIR_INVALID, DIR_FAIL},
                {DIR_FAIL, DIR_NEEDED, DIR_INVALID, DIR_INVALID},
                {DIR_NEEDED, DIR_INVALID, DIR_INVALID, DIR_INVALID},
                {DIR_NEEDED, DIR_FAIL, DIR_INVALID, DIR_INVALID},
                {DIR_INVALID, DIR_FAIL, DIR_INVALID, DIR_NEEDED},
                
                //{DIR_INVALID, DIR_FAIL, DIR_FAIL, DIR_NEEDED}
                {DIR_INVALID, DIR_NEEDED, DIR_FAIL, DIR_FAIL}
            };
    
            byte[][] w2_mb_directions_5 = {
                {DIR_INVALID, DIR_INVALID, DIR_NEEDED, DIR_FAIL},
                {DIR_INVALID, DIR_FAIL, DIR_NEEDED, DIR_FAIL}
            };
    
            byte[][] w2_h_directions_5 = {
                {DIR_INVALID, DIR_FAIL, DIR_INVALID, DIR_NEEDED},
                {DIR_INVALID, DIR_NEEDED, DIR_FAIL, DIR_FAIL}
            };
    
            byte[][] w3_h_directions_1 = {
                {DIR_NEEDED, DIR_FAIL, DIR_INVALID, DIR_INVALID},
                {DIR_NEEDED, DIR_FAIL, DIR_FAIL, DIR_INVALID},
                {DIR_FAIL, DIR_FAIL, DIR_NEEDED, DIR_INVALID},
                {DIR_FAIL, DIR_INVALID, DIR_NEEDED, DIR_FAIL}
            };
            
            byte[][] w3_s_directions_1 = {
                {DIR_INVALID, DIR_INVALID, DIR_FAIL, DIR_NEEDED},
                {DIR_NEEDED, DIR_FAIL, DIR_FAIL, DIR_INVALID},
                {DIR_FAIL, DIR_FAIL, DIR_NEEDED, DIR_INVALID},
                {DIR_NEEDED, DIR_INVALID, DIR_FAIL, DIR_FAIL}
            };
    
            byte[][] w3_h_directions_2 = {
                {DIR_INVALID, DIR_FAIL, DIR_FAIL, DIR_NEEDED},
                {DIR_FAIL, DIR_INVALID, DIR_FAIL, DIR_NEEDED}
            };
            
            byte[][] w3_s_directions_2 = {
                {DIR_FAIL, DIR_FAIL, DIR_NEEDED, DIR_FAIL},
                {DIR_INVALID, DIR_INVALID, DIR_INVALID, DIR_INVALID}
            };
            
            byte[][] w5_1_directions = {
                {DIR_INVALID, DIR_NEEDED, DIR_INVALID, DIR_FAIL},
                {DIR_INVALID, DIR_INVALID, DIR_INVALID, DIR_INVALID}
            };
            
            byte[] w5_2_directions_1 = {DIR_INVALID, DIR_FAIL, DIR_NEEDED, DIR_INVALID};
            
            byte[] w5_2_directions_2 = {DIR_FAIL, DIR_INVALID, DIR_INVALID, DIR_NEEDED};
            
            byte[] w5_3_directions = {DIR_FAIL, DIR_INVALID, DIR_NEEDED, DIR_INVALID};
            
            for(int i = 0; i < 48; i++)
            {
                byte[] hb1 = {DIR_INVALID, DIR_INVALID, DIR_INVALID, DIR_INVALID};
                byte[] hb2 = {DIR_INVALID, DIR_INVALID, DIR_INVALID, DIR_INVALID};
                byte[] hb3 = {DIR_INVALID, DIR_INVALID, DIR_INVALID, DIR_INVALID};
                byte[] hb4 = {DIR_INVALID, DIR_INVALID, DIR_INVALID, DIR_INVALID};
                byte[] hb5 = {DIR_INVALID, DIR_INVALID, DIR_INVALID, DIR_INVALID};
                byte[] hb6 = {DIR_INVALID, DIR_INVALID, DIR_INVALID, DIR_INVALID};
                byte[] hb7 = {DIR_INVALID, DIR_INVALID, DIR_INVALID, DIR_INVALID};
                for(int x = 0; x < 4; x++)
                {
                    hb1[x] = w2_h_directions_4[i][x];
                    hb2[x] = w2_h_directions_4[i][x];
                    hb3[x] = w2_h_directions_4[i][x];
                    hb4[x] = w2_h_directions_4[i][x];
                    hb5[x] = w2_h_directions_4[i][x];
                    hb6[x] = w2_h_directions_4[i][x];
                    hb7[x] = w2_h_directions_4[i][x];
                }
                
                //hb1 = w5_1_directions;
                //hb2 = w5_2_directions_1;
                //hb3 = w5_3_directions;
        
                if(!isMovementCorrect(((byte)(Byte.toUnsignedInt(rng.getRandom_Temp_Pool(2)))), 0, hb1))
                {
                    success = false;
                    break;
                }
        
                if(!isMovementCorrect(((byte)(Byte.toUnsignedInt(rng.getRandom_Temp_Pool(3)))), 1, hb2))
                {
                    success = false;
                    break;
                }
    
                /*if(!isMovementCorrect(((byte) (Byte.toUnsignedInt(rng.getRandom_Temp_Pool(4)))), 2, hb3))
                {
                    success = false;
                    break;
                }*/
        
                for(int x = 0; x < 32; x++)
                {
                    rng.Randomize_Temp();
                }
            }
            
            if(success)
            {
                System.out.print(frame);
                System.out.print(": ");
                for(int i = 0; i < 9; i++)
                {
                    System.out.print(Byte.toUnsignedInt(rng.getRandom_Pool_Real(i)) + ", ");
                }
                System.out.print("\n");
            }
            
            rng.Randomize();
        }
        
        tileMap = new JPanel();
        //tileMap.setLayout(new GridLayout(9, 30));
        tileMap.setLayout(new GridLayout(9, 46));
        //tileMap.setMaximumSize(new Dimension(480, 144));
        tileMap.setMaximumSize(new Dimension(736, 144));
        
        validMap = new JPanel();
        //validMap.setLayout(new GridLayout(9, 30));
        validMap.setLayout(new GridLayout(9, 46));
        //validMap.setMaximumSize(new Dimension(480, 144));
        validMap.setMaximumSize(new Dimension(736, 144));
    
        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(9, 46));
        rightPanel.setMaximumSize(new Dimension(736, 144));
        
        leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(9, 46));
        leftPanel.setMaximumSize(new Dimension(736, 144));
        
        downPanel = new JPanel();
        downPanel.setLayout(new GridLayout(9, 46));
        downPanel.setMaximumSize(new Dimension(736, 144));
        
        upPanel = new JPanel();
        upPanel.setLayout(new GridLayout(9, 46));
        upPanel.setMaximumSize(new Dimension(736, 144));
    
        hbStartMap = new JPanel();
        hbStartMap.setLayout(new GridLayout(9, 46));
        hbStartMap.setMaximumSize(new Dimension(736, 144));
        
        hbEndMap = new JPanel();
        hbEndMap.setLayout(new GridLayout(9, 46));
        hbEndMap.setMaximumSize(new Dimension(736, 144));
        
        completedTiles = new JCheckBox[]
        {
            new JCheckBox("Lvl1 Done", false),
            new JCheckBox("Lvl2 Done", false),
            new JCheckBox("Lvl3 Done", false),
            new JCheckBox("Lvl4 Done", false),
            new JCheckBox("Lvl5 Done", false),
            new JCheckBox("Lvl6 Done", false),
            new JCheckBox("Lvl7 Done", false),
            new JCheckBox("Lvl8 Done", false),
            new JCheckBox("Lvl9 Done", false),
            new JCheckBox("Lvl10 Done", false),
            new JCheckBox("Fort1 Done", false),
            new JCheckBox("Fort2 Done", false),
            new JCheckBox("Fort3 Done", false),
            new JCheckBox("Quicksand Done", false),
            new JCheckBox("Pyramid Done", false),
            new JCheckBox("Mushroom1 Done", false),
            new JCheckBox("Mushroom2 Done", false),
            new JCheckBox("Mushroom3 Done", false),
            new JCheckBox("Rock1", true),
            new JCheckBox("Rock2", true),
            new JCheckBox("HB1", true),
            new JCheckBox("HB2", true),
            new JCheckBox("HB3", true)
        };
    
        completedTiles[5].setEnabled(false);
        completedTiles[6].setEnabled(false);
        completedTiles[7].setEnabled(false);
        completedTiles[8].setEnabled(false);
        completedTiles[9].setEnabled(false);
        completedTiles[11].setEnabled(false);
        completedTiles[12].setEnabled(false);
        
        /*00*/ world2sprites.add("tiles/global/Level1.png");
        /*01*/ world2sprites.add("tiles/global/Level2.png");
        /*02*/ world2sprites.add("tiles/global/Level3.png");
        /*03*/ world2sprites.add("tiles/global/Level4.png");
        /*04*/ world2sprites.add("tiles/global/Level5.png");
        /*05*/ world2sprites.add("tiles/global/Spade.png");
        /*06*/ world2sprites.add("tiles/global/Start.png");
        /*07*/ world2sprites.add("tiles/w2/w2_plain.png");
        /*08*/ world2sprites.add("tiles/w2/w2_rocky.png");
        /*09*/ world2sprites.add("tiles/w2/w2_tree.png");
        /*10*/ world2sprites.add("tiles/w2/w2_pyramid.png");
        /*11*/ world2sprites.add("tiles/w2/w2_rock.png");
        /*12*/ world2sprites.add("tiles/w2/w2_lock.png");
        /*13*/ world2sprites.add("tiles/w2/w2_pipe.png");
        /*14*/ world2sprites.add("tiles/w2/w2_quicksand.png");
        /*15*/ world2sprites.add("tiles/w2/w2_horizontal.png");
        /*16*/ world2sprites.add("tiles/w2/w2_vertical.png");
        /*17*/ world2sprites.add("tiles/w2/w2_node.png");
        /*18*/ world2sprites.add("tiles/w2/w2_node_L.png");
        /*19*/ world2sprites.add("tiles/w2/w2_node_U.png");
        /*20*/ world2sprites.add("tiles/w2/w2_node_LU.png");
        /*21*/ world2sprites.add("tiles/w2/w2_mushroom_house.png");
        /*22*/ world2sprites.add("tiles/w2/w2_fort.png");
        /*23*/ world2sprites.add("tiles/w2/w2_castle_top.png");
        /*24*/ world2sprites.add("tiles/w2/w2_castle_bottom.png");
        /*25*/ world2sprites.add("tiles/w2/w2_river.png");
        /*26*/ world2sprites.add("tiles/w2/w2_river_L.png");
        /*27*/ world2sprites.add("tiles/w2/w2_river_R.png");
        /*28*/ world2sprites.add("tiles/w2/w2_river_DU.png");
        /*29*/ world2sprites.add("tiles/w2/w2_river_LU.png");
        /*30*/ world2sprites.add("tiles/w2/w2_river_LRU.png");
        /*31*/ world2sprites.add("tiles/w2/w2_river_LRD.png");
        /*32*/ world2sprites.add("tiles/global/LevelDone.png");
        /*33*/ world2sprites.add("tiles/global/Black.png");
        
        /*00*/ world3sprites.add("tiles/global/Level1.png");
        /*01*/ world3sprites.add("tiles/global/Level2.png");
        /*02*/ world3sprites.add("tiles/global/Level3.png");
        /*03*/ world3sprites.add("tiles/global/Level4.png");
        /*04*/ world3sprites.add("tiles/global/Level5.png");
        /*05*/ world3sprites.add("tiles/global/Level6.png");
        /*06*/ world3sprites.add("tiles/global/Level7.png");
        /*07*/ world3sprites.add("tiles/global/Level8.png");
        /*08*/ world3sprites.add("tiles/global/Level9.png");
        /*09*/ world3sprites.add("tiles/global/Spade.png");
        /*10*/ world3sprites.add("tiles/global/Start.png");
        /*11*/ world3sprites.add("tiles/global/LevelDone.png");
        /*12*/ world3sprites.add("tiles/w3/w3_plain.png");
        /*13*/ world3sprites.add("tiles/w3/w3_bush.png");
        /*14*/ world3sprites.add("tiles/w3/w3_tree.png");
        /*15*/ world3sprites.add("tiles/w3/w3_deck.png");
        /*16*/ world3sprites.add("tiles/w3/w3_rock.png");
        /*17*/ world3sprites.add("tiles/w3/w3_lock.png");
        /*18*/ world3sprites.add("tiles/w3/w3_pipe.png");
        /*19*/ world3sprites.add("tiles/w3/w3_horizontal.png");
        /*20*/ world3sprites.add("tiles/w3/w3_vertical.png");
        /*21*/ world3sprites.add("tiles/w3/w3_horizontal_river_path_L.png");
        /*22*/ world3sprites.add("tiles/w3/w3_horizontal_river_path_R.png");
        /*23*/ world3sprites.add("tiles/w3/w3_horizontal_river_path_LR.png");
        /*24*/ world3sprites.add("tiles/w3/w3_vertical_river_path.png");
        /*25*/ world3sprites.add("tiles/w3/w3_vertical_river_path_D.png");
        /*26*/ world3sprites.add("tiles/w3/w3_vertical_river_path_U.png");
        /*27*/ world3sprites.add("tiles/w3/w3_vertical_river_path_DU.png");
        /*28*/ world3sprites.add("tiles/w3/w3_node_L.png");
        /*29*/ world3sprites.add("tiles/w3/w3_node_U.png");
        /*30*/ world3sprites.add("tiles/w3/w3_node_LU.png");
        /*31*/ world3sprites.add("tiles/w3/w3_river_node_L.png");
        /*32*/ world3sprites.add("tiles/w3/w3_river_node_U.png");
        /*33*/ world3sprites.add("tiles/w3/w3_river_node_LU.png");
        /*34*/ world3sprites.add("tiles/w3/w3_mushroom_house.png");
        /*35*/ world3sprites.add("tiles/w3/w3_fort.png");
        /*36*/ world3sprites.add("tiles/w3/w3_castle_top.png");
        /*37*/ world3sprites.add("tiles/w3/w3_castle_bottom.png");
        /*38*/ world3sprites.add("tiles/w3/w3_bridge.png");
        /*39*/ world3sprites.add("tiles/w3/w3_horizontal_bridge_open.png");
        /*40*/ world3sprites.add("tiles/w3/w3_horizontal_bridge_closed.png");
        /*41*/ world3sprites.add("tiles/w3/w3_vertical_bridge_open.png");
        /*42*/ world3sprites.add("tiles/w3/w3_vertical_bridge_closed.png");
        /*43*/ world3sprites.add("tiles/w3/w3_boat_U.png");
        /*44*/ world3sprites.add("tiles/w3/w3_boat_D.png");
        /*45*/ world3sprites.add("tiles/w3/w3_hole.png");
        /*46*/ world3sprites.add("tiles/w3/w3_river.png");
        /*47*/ world3sprites.add("tiles/w3/w3_river_LR.png");
        /*48*/ world3sprites.add("tiles/w3/w3_river_LD.png");
        /*49*/ world3sprites.add("tiles/w3/w3_river_R.png");
        /*50*/ world3sprites.add("tiles/w3/w3_river_RD.png");
        /*51*/ world3sprites.add("tiles/w3/w3_river_RU.png");
        /*52*/ world3sprites.add("tiles/w3/w3_river_DU.png");
        /*53*/ world3sprites.add("tiles/w3/w3_river_edge_L.png");
        /*54*/ world3sprites.add("tiles/w3/w3_river_edge_L_corner_BR.png");
        /*55*/ world3sprites.add("tiles/w3/w3_river_edge_L_corner_TR.png");
        /*56*/ world3sprites.add("tiles/w3/w3_river_edge_L_corner_BR_TR.png");
        /*57*/ world3sprites.add("tiles/w3/w3_river_edge_R.png");
        /*58*/ world3sprites.add("tiles/w3/w3_river_edge_R_corner_BL.png");
        /*59*/ world3sprites.add("tiles/w3/w3_river_edge_R_corner_TL.png");
        /*60*/ world3sprites.add("tiles/w3/w3_river_edge_R_corner_BL_TL.png");
        /*61*/ world3sprites.add("tiles/w3/w3_river_edge_D.png");
        /*62*/ world3sprites.add("tiles/w3/w3_river_edge_D_corner_TL.png");
        /*63*/ world3sprites.add("tiles/w3/w3_river_edge_D_corner_TR.png");
        /*64*/ world3sprites.add("tiles/w3/w3_river_edge_D_corner_TL_TR.png");
        /*65*/ world3sprites.add("tiles/w3/w3_river_edge_U.png");
        /*66*/ world3sprites.add("tiles/w3/w3_river_edge_U_corner_BL.png");
        /*67*/ world3sprites.add("tiles/w3/w3_river_edge_U_corner_BR.png");
        /*68*/ world3sprites.add("tiles/w3/w3_river_edge_LD.png");
        /*69*/ world3sprites.add("tiles/w3/w3_river_edge_LU.png");
        /*70*/ world3sprites.add("tiles/w3/w3_river_edge_RD.png");
        /*71*/ world3sprites.add("tiles/w3/w3_river_edge_RU.png");
        /*72*/ world3sprites.add("tiles/w3/w3_river_corner_all.png");
        /*73*/ world3sprites.add("tiles/w3/w3_river_corner_BL.png");
        /*74*/ world3sprites.add("tiles/w3/w3_river_corner_BL_TL.png");
        /*75*/ world3sprites.add("tiles/w3/w3_river_corner_BL_TR.png");
        /*76*/ world3sprites.add("tiles/w3/w3_river_corner_BR.png");
        /*77*/ world3sprites.add("tiles/w3/w3_river_corner_BR_TR.png");
        /*78*/ world3sprites.add("tiles/w3/w3_river_corner_TL.png");
        /*79*/ world3sprites.add("tiles/w3/w3_river_corner_TL_TR.png");
        /*80*/ world3sprites.add("tiles/w3/w3_river_corner_TR.png");
    
        /*00*/ world4sprites.add("tiles/global/Level1.png");
        /*01*/ world4sprites.add("tiles/global/Level2.png");
        /*02*/ world4sprites.add("tiles/global/Level3.png");
        /*03*/ world4sprites.add("tiles/global/Level4.png");
        /*04*/ world4sprites.add("tiles/global/Level5.png");
        /*05*/ world4sprites.add("tiles/global/Level6.png");
        /*06*/ world4sprites.add("tiles/global/Spade.png");
        /*07*/ world4sprites.add("tiles/global/Start.png");
        /*08*/ world4sprites.add("tiles/global/LevelDone.png");
        /*09*/ world4sprites.add("tiles/global/Black.png");
        /*10*/ world4sprites.add("tiles/w4/w4_plain.png");
        /*11*/ world4sprites.add("tiles/w4/w4_flower.png");
        /*12*/ world4sprites.add("tiles/w4/w4_tree.png");
        /*13*/ world4sprites.add("tiles/w4/w4_rock.png");
        /*14*/ world4sprites.add("tiles/w4/w4_lock.png");
        /*15*/ world4sprites.add("tiles/w4/w4_pipe.png");
        /*16*/ world4sprites.add("tiles/w4/w4_horizontal.png");
        /*17*/ world4sprites.add("tiles/w4/w4_vertical.png");
        /*18*/ world4sprites.add("tiles/w4/w4_horizontal_river_path_L.png");
        /*19*/ world4sprites.add("tiles/w4/w4_vertical_river_path_D.png");
        /*20*/ world4sprites.add("tiles/w4/w4_vertical_river_path_U.png");
        /*21*/ world4sprites.add("tiles/w4/w4_vertical_river_path_DU.png");
        /*22*/ world4sprites.add("tiles/w4/w4_node_L.png");
        /*23*/ world4sprites.add("tiles/w4/w4_node_U.png");
        /*24*/ world4sprites.add("tiles/w4/w4_node_LU.png");
        /*25*/ world4sprites.add("tiles/w4/w4_river_node_U.png");
        /*26*/ world4sprites.add("tiles/w4/w4_mushroom_house.png");
        /*27*/ world4sprites.add("tiles/w4/w4_mushroom_house_red.png");
        /*28*/ world4sprites.add("tiles/w4/w4_fort.png");
        /*29*/ world4sprites.add("tiles/w4/w4_castle_top.png");
        /*30*/ world4sprites.add("tiles/w4/w4_castle_bottom.png");
        /*31*/ world4sprites.add("tiles/w4/w4_bridge.png");
        /*32*/ world4sprites.add("tiles/w4/w4_hole.png");
        /*33*/ world4sprites.add("tiles/w4/w4_river.png");
        /*34*/ world4sprites.add("tiles/w4/w4_river_island.png");
        /*35*/ world4sprites.add("tiles/w4/w4_river_L.png");
        /*36*/ world4sprites.add("tiles/w4/w4_river_LR.png");
        /*37*/ world4sprites.add("tiles/w4/w4_river_LD.png");
        /*38*/ world4sprites.add("tiles/w4/w4_river_LU.png");
        /*39*/ world4sprites.add("tiles/w4/w4_river_R.png");
        /*40*/ world4sprites.add("tiles/w4/w4_river_RD.png");
        /*41*/ world4sprites.add("tiles/w4/w4_river_RU.png");
        /*42*/ world4sprites.add("tiles/w4/w4_river_DU.png");
        /*43*/ world4sprites.add("tiles/w4/w4_river_edge_L.png");
        /*44*/ world4sprites.add("tiles/w4/w4_river_edge_R.png");
        /*45*/ world4sprites.add("tiles/w4/w4_river_edge_D.png");
        /*46*/ world4sprites.add("tiles/w4/w4_river_edge_D_corner_TL.png");
        /*47*/ world4sprites.add("tiles/w4/w4_river_edge_D_corner_TR.png");
        /*48*/ world4sprites.add("tiles/w4/w4_river_edge_D_corner_TL_TR.png");
        /*49*/ world4sprites.add("tiles/w4/w4_river_edge_U.png");
        /*50*/ world4sprites.add("tiles/w4/w4_river_edge_U_corner_BL.png");
        /*51*/ world4sprites.add("tiles/w4/w4_river_edge_U_corner_BL_BR.png");
        /*52*/ world4sprites.add("tiles/w4/w4_river_edge_LD.png");
        /*53*/ world4sprites.add("tiles/w4/w4_river_edge_LU.png");
        /*54*/ world4sprites.add("tiles/w4/w4_river_edge_RD.png");
        /*55*/ world4sprites.add("tiles/w4/w4_river_edge_RU.png");
        /*56*/ world4sprites.add("tiles/w4/w4_river_corner_BL.png");
        /*57*/ world4sprites.add("tiles/w4/w4_river_corner_BL_BR.png");
        /*58*/ world4sprites.add("tiles/w4/w4_river_corner_BL_TR.png");
        /*59*/ world4sprites.add("tiles/w4/w4_river_corner_BR.png");
        /*60*/ world4sprites.add("tiles/w4/w4_river_corner_BR_TL.png");
        /*61*/ world4sprites.add("tiles/w4/w4_river_corner_BR_TR.png");
        /*62*/ world4sprites.add("tiles/w4/w4_river_corner_TL.png");
        /*63*/ world4sprites.add("tiles/w4/w4_river_corner_TL_TR.png");
        /*64*/ world4sprites.add("tiles/w4/w4_river_corner_TR.png");
    
        /*00*/ world5sprites.add("tiles/global/Level1.png");
        /*01*/ world5sprites.add("tiles/global/Level2.png");
        /*02*/ world5sprites.add("tiles/global/Level3.png");
        /*03*/ world5sprites.add("tiles/global/Level4.png");
        /*04*/ world5sprites.add("tiles/global/Level5.png");
        /*05*/ world5sprites.add("tiles/global/Level6.png");
        /*06*/ world5sprites.add("tiles/global/Level7.png");
        /*07*/ world5sprites.add("tiles/global/Level8.png");
        /*08*/ world5sprites.add("tiles/global/Level9.png");
        /*09*/ world5sprites.add("tiles/global/LevelDone.png");
        /*10*/ world5sprites.add("tiles/global/Black.png");
        /*11*/ world5sprites.add("tiles/w5/w5_spade.png");
        /*12*/ world5sprites.add("tiles/w5/w5_start.png");
        /*13*/ world5sprites.add("tiles/w5/w5_pipe.png");
        /*14*/ world5sprites.add("tiles/w5/w5_castle_top.png");
        /*15*/ world5sprites.add("tiles/w5/w5_castle_bottom.png");
        /*16*/ world5sprites.add("tiles/w5/w5_land_plain.png");
        /*17*/ world5sprites.add("tiles/w5/w5_land_bush.png");
        /*18*/ world5sprites.add("tiles/w5/w5_land_horizontal.png");
        /*19*/ world5sprites.add("tiles/w5/w5_land_vertical.png");
        /*20*/ world5sprites.add("tiles/w5/w5_land_node.png");
        /*21*/ world5sprites.add("tiles/w5/w5_land_node_L.png");
        /*22*/ world5sprites.add("tiles/w5/w5_land_node_U.png");
        /*23*/ world5sprites.add("tiles/w5/w5_land_node_LU.png");
        /*24*/ world5sprites.add("tiles/w5/w5_land_mushroom_house.png");
        /*25*/ world5sprites.add("tiles/w5/w5_land_fort.png");
        /*26*/ world5sprites.add("tiles/w5/w5_land_twisty_castle.png");
        /*27*/ world5sprites.add("tiles/w5/w5_river.png");
        /*28*/ world5sprites.add("tiles/w5/w5_river_L.png");
        /*29*/ world5sprites.add("tiles/w5/w5_river_LR.png");
        /*30*/ world5sprites.add("tiles/w5/w5_river_LU.png");
        /*31*/ world5sprites.add("tiles/w5/w5_river_R.png");
        /*32*/ world5sprites.add("tiles/w5/w5_river_RD.png");
        /*33*/ world5sprites.add("tiles/w5/w5_river_DU.png");
        /*34*/ world5sprites.add("tiles/w5/w5_river_edge_L.png");
        /*35*/ world5sprites.add("tiles/w5/w5_river_edge_U.png");
        /*36*/ world5sprites.add("tiles/w5/w5_river_edge_LU.png");
        /*37*/ world5sprites.add("tiles/w5/w5_river_corner_TL.png");
        /*38*/ world5sprites.add("tiles/w5/w5_river_corner_TL_TR.png");
        /*39*/ world5sprites.add("tiles/w5/w5_sky_plain.png");
        /*40*/ world5sprites.add("tiles/w5/w5_sky_star.png");
        /*41*/ world5sprites.add("tiles/w5/w5_sky_lock.png");
        /*42*/ world5sprites.add("tiles/w5/w5_sky_horizontal.png");
        /*43*/ world5sprites.add("tiles/w5/w5_sky_vertical.png");
        /*44*/ world5sprites.add("tiles/w5/w5_sky_node.png");
        /*45*/ world5sprites.add("tiles/w5/w5_sky_node_L.png");
        /*46*/ world5sprites.add("tiles/w5/w5_sky_node_U.png");
        /*47*/ world5sprites.add("tiles/w5/w5_sky_node_LU.png");
        /*48*/ world5sprites.add("tiles/w5/w5_sky_mushroom_house.png");
        /*49*/ world5sprites.add("tiles/w5/w5_sky_fort.png");
        /*50*/ world5sprites.add("tiles/w5/w5_sky_edge_R1.png");
        /*51*/ world5sprites.add("tiles/w5/w5_sky_edge_R2.png");
        /*52*/ world5sprites.add("tiles/w5/w5_sky_edge_D1.png");
        /*53*/ world5sprites.add("tiles/w5/w5_sky_edge_D2.png");
        /*54*/ world5sprites.add("tiles/w5/w5_sky_edge_U.png");
        /*55*/ world5sprites.add("tiles/w5/w5_sky_edge_RD.png");
        /*56*/ world5sprites.add("tiles/w5/w5_sky_corner_BL.png");
        /*57*/ world5sprites.add("tiles/w5/w5_sky_corner_BR.png");
        /*58*/ world5sprites.add("tiles/w5/w5_sky_corner_TL.png");
        /*59*/ world5sprites.add("tiles/w5/w5_sky_tiny_1.png");
        /*60*/ world5sprites.add("tiles/w5/w5_sky_tiny_2.png");
        /*61*/ world5sprites.add("tiles/w5/w5_sky_tiny_3.png");
        /*62*/ world5sprites.add("tiles/w5/w5_sky_tiny_4.png");
        /*63*/ world5sprites.add("tiles/w5/w5_sky_tiny_5.png");
        /*64*/ world5sprites.add("tiles/w5/w5_sky_tiny_6.png");
        /*65*/ world5sprites.add("tiles/w5/w5_sky_tiny_7.png");
        /*66*/ world5sprites.add("tiles/w5/w5_sky_tiny_8.png");
        /*67*/ world5sprites.add("tiles/w5/w5_sky_tiny_9.png");
        /*68*/ world5sprites.add("tiles/w5/w5_sky_tiny_10.png");
        /*69*/ world5sprites.add("tiles/w5/w5_bridge.png");
    
        /*00*/ world6sprites.add("tiles/global/Level1.png");
        /*01*/ world6sprites.add("tiles/global/Level2.png");
        /*02*/ world6sprites.add("tiles/global/Level3.png");
        /*03*/ world6sprites.add("tiles/global/Level4.png");
        /*04*/ world6sprites.add("tiles/global/Level5.png");
        /*05*/ world6sprites.add("tiles/global/Level6.png");
        /*06*/ world6sprites.add("tiles/global/Level7.png");
        /*07*/ world6sprites.add("tiles/global/Level8.png");
        /*08*/ world6sprites.add("tiles/global/Level9.png");
        /*09*/ world6sprites.add("tiles/global/Level10.png");
        /*10*/ world6sprites.add("tiles/global/Spade.png");
        /*11*/ world6sprites.add("tiles/global/LevelDone.png");
        /*12*/ world6sprites.add("tiles/global/Black.png");
        /*13*/ world6sprites.add("tiles/w6/w6_start.png");
        /*14*/ world6sprites.add("tiles/w6/w6_pipe.png");
        /*15*/ world6sprites.add("tiles/w6/w6_castle_top.png");
        /*16*/ world6sprites.add("tiles/w6/w6_castle_bottom.png");
        /*17*/ world6sprites.add("tiles/w6/w6_plain.png");
        /*18*/ world6sprites.add("tiles/w6/w6_bush.png");
        /*19*/ world6sprites.add("tiles/w6/w6_icicle.png");
        /*20*/ world6sprites.add("tiles/w6/w6_rock.png");
        /*21*/ world6sprites.add("tiles/w6/w6_lock.png");
        /*22*/ world6sprites.add("tiles/w6/w6_horizontal.png");
        /*23*/ world6sprites.add("tiles/w6/w6_vertical.png");
        /*24*/ world6sprites.add("tiles/w6/w6_horizontal_river_path_LR.png");
        /*25*/ world6sprites.add("tiles/w6/w6_vertical_river_path_DU.png");
        /*26*/ world6sprites.add("tiles/w6/w6_node.png");
        /*27*/ world6sprites.add("tiles/w6/w6_node_L.png");
        /*28*/ world6sprites.add("tiles/w6/w6_node_U.png");
        /*29*/ world6sprites.add("tiles/w6/w6_node_LU.png");
        /*30*/ world6sprites.add("tiles/w6/w6_mushroom_house.png");
        /*31*/ world6sprites.add("tiles/w6/w6_fort.png");
        /*32*/ world6sprites.add("tiles/w6/w6_bridge.png");
        /*33*/ world6sprites.add("tiles/w6/w6_hole.png");
        /*34*/ world6sprites.add("tiles/w6/w6_river.png");
        /*35*/ world6sprites.add("tiles/w6/w6_river_island.png");
        /*36*/ world6sprites.add("tiles/w6/w6_river_LR.png");
        /*37*/ world6sprites.add("tiles/w6/w6_river_LD.png");
        /*38*/ world6sprites.add("tiles/w6/w6_river_LU.png");
        /*39*/ world6sprites.add("tiles/w6/w6_river_RD.png");
        /*40*/ world6sprites.add("tiles/w6/w6_river_RU.png");
        /*41*/ world6sprites.add("tiles/w6/w6_river_DU.png");
        /*42*/ world6sprites.add("tiles/w6/w6_river_edge_L.png");
        /*43*/ world6sprites.add("tiles/w6/w6_river_edge_L_corner_BR_TR.png");
        /*44*/ world6sprites.add("tiles/w6/w6_river_edge_R.png");
        /*45*/ world6sprites.add("tiles/w6/w6_river_edge_R_corner_BL.png");
        /*46*/ world6sprites.add("tiles/w6/w6_river_edge_R_corner_BL_TL.png");
        /*47*/ world6sprites.add("tiles/w6/w6_river_edge_D.png");
        /*48*/ world6sprites.add("tiles/w6/w6_river_edge_D_corner_TR.png");
        /*49*/ world6sprites.add("tiles/w6/w6_river_edge_D_corner_TL_TR.png");
        /*50*/ world6sprites.add("tiles/w6/w6_river_edge_U.png");
        /*51*/ world6sprites.add("tiles/w6/w6_river_edge_LD.png");
        /*52*/ world6sprites.add("tiles/w6/w6_river_edge_LU.png");
        /*53*/ world6sprites.add("tiles/w6/w6_river_edge_RD.png");
        /*54*/ world6sprites.add("tiles/w6/w6_river_edge_RU.png");
        /*55*/ world6sprites.add("tiles/w6/w6_river_corner_BL.png");
        /*56*/ world6sprites.add("tiles/w6/w6_river_corner_BL_BR.png");
        /*57*/ world6sprites.add("tiles/w6/w6_river_corner_BR.png");
        /*58*/ world6sprites.add("tiles/w6/w6_river_corner_TL.png");
        /*59*/ world6sprites.add("tiles/w6/w6_river_corner_TL_TR.png");
        /*60*/ world6sprites.add("tiles/w6/w6_river_corner_TR.png");
        
        /*00*/ validSprites.add("red.png");
        /*01*/ validSprites.add("green.png");
        /*02*/ validSprites.add("light_blue.png");
        /*03*/ validSprites.add("magenta.png");
    
        hb1Start = new JLabel(new ImageIcon(this.getClass().getResource("tiles/bros/hammer_bro_1_R.png")));
        hb2Start = new JLabel(new ImageIcon(this.getClass().getResource("tiles/bros/hammer_bro_1_L.png")));
        hb3Start = new JLabel(new ImageIcon(this.getClass().getResource("tiles/bros/hammer_bro_1_L.png")));
    
        hb1End = new JLabel(new ImageIcon(this.getClass().getResource("tiles/bros/hammer_bro_1_R_blue.png")));
        hb2End = new JLabel(new ImageIcon(this.getClass().getResource("tiles/bros/hammer_bro_1_L_blue.png")));
        hb3End = new JLabel(new ImageIcon(this.getClass().getResource("tiles/bros/hammer_bro_1_L_blue.png")));
        
        for(int y = 0; y < 9; y++)
        {
            for(int x = 0; x < 46; x++)
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(world2sprites.get(world2tiles[y][x])))));
            }
        }
        
        /*for(int y = 0; y < 9; y++)
        {
            for(int x = 0; x < 30; x++)
            {
                validMap.add(new JLabel(new ImageIcon(this.getClass().getResource(validSprites.get(world2valid[y][x])))));
            }
        }*/
        
        for(int y = 0; y < 9; y++)
        {
            for(int x = 0; x < 46; x++)
            {
                if(x == bros[0].getRealXPos()+8 && y == bros[0].getRealYPos())
                {
                    hbStartMap.add(hb1Start);
                }
                else if(x == bros[1].getRealXPos()+8 && y == bros[1].getRealYPos())
                {
                    hbStartMap.add(hb2Start);
                }
                else if(x == bros[2].getRealXPos()+8 && y == bros[2].getRealYPos())
                {
                    hbStartMap.add(hb3Start);
                }
                else
                {
                    hbStartMap.add(new JLabel(new ImageIcon(this.getClass().getResource("tiles/bros/blank.png"))));
                }
                
                if(x == bros[3].getRealXPos()+8 && y == bros[3].getRealYPos())
                {
                    hbEndMap.add(hb1End);
                }
                else if(x == bros[4].getRealXPos()+8 && y == bros[4].getRealYPos())
                {
                    hbEndMap.add(hb2End);
                }
                else if(x == bros[5].getRealXPos()+8 && y == bros[5].getRealYPos())
                {
                    hbEndMap.add(hb3End);
                }
                else
                {
                    hbEndMap.add(new JLabel(new ImageIcon(this.getClass().getResource("tiles/bros/blank.png"))));
                }
            }
        }
        
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(4, 1));
        optionsPanel.setPreferredSize(new Dimension(250,750));
        
        hammerBroOptionsPanel = new JPanel();
        hammerBroOptionsPanel.setLayout(new GridLayout(3, 2));
        
        maps = new JPanel();
        maps.setLayout(new GridLayout(2, 1));
        
        mapPanel = new JPanel();
        mapPanel.setLayout(new OverlayLayout(mapPanel));
    
        directionPanel = new JPanel();
        directionPanel.setLayout(new OverlayLayout(directionPanel));
    
        drawDirections(2);
        
        checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new GridLayout(completedTiles.length / 2, 2));
        
        for(int i = 0; i < completedTiles.length; i++)
        {
            checkBoxPanel.add(completedTiles[i]);
        }
        
        hammerBroMenu = new JComboBox(new String[]{
            "HB1 Start", "HB2 Start", "HB3 Start"
        });
    
        worldMenu = new JComboBox(new String[]{
            "World 2", "World 3", "World 4", "World 5", "World 6"
        });
        
        increaseX = new JButton("↑");
        decreaseX = new JButton("↓");
        
        increaseY = new JButton("↑");
        decreaseY = new JButton("↓");
        
        xPosition = new JTextField("6");
        xPosition.setEditable(false);
        yPosition = new JTextField("1");
        yPosition.setEditable(false);
        
        increaseX.addActionListener(e ->
        {
            int currentX = Integer.parseInt(xPosition.getText());
            int newX = currentX + 1;
            if(currentX < 14)
            {
                xPosition.setText(Integer.toString(newX));
                bros[hammerBroMenu.getSelectedIndex()].setXPos(newX);
                bros[hammerBroMenu.getSelectedIndex()+3].setXPos(newX);
                
                updateHammerPosition();
            }
        });
    
        decreaseX.addActionListener(e ->
        {
            int currentX = Integer.parseInt(xPosition.getText());
            int newX = currentX - 1;
            if(currentX > 0)
            {
                xPosition.setText(Integer.toString(newX));
                bros[hammerBroMenu.getSelectedIndex()].setXPos(newX);
                bros[hammerBroMenu.getSelectedIndex()+3].setXPos(newX);
    
                updateHammerPosition();
            }
        });
        
        increaseY.addActionListener(e ->
        {
            int currentY = Integer.parseInt(yPosition.getText());
            int newY = currentY + 1;
            if(currentY < 4)
            {
                yPosition.setText(Integer.toString(newY));
                bros[hammerBroMenu.getSelectedIndex()].setYPos(newY);
                bros[hammerBroMenu.getSelectedIndex()+3].setYPos(newY);
                
                updateHammerPosition();
            }
        });
    
        decreaseY.addActionListener(e ->
        {
            int currentY = Integer.parseInt(yPosition.getText());
            int newY = currentY - 1;
            if(currentY > 0)
            {
                yPosition.setText(Integer.toString(newY));
                bros[hammerBroMenu.getSelectedIndex()].setYPos(newY);
                bros[hammerBroMenu.getSelectedIndex()+3].setYPos(newY);
    
                updateHammerPosition();
            }
        });
        
        hammerBroMenu.addActionListener(e ->
        {
            xPosition.setText(Integer.toString(bros[hammerBroMenu.getSelectedIndex()].getXPos()));
            yPosition.setText(Integer.toString(bros[hammerBroMenu.getSelectedIndex()].getYPos()));
        });
        
        worldMenu.addActionListener(e ->
        {
            if(worldMenu.getSelectedIndex() == 0)
            {
                for(int y = 0; y < 9; y++)
                {
                    for(int x = 0; x < 46; x++)
                    {
                        int index = x + (y * 46);
                        tileMap.remove(index);
                        tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(world2sprites.get(world2tiles[y][x])))), index);
                    }
                }
                tileMap.updateUI();
                
                bros[0].setXPos(6);
                bros[1].setXPos(9);
                bros[2].setXPos(11);
                bros[3].setXPos(6);
                bros[4].setXPos(9);
                bros[5].setXPos(11);
                
                bros[0].setYPos(1);
                bros[1].setYPos(2);
                bros[2].setYPos(2);
                bros[3].setYPos(1);
                bros[4].setYPos(2);
                bros[5].setYPos(2);
    
                updateHammerPosition();
    
                xPosition.setText(Integer.toString(bros[hammerBroMenu.getSelectedIndex()].getXPos()));
                yPosition.setText(Integer.toString(bros[hammerBroMenu.getSelectedIndex()].getYPos()));
                
                completedTiles[5].setEnabled(false);
                completedTiles[6].setEnabled(false);
                completedTiles[7].setEnabled(false);
                completedTiles[8].setEnabled(false);
                completedTiles[9].setEnabled(false);
                completedTiles[10].setEnabled(true);
                completedTiles[11].setEnabled(false);
                completedTiles[12].setEnabled(false);
                completedTiles[13].setEnabled(true);
                completedTiles[14].setEnabled(true);
                completedTiles[15].setEnabled(true);
                completedTiles[16].setEnabled(true);
                completedTiles[17].setEnabled(true);
                completedTiles[18].setEnabled(true);
                completedTiles[19].setEnabled(true);
                completedTiles[20].setEnabled(true);
                completedTiles[21].setEnabled(true);
                completedTiles[22].setEnabled(true);
            }
            else if(worldMenu.getSelectedIndex() == 1)
            {
                for(int y = 0; y < 9; y++)
                {
                    for(int x = 0; x < 46; x++)
                    {
                        int index = x + (y * 46);
                        tileMap.remove(index);
                        tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(world3sprites.get(world3tiles[y][x])))), index);
                    }
                }
                tileMap.updateUI();
    
                bros[0].setXPos(3);
                bros[1].setXPos(6);
                bros[2].setXPos(0);
                bros[3].setXPos(3);
                bros[4].setXPos(6);
                bros[5].setXPos(0);
                
                bros[0].setYPos(4);
                bros[1].setYPos(1);
                bros[2].setYPos(0);
                bros[3].setYPos(4);
                bros[4].setYPos(1);
                bros[5].setYPos(0);
    
                updateHammerPosition();
    
                xPosition.setText(Integer.toString(bros[hammerBroMenu.getSelectedIndex()].getXPos()));
                yPosition.setText(Integer.toString(bros[hammerBroMenu.getSelectedIndex()].getYPos()));
    
                completedTiles[5].setEnabled(true);
                completedTiles[6].setEnabled(true);
                completedTiles[7].setEnabled(true);
                completedTiles[8].setEnabled(true);
                completedTiles[9].setEnabled(false);
                completedTiles[10].setEnabled(true);
                completedTiles[11].setEnabled(true);
                completedTiles[12].setEnabled(false);
                completedTiles[13].setEnabled(false);
                completedTiles[14].setEnabled(false);
                completedTiles[15].setEnabled(true);
                completedTiles[16].setEnabled(true);
                completedTiles[17].setEnabled(false);
                completedTiles[18].setEnabled(true);
                completedTiles[19].setEnabled(false);
                completedTiles[20].setEnabled(true);
                completedTiles[21].setEnabled(true);
                completedTiles[22].setEnabled(true);
            }
            else if(worldMenu.getSelectedIndex() == 2)
            {
                for(int y = 0; y < 9; y++)
                {
                    for(int x = 0; x < 46; x++)
                    {
                        int index = x + (y * 46);
                        tileMap.remove(index);
                        tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(world4sprites.get(world4tiles[y][x])))), index);
                    }
                }
                tileMap.updateUI();
                
                bros[0].setXPos(10);
                bros[1].setXPos(7);
                bros[2].setXPos(5);
                bros[3].setXPos(10);
                bros[4].setXPos(7);
                bros[5].setXPos(5);
                
                bros[0].setYPos(1);
                bros[1].setYPos(2);
                bros[2].setYPos(2);
                bros[3].setYPos(1);
                bros[4].setYPos(2);
                bros[5].setYPos(2);
                
                updateHammerPosition();
                
                xPosition.setText(Integer.toString(bros[hammerBroMenu.getSelectedIndex()].getXPos()));
                yPosition.setText(Integer.toString(bros[hammerBroMenu.getSelectedIndex()].getYPos()));
                
                completedTiles[5].setEnabled(true);
                completedTiles[6].setEnabled(false);
                completedTiles[7].setEnabled(false);
                completedTiles[8].setEnabled(false);
                completedTiles[9].setEnabled(false);
                completedTiles[10].setEnabled(true);
                completedTiles[11].setEnabled(true);
                completedTiles[12].setEnabled(false);
                completedTiles[13].setEnabled(false);
                completedTiles[14].setEnabled(false);
                completedTiles[15].setEnabled(true);
                completedTiles[16].setEnabled(true);
                completedTiles[17].setEnabled(true);
                completedTiles[18].setEnabled(false);
                completedTiles[19].setEnabled(false);
                completedTiles[20].setEnabled(true);
                completedTiles[21].setEnabled(true);
                completedTiles[22].setEnabled(true);
            }
            else if(worldMenu.getSelectedIndex() == 3)
            {
                for(int y = 0; y < 9; y++)
                {
                    for(int x = 0; x < 46; x++)
                    {
                        int index = x + (y * 46);
                        tileMap.remove(index);
                        tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(world5sprites.get(world5tiles[y][x])))), index);
                    }
                }
                tileMap.updateUI();
    
                bros[0].setXPos(4);
                bros[1].setXPos(11);
                bros[2].setXPos(4);
                bros[3].setXPos(4);
                bros[4].setXPos(11);
                bros[5].setXPos(4);
    
                bros[0].setYPos(0);
                bros[1].setYPos(3);
                bros[2].setYPos(2);
                bros[3].setYPos(0);
                bros[4].setYPos(3);
                bros[5].setYPos(2);
    
                updateHammerPosition();
    
                xPosition.setText(Integer.toString(bros[hammerBroMenu.getSelectedIndex()].getXPos()));
                yPosition.setText(Integer.toString(bros[hammerBroMenu.getSelectedIndex()].getYPos()));
    
                completedTiles[5].setEnabled(true);
                completedTiles[6].setEnabled(true);
                completedTiles[7].setEnabled(true);
                completedTiles[8].setEnabled(true);
                completedTiles[9].setEnabled(false);
                completedTiles[10].setEnabled(true);
                completedTiles[11].setEnabled(true);
                completedTiles[12].setEnabled(false);
                completedTiles[13].setEnabled(false);
                completedTiles[14].setEnabled(false);
                completedTiles[15].setEnabled(true);
                completedTiles[16].setEnabled(true);
                completedTiles[17].setEnabled(true);
                completedTiles[18].setEnabled(false);
                completedTiles[19].setEnabled(false);
                completedTiles[20].setEnabled(true);
                completedTiles[21].setEnabled(true);
                completedTiles[22].setEnabled(true);
            }
            else
            {
                for(int y = 0; y < 9; y++)
                {
                    for(int x = 0; x < 46; x++)
                    {
                        int index = x + (y * 46);
                        tileMap.remove(index);
                        tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(world6sprites.get(world6tiles[y][x])))), index);
                    }
                }
                tileMap.updateUI();
                
                bros[0].setXPos(5);
                bros[1].setXPos(11);
                bros[2].setXPos(10);
                bros[3].setXPos(5);
                bros[4].setXPos(11);
                bros[5].setXPos(10);
        
                bros[0].setYPos(2);
                bros[1].setYPos(4);
                bros[2].setYPos(3);
                bros[3].setYPos(2);
                bros[4].setYPos(4);
                bros[5].setYPos(3);
        
                updateHammerPosition();
        
                xPosition.setText(Integer.toString(bros[hammerBroMenu.getSelectedIndex()].getXPos()));
                yPosition.setText(Integer.toString(bros[hammerBroMenu.getSelectedIndex()].getYPos()));
    
                completedTiles[5].setEnabled(true);
                completedTiles[6].setEnabled(true);
                completedTiles[7].setEnabled(true);
                completedTiles[8].setEnabled(true);
                completedTiles[9].setEnabled(true);
                completedTiles[10].setEnabled(true);
                completedTiles[11].setEnabled(true);
                completedTiles[12].setEnabled(true);
                completedTiles[13].setEnabled(false);
                completedTiles[14].setEnabled(false);
                completedTiles[15].setEnabled(true);
                completedTiles[16].setEnabled(true);
                completedTiles[17].setEnabled(false);
                completedTiles[18].setEnabled(true);
                completedTiles[19].setEnabled(true);
                completedTiles[20].setEnabled(true);
                completedTiles[21].setEnabled(true);
                completedTiles[22].setEnabled(true);
            }
    
            drawDirections(worldMenu.getSelectedIndex()+2);
            
            maps.updateUI();
        });
        
        completedTiles[0].addActionListener(e ->
        {
            boolean isSelected = completedTiles[0].isSelected();
            int world = worldMenu.getSelectedIndex()+2;
            int index;
            String sel,non;
            switch(world)
            {
                case 4:
                    sel = world4sprites.get(8);
                    non = world4sprites.get(0);
                    index = 35 + (6 * 46);
                    break;
                case 5:
                    sel = world5sprites.get(9);
                    non = world5sprites.get(0);
                    index = 10 + (2 * 46);
                    break;
                case 6:
                    sel = world6sprites.get(11);
                    non = world6sprites.get(0);
                    index = 5 + (4 * 46);
                    break;
                default:
                    sel = world2sprites.get(32);
                    non = world2sprites.get(0);
                    index = 11 + (4 * 46);
                    break;
            }
            tileMap.remove(index);
            if(isSelected)
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(sel))), index);
            }
            else
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(non))), index);
            }
            
            validMaps.level1Done(isSelected, world);
            drawDirections(world);
            
            maps.updateUI();
        });
    
        completedTiles[1].addActionListener(e ->
        {
            boolean isSelected = completedTiles[1].isSelected();
            int world = worldMenu.getSelectedIndex()+2;
            int index;
            String sel,non;
            switch(world)
            {
                case 4:
                    sel = world4sprites.get(8);
                    non = world4sprites.get(1);
                    index = 35 + (2 * 46);
                    break;
                case 5:
                    sel = world5sprites.get(9);
                    non = world5sprites.get(1);
                    index = 12;
                    break;
                case 6:
                    sel = world6sprites.get(11);
                    non = world6sprites.get(1);
                    index = 9 + (2 * 46);
                    break;
                default:
                    sel = world2sprites.get(32);
                    non = world2sprites.get(1);
                    index = 15;
                    break;
            }
            tileMap.remove(index);
            if(isSelected)
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(sel))), index);
            }
            else
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(non))), index);
            }
        
            validMaps.level2Done(isSelected, world);
            drawDirections(world);
        
            maps.updateUI();
        });
    
        completedTiles[2].addActionListener(e ->
        {
            boolean isSelected = completedTiles[2].isSelected();
            int world = worldMenu.getSelectedIndex()+2;
            int index;
            String sel,non;
            switch(world)
            {
                case 4:
                    sel = world4sprites.get(8);
                    non = world4sprites.get(2);
                    index = 31 + (2 * 46);
                    break;
                case 5:
                    sel = world5sprites.get(9);
                    non = world5sprites.get(2);
                    index = 16;
                    break;
                case 6:
                    sel = world6sprites.get(11);
                    non = world6sprites.get(2);
                    index = 11 + (6 * 46);
                    break;
                default:
                    sel = world2sprites.get(32);
                    non = world2sprites.get(2);
                    index = 19 + (2 * 46);
                    break;
            }
            tileMap.remove(index);
            if(isSelected)
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(sel))), index);
            }
            else
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(non))), index);
            }
        
            validMaps.level3Done(isSelected, world);
            drawDirections(world);
        
            maps.updateUI();
        });
    
        completedTiles[3].addActionListener(e ->
        {
            boolean isSelected = completedTiles[3].isSelected();
            int world = worldMenu.getSelectedIndex()+2;
            int index;
            String sel,non;
            switch(world)
            {
                case 4:
                    sel = world4sprites.get(8);
                    non = world4sprites.get(3);
                    index = 27 + (6 * 46);
                    break;
                case 5:
                    sel = world5sprites.get(9);
                    non = world5sprites.get(3);
                    index = 32 + (4 * 46);
                    break;
                case 6:
                    sel = world6sprites.get(11);
                    non = world6sprites.get(3);
                    index = 15 + (6 * 46);
                    break;
                default:
                    sel = world2sprites.get(32);
                    non = world2sprites.get(3);
                    index = 25;
                    break;
            }
            tileMap.remove(index);
            if(isSelected)
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(sel))), index);
            }
            else
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(non))), index);
            }
        
            validMaps.level4Done(isSelected, world);
            drawDirections(world);
        
            maps.updateUI();
        });
    
        completedTiles[4].addActionListener(e ->
        {
            boolean isSelected = completedTiles[4].isSelected();
            int world = worldMenu.getSelectedIndex()+2;
            int index;
            String sel,non;
            switch(world)
            {
                case 4:
                    sel = world4sprites.get(8);
                    non = world4sprites.get(4);
                    index = 19 + (6 * 46);
                    break;
                case 5:
                    sel = world5sprites.get(9);
                    non = world5sprites.get(4);
                    index = 36 + (2 * 46);
                    break;
                case 6:
                    sel = world6sprites.get(11);
                    non = world6sprites.get(4);
                    index = 19 + (8 * 46);
                    break;
                default:
                    sel = world2sprites.get(32);
                    non = world2sprites.get(4);
                    index = 23 + (6 * 46);
                    break;
            }
            tileMap.remove(index);
            if(isSelected)
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(sel))), index);
            }
            else
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(non))), index);
            }
        
            validMaps.level5Done(isSelected, world);
            drawDirections(world);
        
            maps.updateUI();
        });
    
        completedTiles[5].addActionListener(e ->
        {
            boolean isSelected = completedTiles[5].isSelected();
            int world = worldMenu.getSelectedIndex()+2;
            int index;
            String sel,non;
            switch(world)
            {
                case 4:
                    sel = world4sprites.get(8);
                    non = world4sprites.get(5);
                    index = 19 + (2 * 46);
                    break;
                case 5:
                    sel = world5sprites.get(9);
                    non = world5sprites.get(5);
                    index = 36 + (8 * 46);
                    break;
                case 6:
                    sel = world6sprites.get(11);
                    non = world6sprites.get(5);
                    index = 19 + (4 * 46);
                    break;
                default:
                    sel = "";
                    non = "";
                    index = -1;
                    System.out.println("completedTiles[5]... how w=" + world);
                    break;
            }
            tileMap.remove(index);
            if(isSelected)
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(sel))), index);
            }
            else
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(non))), index);
            }
        
            validMaps.level6Done(isSelected, world);
            drawDirections(world);
        
            maps.updateUI();
        });
    
        completedTiles[6].addActionListener(e ->
        {
            boolean isSelected = completedTiles[6].isSelected();
            int world = worldMenu.getSelectedIndex()+2;
            int index;
            String sel,non;
            if(world == 5)
            {
                sel = world5sprites.get(9);
                non = world5sprites.get(6);
                index = 34 + (6 * 46);
            }
            else
            {
                sel = world6sprites.get(11);
                non = world6sprites.get(6);
                index = 27 + (6 * 46);
            }
            tileMap.remove(index);
            if(isSelected)
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(sel))), index);
            }
            else
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(non))), index);
            }
        
            validMaps.level7Done(isSelected, world);
            drawDirections(world);
        
            maps.updateUI();
        });
    
        completedTiles[7].addActionListener(e ->
        {
            boolean isSelected = completedTiles[7].isSelected();
            int world = worldMenu.getSelectedIndex()+2;
            int index;
            String sel,non;
            if(world == 5)
            {
                sel = world5sprites.get(9);
                non = world5sprites.get(7);
                index = 30 + (8 * 46);
            }
            else
            {
                sel = world6sprites.get(11);
                non = world6sprites.get(7);
                index = 27 + (4 * 46);
            }
            tileMap.remove(index);
            if(isSelected)
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(sel))), index);
            }
            else
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(non))), index);
            }
        
            validMaps.level8Done(isSelected, world);
            drawDirections(world);
        
            maps.updateUI();
        });
    
        completedTiles[8].addActionListener(e ->
        {
            boolean isSelected = completedTiles[8].isSelected();
            int world = worldMenu.getSelectedIndex()+2;
            int index;
            String sel,non;
            if(world == 5)
            {
                sel = world5sprites.get(9);
                non = world5sprites.get(8);
                index = 28 + (8 * 46);
            }
            else
            {
                sel = world6sprites.get(11);
                non = world6sprites.get(8);
                index = 31 + (2 * 46);
            }
            tileMap.remove(index);
            if(isSelected)
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(sel))), index);
            }
            else
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(non))), index);
            }
        
            validMaps.level9Done(isSelected, world);
            drawDirections(world);
        
            maps.updateUI();
        });
    
        completedTiles[9].addActionListener(e ->
        {
            boolean isSelected = completedTiles[9].isSelected();
            int world = worldMenu.getSelectedIndex()+2;
            int index = 35 + (6 * 46);
            tileMap.remove(index);
            if(isSelected)
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(world6sprites.get(11)))), index);
            }
            else
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(world6sprites.get(9)))), index);
            }
            
            validMaps.level10Done(isSelected, world);
            drawDirections(world);
        
            maps.updateUI();
        });
    
        completedTiles[10].addActionListener(e ->
        {
            boolean isSelected = completedTiles[10].isSelected();
            int world = worldMenu.getSelectedIndex()+2;
            int index1,index2;
            String sel1,non1,sel2,non2;
            switch(world)
            {
                case 4:
                    sel1 = world4sprites.get(8);
                    non1 = world4sprites.get(28);
                    sel2 = world4sprites.get(16);
                    non2 = world4sprites.get(14);
                    index1 = 27 + (2 * 46);
                    index2 = index1 - 1;
                    break;
                case 5:
                    sel1 = world5sprites.get(9);
                    non1 = world5sprites.get(25);
                    sel2 = world5sprites.get(69);
                    non2 = world5sprites.get(33);
                    index1 = 12 + (4 * 46);
                    index2 = index1 - 1;
                    break;
                case 6:
                    sel1 = world6sprites.get(11);
                    non1 = world6sprites.get(31);
                    sel2 = world6sprites.get(22);
                    non2 = world6sprites.get(21);
                    index1 = 9 + (4 * 46);
                    index2 = index1 + 3;
                    break;
                default:
                    sel1 = world2sprites.get(32);
                    non1 = world2sprites.get(22);
                    sel2 = world2sprites.get(16);
                    non2 = world2sprites.get(12);
                    index1 = 15 + (4 * 46);
                    index2 = 15 + (7 * 46);
                    break;
            }
            tileMap.remove(index1);
            if(isSelected)
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(sel1))), index1);
                tileMap.remove(index2);
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(sel2))), index2);
            }
            else
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(non1))), index1);
                tileMap.remove(index2);
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(non2))), index2);
            }
        
            validMaps.fort1Done(isSelected, world);
            drawDirections(world);
        
            maps.updateUI();
        });
    
        completedTiles[11].addActionListener(e ->
        {
            boolean isSelected = completedTiles[11].isSelected();
            int world = worldMenu.getSelectedIndex()+2;
            int index1,index2;
            String sel1,non1,sel2,non2;
            switch(world)
            {
                case 4:
                    sel1 = world4sprites.get(8);
                    non1 = world4sprites.get(28);
                    sel2 = world4sprites.get(31);
                    non2 = world4sprites.get(42);
                    index1 = 21 + (4 * 46);
                    index2 = index1 - 3;
                    break;
                case 5:
                    sel1 = world5sprites.get(9);
                    non1 = world5sprites.get(49);
                    sel2 = world5sprites.get(42);
                    non2 = world5sprites.get(41);
                    index1 = 30 + (6 * 46);
                    index2 = 29 + (4 * 46);
                    break;
                default:
                    sel1 = world6sprites.get(11);
                    non1 = world6sprites.get(31);
                    sel2 = world6sprites.get(22);
                    non2 = world6sprites.get(21);
                    index1 = 23 + (4 * 46);
                    index2 = 24;
                    break;
            }
            tileMap.remove(index1);
            if(isSelected)
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(sel1))), index1);
                tileMap.remove(index2);
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(sel2))), index2);
            }
            else
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(non1))), index1);
                tileMap.remove(index2);
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(non2))), index2);
            }
        
            validMaps.fort2Done(isSelected, world);
            drawDirections(world);
        
            maps.updateUI();
        });
    
        completedTiles[12].addActionListener(e ->
        {
            boolean isSelected = completedTiles[12].isSelected();
            int world = worldMenu.getSelectedIndex()+2;
            int index1 = 33 + (4 * 46);
            int index2 = 33 + (4 * 46);
            tileMap.remove(index1);
            tileMap.remove(index2);
            if(isSelected)
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(world6sprites.get(11)))), index1);
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(world6sprites.get(22)))), index2);
            }
            else
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(world6sprites.get(9)))), index1);
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(world6sprites.get(21)))), index2);
            }
        
            validMaps.fort3Done(isSelected, world);
            drawDirections(world);
            
            maps.updateUI();
        });
    
        completedTiles[13].addActionListener(e ->
        {
            boolean isSelected = completedTiles[13].isSelected();
            int world = worldMenu.getSelectedIndex()+2;
            int index = 23 + (2 * 46);
            tileMap.remove(index);
            if(isSelected)
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(world2sprites.get(32)))), index);
            }
            else
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(world2sprites.get(14)))), index);
            }
        
            validMaps.quicksandDone(isSelected, world);
            drawDirections(world);
            
            maps.updateUI();
        });
    
        completedTiles[14].addActionListener(e ->
        {
            boolean isSelected = completedTiles[14].isSelected();
            int world = worldMenu.getSelectedIndex()+2;
            int index = 27 + (6 * 46);
            tileMap.remove(index);
            if(isSelected)
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(world2sprites.get(32)))), index);
            }
            else
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(world2sprites.get(10)))), index);
            }
        
            validMaps.pyramidDone(isSelected, world);
            drawDirections(world);
        
            maps.updateUI();
        });
    
        completedTiles[15].addActionListener(e ->
        {
            boolean isSelected = completedTiles[15].isSelected();
            int world = worldMenu.getSelectedIndex()+2;
            int index;
            String sel,non;
            switch(world)
            {
                case 4:
                    sel = world4sprites.get(8);
                    non = world4sprites.get(26);
                    index = 33 + (4 * 46);
                    break;
                case 5:
                    sel = world5sprites.get(9);
                    non = world5sprites.get(24);
                    index = 14;
                    break;
                case 6:
                    sel = world6sprites.get(11);
                    non = world6sprites.get(30);
                    index = 11;
                    break;
                default:
                    sel = world2sprites.get(32);
                    non = world2sprites.get(21);
                    index = 13 + (6 * 46);
                    break;
            }
            tileMap.remove(index);
            if(isSelected)
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(sel))), index);
            }
            else
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(non))), index);
            }
        
            validMaps.mushroom1Done(isSelected, world);
            drawDirections(world);
        
            maps.updateUI();
        });
    
        completedTiles[16].addActionListener(e ->
        {
            boolean isSelected = completedTiles[16].isSelected();
            int world = worldMenu.getSelectedIndex()+2;
            int index;
            String sel,non;
            switch(world)
            {
                case 4:
                    sel = world4sprites.get(8);
                    non = world4sprites.get(26);
                    index = 27 + (8 * 46);
                    break;
                case 5:
                    sel = world5sprites.get(9);
                    non = world5sprites.get(48);
                    index = 36;
                    break;
                case 6:
                    sel = world6sprites.get(11);
                    non = world6sprites.get(30);
                    index = 19 + (6 * 46);
                    break;
                default:
                    sel = world2sprites.get(32);
                    non = world2sprites.get(21);
                    index = 21;
                    break;
            }
            tileMap.remove(index);
            if(isSelected)
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(sel))), index);
            }
            else
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(non))), index);
            }
        
            validMaps.mushroom2Done(isSelected, world);
            drawDirections(world);
        
            maps.updateUI();
        });
    
        completedTiles[17].addActionListener(e ->
        {
            boolean isSelected = completedTiles[17].isSelected();
            int world = worldMenu.getSelectedIndex()+2;
            int index;
            String sel,non;
            switch(world)
            {
                case 4:
                    sel = world4sprites.get(8);
                    non = world4sprites.get(27);
                    index = 17 + (2 * 46);
                    break;
                case 5:
                    sel = world5sprites.get(9);
                    non = world5sprites.get(48);
                    index = 34 + (8 * 46);
                    break;
                default:
                    sel = world2sprites.get(32);
                    non = world2sprites.get(21);
                    index = 31;
                    break;
            }
            tileMap.remove(index);
            if(isSelected)
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(sel))), index);
            }
            else
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(non))), index);
            }
        
            validMaps.mushroom3Done(isSelected, world);
            drawDirections(world);
        
            maps.updateUI();
        });
    
        completedTiles[18].addActionListener(e ->
        {
            boolean isSelected = completedTiles[18].isSelected();
            int world = worldMenu.getSelectedIndex()+2;
            int index;
            String sel,non;
            switch(world)
            {
                case 6:
                    sel = world6sprites.get(9);
                    non = world6sprites.get(48);
                    index = 34 + (8 * 46);
                    break;
                default:
                    sel = world2sprites.get(15);
                    non = world2sprites.get(11);
                    index = 20 + (6 * 46);
                    break;
            }
            tileMap.remove(index);
            if(isSelected)
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(sel))), index);
            }
            else
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(non))), index);
            }
        
            validMaps.rock1Broken(isSelected, world);
            drawDirections(world);
        
            maps.updateUI();
        });
    
        completedTiles[19].addActionListener(e ->
        {
            boolean isSelected = completedTiles[19].isSelected();
            int world = worldMenu.getSelectedIndex()+2;
            int index;
            String sel1,non1,sel2,non2;
            switch(world)
            {
                case 6:
                    sel1 = world6sprites.get(9);
                    non1 = world6sprites.get(48);
                    sel2 = "";
                    non2 = "";
                    index = 34 + (8 * 46);
                    break;
                default:
                    sel1 = world2sprites.get(9);
                    non1 = world2sprites.get(18);
                    sel2 = world2sprites.get(11);
                    non2 = world2sprites.get(15);
                    index = 28;
                    break;
            }
            tileMap.remove(index);
            if(world == 2)
            {
                tileMap.remove(index);
            }
            if(isSelected)
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(sel1))), index);
                if(world == 2)
                {
                    tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(sel2))), index);
                }
            }
            else
            {
                tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(non1))), index);
                if(world == 2)
                {
                    tileMap.add(new JLabel(new ImageIcon(this.getClass().getResource(non2))), index);
                }
            }
        
            validMaps.rock2Broken(isSelected, world);
            drawDirections(world);
            
            maps.updateUI();
        });
    
        completedTiles[20].addActionListener(e ->
        {
            hb1Start.setVisible(completedTiles[20].isSelected());
            hb1End.setVisible(completedTiles[20].isSelected());
            
            maps.updateUI();
        });
    
        completedTiles[21].addActionListener(e ->
        {
            hb2Start.setVisible(completedTiles[21].isSelected());
            hb2End.setVisible(completedTiles[21].isSelected());
        
            maps.updateUI();
        });
    
        completedTiles[22].addActionListener(e ->
        {
            hb3Start.setVisible(completedTiles[22].isSelected());
            hb3End.setVisible(completedTiles[22].isSelected());
        
            maps.updateUI();
        });
        
        optionsPanel.add(hammerBroOptionsPanel);
        optionsPanel.add(hammerBroMenu);
        optionsPanel.add(worldMenu);
        optionsPanel.add(checkBoxPanel);
        
        hammerBroOptionsPanel.add(increaseX);
        hammerBroOptionsPanel.add(increaseY);
        hammerBroOptionsPanel.add(xPosition);
        hammerBroOptionsPanel.add(yPosition);
        hammerBroOptionsPanel.add(decreaseX);
        hammerBroOptionsPanel.add(decreaseY);
        
        hbStartMap.setOpaque(true);
        hbStartMap.setBackground(new Color(0, 0, 0, 0));
        hbEndMap.setOpaque(true);
        hbEndMap.setBackground(new Color(0, 0, 0, 0));
        mapPanel.setOpaque(true);
        mapPanel.add(hbStartMap);
        mapPanel.add(hbEndMap);
        mapPanel.add(tileMap);
    
        rightPanel.setOpaque(true);
        rightPanel.setBackground(new Color(0, 0, 0, 0));
        leftPanel.setOpaque(true);
        leftPanel.setBackground(new Color(0, 0, 0, 0));
        downPanel.setOpaque(true);
        downPanel.setBackground(new Color(0, 0, 0, 0));
        upPanel.setOpaque(true);
        upPanel.setBackground(new Color(0, 0, 0, 0));
        directionPanel.setOpaque(true);
        directionPanel.add(rightPanel);
        directionPanel.add(leftPanel);
        directionPanel.add(downPanel);
        directionPanel.add(upPanel);
    
        maps.add(mapPanel);
        maps.add(directionPanel);
        
        contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout());
        
        contentPanel.add(optionsPanel);
        
        contentPanel.add(maps);
        
        this.setContentPane(contentPanel);
    }
    
    private void updateHammerPosition()
    {
        hbStartMap.removeAll();
        hbEndMap.removeAll();
        for(int y = 0; y < 9; y++)
        {
            for(int x = 0; x < 46; x++)
            {
                int i = x + (y * 46);
                int offset;
                switch(worldMenu.getSelectedIndex())
                {
                    case 1:
                    case 4:
                        offset = 0;
                        break;
                    case 3:
                        offset = 9;
                        break;
                    default:
                        offset = 8;
                        break;
                }

                if(bros[0].getRealXPos()+offset == x && bros[0].getRealYPos() == y)
                {
                    hbStartMap.add(hb1Start, i);
                }
                else if(bros[1].getRealXPos()+offset == x && bros[1].getRealYPos() == y)
                {
                    hbStartMap.add(hb2Start, i);
                }
                else if(bros[2].getRealXPos()+offset == x && bros[2].getRealYPos() == y)
                {
                    hbStartMap.add(hb3Start, i);
                }
                else
                {
                    hbStartMap.add(new JLabel(new ImageIcon(this.getClass().getResource("tiles/bros/blank.png"))), i);
                }
                
                if(bros[3].getRealXPos()+offset == x && bros[3].getRealYPos() == y)
                {
                    hbEndMap.add(hb1End, i);
                }
                else if(bros[4].getRealXPos()+offset == x && bros[4].getRealYPos() == y)
                {
                    hbEndMap.add(hb2End, i);
                }
                else if(bros[5].getRealXPos()+offset == x && bros[5].getRealYPos() == y)
                {
                    hbEndMap.add(hb3End, i);
                }
                else
                {
                    hbEndMap.add(new JLabel(new ImageIcon(this.getClass().getResource("tiles/bros/blank.png"))), i);
                }
                
                maps.updateUI();
            }
        }
    }
    
    private void drawDirections(int world)
    {
        int mapStart, mapEnd;
        switch(world)
        {
            case 2:
            case 4:
                mapStart = 8;
                mapEnd = 37;
                break;
            case 5:
                mapStart = 9;
                mapEnd = 36;
                break;
            case 3:
            default:
                mapStart = 0;
                mapEnd = 45;
                break;
        }
        
        rightPanel.removeAll();
        leftPanel.removeAll();
        downPanel.removeAll();
        upPanel.removeAll();
        
        for(int y = 0; y < 9; y++)
        {
            for(int x = 0; x < 46; x++)
            {
                if(x < mapStart || x > mapEnd)
                {
                    rightPanel.add(new JLabel(new ImageIcon(this.getClass().getResource("tiles/bros/blank.png"))));
                    leftPanel.add(new JLabel(new ImageIcon(this.getClass().getResource("tiles/bros/blank.png"))));
                    downPanel.add(new JLabel(new ImageIcon(this.getClass().getResource("tiles/bros/blank.png"))));
                    upPanel.add(new JLabel(new ImageIcon(this.getClass().getResource("tiles/bros/blank.png"))));
                }
                else if((x % 2 == 1 && world == 5) || (x % 2 == 0 && world != 5) || y % 2 == 1)
                {
                    rightPanel.add(new JLabel(new ImageIcon(this.getClass().getResource("tiles/dir/blank.png"))));
                    leftPanel.add(new JLabel(new ImageIcon(this.getClass().getResource("tiles/dir/blank.png"))));
                    downPanel.add(new JLabel(new ImageIcon(this.getClass().getResource("tiles/dir/blank.png"))));
                    upPanel.add(new JLabel(new ImageIcon(this.getClass().getResource("tiles/dir/blank.png"))));
                }
                else
                {
                    int rX = (x-mapStart-1)/2;
                    int rY = y/2;
                    
                    int right, left, down, up;
                    switch(world)
                    {
                        case 3:
                            right = validMaps.world3Valid[rY][rX].getDirIndexInt(0);
                            left = validMaps.world3Valid[rY][rX].getDirIndexInt(1);
                            down = validMaps.world3Valid[rY][rX].getDirIndexInt(2);
                            up = validMaps.world3Valid[rY][rX].getDirIndexInt(3);
                            break;
                        case 2:
                            right = validMaps.world2Valid[rY][rX].getDirIndexInt(0);
                            left = validMaps.world2Valid[rY][rX].getDirIndexInt(1);
                            down = validMaps.world2Valid[rY][rX].getDirIndexInt(2);
                            up = validMaps.world2Valid[rY][rX].getDirIndexInt(3);
                            break;
                        case 4:
                            right = validMaps.world4Valid[rY][rX].getDirIndexInt(0);
                            left = validMaps.world4Valid[rY][rX].getDirIndexInt(1);
                            down = validMaps.world4Valid[rY][rX].getDirIndexInt(2);
                            up = validMaps.world4Valid[rY][rX].getDirIndexInt(3);
                            break;
                        case 5:
                            right = validMaps.world5Valid[rY][rX].getDirIndexInt(0);
                            left = validMaps.world5Valid[rY][rX].getDirIndexInt(1);
                            down = validMaps.world5Valid[rY][rX].getDirIndexInt(2);
                            up = validMaps.world5Valid[rY][rX].getDirIndexInt(3);
                            break;
                        default:
                            right = validMaps.world6Valid[rY][rX].getDirIndexInt(0);
                            left = validMaps.world6Valid[rY][rX].getDirIndexInt(1);
                            down = validMaps.world6Valid[rY][rX].getDirIndexInt(2);
                            up = validMaps.world6Valid[rY][rX].getDirIndexInt(3);
                            break;
                    }
                    
                    if(right == 2)
                    {
                        rightPanel.add(new JLabel(new ImageIcon(this.getClass().getResource("tiles/dir/right.png"))));
                    }
                    else
                    {
                        rightPanel.add(new JLabel(new ImageIcon(this.getClass().getResource("tiles/dir/blank.png"))));
                    }
                
                    if(left == 2)
                    {
                        leftPanel.add(new JLabel(new ImageIcon(this.getClass().getResource("tiles/dir/left.png"))));
                    }
                    else
                    {
                        leftPanel.add(new JLabel(new ImageIcon(this.getClass().getResource("tiles/dir/blank.png"))));
                    }
                
                    if(down == 2)
                    {
                        downPanel.add(new JLabel(new ImageIcon(this.getClass().getResource("tiles/dir/down.png"))));
                    }
                    else
                    {
                        downPanel.add(new JLabel(new ImageIcon(this.getClass().getResource("tiles/dir/blank.png"))));
                    }
                
                    if(up == 2)
                    {
                        upPanel.add(new JLabel(new ImageIcon(this.getClass().getResource("tiles/dir/up.png"))));
                    }
                    else
                    {
                        upPanel.add(new JLabel(new ImageIcon(this.getClass().getResource("tiles/dir/blank.png"))));
                    }
                }
            }
        }
    }
    
    private boolean isMovementDirValid(int xPos, int yPos, int[][] validMap, int dir)
    {
        if(dir == 0)
        {
            if(xPos < 28)
            {
                return validMap[yPos][xPos + 1] == 2 && (validMap[yPos][xPos + 2] == 1 || validMap[yPos][xPos + 2] == 3);
            }
        }
        else if(dir == 1)
        {
            if(xPos > 1)
            {
                return validMap[yPos][xPos - 1] == 2 && (validMap[yPos][xPos - 2] == 1 || validMap[yPos][xPos - 2] == 3);
            }
        }
        else if(dir == 2)
        {
            if(yPos < 7)
            {
                return validMap[yPos + 1][xPos] == 2 && (validMap[yPos + 2][xPos] == 1 || validMap[yPos + 2][xPos] == 3);
            }
        }
        else
        {
            if(yPos > 1)
            {
                return validMap[yPos - 1][xPos] == 2 && (validMap[yPos - 2][xPos] == 1 || validMap[yPos - 2][xPos] == 3);
            }
        }
        return false;
    }
    
    private boolean isMovementCorrect(byte rng, int index, byte[] directions)
    {
        int tries = 4;
        byte facing = bro_facing[index];
        byte direction = (byte)((Byte.toUnsignedInt(rng)) & 0x3);
        byte increment = (byte)((((Byte.toUnsignedInt(rng)) & 0x80) == 0x80) ? 1 : -1);
        
        while(tries > 0)
        {
            direction += increment;
            direction &= 3;
            
            //If we want to travel in opposite direction, don't let that happen by going to next iteration
            if((facing ^ direction) == 1)
            {
                continue;
            }
            
            //Picked a direction so decrement tries
            tries -= 1;
            if(tries == 0)
            {
                //We have now tried everything except for the opposite direction that we are facing so try that direction
                direction = ((byte)(facing ^ 1));
            }
            
            //Validate direction
            if((byte)(Byte.toUnsignedInt(directions[Byte.toUnsignedInt(direction)])) == DIR_FAIL)
            {
                return false;
            }
            
            if((byte)(Byte.toUnsignedInt(directions[Byte.toUnsignedInt(direction)])) == DIR_INVALID)
            {
                continue;
            }
            
            //Chose the needed direction
            bro_facing[index] = direction;
            return true;
        }
        
        //This will happen if the hammer bro cannot move, so return false
        return false;
    }
}