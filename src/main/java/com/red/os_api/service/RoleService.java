package com.red.os_api.service;

import com.red.os_api.entity.Category;
import com.red.os_api.entity.Role;
import com.red.os_api.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService implements AppService<Role, Integer> {

    AppRepository<Role,Integer> roleRep;

    @Autowired
    public void setRoleRep(AppRepository<Role, Integer> roleRep) {
        this.roleRep = roleRep;
    }

    @Override
    public List<Role> getAll() {
        List<Role> roleList = new ArrayList<>();
        roleRep.findAll().forEach(roleList::add);
        return roleList;
    }

    @Override
    public Role getById(Integer id) {
        return roleRep.findById(id).get();
    }

    @Override
    public Role insert(Role role) {
        return roleRep.save(role);
    }

    @Override
    public void update(Integer id, Role role) {
        Role roleNew = roleRep.findById(id).get();
        roleNew.setName(role.getName());
        roleNew.setDescription(role.getDescription());
        roleRep.save(roleNew);
    }

    @Override
    public void delete(Integer id) {
        roleRep.delete(roleRep.findById(id).get());
    }
}
