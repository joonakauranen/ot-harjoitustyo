package fi.koululainenjoona.logic;

import java.security.MessageDigest;
import java.util.Date;

public class Block {

    private final String data;
    private final long date;
    private final String hash;
    private final String previousHash;

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

    public String convertToHexadecimal(byte[] bytes) {

        StringBuilder hexadecimalBuilder = new StringBuilder();

        for (byte b : bytes) {
            hexadecimalBuilder.append(String.format("%02x", b));
        }

        return hexadecimalBuilder.toString();
    }

    public String createHash() {
        String hashData = this.previousHash + Long.toString(this.date) + this.data;
        String blocksHash = applyHashFunction(hashData);
        return blocksHash;
    }

    @Override
    public String toString() {
        return "Block data: " + this.data + " Hash: " + this.hash;
    }

}
