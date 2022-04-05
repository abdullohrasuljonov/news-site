package uz.pdp.newssite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.newssite.entity.Role;
import uz.pdp.newssite.payload.ApiResponse;
import uz.pdp.newssite.payload.RoleDto;
import uz.pdp.newssite.repository.RoleRepository;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public ApiResponse addRole(RoleDto roleDto) {
        if (roleRepository.existsByName(roleDto.getName()))
            return new ApiResponse("Role already exist!",false);
        Role role=new Role(
                roleDto.getName(),
                roleDto.getPermissions(),
                roleDto.getDescription());
        roleRepository.save(role);
        return new ApiResponse("Role has been created!",true);
    }

    public ApiResponse editRole(Long id,RoleDto roleDto) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (!optionalRole.isPresent())
            return new ApiResponse("There is no role such an id!",false);
        if (roleRepository.existsByName(roleDto.getName()))
            return new ApiResponse("Role already exist!",false);
        Role role = optionalRole.get();
        role.setName(roleDto.getName());
        role.setDescription(roleDto.getDescription());
        role.setPermissions(roleDto.getPermissions());
        roleRepository.save(role);
        return new ApiResponse("Successfully edited!",true);
    }
}
