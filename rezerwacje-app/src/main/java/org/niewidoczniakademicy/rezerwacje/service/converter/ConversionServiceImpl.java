package org.niewidoczniakademicy.rezerwacje.service.converter;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.core.model.database.SystemUser;
import org.niewidoczniakademicy.rezerwacje.core.model.rest.user.AddSystemUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ConversionServiceImpl implements ConversionService {

    private final SystemUserConverter systemUserConverter;

    @Override
    public SystemUser convert(AddSystemUserRequest request) {
        return systemUserConverter.createFrom(request);
    }
}
