package rmit.sef.assignment1.core;

import java.util.LinkedList;

public class JobPool {
	private LinkedList<Job> jobs;
	
	public boolean addJob(Job j) {
		jobs.add(j);
		return true;
	}
}
