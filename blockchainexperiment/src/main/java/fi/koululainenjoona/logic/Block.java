package fi.koululainenjoona.logic;

import java.security.MessageDigest;
import java.util.Date;

/**
 *This class contains the functionality for managing the individual blocks
 */
public class Block {

    private final String data;
    private final long date;
    private String hash;
    private final String previousHash;
    private int nonce;

    /**
     * The constructor first sets some information used for creating a hash for
     * the Block and then creates the hash itself
     * @param data
     * @param previousHash
     */
    public Block(String data, String previousHash) {
        this.data = data;
        this.date = new Date().getTime();
        this.previousHash = previousHash;
        this.hash = createHash();

    }

    public String getHash() {
        return hash;
    }

    public String getData() {
        return data;
    }
    
    public String getPreviousHash() {
        return previousHash;
    }

    /**
     * This method applies the SHA3-256 hash function to a String input. It uses
     * the input combined with the data set in the constructor.
     * @param input
     * @return String
     */
    public String applyHashFunction(String input) {

        try {

            MessageDigest digest = MessageDigest.getInstance("SHA3-256");
            byte[] hashbytes = digest.digest(input.getBytes("UTF-8"));
            String hexadecimalHash = convertToHexadecimal(hashbytes);

            return hexadecimalHash;

        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * This method converts the SHA3-256 created byte[] digest to a hexadecimal form String
     * @param bytes
     * @return String
     */
    public String convertToHexadecimal(byte[] bytes) {

        StringBuilder hexadecimalBuilder = new StringBuilder();

        for (byte b : bytes) {
            hexadecimalBuilder.append(String.format("%02x", b));
        }

        return hexadecimalBuilder.toString();
    }

    /**
     * This method creates a String combined from the data the constructor has set
     * and uses a hash function to calculate a unique hash
     * @return String
     */
    public String createHash() {
        String hashData = this.previousHash + Long.toString(this.date) + this.data + Integer.toString(nonce);
        String blocksHash = applyHashFunction(hashData);
        return blocksHash;
    }

    /**
     * This method tries to find a number (the nonce) that when combined with the other parameters of
     * the createHash() method creates a hash that starts with four zeros. The purpose is to act as a
     * computationally intensive operation that prevents malicious actors from manipulating the chain.
     * In this application's case this computation task is easy enough for it to work for demonstrative purposes
     */
    public void mineBlock() {
        
        System.out.println("\nMining a block, this takes a few seconds");
        
        String toMatch = new String(new char[4]).replace('\0', '0');
        
        while (!this.hash.substring(0, 4).equals(toMatch)) {
            this.nonce++;
            this.hash = createHash();
        }
        System.out.println("Block succesfully mined!");
    }

    @Override
    public String toString() {
        return "Block data: " + this.data + " Hash: " + this.hash;
    }

}
