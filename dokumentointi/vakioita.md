#Ohjelman vakioita

##Yksiköt

Lähdekoodissa fysiikalle relevantit arvot ovat seuraavina yksikköinä:

* **Aika:** 1 tick = 0.01s -> 100 pelimoottorin päivitystä yhdessä sekunnissa
* **Matka:** Millimetriä
* **Nopeus:** Millimetriä / tick = mm / 0.01s
* **Paino:** Grammaa

##Kentän koordinaatit

* Kotijoukkoe on "pienikoordinaattiselta" puolelta heittävä joukkoe
* Origo (0, 0) sijaitsee kotijoukkoeen päädyn vasemmassa takakulmassa
* Oikea takakulma: (5000, 0)
* Vierasjoukkoeen oikea takakulma: (0, 20000)
* Vierasjoukkoeen vasen takakulma: (5000, 20000)
* Kotijoukkoeen kyykät / vierasjoukkoeen heittoneliön etusivu: (0, 15000) - (5000, 15000)
* Vierasjoukkoeen kyykät / kotijoukkoeen heittoneliön etusivu: (0, 5000) - (5000, 5000)
