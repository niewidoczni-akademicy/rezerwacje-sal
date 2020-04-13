package org.niewidoczniakademicy.rezerwacje.service.converter;

import org.niewidoczniakademicy.rezerwacje.core.model.database.SystemUser;
import org.niewidoczniakademicy.rezerwacje.core.model.rest.user.AddSystemUserRequest;

public interface ConversionService {

    SystemUser convert(AddSystemUserRequest request);
}
