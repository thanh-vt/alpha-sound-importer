package com.vengeance.importer.model.entity;

import com.vengeance.importer.constant.ModelStatus;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "song")
@Where(clause = "status = 1")
@NamedEntityGraph(
    name = "song-artist",
    attributeNodes = {
        @NamedAttributeNode("artists")
    }
)
public class Song {

    @Transient
    private Long rn;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "song_id_gen")
    @SequenceGenerator(name = "song_id_gen", sequenceName = "song_id_seq", allocationSize = 1)
    @ToString.Include
    private Long id;

    @ToString.Include
    private String title;

    private String unaccentTitle;

    @ToString.Include
    private Date releaseDate;

    @Transient
    @ToString.Include
    private String url;

    @Builder.Default
    @ColumnDefault("0")
    private Long displayRating = 0L;

    @Builder.Default
    @Column(name = "listening_frequency")
    @ColumnDefault("0")
    private Long listeningFrequency = 0L;

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

    //    @Column(columnDefinition = "LONGTEXT")
    @Column(columnDefinition = "TEXT")
    private String lyric;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "song_artist",
        joinColumns = @JoinColumn(
            name = "song_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(
            name = "artist_id", referencedColumnName = "id"),
        uniqueConstraints = @UniqueConstraint(columnNames = {"song_id", "artist_id"}))
    @Fetch(value = FetchMode.SUBSELECT)
    private Collection<Artist> artists;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "songs")
    @Fetch(value = FetchMode.SUBSELECT)
    private Collection<Album> albums;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "song_tag",
        joinColumns = @JoinColumn(
            name = "song_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(
            name = "tag_id", referencedColumnName = "id"),
        uniqueConstraints = @UniqueConstraint(columnNames = {"song_id", "tag_id"}))
    @Fetch(value = FetchMode.SUBSELECT)
    private Collection<Tag> tags;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "song_genre",
        joinColumns = @JoinColumn(
            name = "song_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(
            name = "genre_id", referencedColumnName = "id"),
        uniqueConstraints = @UniqueConstraint(columnNames = {"song_id", "genre_id"}))
    @Fetch(value = FetchMode.SUBSELECT)
    private Collection<Genre> genres;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "theme_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Theme theme;

    @Convert(converter = DurationConverter.class)
    private Duration duration;
}
