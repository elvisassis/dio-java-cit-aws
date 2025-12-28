package br.com.elvisassis.generics.repository;

import br.com.elvisassis.generics.domain.GenericDomain;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Defines the contract for a generic Data Access Object.
 * This interface promotes loose coupling and programming to an interface.
 *
 * @param <ID> The type of the entity's identifier.
 * @param <T>  The type of the domain entity, which must extend GenericDomain.
 */
public interface Repository<ID, T extends GenericDomain<ID>> {

    T save(T domain);

    boolean saveBatch(int batchSize, T... domains);

    boolean saveAll(List<? extends T> items);

    List<T> findAll();

    Optional<T> find(Predicate<T> filter);

    T update(ID id, T domain);

    boolean delete(T domain);

    int count();

    /**
     * Static generic method to print the IDs of any collection of items
     * that adhere to the GenericDomain contract.
     * Note how the method declares its own generic types.
     */
    static <I, D extends GenericDomain<I>> void printIds(List<D> items) {
        System.out.println("--- Printing IDs ---");
        items.forEach(item -> System.out.println(item.getId()));
        System.out.println("--------------------");
    }

    /**
     * Static method demonstrating a lower-bounded wildcard (? super).
     * This method can add Integers to a List<Integer>, List<Number>, or List<Object>.
     * It follows the "Consumer Super" principle.
     *
     * @param target The list to which integers will be added (the consumer).
     * @return The same list that was passed as an argument.
     */
    static List<? super Integer> addIntegers(List<? super Integer> target) {
        target.add(1);
        target.add(2);
        target.add(3);
        return target;
    }

    public static <T> List<? super T> copy(
            List<? extends T> source,
            List<? super T> destination
    ) {
        for (T item : source) {
            destination.add(item);
        }
        return destination;
    }
}