package ci.pigier.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ci.pigier.entity.Contact;
import ci.pigier.entity.Personne;

@Stateless
public class ContactService {

	@PersistenceContext
	private EntityManager entityManagerContact;
	
	/**
	 * Persiste un produit dans la base de données.
	 *
	 * @param product Le produit à persister.
	 */
	public void save(Contact contact) {
		// Utilisation de l'EntityManager pour persister le produit dans la base de données
		entityManagerContact.persist(contact);
	}
	
	/**
	 * Récupère la liste des personnes depuis la base de données.
	 *
	 * @return ArrayList des personnes en base de données.
	 */
	public ArrayList<Contact> get() {
		// Création d'une requête JPQL pour récupérer tous les personnes de la base de données
		TypedQuery<Contact> contacts = entityManagerContact.createQuery("SELECT c FROM Contact c", Contact.class);
		// Exécution de la requête et récupération des résultats dans une liste typée de personnes
		return (ArrayList<Contact>) contacts.getResultList();
	}
	
	public Contact getById(Long id) {
	    // Utilisation de l'EntityManager pour récupérer le contact par son identifiant
	    return entityManagerContact.find(Contact.class, id);
	}

	public Contact getByPersonne(Personne personne) {
		// Création d'une requête JPQL pour sélectionner le contact correspondant à la personne spécifiée
	    TypedQuery<Contact> query = entityManagerContact.createQuery("SELECT c FROM Contact c WHERE c.id = "+personne.getContact().getId(), Contact.class);
	    // Exécution de la requête et récupération des résultats dans une liste
	    List<Contact> result = query.getResultList();
	    // Renvoi du premier contact trouvé, ou null s'il n'y a aucun contact associé à la personne spécifiée
	    return result.isEmpty() ? null : result.get(0);
	}
	
	public void update(Contact contact) {
	    // Utilisation de merge pour mettre à jour le contact dans la base de données
	    entityManagerContact.merge(contact);
	}

	public void delete(Contact contact) {
	    // Utilisation de remove pour supprimer le contact de la base de données
	    entityManagerContact.remove(entityManagerContact.contains(contact) ? contact : entityManagerContact.merge(contact));
	}
	
}
