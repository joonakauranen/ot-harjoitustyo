package fi.koululainenjoona.googlesheets;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.google.api.services.sheets.v4.model.ClearValuesRequest;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import fi.koululainenjoona.logic.Block;
import fi.koululainenjoona.logic.Chain;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

import java.util.Arrays;
import java.util.List;

public class SheetsChain {

    private Sheets sheetsService;
    private String spreadsheetId = "1YfgQ27ZTYH4ORWwX9duBnVBVq5dM8lSUDKzokZxXNqk";

    public SheetsChain() throws GeneralSecurityException, IOException {
        this.sheetsService = SheetsService.getSheetsService();
    }

    public void writeSheet(Block block) throws IOException {

        String genesisBlockData = block.getData();
        String genesisBlockHash = block.getHash();

        ValueRange body = new ValueRange()
                .setValues(Arrays.asList(Arrays.asList(genesisBlockData, genesisBlockHash)));

        UpdateValuesResponse result = this.sheetsService.spreadsheets().values()
                .update(this.spreadsheetId, "A1", body)
                .setValueInputOption("RAW")
                .execute();
    }

    public void appendToSheet(Block block) throws IOException {

        String data = block.getData();
        String hash = block.getHash();

        ValueRange appendBody = new ValueRange().setValues(Arrays.asList(Arrays.asList(data, hash)));

        AppendValuesResponse appendResult = this.sheetsService.spreadsheets().values()
                .append(this.spreadsheetId, "A1", appendBody)
                .setValueInputOption("USER_ENTERED")
                .setInsertDataOption("INSERT_ROWS")
                .setIncludeValuesInResponse(true)
                .execute();
    }

    public void clearSheet() throws IOException {
        ClearValuesRequest clearValuesRequest = new ClearValuesRequest();

        this.sheetsService.spreadsheets().values().clear(this.spreadsheetId, "A1:Z1000", clearValuesRequest).execute();

    }

    public void openStandardSheetInBrowser() {
        String url = "https://docs.google.com/spreadsheets/d/1YfgQ27ZTYH4ORWwX9duBnVBVq5dM8lSUDKzokZxXNqk/edit?usp=sharing";

        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                System.out.println("IOException");
            }
        } else {
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + url);
            } catch (IOException e) {
                System.out.println("IOException");
            }
        }
    }

    public String readData(int field) throws IOException {

        List<String> range = Arrays.asList("A" + field);

        String sheetData = this.sheetReader(range);

        return sheetData;
    }

    public String readHash(int field) throws IOException {

        List<String> range = Arrays.asList("B" + field);

        String sheetHash = this.sheetReader(range);

        return sheetHash;
    }

    public String parseValue(ValueRange range) {

        String parsedString = String.valueOf(range);

        if (!parsedString.contains("\",\"values\":[[")) {
            return "";
        }

        parsedString = parsedString.substring(parsedString.indexOf(":[[") + 4, parsedString.indexOf("\"]]}"));

        return parsedString;
    }

    public String sheetReader(List<String> ranges) throws IOException {

        BatchGetValuesResponse readResult = this.sheetsService.spreadsheets().values()
                .batchGet(this.spreadsheetId)
                .setRanges(ranges)
                .execute();

        ValueRange result = readResult.getValueRanges().get(0);

        String parsedResult = this.parseValue(result);

        return parsedResult;

    }

    public void checkSheetsChainValidity(Chain chain) throws IOException {

        Boolean isValid = true;

        Block currentBlock;

        String sheetBlockData;
        String sheetBlockHash;

        String errorReport = "";

        for (int i = 0; i < chain.getChainSize(); i++) {
            currentBlock = chain.getBlock(i);

            sheetBlockData = this.readData(i + 1);
            sheetBlockHash = this.readHash(i + 1);

            if (!currentBlock.getData().equals(sheetBlockData)) {
                int cellNumber = i + 1;
                errorReport = errorReport + "Data in cell A" + cellNumber + "\n";
                isValid = false;
            }

            if (!currentBlock.getHash().equals(sheetBlockHash)) {
                int cellNumber = i + 1;
                errorReport = errorReport + "Hash in cell B" + cellNumber + "\n";
                isValid = false;
            }
        }

        if (isValid) {
            System.out.println("Google Sheets matches with the local copy.\n");
        } else {

            this.rewriteChain(chain);

            System.out.println("Found corrupted data: \n\n" + errorReport + "\n\nRewrote the sheet.\n");
        }
    }

    public void rewriteChain(Chain chain) throws IOException {

        this.clearSheet();

        for (int i = 0; i < chain.getChainSize(); i++) {
            this.appendToSheet(chain.getBlock(i));
        }
    }
}
