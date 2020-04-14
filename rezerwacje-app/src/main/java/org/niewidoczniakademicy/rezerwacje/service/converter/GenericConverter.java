package org.niewidoczniakademicy.rezerwacje.service.converter;

public interface GenericConverter<From, To> {

    To createFrom(From dto);
}
