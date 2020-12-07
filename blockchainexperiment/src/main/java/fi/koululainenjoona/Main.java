package fi.koululainenjoona;

import fi.koululainenjoona.ui.UI;
import fi.koululainenjoona.logic.Chain;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Scanner;

/**
 * This is the main class used for running the application
 */
public class Main {

    /**
     * This is the main method. It sets the necessary Objects and starts the user interface
     * @param args
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public static void main(String[] args) throws GeneralSecurityException, IOException {

        Chain chain = new Chain();
        Scanner scanner = new Scanner(System.in);
        UI ui = new UI(chain, scanner);

        ui.run();

    }
}
