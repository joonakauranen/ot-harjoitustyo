package blockchainexperiment;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Chain chain = new Chain();
        Scanner scanner = new Scanner(System.in);
        UI ui = new UI(chain, scanner);

        ui.run();

    }
}
