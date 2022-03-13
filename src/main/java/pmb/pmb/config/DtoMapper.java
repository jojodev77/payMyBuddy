package pmb.pmb.config;


import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import pmb.pmb.dto.UserPartner;
import pmb.pmb.model.UserPartnerAccount;

@Component
@Mapper(uses = {DtoMapper.class} , componentModel = "spring")
public interface DtoMapper {
	DtoMapper INSTANCE = Mappers.getMapper( DtoMapper.class );
	public Set<UserPartner>  map(Set<UserPartnerAccount> serviceDto);
}
