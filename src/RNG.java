public class RNG
{
    private byte[] Random_Pool = {(byte)0x88, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
    private byte[] Random_Pool_Temp = {(byte)0x88, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
    
    public void Randomize()
    {
        byte Temp_Var1 = (byte)(Byte.toUnsignedInt(Random_Pool[0]) & 0x2);
        byte carry = (byte)(!((Byte.toUnsignedInt(Random_Pool[1]) & 0x2 ^ Byte.toUnsignedInt(Temp_Var1)) == 0) ? 1 : 0);
    
        for (int i = 0; Long.compareUnsigned(i, (long)Random_Pool.length) < 0; i++) {
            /**
             * Carry is shifted into the most-significant bit.
             * Least-significant bit is saved off into carry.
             */
            byte b = (byte)(Byte.toUnsignedInt(Random_Pool[i]) & 1);
            Random_Pool[i] = (byte)(Byte.toUnsignedInt(carry) << 7 | Byte.toUnsignedInt(Random_Pool[i]) >> 1);
            carry = b;
        }
        
        for(int i = 0; i < 9; i++)
        {
            Random_Pool_Temp[i] = (byte)(Byte.toUnsignedInt(Random_Pool[i]));
        }
    }
    
    public void Randomize_Temp()
    {
        byte Temp_Var1 = (byte)(Byte.toUnsignedInt(Random_Pool_Temp[0]) & 0x2);
        byte carry = (byte)(!((Byte.toUnsignedInt(Random_Pool_Temp[1]) & 0x2 ^ Byte.toUnsignedInt(Temp_Var1)) == 0) ? 1 : 0);
        
        for (int i = 0; Long.compareUnsigned(i, (long)Random_Pool_Temp.length) < 0; i++) {
            /**
             * Carry is shifted into the most-significant bit.
             * Least-significant bit is saved off into carry.
             */
            byte b = (byte)(Byte.toUnsignedInt(Random_Pool_Temp[i]) & 1);
            Random_Pool_Temp[i] = (byte)(Byte.toUnsignedInt(carry) << 7 | Byte.toUnsignedInt(Random_Pool_Temp[i]) >> 1);
            carry = b;
        }
    }
    
    public void tempPoolSync()
    {
        for(int i = 0; i < 9; i++)
        {
            Random_Pool_Temp[i] = (byte)(Byte.toUnsignedInt(Random_Pool[i]));
        }
    }
    
    public byte[] getRandom_Pool()
    {
        return Random_Pool;
    }
    
    public byte getRandom_Pool_Real(int i)
    {
        return Random_Pool[i];
    }
    
    public byte getRandom_Pool(int i)
    {
        return Random_Pool[i+1];
    }
    
    public byte[] getRandom_Temp_Pool()
    {
        return Random_Pool_Temp;
    }
    
    public byte getRandom_Temp_Pool(int i)
    {
        return Random_Pool_Temp[i+1];
    }
}