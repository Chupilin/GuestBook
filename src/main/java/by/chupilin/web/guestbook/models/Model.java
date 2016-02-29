package by.chupilin.web.guestbook.models;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;

@MappedSuperclass
public abstract class Model implements Serializable {

    private static final long serialVersionUID = 2426998516961517343L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Incremental
    @Column(name = "id")
    private Long id;

    public Model() {
    }

    public Model(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof Model) && (id != null)
                ? id.equals(((Model) other).id)
                : (other == this);
    }

    @Override
    public int hashCode() {
        return (id != null)
                ? (this.getClass().hashCode() + id.hashCode())
                : super.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.getClass().getName());
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            result.append(" ");
            try {
                result.append(field.getName());
                result.append(": ");
                result.append(field.get(this));
            } catch (IllegalAccessException ex) {
                System.out.println(ex);
            }
        }
        return result.toString();
    }

}