package pw.cinque.packetapi;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class PacketValues implements Iterable {

    private final List<Object> values;

    /**
     * Gets the amount of values
     *
     * @return The amount of values for this packet
     */
    public int size() {
        return values.size();
    }

    /**
     * Gets a value
     *
     * @param i The value index
     * @return The value at the specified index
     * @throws ArrayIndexOutOfBoundsException if the index is out of range
     */
    public Object get(int i) {
        return values.get(i);
    }

    /**
     * Gets a value
     *
     * @param i    The value index
     * @param type The value type
     * @return The value at the specified index
     * @throws ArrayIndexOutOfBoundsException if the index is out of range
     * @throws ClassCastException             if the value is not assignable to the specified type
     */
    public <T> T get(int i, Class<T> type) {
        return type.cast(values.get(i));
    }

    @Override
    public Iterator iterator() {
        return new PacketValuesIterator();
    }

    public class PacketValuesIterator implements Iterator<Object> {

        private int index;

        @Override
        public boolean hasNext() {
            return index < values.size();
        }

        @Override
        public Object next() {
            return values.get(index++);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void forEachRemaining(Consumer<? super Object> action) {
            throw new UnsupportedOperationException();
        }

    }

}
