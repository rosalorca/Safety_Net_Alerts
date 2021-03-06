package com.openclassrooms.Safety_Net_Alerts.service;

import com.openclassrooms.Safety_Net_Alerts.repository.DataStore;
import com.openclassrooms.Safety_Net_Alerts.model.Persons;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j

public class PersonsService {

    @Autowired
    private DataStore dataStore;

    public void setDataStore(final DataStore dataStore) {
        this.dataStore = dataStore;
    }

    /**
     * @return
     */
    public List<Persons> getPersons() {
        return dataStore.getData().getPersons();
    }

    /**
     * @param city
     * @return
     */
    public List<Persons> getPersonsInCity(String city) {
        return dataStore.getData().getPersons().stream()
                .filter(p -> p.getCity().equals(city)).collect(Collectors.toList());
    }

    /**
     * @param addresses
     * @return
     */
    public List<Persons> getPersonsAtAddresses(final List<String> addresses) {
        return dataStore.getData().getPersons().stream()
                .filter(p -> addresses.contains(p.getAddress()))
                .collect(Collectors.toList());
    }

    /**
     * @param lastName
     * @return
     * renvoie toutes les personnes qui ont le même nom
     */
    public List<Persons> getPersonInfo(final String lastName) {
        return dataStore.getData().getPersons().stream()
                .filter(persons -> persons.getLastName().equals(lastName))
                .collect(Collectors.toList());
    }

    /**
     * @param persons
     * the request for add, update and delete fire stations
     */
    public void addPersons(Persons persons) {

        dataStore.getData().getPersons().add(persons);
        log.info("la personne a ètè bien ajouté!");
    }

    /**
     * @param firstName
     * @param lastName
     * @param updatePerson
     * @return
     */
    public Persons updatePersons(String firstName, String lastName, Persons updatePerson) {
        Optional<Persons> optionalPerson = dataStore.getData().getPersons()
                .stream()
                .filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
                .findFirst();
        if (optionalPerson.isPresent()) {
            System.out.println("j'ai trouvé la personne a modifier !");
            Persons person = optionalPerson.get();
            if (updatePerson.getFirstName() != null) {
                person.setFirstName(updatePerson.getFirstName());
            }
            if (updatePerson.getLastName() != null) {
                person.setLastName(updatePerson.getLastName());
            }
            if (updatePerson.getAddress() != null) {
                person.setAddress(updatePerson.getAddress());
            }
            if (updatePerson.getPhone() != null) {
                person.setPhone(updatePerson.getPhone());
            }
            if (updatePerson.getCity() != null) {
                person.setCity(updatePerson.getCity());
            }
            if (updatePerson.getEmail() != null) {
                person.setEmail(updatePerson.getEmail());
            }
            if (updatePerson.getZip() != 0) {
                person.setZip(updatePerson.getZip());
            }
            log.info("la personne a ètè bien modifiée !");
            return person;

        } else {
            log.error("je n'ai pas trouvé la personne à modifier !");
            return null;
        }
    }

    /**
     * @param firstName
     * @param lastName
     * @return
     */
    public boolean deletePersons(String firstName, String lastName) {
        Optional<Persons> optionalPerson = dataStore.getData().getPersons()
                .stream()
                .filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
                .findFirst();
        if (optionalPerson.isPresent()) {
            System.out.println("j'ai trouvé la personne à supprimer !");
            dataStore.getData().getPersons().remove(optionalPerson.get());
            return true;
        } else {
            log.error("je n'ai pas trouvé la personne à supprimer !");
            return false;
        }
    }

}
