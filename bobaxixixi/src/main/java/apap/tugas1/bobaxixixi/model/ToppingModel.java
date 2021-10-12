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
@Table(name="topping")

public class ToppingModel implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idTopping;

    @NotNull
    @Size
    @Column(nullable=false)
    private String namaTopping;

    @NotNull
    @Size
    @Column(nullable=false)
    private Long hargaTopping;

    @OneToMany(mappedBy="bobatea", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<BobateaModel> listBobatea;

}
