# Rakennekuvaus

Ohjelman ytimenä on kolme luokkaa: Game, joka sisältää
itse pelilogiikan ja toiminnot, Display, joka pitää huolta
ohjelman piirtämisestä näytölle sekä Main, joka yhdistää
kaikki luokat ja käynnistää pelin.

Main-luokan päämetodissa luodaan luokkien Game ja Display
lisäksi luokat ImageContainer, joka hoitaa kuvien lataamisen 
muistiin ja jakaa niitä tarvitseville luokille; GamePanel, 
johon linkitetään Game sekä ImageContainer, joka tekee itse 
raskaan piirtotyön ja joka annetaan Displaylle JPanelina; Input
johon linkitetään Display, joka lisätään listeneriksi GamePainteriin
ja joka hoitaa pelaajainteraktion; CoordinateTranslator johon
linkitetään Game ja joka pitää huolta koordinaattien muuntamisesta
näytöltä peliin ja takaisin; sekä kaksi Player-rajapinnan toteuttavaa luokkaa
jotka lisätään Gameen. Display sisältää myös luokat EndPanel ja MenuPanel, jotka
vastaavat pelin valikoista, ja Game on tekemisissä näitä käyttävien logiikkaluokkien
GameInitializer ja EndHandler kanssa.

Display käyttää apunaan luokkia UIPainter, joka maalaa annetulle grafiikkaobjektille
käyttöliittymän ja BackgroundPainter, joka piirtää pelin taustan. Molemmat näistä sisältävät
luokan BasicPainter, joka hoitaa peruspiirto-operaatioita. UIPainter käyttää TrajectoryCalculatoria,
jonka avulla se laskee, mihin heitto voisi tippua.

Game luo käyttöönsä kaksi luokan Team instanssia, jotka puolestaan luovat 
itsellensä KyykkaContainer-instanssin sekä neljä Thrower-oliota. KyykkaContainer puolestaan
sisältää 40 aluksi kappaletta Kyykka-luokan instansseja. Game sisältää
myös tietorakenteet Player-rajapinnan toteuttaville olioille sekä Karttu-olioille.

Jokainen PhysicsEntity-abstraktiluokan toteuttava luokka - Kyykka, Karttu sekä
Thrower - sisältää HitBox-olion, joka kuvaa olioiden fyysistä sijaintia.
HitBox-olio puolestaan sisältää Point3D-olion, joka kuvaa paikkaa kolmiulotteisessa
karteesisessa koordinaatistossa. Jokainen PhysicsEntity sisältää myös itselleen ominaisen Sprite-rajapinnan toteuttavan luokan.
Tätä käytetään PhysicsEntityjä piirtäessä - Sekä PhysicsEntity että Sprite
toteuttavat rajapinnan Drawable, ja kun PhysicsEntityn getImgName-metodia
kutsuu, kutsuu PhysicsEntity Spriten getImgName-metodia.

PhysicsEntity perii luokan MovingEntity, joka hoitaa perus liikkumismekaniikkaa.

Pelin aikana syntyy luokkia ThrowParams pelaajien luodessa heittoparametreja,
sekä Karttu Thrower-olioiden "heittäessä". Karttu-oliot lisätään Game-olioon
ja tyhjennetään aina neljän heiton jälkeen. Samalla Kyykka-olioiden määrä vähenee
niiden joutuessa pelineliöiden ulkopuolelle.