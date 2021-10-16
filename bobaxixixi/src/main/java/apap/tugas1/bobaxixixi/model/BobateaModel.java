package apap.tugas1.bobaxixixi.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
    @Column
    private String namaBobatea;

    @NotNull
    @Column
    private Integer hargaBobatea;

    @NotNull
    @Column
    private Integer sizeBobatea;

    @NotNull
    @Column
    private Integer iceLevel;

    @NotNull
    @Column
    private Integer sugarLevel;

//    @ManyToMany(mappedBy = "listBobatea")
//    List<StoreModel> listStore;
    @OneToMany(mappedBy = "bobatea", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StoreBobateaModel> listStoreBobatea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_topping", referencedColumnName = "idTopping")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ToppingModel topping;

}
