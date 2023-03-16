package merona.nabdbackend;

import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@DynamicUpdate
public abstract class BaseEntity {
    @CreatedDate
    @Column(updatable = false, name = "create_at")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "update_at")
    private LocalDateTime lastModifiedDate;
}
