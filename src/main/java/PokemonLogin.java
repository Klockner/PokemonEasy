import com.pokegoapi.api.PokemonGo;
import com.pokegoapi.auth.GoogleUserCredentialProvider;
import com.pokegoapi.exceptions.LoginFailedException;
import com.pokegoapi.exceptions.RemoteServerException;
import okhttp3.OkHttpClient;

import java.util.Scanner;

/**
 * Created by klockner on 09/08/16.
 */
public class PokemonLogin {

    String refreshToken;

    public PokemonGo loginWithGoogle() throws LoginFailedException, RemoteServerException {
        OkHttpClient httpClient = new OkHttpClient();

        /**
         * Google:
         * You will need to redirect your user to GoogleUserCredentialProvider.LOGIN_URL
         * Afer this, the user must signin on google and get the token that will be show to him.
         * This token will need to be put as argument to login.
         */
        GoogleUserCredentialProvider provider = new GoogleUserCredentialProvider(httpClient);

        // in this url, you will get a code for the google account that is logged
        System.out.println("Please go to " + GoogleUserCredentialProvider.LOGIN_URL);
        System.out.println("Enter authorization code:");

        // Ask the user to enter it in the standart input
        Scanner sc = new Scanner(System.in);
        String access = sc.nextLine();

        // we should be able to login with this token
        provider.login(access);

        refreshToken = provider.getRefreshToken();

        System.out.println("################## TOKEN - (copy and paste inside SecretToken class) ######################");
        System.err.println(refreshToken);
        System.out.println("###########################################################################################");

        PokemonGo go = new PokemonGo(new GoogleUserCredentialProvider(httpClient, refreshToken), httpClient);

        return go;
    }

    public PokemonGo loginWithToken(String token) throws LoginFailedException, RemoteServerException {
        OkHttpClient httpClient = new OkHttpClient();
        PokemonGo go = new PokemonGo(new GoogleUserCredentialProvider(httpClient, token), httpClient);
        return go;
    }
}
