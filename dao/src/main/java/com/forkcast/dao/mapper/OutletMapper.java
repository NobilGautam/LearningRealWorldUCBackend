package com.forkcast.dao.mapper;

import com.forkcast.dao.dto.OutletRequest;
import com.forkcast.dao.dto.OutletResponse;
import com.forkcast.dao.entities.Outlet;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OutletMapper {

    OutletMapper INSTANCE = Mappers.getMapper(OutletMapper.class);

    OutletResponse toResponse(Outlet outlet);

    Outlet toOutlet(OutletRequest outletRequest);
}
