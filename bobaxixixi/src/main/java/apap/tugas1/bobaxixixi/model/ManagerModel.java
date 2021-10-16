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
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Entity
@Table(name="manager")

public class ManagerModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idManager;

    @NotNull
    @Size
    @Column(nullable = false)
    private String fullName;

    @NotNull
    @Column(nullable = false)
    private Integer jenisKelamin;

    @NotNull
    @Column(nullable=false)
    @DateTimeFormat(pattern= "yyyy-MM-dd")
    private Date dateBirth;

    @OneToOne(mappedBy = "manager", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private StoreModel storeModel;


}
