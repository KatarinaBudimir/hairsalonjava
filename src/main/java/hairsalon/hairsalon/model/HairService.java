package hairsalon.hairsalon.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;


@Entity
@Table(name="hair_services")
public class HairService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 50, message = "Ime usluge mora imati barem 2 i manje od 50 slova.")
    @Column(nullable = false, length = 50)
    private String serviceName;

    @Size(min = 5, max = 200, message = "Opis usluge mora imati barem 5 i manje od 200 slova.")
    @Column(nullable = false, length = 50)
    private String description;


    @DecimalMin(value = "0.00", message = "Cijena ne može biti manja ili jednaka 0.")
    @Digits(integer = 3, fraction = 2, message = "Cijena ne može imati više od 2 decimalna mjesta.")
    private BigDecimal price;


    public HairService(String serviceName, String description, BigDecimal price) {
        this.serviceName = serviceName;
        this.description = description;
        this.price = price;
    }

    public HairService() {
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }



}