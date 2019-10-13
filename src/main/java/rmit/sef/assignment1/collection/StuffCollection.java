package rmit.sef.assignment1.collection;

import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import rmit.sef.assignment1.core.Stuff;
import rmit.sef.assignment1.db.DataKeeper;

public class StuffCollection {

	private ObjectRepository<Stuff> repository;

	public StuffCollection() {
		repository = DataKeeper.getInstance().getRepository(Stuff.class);
	}

	public boolean addstuff(Stuff stuff) {
		if (isStuffExist(stuff))
			return false;
		return repository.insert(stuff).getAffectedCount() == 1;
	}

	public boolean isStuffExist(Stuff user) {
		return repository.find(ObjectFilters.eq("email", user.getEmail())).size() > 0;
	}
	
	public boolean isStuffExist(String email) {
		return repository.find(ObjectFilters.eq("email",email)).size() > 0;
	}

	public boolean isUserExist(String email) {
		return repository.find(ObjectFilters.eq("email", email)).size() > 0;
	}

	public boolean addstuffs(Stuff[] users) {
		return repository.insert(users).getAffectedCount() == users.length;
	}

	public Stuff getstuff(String emial) {
		return repository.find(ObjectFilters.eq("email", emial)).firstOrDefault();
	}

	public void updatestuff(Stuff user) {
		repository.update(user);
	}

	public void removestuff(Stuff user) {
		repository.remove(user);
	}
	public void removeAll() {
		repository.remove(ObjectFilters.ALL);
	}

}
