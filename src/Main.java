import java.util.Scanner;

/**
 * Main.java – gestisco la blockchain creando nuovi nodi o verificando l'inrtegrita della catena
 * @author Bucigno Andrea
 * @see Block
 * @see Blockchain
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner    = new Scanner(System.in);
        int difficulty = 3;

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║   Blockchain Minimalista  v1.0       ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("Inserisci la difficoltà di mining (consigliato 2-4): ");
        if (scanner.hasNextInt()) {
            difficulty = scanner.nextInt();
            scanner.nextLine();
        }

        Blockchain blockchain = new Blockchain(difficulty);

        while (true) {
            System.out.println("\nSeleziona un'opzione:");
            System.out.println("  1. Aggiungi blocco (in fondo alla catena)");
            System.out.println("  2. Visualizza l'intera blockchain");
            System.out.println("  3. Verifica integrità della catena");
            System.out.println("  4. Exit");
            System.out.print("> ");

            int option;
            try {
                option = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Opzione non valida, riprova.");
                continue;
            }

            switch (option) {

                case 1:
                    System.out.print("Inserisci il contenuto del blocco: ");
                    String data = scanner.nextLine();
                    blockchain.addBlock(data);
                    break;

                case 2:
                    blockchain.printChain();
                    break;

                case 3:
                    System.out.println("\nVerifica in corso...");
                    boolean valid = blockchain.isChainValid();
                    if (valid) {
                        System.out.println("✔  La blockchain è VALIDA e integra.");
                    } else {
                        System.out.println("✘  La blockchain è CORROTTA!");
                    }
                    break;

                case 4:
                    System.out.println("Uscita...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opzione non valida, riprova.");
            }
        }
    }
}