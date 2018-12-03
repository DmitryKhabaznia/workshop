package workshop.util.converter;

/**
 * Interface that defines converter from source class to target class.
 *
 * @param <S> specified type of source class
 * @param <T> specified type of target class
 */
public interface Converter<S, T> {

    /**
     * Return instance of target class that was converted from source.
     *
     * @param source specified source
     * @return instance of target class that was converted from source
     */
    T convert(S source);

}
