import com.pokegoapi.api.PokemonGo;
import com.pokegoapi.api.pokemon.Pokemon;
import com.pokegoapi.exceptions.LoginFailedException;
import com.pokegoapi.exceptions.RemoteServerException;

import java.util.*;

/**
 * Created by klockner on 09/08/16.
 */
public class PokemonUtil {
    private PokemonGo go;

    public PokemonUtil(PokemonGo go) {
        this.go = go;
    }

    public void orderDescIvRatio(List<Pokemon> pokeList) {
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
    }

    public void listAllPokemons() throws LoginFailedException, RemoteServerException {
        List<Pokemon> pokeList = go.getInventories().getPokebank().getPokemons();

        orderDescIvRatio(pokeList);

        for (Pokemon poke : pokeList)
        {
            System.out.println("ID: " + poke.getPokemonId().getNumber() + " - " + poke.getPokemonId().name() + " - IV: " + poke.getIvRatio() + " - CP: " + poke.getCp());
        }
        System.out.println("Total: " + pokeList.size());
    }

    //Transfer pokemon filtered by name, CP && IV
    public void transferPokemonsFilterCpAndIv(int cpAmount, double ivAmount) throws LoginFailedException, RemoteServerException {
        for (Pokemon poke : go.getInventories().getPokebank().getPokemons()) {
            if (poke.getCp() < cpAmount && poke.getIvRatio() < ivAmount) {
                poke.transferPokemon();
            }
        }
    }

    //Transfer pokemon filtered by name, CP && IV
    public void transferPokemonFilterNameCpAndIv(String pokeName, int cpAmount, double ivAmount) throws LoginFailedException, RemoteServerException {
        for (Pokemon poke : go.getInventories().getPokebank().getPokemons()) {
            if (poke.getPokemonId().name().equalsIgnoreCase(pokeName)) {
                if (poke.getCp() < cpAmount && poke.getIvRatio() < ivAmount) {
                    poke.transferPokemon();
                }
            }
        }
    }

    public void removePokemonSpecieByIvMoreThan(int pokemonSpecieQuantitie) throws LoginFailedException, RemoteServerException {
        List<Pokemon> pokeList = go.getInventories().getPokebank().getPokemons();
        orderDescIvRatio(pokeList);
        int contador = 0;

        Map<String, List<Pokemon>> bestPokemons = new HashMap<String, List<Pokemon>>();

        for(Pokemon poke : pokeList) {
            if (bestPokemons.get(poke.getPokemonId().name()) == null) {
                bestPokemons.put(poke.getPokemonId().name(), new ArrayList<Pokemon>());
            }

            if (bestPokemons.get(poke.getPokemonId().name()).size() < pokemonSpecieQuantitie) {
                bestPokemons.get(poke.getPokemonId().name()).add(poke);
            } else {
                poke.transferPokemon();
                contador++;
                System.out.println("Transferindo: " + poke.getPokemonId() + "IV: " + poke.getIvRatio() + " - CP: " + poke.getCp());
            }
        }
        System.out.println("Pokemons transferidos: " + contador);
    }
}
