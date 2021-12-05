package tech.btzstudio.family.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "file")
public class File extends BaseEntity {

    public enum FileType {
        IMAGE,
        PDF,
        MISC
    }

    @Column(name = "type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private FileType type;

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }
}
