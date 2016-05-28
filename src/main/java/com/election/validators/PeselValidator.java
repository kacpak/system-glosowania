package com.election.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.election.controllers.PersonController;
import com.election.domain.Person;

/*- według http://www.algorytm.org/numery-identyfikacyjne/pesel.html */

@Component
public class PeselValidator implements Validator {

	private Logger log = LoggerFactory.getLogger(PersonController.class);

	@Override
	public void validate(Object target, Errors errors) {

		Person person = (Person) target;

		if (person.getPeselNumber().matches("\\d{11}")) {
			int month = Integer.parseInt(person.getPeselNumber().substring(2, 4));
			log.info("month " + month);
			
		
			if(month > 80 && month <= 80+12){ /*-  dla lat 1800 - 1899 + 80 do miesiąca */
				errors.rejectValue("peselNumber", "month>80");
			}
			
			else if(month > 40 && month <= 40+12){  /*-  dla lat 2100 - 2199 + 40 do miesiąca */
				errors.rejectValue("peselNumber", "month>40");
			}
			
			else if(month > 60 && month <= 60+12){  /*-  dla lat 2200 - 2299 + 60 do miesiąca */
				errors.rejectValue("peselNumber", "month>60");
			}
			
			else if(month > 20 && month <= 20+12){  /*-  dla lat 2200 - 2299 + 60 do miesiąca */
				errors.rejectValue("peselNumber", "month>20");
			}
			
			else if(month > 0 && month <= 12){  /*-  dla lat dla lat 1900 - 1999 */
				/* jest ok */
			}
			
			else{
				errors.rejectValue("peselNumber", "wrongMonth");
			}
			
			
			
			
			
		}


	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Person.class.equals(clazz) || Person.class.equals(clazz);
	}

}



/*- Kopia http://www.algorytm.org/numery-identyfikacyjne/pesel.html  poniżej*/


/*
 Zgodnie z informacją zamieszczoną na stronach rządowych Rejestr PESEL –
Powszechny Elektroniczny System Ewidencji Ludności prowadzony jest od 1979 roku i zawiera dane osób przebywających stale na terytorium RP, 
zameldowanych na pobyt stały lub czasowy trwający ponad 2 miesiące a także osób ubiegających się o wydanie dowodu osobistego lub osób, 
dla których odrębne przepisy przewidują potrzebę posiadania numeru PESEL.

Budowa numeru PESEL jest następująca:
cyfry 1-2 to ostatnie dwie cyfry roku urodzenia
cyfry 3-4 to dwie cyfry miesiąca urodzenia
cyfry 5-6 to dwie cyfry dnia urodzenia
cyfry 7-10 liczba porządkowa z oznaczeniem płci (liczba parzysta - kobieta, liczba nieparzysta - mężczyzna)
cyfra 11 suma kontrolna

Jak zapewne zauważyłeś zapis taki nie pozwoliłby na rozróżnienie osób urodzonych w latach np. 1999 i 1899. Dlatego też dla odróżnienia do numeru miesiąca dodawane są następujące wartości:
dla lat 1800 - 1899 - 80
dla lat 1900 - 1999 - 0
dla lat 2000 - 2099 - 20
dla lat 2100 - 2199 - 40
dla lat 2200 - 2299 - 60

Tak więc początek numeru PESEL osoby urodzonej 10 lutego 1899 roku będzie wyglądał tak: 998210... a osoby urodzonej 10 lutego 1999 roku tak: 990210...
Teraz została nam jeszcze sprawa obliczania sumy kontrolnej. Każdą pozycję numeru ewidencyjnego mnoży się przez odpowiednią wagę, 
są to kolejno: 1 3 7 9 1 3 7 9 1 3. Następnie utworzone iloczyny dodaje się i wynik dzieli się modulo 10. Wynik ten należy odjąć od 10 i 
znów podzielić przez modulo 10 (to zabezpieczenie gdybyśmy w poprzednim kroku otrzymali 10).

PRZYKŁAD:

Rozważmy PESEL osoby urodzonej 8 lipca 1902 roku, płci żeńskiej (parzysta końcówka numeru z serii – 0362). 
Czyli mamy wówczas 0207080362. Teraz kolejne cyfry należy przemnożyć przez odpowiednie wagi i dodać do siebie.
0*1 + 2*3 + 0*7 + 7*9 + 0*1 + 8*3 + 0*7 + 3*9 + 6*1 + 2*3 = 0 + 6 + 0 + 63 + 0 + 24 + 0 + 27 + 6 + 6 = 132
Wynik dzielimy modulo przez 10.
132 mod 10 = 2
A następnie odejmujemy od 10
10 - 2 = 8.
I wynik dzielimy znów modulo 10
8 mod 10 = 8
Cyfra kontrolna to 8, zatem nasz prawidłowy numer PESEL to: 02070803628








*/

















