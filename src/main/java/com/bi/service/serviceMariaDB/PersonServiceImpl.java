package com.bi.service.serviceMariaDB;


import com.bi.service.model.mariadb.Country;
import com.bi.service.model.mariadb.Gender;
import com.bi.service.model.mariadb.Person;
import com.bi.service.model.mongodb.PepPerson;
import com.bi.service.repositoriesMariaDB.CountryRepository;
import com.bi.service.repositoriesMariaDB.GenderRepository;
import com.bi.service.repositoriesMariaDB.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Service
public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;

    private GenderRepository genderRepository;

    private CountryRepository countryRepository;


    public PersonServiceImpl(PersonRepository personRepository, GenderRepository genderRepository, CountryRepository countryRepository) {
        this.personRepository = personRepository;
        this.genderRepository = genderRepository;
        this.countryRepository = countryRepository;

    }

    @Override
    /*    @Transactional*/
    public Person addPerson(PepPerson pepPerson) {

        Person person = persistPerson(pepPerson);

        return person;
    }

    private Person persistPerson(PepPerson pepPerson) {

        long timeNowAll = System.currentTimeMillis();

        Person person = new Person();

        person.setName(pepPerson.getFirstName());
        person.setLastName(pepPerson.getLastName());
        person.setAdditionalInfo(pepPerson.getIdentifier());

        person.setCountries(Arrays.asList(storeCountryIfNotExist(pepPerson.getCountry())));

        person.setGender(storeGenderIfNotExist(pepPerson.getGender()));

        long timePastAll = System.currentTimeMillis();
        personRepository.save(person);

        System.out.println("czas ktory upłynął w all : " + (timePastAll - timeNowAll));

        //bulkWithEntityManager(personList);

        return person;

    }

    private Gender storeGenderIfNotExist(String gender) {

        long timeNowGender = System.currentTimeMillis();
        Gender genderTemp = genderRepository.findByName(gender);

        if (genderTemp != null) {
            return genderTemp;
        }
        long timepastGender = System.currentTimeMillis();
        System.out.println("czas ktory upłynął w gender : " + (timepastGender - timeNowGender));

        Gender newGender = new Gender();
        newGender.setName(gender);

        // long timepastGender = System.currentTimeMillis();

        System.out.println("czas ktory upłynął w gender2 : " + (timepastGender - timeNowGender));

        return genderRepository.save(newGender);
    }


    private Country storeCountryIfNotExist(String country) {
        long timeNowCountry = System.currentTimeMillis();

        Country countrytemp = countryRepository.findByName(country);

        if (countrytemp != null) {
            return countrytemp;
        }
        Country newCountry = new Country();
        newCountry.setName(country);
        long timepastCountry = System.currentTimeMillis();

        System.out.println("czas ktory upłynął w country : " + (timepastCountry - timeNowCountry));

        return countryRepository.save(newCountry);

    }

}