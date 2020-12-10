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

/**
 *
 * This class contains the functionality for interacting with Google Sheets
 */
public class SheetsChain {

    private final Sheets sheetsService;
    private final String spreadsheetId;
    private String errorReport;
    private Boolean isValid;

    /**
     * The constructors calls the SheetService class and obtains the necessary
     * service that allows the app to connect to Google Sheets
     *
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public SheetsChain() throws GeneralSecurityException, IOException {
        this.sheetsService = SheetsService.getSheetsService();
        this.spreadsheetId = "1YfgQ27ZTYH4ORWwX9duBnVBVq5dM8lSUDKzokZxXNqk";
        this.errorReport = "";
        this.isValid = true;
    }

    /**
     * Writes data that's stored in a block to Google Sheets spreadsheet
     *
     * @param block writes the blocks data on a spreadsheet
     * @throws IOException
     */
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

    /**
     * Appends data that a block contains to a spreadsheet. Appending is used
     * after a standard piece of information is written with the writeSheet()
     * method. Using append makes writing on the sheet possible without know how
     * many cells are already written
     *
     * @param block a block to appended after the previous entries
     * @throws IOException
     */
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

    /**
     * Clears all the cells in a sheet
     *
     * @throws IOException
     */
    public void clearSheet() throws IOException {
        ClearValuesRequest clearValuesRequest = new ClearValuesRequest();

        this.sheetsService.spreadsheets().values().clear(this.spreadsheetId, "A1:Z1000", clearValuesRequest).execute();

    }

    /**
     * Opens the standard sheet in browser.
     */
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

    /**
     * This application only writes to cells A"n" and B"n". This method reads
     * the data stored in the n:th row of the column A. Index determines the row
     *
     * @param field determines the cell where data is read from
     * @return String data inside a spreadsheet cell
     * @throws IOException
     */
    public String readData(int field) throws IOException {

        List<String> range = Arrays.asList("A" + field);

        String sheetData = this.sheetReader(range);

        return sheetData;
    }

    /**
     * This application only writes to cells A"n" and B"n". This method reads
     * the data stored in the n:th row of the column A. Index determines the row
     *
     * @param field determines the cell where a hash is read from
     * @return String hash from a spreadsheet cell
     * @throws IOException
     */
    public String readHash(int field) throws IOException {

        List<String> range = Arrays.asList("B" + field);

        String sheetHash = this.sheetReader(range);

        return sheetHash;
    }

    private String parseValue(ValueRange range) {

        String parsedString = String.valueOf(range);

        if (!parsedString.contains("\",\"values\":[[")) {
            return "";
        }

        parsedString = parsedString.substring(parsedString.indexOf(":[[") + 4, parsedString.indexOf("\"]]}"));

        return parsedString;
    }

    /**
     * This method reads information from Google Sheets. It's a generic reader
     * used by other more specific methods related to reading data from a
     * spreadsheet
     *
     * @param ranges the range of a spreadsheet
     * @return String data read from the particular range of a spreadsheet
     * @throws IOException
     */
    public String sheetReader(List<String> ranges) throws IOException {

        BatchGetValuesResponse readResult = this.sheetsService.spreadsheets().values()
                .batchGet(this.spreadsheetId)
                .setRanges(ranges)
                .execute();

        ValueRange result = readResult.getValueRanges().get(0);

        String parsedResult = this.parseValue(result);

        return parsedResult;

    }

    /**
     * This function reads data from a spreadsheet and compares it to a local
     * copy of the data that is supposed to be the same.It doesn't recalculate
     * the hashes, but instead uses a verified copy stored locally
     *
     * @param chain Chain object to verify
     * @return String A confirmation of validity or an error message
     * @throws IOException
     */
    public String checkSheetsChainValidity(Chain chain) throws IOException {

        this.isValid = true;
        this.errorReport = "";
        Block currentBlock;
        String sheetBlockData;
        String sheetBlockHash;

        for (int i = 0; i < chain.getChainSize(); i++) {
            currentBlock = chain.getBlock(i);
            sheetBlockData = this.readData(i + 1);
            sheetBlockHash = this.readHash(i + 1);

            this.compareLocalDataToSheetsData(currentBlock, sheetBlockData, i);

            this.compareLocalHashToSheetsHash(currentBlock, sheetBlockHash, i);
        }

        if (!isValid) {

            this.rewriteChain(chain);

        }

        return this.constructReturnMessage(isValid);
    }

    private void compareLocalDataToSheetsData(Block block, String blockData, int sheetsCellIndex) {
        if (!block.getData().equals(blockData)) {
            int cellNumber = sheetsCellIndex + 1;
            this.errorReport = this.errorReport + "Data in cell A" + cellNumber + "\n";
            isValid = false;
        }
    }

    private void compareLocalHashToSheetsHash(Block block, String blockHash, int sheetsCellIndex) {
        if (!block.getHash().equals(blockHash)) {
            int cellNumber = sheetsCellIndex + 1;
            this.errorReport = this.errorReport + "Data in cell B" + cellNumber + "\n";
            isValid = false;
        }
    }

    private String constructReturnMessage(Boolean isChainValid) {
        String message = "";

        if (isChainValid) {
            message = "\nGoogle Sheets matches with the local copy.";
        } else {
            message = "\nFound corrupted entries in:\n" + this.errorReport + "\nRewrote the sheet.";
        }
        return message;
    }

    /**
     * This method clears a spreadsheet and rewrites it according to the data
     * stored locally
     *
     * @param chain this chain will be rewritten
     * @throws IOException
     */
    public void rewriteChain(Chain chain) throws IOException {

        this.clearSheet();

        for (int i = 0; i < chain.getChainSize(); i++) {
            this.appendToSheet(chain.getBlock(i));
        }
    }
}
