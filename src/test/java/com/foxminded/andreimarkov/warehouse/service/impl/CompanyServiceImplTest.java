package com.foxminded.andreimarkov.warehouse.service.impl;

import com.foxminded.andreimarkov.warehouse.dao.impl.JdbcCompanyDAOImpl;
import com.foxminded.andreimarkov.warehouse.dto.CompanyDTO;
import com.foxminded.andreimarkov.warehouse.model.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CompanyServiceImplTest {

    @Mock
    private JdbcCompanyDAOImpl companyDAO;
    private CompanyDTO companyDTO;
    private Company company;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private CompanyServiceImpl companyService;

    @BeforeEach
    void setUp() {
        companyDTO = new CompanyDTO();
        company  = new Company();
        when(mapper.map(companyDTO,Company.class)).thenReturn(company);
        when(mapper.map(company,CompanyDTO.class)).thenReturn(companyDTO);
        companyDTO.setId(20000L);
        companyDTO.setName("New Tech Company");
        companyDTO.setAddress("some street");
        companyDTO.setPhone("777-33-33");
        companyDTO.setBalance(10000);
    }

    @Test
    void save() {
        when(companyDAO.save(any())).thenReturn(new Company());
        companyService.save(companyDTO);
        verify(companyDAO, only()).save(any(Company.class));
    }

    @Test
    void findAll() {
        List<Company> all = new ArrayList<>();
        all.add(company);
        when(companyDAO.findAll()).thenReturn(all);
        companyService.findAll();
        verify(companyDAO, only()).findAll();
    }

    @Test
    void getById() {
        company.setId(10000L);
        company.setName("Comp Industry");
        company.setAddress("some street");
        company.setPhone("111-22-33");
        company.setBalance(1000);
        when(companyDAO.getById(anyLong())).thenReturn(Optional.ofNullable(company));
        Optional<CompanyDTO> actual = companyService.getById(10000L);
        assertEquals(companyDTO, actual.get());
    }

    @Test
    void update() {
        companyDTO.setName("Updated Company");
        companyService.update(companyDTO);
        verify(companyDAO,times(1)).update(company);
    }

    @Test
    void delete() {
        company.setId(20000L);
        when(companyDAO.getById(anyLong())).thenReturn(Optional.of(company));
        companyService.delete(companyDTO.getId());
        verify(companyDAO,times(1)).delete(company.getId());
    }
}