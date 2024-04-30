package API;

import static java.lang.Math.pow;
import static java.lang.StrictMath.pow;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Ngọc
 */
public class HashString {
    private String str;
    private long[][] hashMod;
    private long[][] pow;
    private long[] mod = new long[2];
    
    final long base = 257;
    
    public HashString() {
    }    

    public HashString(String str) {
        this.str = str;
        
        this.pow = new long[2][];
        this.pow[0] = new long[str.length() + 10];
        this.pow[1] = new long[str.length() + 10];
        
        /// ko dc khai báo hashmod[0] = hashmod[1] = ...
        /// điều này dẫn đến giá trị của 2 mảng sẽ bị giống nhau dù có thao tác khác nhau
        this.hashMod = new long[2][];
        this.hashMod[0] = new long[str.length() + 10];
        this.hashMod[1] = new long[str.length() + 10];
        
        this.mod[0] = (long)(1e9 + 7);
        this.mod[1] = (long)(1e9 + 3);
        
        this.pow[0][0] = 1L;
        this.pow[1][0] = 1L;
    }
    
    public long getHashMod(int i, int j) {
        return hashMod[i][j];
    }
    
    public long[][] getHashMod() {
        return hashMod;
    }

    public void setHashMod(long[][] hashMod) {
        this.hashMod = hashMod;
    }

    public long[][] getPow() {
        return pow;
    }

    public void setPow(long[][] pow) {
        this.pow = pow;
    }

    public long[] getMod() {
        return mod;
    }

    public void setMod(long[] mod) {
        this.mod = mod;
    }
        
    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
    
    public long getHash(int l, int r, int type)
    {
        return (hashMod[type][r] - hashMod[type][l-1] * pow[type][r-l+1] % mod[type] + 
                                            mod[type] * mod[type]) % mod[type];
    }
    
    public boolean check(HashString res, int l, int r)
    {
        return (this.getHash(l, r, 0) == res.hashMod[0][r-l+1] &&
                this.getHash(l, r, 1) == res.hashMod[1][r-l+1]);
    }
    
    public void setHash()
    {
        str = " " + str;
        int n = str.length() - 1;
        
        pow[0][0] = 1L; pow[1][0] = 1L;
        hashMod[0][0] = hashMod[1][0] = 0;
        
        for(int i = 1; i <= n; ++i) 
        {
            char c = (str.charAt(i));
            
            pow[0][i] = 1L * pow[0][i-1] * base % mod[0];
            pow[1][i] = 1L * pow[1][i-1] * base % mod[1];
            
            hashMod[0][i] = (hashMod[0][i-1] * base % mod[0] + c) % mod[0];
            hashMod[1][i] = (hashMod[1][i-1] * base % mod[1] + c) % mod[1];
        }
    }
}
