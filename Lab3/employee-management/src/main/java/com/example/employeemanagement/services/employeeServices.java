package com.example.employeemanagement.services;

import com.example.employeemanagement.models.employee;
import jakarta.ejb.Stateless;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class employeeServices {

        private static Map<Integer, employee> empMap = new HashMap<>();
        private static int currentId = 1;

        public List<employee> getAll() {
            return new ArrayList<>(empMap.values());
        }

        public employee getById(int id) {
            return empMap.get(id);
        }

        public employee create(employee emp) {
            emp.setId(currentId++);
            empMap.put(emp.getId(), emp);
            return emp;
        }

        public employee update(int id, employee emp) {
            if (!empMap.containsKey(id)) return null;
            emp.setId(id);
            empMap.put(id, emp);
            return emp;
        }

        public boolean delete(int id) {
            return empMap.remove(id) != null;
        }
    }

