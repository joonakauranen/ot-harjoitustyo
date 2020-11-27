package fi.koululainenjoona;

import fi.koululainenjoona.ui.UI;
import fi.koululainenjoona.logic.Chain;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws GeneralSecurityException, IOException {

        Chain chain = new Chain();
        //SheetsChain sheets = new SheetsChain();
        Scanner scanner = new Scanner(System.in);
        UI ui = new UI(chain, scanner);

        ui.run();

    }
}
