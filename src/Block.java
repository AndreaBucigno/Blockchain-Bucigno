import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Block.java
 * Evoluzione del progetto LinkedList fatto lo scorso anno.
 * La classe Block rappresenta un blocco della blockchain.
 * Mantiene il concetto di "next" dalla LinkedList originale e aggiunge
 * tutti gli attributi richiesti (RF1, RF2, RF3).
 */
public class Block {

    private Block next;

    private String hash;

    private String prevHash;

    private String BlockName;

    private long timestamp;

    private int nonce;


    /**
     * Costruttore della classe Block.
     * Inizializza i dati del blocco e calcola il primo hash.
     *
     * @param BlockName contenuto informativo del blocco
     * @param prevHash hash del blocco precedente
     */
    public Block(String BlockName, String prevHash) {
        this.BlockName = BlockName;
        this.prevHash = prevHash;
        this.timestamp = System.currentTimeMillis();
        this.nonce = 0;
        this.next = null;

        // L'hash viene calcolato alla creazione
        this.hash = calculateHash();
    }

    /**
     * RF2 – Generazione Hash SHA-256.
     * L'hash viene calcolato usando:
     *
     * prevHash + timestamp + nonce + data
     *
     * @return hash SHA-256 del blocco in formato esadecimale
     */
    public String calculateHash() {
        String input = prevHash + timestamp + nonce + BlockName;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] rawHash = digest.digest(input.getBytes("UTF-8"));

            // Conversione dei byte dell'hash in formato esadecimale.
            // Questa implementazione è stata adattata da esempi trovati online
            // sull'utilizzo di SHA-256 in Java.

            StringBuilder hexString = new StringBuilder();
            for (byte b : rawHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            throw new RuntimeException("Errore nel calcolo SHA-256: " + e.getMessage());
        }
    }

    /**
     * RF3 – Proof of Work (Mining).
     * Incrementa il nonce finché l'hash non inizia
     * con un numero di zeri pari alla difficoltà.
     *
     * @param difficulty numero di zeri richiesti all'inizio dell'hash
     */
    public void mineBlock(int difficulty) {
        String target = "0".repeat(difficulty);
        System.out.println("  Mining blocco con difficoltà " + difficulty + "...");

        while (!hash.startsWith(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("  Blocco minato! Nonce=" + nonce + "  Hash=" + hash);
    }



    public String getHash(){
        return hash;
    }


    public String getPrevHash(){
        return prevHash;
    }


    public String getData(){
        return BlockName;
    }


    public long getTimestamp(){
        return timestamp;
    }


    public int getNonce(){
        return nonce;
    }

    /**
     * Restituisce il blocco successivo.
     *
     * @return blocco successivo
     */
    public Block getNext(){
        return next;
    }

    /**
     * Imposta il blocco successivo.
     *
     * @param next blocco successivo
     */
    public void setNext(Block next){
        this.next = next;
    }

    /**
     * Verifica se il blocco è l'ultimo della catena.
     *
     * @return true se è l'ultimo blocco, false altrimenti
     */
    public boolean isLast() {
        return next == null;
    }

    /**
     * Restituisce una rappresentazione testuale del blocco.
     *
     * @return stringa descrittiva del blocco
     */
    @Override
    public String toString() {
        return "┌─ Block ────────────────────────────────────────────────\n" +
                "│  Data      : " + BlockName      + "\n" +
                "│  Timestamp : " + timestamp + "\n" +
                "│  Nonce     : " + nonce     + "\n" +
                "│  PrevHash  : " + prevHash  + "\n" +
                "│  Hash      : " + hash      + "\n" +
                "└────────────────────────────────────────────────────────";
    }
}