/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.workers;

import app.beans.Personne;
import java.util.List;

/**
 *
 * @author RenzS
 */
public class PersonneManager {

    private int index = 0;
    private List<Personne> listePersonnes;

    public PersonneManager() {
    }

    public Personne courantPersonne() {

        if (listePersonnes ==null || listePersonnes.isEmpty()) {
            return null;
        }
        if (index>= listePersonnes.size()) {
            index = listePersonnes.size() -1;
        }
        return listePersonnes.get(index);
    }

    public Personne debutPersonne() {

        return listePersonnes.get(0);
    }

    public Personne finPersonne() {
        int fin = 0;
        fin = listePersonnes.size();
        fin--;
        return listePersonnes.get(fin);
    }

    public Personne precedentPersonne() {
        if (listePersonnes != null) {
            if (index >= 1) {
                index--;
            } else {
                index = 0;
            }
        }

        return listePersonnes.get(index);
    }

    public Personne setPersonnes(List<Personne> personnes) {
        listePersonnes = personnes;
        return listePersonnes.get(index);
    }

    public Personne suivantPersonne() {
        if (index < listePersonnes.size()) {
            index++;
        }

        return listePersonnes.get(index);
    }

}
