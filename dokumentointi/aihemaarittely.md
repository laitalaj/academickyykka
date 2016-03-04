#Aihemäärittely

**Aihe:** Akateeminen kyykkä-videopeli

Pelissä pelataan joukkoekyykkää [akateemisen kyykän säännöillä](http://kyykka.fi/s%C3%A4%C3%A4nn%C3%B6t) joko toista pelaajaa tai tekoälyä vastaan.

Peli on vuoropohjainen - heittoja suoritetaan vuoron perään. Graafinen toteutus on kaksiulotteinen. Peliä kuvataan heittoneliön takaa.

Vaikka grafiikat ovatkin kaksiulotteiset, pelin (yksinkertainen) fysiikkasimulaatio tehdään kolmiulotteisesti. Grafiikkaan luodaan myös kolmiulotteista vaikutelmaa varjoilla, perspektiivillä (kauempana kamerasta olevat asiat pienempiä), yms.

Syötteitä pelissä annetaan lähinnä hiirellä; pelaaja liikkuu hiirtä liikuttamalla ja heiton ominaisuudet määritellään klikkauksin.

**Käyttäjät:** Pelaajat

**Pelaajan toiminnot:**
* Pelin aloittaminen
  * Vastustajan määrittäminen: Toinen pelaaja tai tekoäly
* Pelaajan heittopaikan määrittäminen
* Heiton suunnan määrittäminen
* Heiton voiman määrittäminen
* Kierteen määrän määrittäminen
* Tavoite: Poistaa omat kyykät pelineliöstä

**Mahdollisia lisätoimintoja:** (ajan salliessa)
* Päävalikko: Asetukset yms.
* Erilaiset pelaajat - joukkoeen muodostus: Tarkka-ampujat heittävät tarkemmin, mutta raivopäät heittävät kovempaa.
* Alkoholin käyttö: Juota pelaajallesi alkoholia - lisää voimaa uhraamalla tarkkuutta
* Kartun koon valinta: Pienempi lentää pidemmälle ja tarkemmin mutta ei kaada niin paljoa
* Erilaiset heittotyylit: Juoksulähtö lisää voimaa mutta vähentää tarkkuutta sekä riskeeraa vastustajan kyykkien kaatamisen

**Luokkakaavio**

![Luokkakaavio](/dokumentointi/luokkakaavio5.png)

Määrittelyvaiheen luokkakaavio [täällä](/dokumentointi/vanhatkaaviot/luokkakaavio.png)

**Sekvenssikaavioita**

![Heittopaikan määritys](/dokumentointi/sekvenssi_heittopaikka2.png)
![Heittosuunnan määritys](/dokumentointi/sekvenssi_heittosuunta2.png)