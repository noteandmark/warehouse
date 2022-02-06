package com.foxminded.andreimarkov.warehouse.service.impl;

import com.foxminded.andreimarkov.warehouse.dao.impl.JdbcCompanyDAOImpl;
import com.foxminded.andreimarkov.warehouse.dto.CompanyDTO;
import com.foxminded.andreimarkov.warehouse.exceptions.ServiceException;
import com.foxminded.andreimarkov.warehouse.model.Company;
import com.foxminded.andreimarkov.warehouse.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
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
        log.debug("start method save CompanyDTO");
        Company company = mapper.map(companyDTO, Company.class);
        log.debug("saved company {}", company.getName());
        return mapper.map(companyDAO.save(company), CompanyDTO.class);
    }

    @Override
    public List<CompanyDTO> findAll() {
        log.debug("getting findAll in companyService");
        List<Company> all = companyDAO.findAll();
        if (all.isEmpty()) {
            log.warn("there are no any company");
            throw new ServiceException("Company is empty");
        }
        return mapListOfEntityToDTO(all);
    }

    @Override
    public Optional<CompanyDTO> getById(long id) {
        log.debug("getting company by id {}", id);
        Optional<Company> optionalCompany = companyDAO.getById(id);
        log.debug("get optional value");
        Company companyById;
        if (!optionalCompany.isPresent()) {
            log.warn("no company with id {}",id);
            throw new ServiceException("No company with this id");
        }
        companyById = optionalCompany.get();
        CompanyDTO companyDTO = mapper.map(companyById, CompanyDTO.class);
        log.debug("get companyDTO");
        return Optional.ofNullable(companyDTO);
    }

    @Override
    public CompanyDTO update(CompanyDTO companyDTO) {
        log.debug("starting update companyDTO");
        Company updated = companyDAO.update(mapper.map(companyDTO, Company.class));
        log.debug("updated companyDTO");
        return mapper.map(updated, CompanyDTO.class);
    }

    @Override
    public int delete(long id) {
        log.debug("starting delete company with id {}",id);
        return companyDAO.delete(id);
    }

    private List<CompanyDTO> mapListOfEntityToDTO(List<Company> all) {
        log.debug("start mapping List<Company> in List<CompanyDTO>");
        return all.stream().map(company -> mapper.map(company,CompanyDTO.class))
                .collect(Collectors.toList());
    }
}
