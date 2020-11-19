package br.com.i9core.auth.infrastructure.adapter.mapper;

import br.com.i9core.auth.core.domain.client.ClientApplication;
import br.com.i9core.auth.infrastructure.data.entity.ClientApplicationEntity;
import br.com.i9core.auth.shared.model.AutoMapper;

public class ClientApplicationMapper extends AutoMapper<ClientApplication, ClientApplicationEntity> {

    public static ClientApplicationMapper getInstance() {
        return new ClientApplicationMapper();
    }

}
