package fi.koululainenjoona.googlesheets;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ClearValuesRequest;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import fi.koululainenjoona.logic.Block;
import java.io.IOException;
import java.security.GeneralSecurityException;

import java.util.Arrays;

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
        
        UpdateValuesResponse result = sheetsService.spreadsheets().values()
                .update(spreadsheetId, "A1", body)
                .setValueInputOption("RAW")
                .execute();
    }
    
    public void appendToSheet(Block block) throws IOException {
        
        String data = block.getData();
        String hash = block.getHash();
        
        ValueRange appendBody = new ValueRange().setValues(Arrays.asList(Arrays.asList(data, hash)));
	
        AppendValuesResponse appendResult = sheetsService.spreadsheets().values()
	  .append(spreadsheetId, "A1", appendBody)
	  .setValueInputOption("USER_ENTERED")
	  .setInsertDataOption("INSERT_ROWS")
	  .setIncludeValuesInResponse(true)
	  .execute();
    }

    public void clearSheet() throws IOException {
        ClearValuesRequest clearValuesRequest = new ClearValuesRequest();

        sheetsService.spreadsheets().values().clear(spreadsheetId, "A1:Z1000", clearValuesRequest).execute();

    }
}
