package apap.tugas1.bobaxixixi.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Entity
@Table(name="bobatea")

public class BobateaModel implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idBobatea;

    @NotNull
    @Size
    @Column(nullable=false)
    private String namaBobatea;

    @NotNull
    @Size
    @Column(nullable=false)
    private Long hargaBobatea;

    @NotNull
    @Size
    @Column(nullable=false)
    private String sizeBobatea;

    @NotNull
    @Size
    @Column(nullable=false)
    private String iceLevel;

    @NotNull
    @Size
    @Column(nullable=false)
    private String sugarLevel;

    @ManyToMany(mappedBy = "listBobatea")
    List<StoreModel> listStore;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_topping", referencedColumnName = "idTopping", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ToppingModel topping;

}
