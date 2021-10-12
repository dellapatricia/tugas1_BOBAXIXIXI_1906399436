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
@Table(name="store")

public class StoreModel implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idStore;

    @NotNull
    @Size
    @Column(nullable=false)
    private String alamatStore;


    @NotNull
    @Size
    @Column(nullable=false)
    private String namaStore;

    @NotNull
    @Size(max=30)
    @Column(nullable=false)
    private String noTelp;

    @NotNull
    @Size
    @Column(nullable=false)
    private String storeCode;

    @NotNull
    @Column(nullable=false)
    @DateTimeFormat(pattern="HH:mm")
    private LocalTime openHour;

    @NotNull
    @Column(nullable=false)
    @DateTimeFormat(pattern="HH:mm")
    private LocalTime closeHour;

    //Relasi dengan ManagerModel
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_manager", referencedColumnName = "idManager", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ManagerModel manager;

    //Relasi dengan BobateaModel
    @ManyToMany
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idStoreBobatea;
    @JoinTable(
            name = "store_bobatea",
            joinColumns = @JoinColumn(name = "id_store"),
            inverseJoinColumns = @JoinColumn(name = "id_boba")
            )
    @Column(nullable=false)
    private String productionCode;
    List<BobateaModel> listBobatea;
}
