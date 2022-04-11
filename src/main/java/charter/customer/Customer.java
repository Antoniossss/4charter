package charter.customer;


import charter.AbstractEntity;

import javax.persistence.Entity;

@Entity
public class Customer extends AbstractEntity {
    String name;
}
