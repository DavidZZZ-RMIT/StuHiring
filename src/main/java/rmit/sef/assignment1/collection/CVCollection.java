package rmit.sef.assignment1.collection;

import java.util.List;

import org.dizitart.no2.IndexOptions;
import org.dizitart.no2.IndexType;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import rmit.sef.assignment1.core.CV;
import rmit.sef.assignment1.db.DataKeeper;

public class CVCollection {
	private ObjectRepository<CV> repository;

	public CVCollection() {
		repository = DataKeeper.getInstance().getRepository(CV.class);
	}

	public boolean add(CV cv) {
		if (isExist(cv))
			return false;
		System.out.print("CVCollection.add:" + cv.getId());
		return repository.insert(cv).getAffectedCount() == 1;
	}

	public boolean isExist(CV cv) {
		return repository.find(ObjectFilters.eq("id", cv.getId())).size() > 0;
	}

	public boolean isExist(String cvid) {
		return repository.find(ObjectFilters.eq("id", cvid)).size() > 0;
	}

	public CV getCV(String id) {
		return repository.find(ObjectFilters.eq("id", id)).firstOrDefault();
	}

	public void update(CV cv) {
		repository.update(cv);
	}

	public void remove(CV cv) {
		repository.remove(cv);
	}

	public void remove(String cvId) {
		repository.remove(getCV(cvId));
	}

	public List<CV> getStudentCV(String studentEmail) {
		return repository.find(ObjectFilters.eq("studentId", studentEmail)).toList();
	}
}
