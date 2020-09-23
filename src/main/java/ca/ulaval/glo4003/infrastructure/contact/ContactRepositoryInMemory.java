package ca.ulaval.glo4003.infrastructure.contact;

import ca.ulaval.glo4003.domain.contact.Contact;
import ca.ulaval.glo4003.domain.contact.ContactNotFoundException;
import ca.ulaval.glo4003.domain.contact.ContactRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactRepositoryInMemory implements ContactRepository {
  private final Map<String, Contact> contacts = new HashMap<>();

  @Override
  public List<Contact> findAll() {
    return new ArrayList<>(contacts.values());
  }

  @Override
  public Contact findById(String id) {
    return contacts.get(id);
  }

  @Override
  public void update(Contact contact) throws ContactNotFoundException {
    Contact foundContact = contacts.get(contact.getId());

    if (foundContact != null) {
      contacts.put(contact.getId(), contact);
    } else {
      throw new ContactNotFoundException("Contact not found, cannot be updated");
    }
  }

  @Override
  public void save(Contact contact) {
    contacts.put(contact.getId(), contact);
  }

  @Override
  public void remove(String id) {
    contacts.remove(id);
  }
}
