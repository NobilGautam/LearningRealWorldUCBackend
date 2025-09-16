package com.forkcast.service.impl;

import com.forkcast.common.exceptions.OwnerNotFoundException;
import com.forkcast.common.exceptions.OutletNotFoundException;
import com.forkcast.dao.dto.OutletRequest;
import com.forkcast.dao.dto.OutletResponse;
import com.forkcast.dao.entities.Outlet;
import com.forkcast.dao.entities.User;
import com.forkcast.dao.mapper.OutletMapper;
import com.forkcast.dao.repository.OutletRepository;
import com.forkcast.dao.repository.UserRepository;
import com.forkcast.service.OutletService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OutletServiceImpl implements OutletService {

    @Autowired
    private OutletRepository outletRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    OutletMapper outletMapper;

    @Override
    public OutletResponse registerOutlet(OutletRequest outletRequest) {
        User owner = userRepository.findByEmail(outletRequest.getOwnerEmail())
                .orElseThrow(() -> new OwnerNotFoundException(outletRequest.getOwnerEmail()));

        Outlet outlet = outletMapper.toOutlet(outletRequest);
        outlet.setOwner(owner);

        Outlet saved = outletRepository.save(outlet);
        OutletResponse response = outletMapper.toResponse(saved);
        response.setOwnerEmail(owner.getEmail());

        return response;
    }

    @Override
    @Transactional
    public OutletResponse updateOutlet(OutletRequest outletRequest) {
        Long outletId = outletRequest.getOutletId();
        Outlet outlet = outletRepository.findById(outletId)
                .orElseThrow(() -> new OutletNotFoundException(outletId));

        outlet.setOutletName(outletRequest.getOutletName());
        outlet.setAddressLine1(outletRequest.getAddressLine1());
        outlet.setAddressLine2(outletRequest.getAddressLine2());
        outlet.setCity(outletRequest.getCity());
        outlet.setState(outletRequest.getState());
        outlet.setPinCode(outletRequest.getPinCode());

        Outlet updated = outletRepository.save(outlet);
        OutletResponse response = outletMapper.toResponse(updated);
        response.setOwnerEmail(updated.getOwner().getEmail());

        return response;
    }

    @Override
    public List<OutletResponse> getOutlet(OutletRequest outletRequest) {
        // Fetch by owner email
        User owner = userRepository.findByEmail(outletRequest.getOwnerEmail())
                .orElseThrow(() -> new OwnerNotFoundException(outletRequest.getOwnerEmail()));

        List<Outlet> outlets = outletRepository.findByOwner(owner);
        if (outlets.isEmpty()) {
            throw new OutletNotFoundException(owner.getEmail());
        }

        List<OutletResponse> outletResponses = new ArrayList<>();
        for (Outlet outlet : outlets) {
            OutletResponse response = outletMapper.toResponse(outlet);
            response.setOwnerEmail(owner.getEmail());
            outletResponses.add(response);
        }
        return outletResponses;
    }

    @Override
    @Transactional
    public OutletResponse removeOutlet(OutletRequest outletRequest) {
        Long outletId = outletRequest.getOutletId();
        Outlet outlet = outletRepository.findById(outletId)
                .orElseThrow(() -> new OutletNotFoundException(outletId));

        //todo: perform soft delete
        outletRepository.delete(outlet);
        OutletResponse response = outletMapper.toResponse(outlet);
        response.setOwnerEmail(outlet.getOwner().getEmail());
        return response;
    }

    @Override
    public List<OutletResponse> getAllOutletsForOwner(String email) {
        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new OwnerNotFoundException(email));

        List<Outlet> outlets = outletRepository.findByOwner(owner);
        if (outlets.isEmpty()) {
            throw new OutletNotFoundException(owner.getEmail());
        }

        List<OutletResponse> outletResponses = new ArrayList<>();
        for (Outlet outlet : outlets) {
            OutletResponse response = outletMapper.toResponse(outlet);
            response.setOwnerEmail(owner.getEmail());
            outletResponses.add(response);
        }
        return outletResponses;
    }
}
