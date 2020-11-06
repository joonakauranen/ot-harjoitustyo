
package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KassapaateTest {
    
    Kassapaate kassa;
    Maksukortti korttiPieniKate;
    Maksukortti korttiSuuriKate;

    @Before
    public void setUp() {
        kassa = new Kassapaate();
        korttiPieniKate = new Maksukortti(100);
        korttiSuuriKate = new Maksukortti(800);
    }
    
    @Test
    public void konstruktoriAsettaaOikeanAlkusaldon() {
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void kontruktoriAsettaaOikeanMyyntimaaran() {
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoEdullisestiAntaaOikenVaihtorahanKunMaksuOnRiittava() {
        assertEquals(60, kassa.syoEdullisesti(300));
    }
    
    @Test
    public void syoEdullisestiPalauttaaRahanKunMaksuEiOleRiittava() {
        assertEquals(230, kassa.syoEdullisesti(230));
    }
    
    @Test
    public void syoMaukkaastiAntaaOikenVaihtorahanKunMaksuOnRiittava() {
        assertEquals(100, kassa.syoMaukkaasti(500));
    }
    
    @Test
    public void syoMaukkaastiPalauttaaRahanKunMaksuEiOleRiittava() {
        assertEquals(390, kassa.syoMaukkaasti(390));
    }
    
    @Test
    public void syoEdullisestiKasvattaaKassanSaldoaKunMaksuOnRiittava() {
        kassa.syoEdullisesti(240);
        assertEquals(100240, kassa.kassassaRahaa());
    }
    
    @Test
    public void syoMaukkaastiKasvattaaKassanSaldoaKunMaksuOnRiittava() {
        kassa.syoMaukkaasti(400);
        assertEquals(100400, kassa.kassassaRahaa());
    }
    
    @Test
    public void syoEdullisestiEiKasvattaKassanSaldoaKunMaksuEiOleRiittava() {
        kassa.syoEdullisesti(230);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
   @Test
    public void syoMaukkaastiEiKasvattaKassanSaldoaKunMaksuEiOleRiittava() {
        kassa.syoMaukkaasti(390);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void syoEdullisestiKasvattaaMyytyjenSaldoaKunMaksuOnRiittava() {
        kassa.syoEdullisesti(240);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiKasvattaaMyytyjenSaldoaKunMaksuOnRiittava() {
        kassa.syoMaukkaasti(400);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void syoEdullisestiEiKasvattaMyytyjenSaldoaKunMaksuEiOleRiittava() {
        kassa.syoEdullisesti(230);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiEiKasvattaMyytyjenSaldoaKunMaksuEiOleRiittava() {
        kassa.syoMaukkaasti(390);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void syoEdullisestiKorttimaksuVeloitetaanKunMaksuOnRiittava() {
        kassa.syoEdullisesti(korttiSuuriKate);
        assertEquals(560, korttiSuuriKate.saldo());
    }
    
    @Test
    public void syoMaukkaastiKorttimaksuVeloitetaanKunMaksuOnRiittava() {
        kassa.syoMaukkaasti(korttiSuuriKate);
        assertEquals(400, korttiSuuriKate.saldo());
    }
    
    @Test
    public void syoEdullisestiKorttimaksuPalauttaaTrueKunMaksuOnnistuu() {
        assertTrue(kassa.syoEdullisesti(korttiSuuriKate));
    }
    
    @Test
    public void syoMaukkaastiKorttimaksuPalauttaaTrueKunMaksuOnnistuu() {
        assertTrue(kassa.syoMaukkaasti(korttiSuuriKate));
    }
   
    
    @Test
    public void syoEdullisestiKorttimaksuKasvattaaMyytyjenSaldoaKunMaksuOnRiittava() {
        kassa.syoEdullisesti(korttiSuuriKate);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiKorttimaksuKasvattaaMyytyjenSaldoaKunMaksuOnRiittava() {
        kassa.syoMaukkaasti(korttiSuuriKate);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void syoEdullisestiKorttiMaksuEiKasvattaMyytyjenSaldoaKunMaksuEiOleRiittava() {
        kassa.syoEdullisesti(korttiPieniKate);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiKorttimaksuEiKasvattaMyytyjenSaldoaKunMaksuEiOleRiittava() {
        kassa.syoMaukkaasti(korttiPieniKate);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void syoEdullisestiKorttimaksuaEiVeloitetaKunMaksuEiOleRiittava() {
        kassa.syoEdullisesti(korttiPieniKate);
        assertEquals(100, korttiPieniKate.saldo());
    }
    
    @Test
    public void syoMaukkaastiKorttimaksuaEiVeloitetataKunMaksuEiOleRiittava() {
        kassa.syoMaukkaasti(korttiPieniKate);
        assertEquals(100, korttiPieniKate.saldo());
    }
    
    @Test
    public void syoEdullisestiKorttimaksuPalauttaaFalseKunMaksuEiOnnistu() {
        assertFalse(kassa.syoEdullisesti(korttiPieniKate));
    }
    
    @Test
    public void syoMaukkaastiKorttimaksuPalauttaaFalseKunMaksuEiOnnistu() {
        assertFalse(kassa.syoMaukkaasti(korttiPieniKate));
    }
    
    @Test
    public void korttimaksuEiMuutaKassanSaldoa() {
        kassa.syoEdullisesti(korttiSuuriKate);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void kortilleLatausLisaaKortinSaldoa() {
        kassa.lataaRahaaKortille(korttiPieniKate, 400);
        assertEquals(500, korttiPieniKate.saldo());
    }
    
    @Test
    public void kortilleLatausLisaaKassanSaldoa() {
        //Tämä ei ole kovin loogista, mutta päätin tehdä täsmälleen ohjeen mukaan
        kassa.lataaRahaaKortille(korttiPieniKate, 400);
        assertEquals(100400, kassa.kassassaRahaa());
    }
    
    @Test
    public void kortilleLatausEiOnnistuNegaativisellaParametrilla() {
        //Tämä ei ole kovin loogista, mutta päätin tehdä täsmälleen ohjeen mukaan
        kassa.lataaRahaaKortille(korttiPieniKate, -1);
        assertEquals(100000, kassa.kassassaRahaa());
    }
        
        
}
