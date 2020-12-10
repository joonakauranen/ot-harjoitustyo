package fi.koululainenjoona.googlesheets;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

/**
 * This class obtains the authorization for interacting with Google Sheets API
 */
public class SheetsService {

    private static final String APPNAME = "blockchainexperiment";

    /**
     * This method contains the necessary service that allows the app to connect
     * to Google Sheets
     *
     * @return Sheets service to interact with a spreadsheet
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static Sheets getSheetsService() throws IOException, GeneralSecurityException {
        Credential credential = SheetsService.authorize();
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), credential).setApplicationName(APPNAME).build();
    }

    /**
     * Creates the authorization flow for the users to get permission to use a
     * spreadsheet in Google Sheets
     *
     * @return Credential the credentials for accessing the Google API
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static Credential authorize() throws IOException, GeneralSecurityException {
        InputStream in = SheetsService.class.getResourceAsStream("/credentials.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), new InputStreamReader(in));

        List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS);

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), clientSecrets, scopes).setDataStoreFactory(new MemoryDataStoreFactory())
                .setAccessType("offline").build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");

        return credential;
    }

}
