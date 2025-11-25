import { Component } from '@angular/core';

@Component({
  selector: 'app-translate',
  imports: [],
  templateUrl: './translate.component.html',
  styleUrl: './translate.component.css'
})
export class TranslateComponent {

  //Variables to hold the English and the Pig Latin
  englishText:string = ""
  pigLatinText:string = ""


  //Function to do the translation
  translate() {
    // Split the English text into words
    const words = this.englishText.split(' ');

    // Translate each word to Pig Latin
    const translatedWords = words.map(word => {
      // Check if the word starts with a vowel
      if (/^[aeiouAEIOU]/.test(word)) {
        return word + 'way'; // Add "way" if it starts with a vowel
      } else {
        // Move the first consonant(s) to the end and add "ay"
        const match = word.match(/^([^aeiouAEIOU]+)(.*)$/);
        return match ? match[2] + match[1] + 'ay' : word;
      }
    });

    // Join the translated words back into a single string
    this.pigLatinText = translatedWords.join(' ');
  }

}
