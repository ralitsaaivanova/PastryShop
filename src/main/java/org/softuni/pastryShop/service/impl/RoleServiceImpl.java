package org.softuni.pastryShop.service.impl;

import org.softuni.pastryShop.model.entities.Role;
import org.softuni.pastryShop.repository.RoleRepository;
import org.softuni.pastryShop.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
