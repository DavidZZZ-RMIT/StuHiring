package rmit.sef.assignment1.collection;

import java.util.List;

import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import rmit.sef.assignment1.core.Comment;
import rmit.sef.assignment1.db.DataKeeper;

public class CommentCollection {
	private ObjectRepository<Comment> repository;

	public CommentCollection() {
		repository = DataKeeper.getInstance().getRepository(Comment.class);
	}

	public boolean add(Comment cmt) {
		return repository.insert(cmt).getAffectedCount() == 1;
	}

	public void remove(Comment cmt) {
		repository.remove(cmt);
	}

	public List<Comment> getCommentsByJobApplicationId(String jobApplicationId) {
		return repository.find(ObjectFilters.eq("jobApplicationId", jobApplicationId)).toList();
	}
	
	public List<Comment> getCommentsByAuthor(String author) {
		return repository.find(ObjectFilters.eq("author", author)).toList();
	}

	public List<Comment> getComments(String jobApplicationId, String author) {
		return repository.find(ObjectFilters.and(ObjectFilters.eq("jobApplicationId", jobApplicationId),
				ObjectFilters.eq("author", author))).toList();
	}
	
	public void removeAll() {
		repository.remove(ObjectFilters.ALL);
	}
}
