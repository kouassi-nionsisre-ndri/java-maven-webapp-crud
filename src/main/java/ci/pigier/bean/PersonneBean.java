package ci.pigier.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import ci.pigier.entity.Contact;
import ci.pigier.entity.Personne;
import ci.pigier.service.ContactService;
import ci.pigier.service.PersonneService;

@ManagedBean
@SessionScoped
public class PersonneBean {
	
	@EJB
	private PersonneService personneService;
	@EJB
	private ContactService contactService;
	
	private String nom;
	private String prenom;
	private String sexe;
	private String pays;
	private String telephone;
	private String ville;
	private Boolean isEdit = false;
	private Long personneId;

	public List<Entry<Personne, Contact>> listeDesPersonnesAvecLeurContact() {
	    List<Entry<Personne, Contact>> personnesAvecContact = new ArrayList<>();
	    List<Personne> personnes = personneService.get();
	    for (Personne personne : personnes) {
	        Contact contact = contactService.getByPersonne(personne);
	        personnesAvecContact.add(new SimpleEntry<>(personne, contact));
	    }
	    return personnesAvecContact;
	}
	
	public List<Personne> listeDesPersonnes() {
	    List<Personne> personnes = personneService.get();
	    return personnes;
	}

	public String ajouterNouveau() {
		
		Contact nouveauContact = new Contact();
	    nouveauContact.setPays(pays);
	    nouveauContact.setTelephone(telephone);	
	    nouveauContact.setVille(ville);
	    
	    // Sauvegarder ou mettre à jour le contact dans la base de données
	    if (nouveauContact.getId() == null) {
	        contactService.save(nouveauContact);
	    } else {
	        contactService.update(nouveauContact);
	    }
	    
	    Personne nouvellePersonne = new Personne();
	    nouvellePersonne.setNom(nom);
	    nouvellePersonne.setPrenom(prenom);
	    nouvellePersonne.setSexe(sexe);
	    
	    // Associer le contact à la nouvelle personne
	    nouvellePersonne.setContact(nouveauContact);
	    
		// Sauvegarde en base de données
	    if (nouveauContact.getId() == null) {
			personneService.save(nouvellePersonne);
	    } else {
	    	personneService.update(nouvellePersonne);
	    }
	    
		System.out.println("Vous avez ajouté une Personne avec succès");
		
        // Réinitialiser les champs après l'ajout
		nom = null;
		prenom = null;
		sexe = null;
		pays = null;
		telephone = null;
		ville = null;
        isEdit = false;
        personneId = null;
		return "AjoutPersonneOk";
		
	}
	
	public String preparerModificationPersonne(Long personneId) {
	    // Récupérer la personne à modifier en fonction de son ID
	    Personne personneAModifier = personneService.getById(personneId);
	    Contact contactAModifier = contactService.getByPersonne(personneAModifier);
	    if (personneAModifier != null) {
	        // Pré-remplir les champs du formulaire avec les détails de la personne à modifier
	        this.nom = personneAModifier.getNom();
	        this.prenom = personneAModifier.getPrenom();
	        this.sexe = personneAModifier.getSexe();
	        this.pays = contactAModifier.getPays();
	        this.telephone = contactAModifier.getTelephone();
	        this.ville = contactAModifier.getVille();
	        this.isEdit = true;
	        this.personneId = personneId;
	    } 
	    // Rediriger vers la page de modification
	    return "index";
	}
	
	public String annulerModificationPersonne() {
        this.nom = null;
        this.prenom = null;
        this.sexe = null;
        this.pays = null;
        this.telephone = null;
        this.ville = null;
        this.isEdit = false;
        this.personneId = null;
	    // Rediriger vers la page de modification
	    return "index";
	}

	
	public String modifierPersonne() {
		System.out.println("Valeur de personneId : "+this.personneId);
	    Personne personneAModifier = personneService.getById(this.personneId);
	    Contact contactAModifier = contactService.getByPersonne(personneAModifier);
		System.out.println("Valeur de Personne : "+personneAModifier.toString());
		if (personneAModifier != null) {
	        // Pré-remplir les champs du formulaire avec les détails de la personne à modifier
	        personneAModifier.setNom(this.nom);
	        personneAModifier.setPrenom(this.prenom);
	        personneAModifier.setSexe(this.sexe);
	        contactAModifier.setPays(this.pays);
	        contactAModifier.setTelephone(this.telephone);
	        contactAModifier.setVille(this.ville);
	        contactService.update(contactAModifier);
	        personneService.update(personneAModifier);
	        this.annulerModificationPersonne();
	    }
	    return "ModiferPersonneOk";
	}

	public String supprimerPersonne(Long personneId) {
	    Personne personneToDelete = personneService.getById(personneId);
	    if (personneToDelete != null) {
	        personneService.delete(personneToDelete);
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Personne supprimée avec succès", null));
	    } else {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Personne non trouvée", null));
	    }
	    return "SupprimerPersonneOk"; // ou la page vers laquelle vous souhaitez rediriger
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public Boolean getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(Boolean isEdit) {
		this.isEdit = isEdit;
	}

	public Long getPersonneId() {
		return personneId;
	}

	public void setPersonneId(Long personneId) {
		this.personneId = personneId;
	}
	
}
