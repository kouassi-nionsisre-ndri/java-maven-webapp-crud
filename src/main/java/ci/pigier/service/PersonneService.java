package ci.pigier.service;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ci.pigier.entity.Personne;

@Stateless
public class PersonneService {

	@PersistenceContext
	private EntityManager entityManagerPersonne;
	
	/**
	 * Persiste un produit dans la base de données.
	 *
	 * @param product Le produit à persister.
	 */
	public void save(Personne personne) {
		// Utilisation de l'EntityManager pour persister le produit dans la base de données
		entityManagerPersonne.persist(personne);
	}
	
	/**
	 * Récupère la liste des personnes depuis la base de données.
	 * 
	 * @return ArrayList des personnes en base de données.
	 */
	public ArrayList<Personne> get() {
		// Création d'une requête JPQL pour récupérer tous les personnes de la base de données
		TypedQuery<Personne> personnes = entityManagerPersonne.createQuery("SELECT p FROM Personne p", Personne.class);
		
		// Affichage du contenu de la liste de personnes (à des fins de débogage)
	    //System.out.println("Liste des personnes récupérées depuis la base de données : " + personnes.getResultList().toString());
		
		// Exécution de la requête et récupération des résultats dans une liste typée de personnes
		return (ArrayList<Personne>) personnes.getResultList();
	}
	
	public Personne getById(Long id) {
	    // Utilisation de l'EntityManager pour récupérer la personne par son identifiant
	    return entityManagerPersonne.find(Personne.class, id);
	}
	
	public void update(Personne personne) {
	    // Utilisation de merge pour mettre à jour la personne dans la base de données
	    entityManagerPersonne.merge(personne);
	}

	public void delete(Personne personne) {
	    // Utilisation de remove pour supprimer la personne de la base de données
	    entityManagerPersonne.remove(entityManagerPersonne.contains(personne) ? personne : entityManagerPersonne.merge(personne));
	}
	
}
