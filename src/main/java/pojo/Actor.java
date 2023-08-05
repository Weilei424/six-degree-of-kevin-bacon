package pojo;

public class Actor {
	private String actorId;
	private String name;
	
	public Actor() {
		super();
	}

	public Actor(String id, String name) {
		this.actorId = id;
		this.name = name;
	}

	public String getActorId() {
		return actorId;
	}

	public void setActorId(String id) {
		this.actorId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
