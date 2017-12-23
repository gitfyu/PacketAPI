package pw.cinque.packetapi;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class PacketValues {

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
    @SuppressWarnings("unchecked")
    public <T> T get(int i, Class<T> type) {
        return (T) values.get(i);
    }

}
