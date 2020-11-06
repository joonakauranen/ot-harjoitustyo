package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10000);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kortinSaldoAlussaOikein() {
        assertEquals("saldo: 100.0", kortti.toString());
    }
    
    @Test
    public void kortilleLataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(5000);
        assertEquals("saldo: 150.0", kortti.toString());
    }
    
    @Test
    public void kortiltaOttaminenToimiiOikein() {
        kortti.otaRahaa(5000);
        assertEquals("saldo: 50.0", kortti.toString());
    }
    
    @Test
    public void kortiltaOttaminenEiMahdollistaJosEiTarpeeksiSaldoa() {
        kortti.otaRahaa(10001);
        assertEquals("saldo: 100.0", kortti.toString());
    }
    
    @Test
    public void palauttaaTrueJosSaldoRiittaa() {
        assertTrue(kortti.otaRahaa(9999));
    }
    
    @Test
    public void palauttaaFalseJosSaldoEiRiita() {
        assertFalse(kortti.otaRahaa(10001));
    }
    
    @Test
    public void saldoMetodiPalauttaaOikeanSaldon() {
        assertEquals(10000, kortti.saldo());
    }
}
