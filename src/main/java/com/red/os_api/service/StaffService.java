package com.red.os_api.service;

import com.red.os_api.entity.Category;
import com.red.os_api.entity.Staff;
import com.red.os_api.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaffService implements AppService<Staff, Integer> {

    AppRepository<Staff,Integer> staffRep;

    @Autowired
    public void setStaffRep(AppRepository<Staff, Integer> staffRep) {
        this.staffRep = staffRep;
    }

    @Override
    public List<Staff> getAll() {
        List<Staff> staffList = new ArrayList<>();
        staffRep.findAll().forEach(staffList::add);
        return staffList;
    }

    @Override
    public Staff getById(Integer id) {
        return staffRep.findById(id).get();
    }

    @Override
    public Staff insert(Staff staff) {
        return staffRep.save(staff);
    }

    @Override
    public void update(Integer id, Staff staff) {
        Staff staffNew = staffRep.findById(id).get();
        staffNew.setName(staff.getName());
        staffNew.setEmail(staff.getEmail());
        staffNew.setJob_title(staff.getJob_title());
        staffNew.setRoles(staff.getRoles());
        staffRep.save(staffNew);
    }

    @Override
    public void delete(Integer id) {
        staffRep.delete(staffRep.findById(id).get());
    }
}
