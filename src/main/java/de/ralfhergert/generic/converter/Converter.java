package de.ralfhergert.generic.converter;

/**
 * Generic super-interface for all converters.
 *
 * @param <Source> type from which this convert can convert
 * @param <Destination> type into which this convert can convert
 */
public interface Converter<Source,Destination> {

	Destination convert(Source source);
}
