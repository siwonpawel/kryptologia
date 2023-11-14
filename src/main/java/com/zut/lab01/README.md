Kryptografia LAB 01

# a) Jaki jest czas wykonania ataku dla szyfrogramu składającego się z 5 bloków? Podaj również dane techniczne komputera, na którym przeprowadzono test. Test wykonaj 3-krotnie i podaj uśrednione wyniki.

1. 0,572s
2. 0,0494
3. 0,573

AVG = 0,546(3)s

MacBook Pro 14" M1 Pro (16GB RAM, 8 CPU)

# b) Kiedy możliwy jest odczyt również pierwszego bloku?

Kiedy znany jest wektor inicjalizujący (IV). Dla szyfrowania danego bloku w CBC wykorzystuje się blok poprzedni, dla bloku pierwszego potrzebny jest więc dodatkowy ciąg. To samo dzieje się przy odszyfrowywaniu, poprzedni blok jest wykorzystywany do odczytania następnego. “Dodatkowy ciąg” to wygenerowany losowy ciąg znaków IV. 

# c) Jaki błąd przy implementacji należy popełnić, aby atak był możliwy?

Zwracać dodatkową informację o błędzie. Zamiast jedynie informować o tym że odszyfrowywanie się nie powiodło, zwracać informację że padding jest błędny (np. serwer zwracający kod błędu 500 jeśli padding jest niepoprawny, a 200 jeśli jest dobry). 

# d) W jakich środowiskach zaimplementowano ten atak? (wymień przynajmniej 3)

- ASP.NET
- SSL/TLS
- IPSEC
- SSH2

# e) Czy atak działa tylko dla algorytmu AES? Odpowiedź uzasadnij.

Nie, zadziała dla algorytmów które szyfrują bazując na blokach o stałej wielkości. 

# f) Ile razy maksymalnie należy odpytać wyrocznię w celu odczytania jednego bloku?

Ile razy maksymalnie należy odpytać wyrocznię w celu odczytania jednego bloku? 255 * (wielkość jednego bloku w bajtach)  

Dla AES 4080 razy. 

# g) Czy w przypadku zastosowania innych schematów padding’u atak będzie działał?

Tak, jeśli w paddingu będzie informacja o tym ile bajtów wypełnia.
n - liczba wypełnionych przez padding bajtów.
Przykłady innych schematów: ciąg od 1 do n, ciąg zer z ostatnim symbolem n, ostatni symbol jak n i pozostałe wybrane losowo.

Odpowiedź uzasadnij.