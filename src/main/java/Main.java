import com.pokegoapi.api.PokemonGo;
import com.pokegoapi.exceptions.LoginFailedException;
import com.pokegoapi.exceptions.RemoteServerException;

/**
 * Created by klockner on 09/08/16.
 */
public class Main {

    public static void main(String[] args) {
        try {
            PokemonLogin pokeLogin = new PokemonLogin();
            PokemonGo go = pokeLogin.loginWithGoogle();
            //Comentar linha de baixo e descomentar a de cima
//            PokemonGo go = pokeLogin.loginWithToken(SecretToken.getGoogleToken());
            Thread.sleep(3000);

            PokemonUtil pokeUtil = new PokemonUtil(go);
            pokeUtil.listAllPokemons();

        } catch (RemoteServerException e) {
            System.out.println("Server error");
            e.printStackTrace();
        } catch (LoginFailedException e) {
            System.out.println("Login error");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
