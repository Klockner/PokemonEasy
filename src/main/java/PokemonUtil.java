import com.pokegoapi.api.PokemonGo;
import com.pokegoapi.api.pokemon.Pokemon;
import com.pokegoapi.exceptions.LoginFailedException;
import com.pokegoapi.exceptions.RemoteServerException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by klockner on 09/08/16.
 */
public class PokemonUtil {
    private PokemonGo go;

    public PokemonUtil(PokemonGo go) {
        this.go = go;
    }

    public void listAllPokemons() throws LoginFailedException, RemoteServerException {
        List<Pokemon> pokeList = go.getInventories().getPokebank().getPokemons();

        Collections.sort(pokeList, new Comparator<Pokemon>() {
            public int compare(Pokemon pokemon, Pokemon t1) {
                if (pokemon.getIvRatio() < t1.getIvRatio()) {
                    return 1;
                } else if (pokemon.getIvRatio() == t1.getIvRatio()) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });

        for (Pokemon poke : pokeList)
        {
            System.out.println(poke.getPokemonId().name() + " - IV: " + poke.getIvRatio() + " - CP: " + poke.getCp());
        }
        System.out.println("Total: " + pokeList.size());
    }

    public void transferPokemons() {
//        go.getInventories();
    }
}
