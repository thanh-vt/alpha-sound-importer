package com.vengeance.importer.model.entity;

import com.vengeance.importer.constant.ModelStatus;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "artist")
@Where(clause = "status = 1")
public class Artist {

    @Transient
    private Long rn;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "artist_id_gen")
    @SequenceGenerator(name = "artist_id_gen", sequenceName = "artist_id_seq", allocationSize = 1)
    @ToString.Include
    private Long id;

    @ToString.Include
    private String name;

    private String unaccentName;

    @ToString.Include
    private Date birthDate;

    @Transient
    private String avatarUrl;

    //    @Column(columnDefinition = "LONGTEXT")
    @Column(columnDefinition = "TEXT")
    private String biography;

    @Transient
    private Boolean liked;

    @Builder.Default
    @Column(name = "like_count")
    @ColumnDefault("0")
    private Long likeCount = 0L;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "status")
    @Convert(converter = ModelStatus.StatusAttributeConverter.class)
    private ModelStatus status;

    @Column(name = "sync")
    private Integer sync;

    //    @JsonBackReference(value = "song-artist")
    @ManyToMany(mappedBy = "artists", fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    @Exclude
    private Collection<Song> songs;

    //    @JsonBackReference(value = "album-artist")
    @ManyToMany(mappedBy = "artists", fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    @Exclude
    private Collection<Album> albums;
}
