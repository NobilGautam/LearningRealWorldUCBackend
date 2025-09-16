package com.forkcast.service;

import com.forkcast.dao.dto.OutletRequest;
import com.forkcast.dao.dto.OutletResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OutletService {
    OutletResponse registerOutlet(OutletRequest outletRequest);
    OutletResponse updateOutlet(OutletRequest outletRequest);
    List<OutletResponse> getOutlet(OutletRequest outletRequest);
    OutletResponse removeOutlet(OutletRequest outletRequest);
    List<OutletResponse> getAllOutletsForOwner(String email);
}
