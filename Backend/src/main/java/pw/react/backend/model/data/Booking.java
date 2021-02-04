package pw.react.backend.model.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import pw.react.backend.model.bookly.BooklyBooking;
import pw.react.backend.utils.JsonDateDeserializer;
import pw.react.backend.utils.JsonDateSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;


@Entity
@Table(name = "booking")  // Generate table in database with fields below
@Data // Lombok - autogenerate all getters and setters for variables
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Booking implements Serializable {

    private static final long serialVersionUID = -6783504532088859179L;

    //public static Booking EMPTY = new Booking();
    //public static Booking EMPTY = new Booking();




    @Id    
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "userId")
    private long userId;

    @Column(name = "userFirstName")
    private String userFirstName;

    @Column(name = "userLastName")
    private String userLastName;

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST) // Do not destroy address entry
    @JoinColumn(name = "parking_id", referencedColumnName = "id")
    private Parking parking;

    @Column(name = "startDateTime")
    @JsonDeserialize(using = JsonDateDeserializer.class) // For data decoding
    @JsonSerialize(using = JsonDateSerializer.class) // For data encoding
	public LocalDateTime startDateTime;

    @Column(name = "endDateTime")
    @JsonDeserialize(using = JsonDateDeserializer.class) // For data decoding
    @JsonSerialize(using = JsonDateSerializer.class) // For data encoding
    private LocalDateTime endDateTime;



    public Booking() { }

    public static Booking createBooking(BooklyBooking booklyBooking, Parking parking) {
        Booking booking = new Booking();
        
        booking.userId = booklyBooking.getUserId();
        booking.userFirstName = booklyBooking.getUserFirstName();
        booking.userLastName = booklyBooking.getUserLastName();
        booking.parking = parking;
        booking.startDateTime= booklyBooking.getStartDateTime();
        booking.endDateTime = booklyBooking.getEndDateTime();

        return booking;
    }
   
}