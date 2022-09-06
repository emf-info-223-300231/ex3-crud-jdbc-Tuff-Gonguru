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
            System.out.println("test erreur");
        }
        if (index>= listePersonnes.size()) {
            index = index -1;
        }
        return listePersonnes.get(index);
    }

    public Personne debutPersonne() {
        index=0;
        return listePersonnes.get(index);
    }

    public Personne finPersonne() {
 
        index=listePersonnes.size()-1;
        return listePersonnes.get(index);
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
        return courantPersonne();
    }

    public Personne suivantPersonne() {
        if (index < listePersonnes.size()-1) {
            index++;
        }

        return listePersonnes.get(index);
    }

}
