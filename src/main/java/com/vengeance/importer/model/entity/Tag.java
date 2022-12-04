package com.vengeance.importer.model.entity;

import com.vengeance.importer.constant.ModelStatus;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Builder
@Entity
@Table(name = "tag")
@Where(clause = "status = 1")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_id_gen")
    @SequenceGenerator(name = "tag_id_gen", sequenceName = "tag_id_seq", allocationSize = 1)
    @ToString.Include
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private String name;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    @Exclude
    private Collection<Song> songs;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    @Exclude
    private Collection<Album> albums;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "status")
    @Convert(converter = ModelStatus.StatusAttributeConverter.class)
    private ModelStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Tag tag = (Tag) o;
        return id != null && Objects.equals(id, tag.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
