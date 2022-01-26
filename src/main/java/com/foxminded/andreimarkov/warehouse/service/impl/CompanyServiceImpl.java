package com.foxminded.andreimarkov.warehouse.service.impl;

import com.foxminded.andreimarkov.warehouse.dao.impl.JdbcCompanyDAOImpl;
import com.foxminded.andreimarkov.warehouse.dto.CompanyDTO;
import com.foxminded.andreimarkov.warehouse.model.Company;
import com.foxminded.andreimarkov.warehouse.service.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final JdbcCompanyDAOImpl companyDAO;
    private final ModelMapper mapper;

    @Autowired
    public CompanyServiceImpl(JdbcCompanyDAOImpl companyDAO, ModelMapper mapper) {
        this.companyDAO = companyDAO;
        this.mapper = mapper;
    }

    @Override
    public CompanyDTO save(CompanyDTO companyDTO) {
        Company company = mapper.map(companyDTO, Company.class);
        return mapper.map(companyDAO.save(company), CompanyDTO.class);
    }

    @Override
    public List<CompanyDTO> findAll() {
        List<Company> all = companyDAO.findAll();
        return mapListOfEntityToDTO(all);
    }

    @Override
    public Optional<CompanyDTO> getById(long id) {
        Company companyById = companyDAO.getById(id).get();
        CompanyDTO companyDTO = mapper.map(companyById, CompanyDTO.class);
        return Optional.ofNullable(companyDTO);
    }

    @Override
    public CompanyDTO update(CompanyDTO companyDTO) {
        Company updated = companyDAO.update(mapper.map(companyDTO, Company.class));
        return mapper.map(updated, CompanyDTO.class);
    }

    @Override
    public int delete(long id) {
        return companyDAO.delete(id);
    }

    private List<CompanyDTO> mapListOfEntityToDTO(List<Company> all) {
        return all.stream().map(company -> mapper.map(company,CompanyDTO.class))
                .collect(Collectors.toList());
    }
}
