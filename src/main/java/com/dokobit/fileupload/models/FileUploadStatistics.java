package com.dokobit.fileupload.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "FileUploadStatistics", indexes = {
        @Index(name = "idx_fileuploadstatistics_unq", columnList = "ipAddress", unique = true)
})
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class FileUploadStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    @NonNull
    public String ipAddress;

    public int timesAccessed = 1;

    @CreationTimestamp
    public Date createdDate;

    @UpdateTimestamp
    public Date updatedDate;
}
