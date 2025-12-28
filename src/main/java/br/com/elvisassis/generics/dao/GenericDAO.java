package br.com.elvisassis.generics.dao;

import br.com.elvisassis.generics.domain.GenericDomain;
import br.com.elvisassis.generics.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Generic DAO implementation providing an in-memory storage.
 * This class provides a reusable base for concrete DAOs, implementing the
 * contract defined in the {@link Repository} interface.
 *
 * @param <ID> Type of the entity identifier.
 * @param <T>  Domain type, constrained to GenericDomain<ID>.
 */
public abstract class GenericDAO<ID, T extends GenericDomain<ID>> implements Repository<ID, T> {

    private final List<T> db = new ArrayList<>();

    @Override
    public T save(T domain) {
        db.add(domain);
        return domain;
    }

    @Override
    @SafeVarargs
    public final boolean saveBatch(int batchSize, T... domains) {
        System.out.println("Saving batch of " + batchSize + " items.");
        return db.addAll(List.of(domains));
    }

    @Override
    public boolean saveAll(List<? extends T> items) {
        return db.addAll(items);
    }

    @Override
    public List<T> findAll() {
        return List.copyOf(db);
    }

    @Override
    public Optional<T> find(Predicate<T> filter) {
        return db.stream().filter(filter).findFirst();
    }

    @Override
    public T update(ID id, T domain) {
        var existing = find(d -> d.getId().equals(id))
                .orElseThrow(() -> new RuntimeException("ID not found: " + id));
        db.remove(existing);
        db.add(domain);
        return domain;
    }

    @Override
    public boolean delete(T domain) {
        return db.remove(domain);
    }

    @Override
    public int count() {
        return db.size();
    }
}