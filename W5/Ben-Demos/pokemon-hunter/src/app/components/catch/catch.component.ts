import { Component, signal, WritableSignal } from '@angular/core';
import { PokemonService } from '../../services/pokemon.service';
import { Pokemon } from '../../interfaces/pokemon';

@Component({
  selector: 'app-catch',
  imports: [],
  templateUrl: './catch.component.html',
  styleUrl: './catch.component.css'
})
export class CatchComponent {

  //Constructor Inject the Service
  constructor(private pokemonService:PokemonService){} 

  //Variable to hold the pokemon from PokeAPI
  pokemon: WritableSignal<Pokemon | null> = signal(null);

  //ngOnInit - a component lifecycle method that lets us define logic to fire when the component renders
  ngOnInit(){
    this.getPokemon()
  }

  //The method that gets a Pokemon with the Service
  getPokemon(){

    //When we get an Observable returned, we need to SUBSCRIBE to it to access its data (The HTTP Response)
    this.pokemonService.getPokemon().subscribe(data => {
      console.log(data)

      //populate pokemon with the data in the response
      this.pokemon.set(data)
    })

  }

}