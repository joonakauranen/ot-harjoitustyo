package fi.koululainenjoona.googlesheets;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ClearValuesRequest;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.io.IOException;
import java.security.GeneralSecurityException;

import java.util.Arrays;

public class SheetsChain {

    private Sheets sheetsService;
    private String spreadsheetId = "1YfgQ27ZTYH4ORWwX9duBnVBVq5dM8lSUDKzokZxXNqk";

    public SheetsChain() throws GeneralSecurityException, IOException {
        this.sheetsService = SheetsService.getSheetsService();
    }

    public void writeSheet() throws IOException {
        ValueRange body = new ValueRange()
                .setValues(Arrays.asList(
                        Arrays.asList("Expenses January"),
                        Arrays.asList("books", "30"),
                        Arrays.asList("pens", "10"),
                        Arrays.asList("Expenses February"),
                        Arrays.asList("clothes", "20"),
                        Arrays.asList("shoes", "5")));
        UpdateValuesResponse result = sheetsService.spreadsheets().values()
                .update(spreadsheetId, "A1", body)
                .setValueInputOption("RAW")
                .execute();
    }

    public void clearSheet() throws IOException {
        ClearValuesRequest clearValuesRequest = new ClearValuesRequest();

        sheetsService.spreadsheets().values().clear(spreadsheetId, "A1:Z1000", clearValuesRequest).execute();

    }
}
