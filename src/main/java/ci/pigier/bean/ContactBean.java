package ci.pigier.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import ci.pigier.entity.Contact;
import ci.pigier.service.ContactService;


@ManagedBean
@SessionScoped
public class ContactBean {
	
	@EJB
	private ContactService contactService;
	private Contact contact = new Contact();

	private Long id;
	private String pays;
	private String ville;
	private String telephone;
	
	public List<Contact> listeDesContacts() {
	    return contactService.get();
	}
	
	public String ajouterNouveau() {
		contact.setPays(pays);
        contact.setVille(ville);
        contact.setTelephone(telephone);
        // Sauvegarde en base de données
        contactService.save(contact);
        System.out.println("Contact ajouté avec succès");
        // Réinitialiser les champs après l'ajout
        pays = null;
        ville = null;
        telephone = null;
        return "AjoutContactOk";
	}
	
	public String modifierContact(Long contactId) {
	    Contact contactToUpdate = contactService.getById(contactId);
	    if (contactToUpdate != null) {
	        contactService.update(contactToUpdate);
	    }
	    return "ModifierContactOk";
	}

	public String supprimerContact(Long contactId) {
	    Contact contactToDelete = contactService.getById(contactId);
	    if (contactToDelete != null) {
	        contactService.delete(contactToDelete);
	        System.out.println("Contact supprimé avec succès");
	    } else {
	        System.out.println("Contact non supprimé");
	    }
	    return "SupprimerContactOk";
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

}
