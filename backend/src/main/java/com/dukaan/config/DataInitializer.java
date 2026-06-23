package com.dukaan.config;

import com.dukaan.entity.Supplier;
import com.dukaan.entity.User;
import com.dukaan.entity.enums.UserRole;
import com.dukaan.repository.SupplierRepository;
import com.dukaan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    
    private final UserRepository userRepository;
    private final SupplierRepository supplierRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) {
        initializeUsers();
        initializeSuppliers();
    }
    
    private void initializeUsers() {
        if (userRepository.count() == 0) {
            log.info("Initializing default users...");
            
            // Create admin user
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .fullName("Administrator")
                    .email("admin@dukaan.com")
                    .mobile("9999999999")
                    .role(UserRole.ADMIN)
                    .active(true)
                    .notes("Default admin user")
                    .build();
            userRepository.save(admin);
            log.info("Created admin user: admin/admin123");
            
            // Create staff user
            User staff = User.builder()
                    .username("staff")
                    .password(passwordEncoder.encode("staff123"))
                    .fullName("Staff User")
                    .email("staff@dukaan.com")
                    .mobile("8888888888")
                    .role(UserRole.STAFF)
                    .active(true)
                    .notes("Default staff user")
                    .build();
            userRepository.save(staff);
            log.info("Created staff user: staff/staff123");
            
            log.info("User initialization completed");
        }
    }
    
    private void initializeSuppliers() {
        if (supplierRepository.count() == 0) {
            log.info("Initializing default supplier...");
            
            Supplier defaultSupplier = Supplier.builder()
                    .name("Default Supplier")
                    .mobile("9999999999")
                    .email("supplier@dukaan.com")
                    .address("Default Address")
                    .gstNumber("29ABCDE1234F1Z5")
                    .outstandingBalance(BigDecimal.ZERO)
                    .notes("Default supplier for system")
                    .active(true)
                    .build();
            supplierRepository.save(defaultSupplier);
            log.info("Created default supplier");
            
            log.info("Supplier initialization completed");
        }
    }
}
