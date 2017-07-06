ackage com.tuff.hyldium.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class UserItemOrder {

	@Id
	@ManyToOne
	public ItemOrder
	
	@Id
	@ManyToOne
	public User user;
	
	public float bundlePart;
	
}
