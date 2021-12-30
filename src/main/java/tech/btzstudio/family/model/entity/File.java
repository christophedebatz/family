package tech.btzstudio.family.model.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "file")
public class File implements EntityInterface {

    public enum FileType {
        IMAGE,
        PDF,
        MISC
    }

    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    @GeneratedValue
    private UUID id;

    @Column(name = "type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private FileType type;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }
}
