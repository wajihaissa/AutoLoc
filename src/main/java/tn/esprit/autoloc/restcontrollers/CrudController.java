package tn.esprit.autoloc.restcontrollers;

import java.util.List;
import java.util.Optional;
import java.lang.reflect.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

abstract class CrudController<T, R extends JpaRepository<T, Long>> {
    private final R repository;
    CrudController(R repository) { this.repository = repository; }

    @PostMapping
    public ResponseEntity<T> create(@RequestBody T entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(entity));
    }
    @GetMapping
    public List<T> getAll() { return repository.findAll(); }
    @GetMapping("/{id}")
    public ResponseEntity<T> get(@PathVariable Long id) {
        return repository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable Long id, @RequestBody T entity) {
        Optional<T> existing = repository.findById(id);
        if (existing.isEmpty()) return ResponseEntity.notFound().build();
        try {
            for (Class<?> type = entity.getClass(); type != null; type = type.getSuperclass()) {
                for (Field field : type.getDeclaredFields()) {
                    if (field.isAnnotationPresent(jakarta.persistence.Id.class)) {
                        field.setAccessible(true);
                        field.set(entity, id);
                        return ResponseEntity.ok(repository.save(entity));
                    }
                }
            }
            throw new IllegalStateException("Entity has no @Id field: " + entity.getClass().getName());
        } catch (IllegalAccessException exception) {
            throw new IllegalStateException("Unable to set entity ID", exception);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) return ResponseEntity.notFound().build();
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
