/**
 * Blockchain.java
 * gestisce la catena di blocchi.
 */
public class Blockchain {

    /**
     * Testa della lista (blocco Genesi).
     */
    private Block head;

    /**
     * Numero di zeri iniziali richiesti per il mining.
     */
    private int difficulty;

    /**
     * Costruttore della blockchain.
     * Crea automaticamente il blocco Genesi e lo mina
     * secondo la difficoltà impostata.
     *
     * @param difficulty numero di zeri richiesti nell'hash
     */
    public Blockchain(int difficulty) {
        this.difficulty = difficulty;

        System.out.println("=== Creazione blocco Genesi ===");

        Block genesis = new Block("Blocco Genesi", "0");
        genesis.mineBlock(difficulty);

        head = genesis;
    }

    /**
     * Aggiunge un blocco in testa alla blockchain.
     *
     *Preso dalla linkedlist dello scorso anno visto che la blockchain non accetta di inserire blocchi all'inizio
     * @param block blocco da inserire in testa
     */
    public void addNodeFirst(Block block) {

        block.setNext(head);
        head = block;

        System.out.println("[ATTENZIONE] Aggiunta in testa: la catena potrebbe risultare non valida.");
    }

    /**
     * Aggiunge un blocco in coda alla blockchain.
     *
     * Questo metodo è la versione corretta del metodo
     * addNodeLast della LinkedList originale.
     *
     * Il nuovo blocco viene collegato usando l'hash
     * dell'ultimo blocco come prevHash.
     *
     * @param data contenuto del nuovo blocco
     */
    public void addBlock(String data) {

        // Trova l'ultimo blocco
        Block current = head;
        while (current.getNext() != null) {
            current = current.getNext();
        }

        String lastHash = current.getHash();

        // Creazione nuovo blocco
        Block newBlock = new Block(data, lastHash);

        System.out.println("\n=== Aggiunta blocco: \"" + data + "\" ===");

        newBlock.mineBlock(difficulty);

        // Collegamento corretto del blocco
        current.setNext(newBlock);
    }

    /**
     * RF4 – Verifica l'integrità dell'intera blockchain.
     *
     * Controlla che:
     * 1) L'hash salvato sia corretto
     * 2) Il prevHash corrisponda all'hash del blocco precedente
     *
     * @return true se la blockchain è valida, false altrimenti
     */
    public boolean isChainValid() {

        Block current = head;
        Block previous = null;

        while (current != null) {

            // Verifica hash
            String recalculated = current.calculateHash();
            if (!current.getHash().equals(recalculated)) {
                System.out.println("[ERRORE] Hash corrotto nel blocco: " + current.getData());
                return false;
            }

            // Verifica collegamento
            if (previous != null &&
                    !current.getPrevHash().equals(previous.getHash())) {

                System.out.println("[ERRORE] Collegamento spezzato dopo il blocco: "
                        + previous.getData());
                return false;
            }

            previous = current;
            current = current.getNext();
        }

        return true;
    }

    /**
     * Stampa l'intera blockchain.
     * Mostra tutti i blocchi in ordine.
     */
    public void printChain() {

        if (head == null) {
            System.out.println("La blockchain è vuota.");
            return;
        }

        Block current = head;
        int index = 0;

        while (current != null) {

            System.out.println("\n[Blocco #" + index + "]");
            System.out.println(current);

            current = current.getNext();
            index++;
        }
    }
}